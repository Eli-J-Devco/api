package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.utils.Lib;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimitService extends DB {

    private final RedisAdvancedClusterCommands<String, String> commands;

    private static final String LUA_SCRIPT =
            "redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, ARGV[1]) " +
                    "local count = redis.call('ZCARD', KEYS[1]) " +
                    "if count >= tonumber(ARGV[2]) then return 0 end " +
                    "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[3]) " +
                    "redis.call('EXPIRE', KEYS[1], 70) " +
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

            ApiAccessService service = new ApiAccessService();

            String userInfoKey = "user_info:" + key;
            String userKey = "rate_limit_per_min:" + key;

            String limitStr = "0";
            Map<String, String> userInfo = commands.hgetall(userInfoKey);
            log.info("RateLimitService.allowRequest userInfo begin");
            log.info(userInfo);
            log.info("RateLimitService.allowRequest userInfo end");

            if (userInfo == null || userInfo.isEmpty()) {
                ApiAccessEntity entity = service.getByApiKey(key);

                if (entity != null) {
                    limitStr = String.valueOf(entity.getRate_limit_per_min());

                    commands.hset(userInfoKey, "status", String.valueOf(entity.getStatus()));
                    commands.hset(userInfoKey, "rate_limit", limitStr);
                    commands.expire(userInfoKey, 120);
                }
            } else {
                limitStr = userInfo.get("rate_limit");
            }



            log.info("RateLimitService.allowRequest limitStr before = " + limitStr);

            long now = System.currentTimeMillis();
            long windowStart = now - 60000;

            if (Lib.isBlank(limitStr)) {
                ApiAccessEntity entity = service.getByApiKey(key);
                if (entity != null) {
                    limitStr = String.valueOf(entity.getRate_limit_per_min());
                } else {
                    limitStr = "10";
                }
            }

            log.info("RateLimitService.allowRequest limitStr after = " + limitStr);

            Long result = commands.eval(
                    LUA_SCRIPT,
                    ScriptOutputType.INTEGER,
                    new String[]{userKey},
                    String.valueOf(windowStart),
                    limitStr,
                    String.valueOf(now)
            );

            if (result == null || result == 0) {
                // update DB
                Map<String, Object> params = new HashMap<>();
                params.put("security_key", key);
                params.put("status", 2);
                update("ApiAccess.updateConfig", params);

                commands.hset(userInfoKey, "status", "2");

                return false;
            }

        } catch (Exception ex) {
            log.error("RateLimitService.allowRequest", ex);
        }
        return true;
    }
}
