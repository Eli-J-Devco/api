package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.entities.ApiEndPointEntity;
import com.nwm.api.utils.Lib;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimitService extends DB {

    private final StringRedisTemplate redisTemplate;

    private static final String LUA_SCRIPT =
            "redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, ARGV[1]) " +
                    "local count = redis.call('ZCARD', KEYS[1]) " +
                    "if count >= tonumber(ARGV[2]) then return 0 end " +
                    "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[3]) " +
                    "redis.call('EXPIRE', KEYS[1], 70) " +
                    "return 1";

    public RateLimitService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(String key, String route, String method) {
        try {
            ApiAccessService service = new ApiAccessService();
//            String endpointKey = "endpoint:list";
//            String endpoint = method.toUpperCase() + ":" + route;
//
//            // check redis exist all end point store from db
//            Long size = redisTemplate.opsForSet().size(endpointKey);
//            if (size == null || size == 0) {
//                // if not exist, query db get all list API and save redis
//                List<ApiEndPointEntity> list = queryForList("ApiEndPoint.getList");
//
//                if (list != null && !list.isEmpty()) {
//                    for (ApiEndPointEntity e : list) {
//                        String ep = e.getMethod().toUpperCase() + ":" + e.getRoute();
//                        redisTemplate.opsForSet().add(endpointKey, ep);
//                    }
//                    redisTemplate.expire(endpointKey, 2, TimeUnit.MINUTES);
//                }
//            }
//            Boolean exists = redisTemplate.opsForSet().isMember(endpointKey, endpoint);
//
//            if (exists == null || !exists) {
//                // if api not in database => normal API, no need check limit
//                return true;
//            }

            // check endpoint is belong to user
//            String userEndpointKey = "user:endpoints:" + key;
//            Boolean hasUserCache = redisTemplate.hasKey(userEndpointKey);
//            if (!hasUserCache) {
//                // load endpoint theo user từ DB
//                Map<String, String> params = new HashMap<>();
//                params.put("key", key);
//                List<ApiEndPointEntity> userEndpoints = queryForList("ApiEndPoint.listAccessOfUser", params);
//
//                if (userEndpoints != null && !userEndpoints.isEmpty()) {
//                    for (ApiEndPointEntity e : userEndpoints) {
//                        String ep = e.getMethod().toUpperCase() + ":" + e.getRoute();
//                        redisTemplate.opsForSet().add(userEndpointKey, ep);
//                    }
//                }
//
//                redisTemplate.expire(userEndpointKey, 2, TimeUnit.MINUTES);
//            }
//
//            Boolean userHasEndpoint = redisTemplate.opsForSet().isMember(userEndpointKey, endpoint);
//
//            if (userHasEndpoint == null || !userHasEndpoint) {
//                // user can not access this end point => return true for validate in controller
//                return true;
//            }

            String userInfoKey = "user_info:" + key;
            String userKey = "rate_limit_per_min:" + key;

            String limitStr = "0";

            Map<Object, Object> userInfo = redisTemplate.opsForHash().entries(userInfoKey);

            if (userInfo == null || userInfo.isEmpty()) {
                ApiAccessEntity entity = service.getByApiKey(key);
                if (entity != null) {
                    limitStr = String.valueOf(entity.getRate_limit_per_min());

                    redisTemplate.opsForHash().put(userInfoKey, "status", String.valueOf(entity.getStatus()));
                    redisTemplate.opsForHash().put(userInfoKey, "rate_limit", limitStr);
                    redisTemplate.expire(userInfoKey, 2, TimeUnit.MINUTES);
                }
            } else {
                limitStr = (String) userInfo.get("rate_limit");
            }

//            if ("2".equals((String) userInfo.get("status"))) {
//                return true;
//            }

            long now = System.currentTimeMillis();
            long windowStart = now - 60000;

            Long result = redisTemplate.execute(
                    new DefaultRedisScript<>(LUA_SCRIPT, Long.class),
                    Collections.singletonList(userKey),
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

                redisTemplate.opsForHash().put(userInfoKey, "status", "2");

                return false;
            }


        } catch (Exception ex) {
            log.error("RateLimitService.allowRequest", ex);
        }
        return true;
    }
}
