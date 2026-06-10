package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.utils.Lib;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RateLimitService extends DB {

    private final RedisAdvancedClusterCommands<String, String> commands;
    private final ApiAccessService service = new ApiAccessService();
    private static final String LUA_SCRIPT =
                    "if redis.call('GET', KEYS[2]) then return 0 end " +
                    "local current = redis.call('INCR', KEYS[1]) " +
                    "if current == 1 then " +
                    "   redis.call('EXPIRE', KEYS[1], ARGV[2]) " +
                    "end " +
                    "if current > tonumber(ARGV[1]) then " +
                    "   redis.call('SET', KEYS[2], 1, 'EX', ARGV[2], 'NX') " +
                    "   return 0 " +
                    "end " +
                    "return 1";

    public RateLimitService(@Autowired(required = false) RedisAdvancedClusterCommands<String, String> commands) {
        this.commands = commands;
    }

    public boolean allowRequest(String key, String route, String method) {
        try {
            if (commands == null) {
                log.info("RateLimitService.allowRequest commands is null");
                return true;
            }

            String tag = "{" + key + "}";
            String userInfoKey = "user_info:" + tag;
            String lockKey = "user_info_lock:" + tag;

            String limitStr = commands.hget(userInfoKey, "rate_limit");
            log.info("RateLimitService.allowRequest limitStr before = " + limitStr);
            if (Lib.isBlank(limitStr)) {
                String lockResult = commands.set(lockKey, "1", SetArgs.Builder.nx().ex(5));
                limitStr = "10";
                if ("OK".equals(lockResult)) {
                    ApiAccessEntity entity = service.getByApiKey(key);

                    if (entity != null) {
                        limitStr = String.valueOf(entity.getRate_limit_per_min());

                        commands.hset(userInfoKey, "rate_limit", limitStr);
                        commands.expire(userInfoKey, 900);
                    }
                    commands.del(lockKey);
                } else {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(20);
                        limitStr = commands.hget(userInfoKey, "rate_limit");
                        if (!Lib.isBlank(limitStr)) break;
                    }

                    if (Lib.isBlank(limitStr)) {
                        limitStr = "10"; // fallback
                    }
                }
            }
            log.info("RateLimitService.allowRequest limitStr after = " + limitStr);

            long now = System.currentTimeMillis();
            long currentSecond = (now / 1000) % 60;
            long ttl = (currentSecond == 0) ? 60 : 60 - currentSecond;
            long minute = now / 60000;
            String userKey = "rate_limit_per_min:" + tag + ":" + minute;
            String blockKey = "rate_limit_block:" + tag;

            Long result = commands.eval(
                    LUA_SCRIPT,
                    ScriptOutputType.INTEGER,
                    new String[]{userKey, blockKey},
                    limitStr,
                    String.valueOf(ttl)
            );

            log.info("RateLimitService.allowRequest result = " + result);

            if (result == null || result == 0) {
                return false;
            }

        } catch (Exception ex) {
            log.error("RateLimitService.allowRequest", ex);
        }
        return true;
    }
}
