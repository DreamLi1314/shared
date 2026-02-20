package com.geoviswtx.service;

import com.geoviswtx.common.Tool;
import com.geoviswtx.common.reflect.ReflectUtils;
import com.geoviswtx.dto.AppGameDto;
import com.geoviswtx.dto.GiftTopAppGameDto;
import com.geoviswtx.properties.AppConfProperties;
import com.geoviswtx.utils.HttpPostUtils;
import com.geoviswtx.utils.SignatureUtils;
import com.geoviswtx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AppProcessService {

    private final AppConfProperties confProperties;
    private final RedisTemplate<String, Serializable> redisTemplate;

    private static final String CODE_REDIS_PREFIX = "ksh-code-";
    private static final String TOKEN_REDIS = "access-token";

    public AppProcessService(AppConfProperties confProperties,
                             RedisTemplate<String, Serializable> redisTemplate)
    {
        this.confProperties = confProperties;
        this.redisTemplate = redisTemplate;
    }

    public AppPingResponse ping(AppGameDto gameVo) throws Exception {
        String url = fixRequestUrl(Tool.appendPath(confProperties.getBasePath(),
                "/openapi/developer/live/data/task/status"));

        Map<String, String> params = getSignMap(gameVo);
        String signature = SignatureUtils.generateSignature(params, confProperties.getSecret());
        gameVo.setSign(signature);

        AppPingResponse response = HttpPostUtils.post(url, null, gameVo, AppPingResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    public AppResponse disConnect(AppGameDto gameVo) throws Exception {
        String url = fixRequestUrl(Tool.appendPath(confProperties.getBasePath(),
                "/openapi/developer/live/data/task/stop"));

        Map<String, String> params = getSignMap(gameVo);
        String signature = SignatureUtils.generateSignature(params, confProperties.getSecret());
        gameVo.setSign(signature);

        AppResponse response = HttpPostUtils.post(url, null, gameVo, AppResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    /**
     * 多次调用返回同一 token
     * @param gameVo    vo
     * @return          TmeConnectItem
     */
    public AppConnectResponse connect(AppGameDto gameVo) throws Exception {
        String url = fixRequestUrl(Tool.appendPath(confProperties.getBasePath(),
                "/openapi/developer/live/data/task/start"));

        String roundId = gameVo.getRoundId();

        if(StringUtils.isEmpty(roundId)) {
            roundId = SnowFlakeService.getBean().nextIdStr();
            gameVo.setRoundId(roundId);
        }

        Map<String, String> params = getSignMap(gameVo);
        String signature = SignatureUtils.generateSignature(params, confProperties.getSecret());
        gameVo.setSign(signature);

        AppConnectResponse response = HttpPostUtils.post(url, null, gameVo, AppConnectResponse.class);

        log.info("Get response: {}", response);

        // 第一次连接的时候返回 roundId
        response.setGameId(roundId);

        return response;
    }

    public AppResponse giftTop(GiftTopAppGameDto gameVo) throws Exception {
        String url = fixRequestUrl(Tool.appendPath(confProperties.getBasePath(),
                "/openapi/developer/live/interactive/gift/top"));

        log.info("gameVo: {}", gameVo);

        Map<String, String> params = getSignMap(gameVo);

        log.info("getSignMap: {}", params);

        String signature = SignatureUtils.generateSignature(params, confProperties.getSecret());
        gameVo.setSign(signature);

        log.info("post body: {}", gameVo);

        AppResponse response = HttpPostUtils.post(url, null, gameVo, AppResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    private String fixRequestUrl(String url) throws Exception {
        AccessTokenResponse accessToken = getAccessToken();
        return url + "?app_id=" + confProperties.getId() + "&access_token=" + accessToken.getAccessToken();
    }

    public AccessTokenResponse getAccessToken() throws Exception {
        AccessTokenResponse accessTokenResponse = (AccessTokenResponse) redisTemplate.opsForValue().get(
                CODE_REDIS_PREFIX + TOKEN_REDIS);

        if(accessTokenResponse != null) {
            return accessTokenResponse;
        }

        String url = Tool.appendPath(confProperties.getBasePath(), "/oauth2/access_token");

        Map<String, String> form = new HashMap<>();
        form.put("app_id", confProperties.getId());
        form.put("app_secret", confProperties.getSecret());
        form.put("grant_type", "client_credentials");

        accessTokenResponse = HttpPostUtils.postForm(url, form, AccessTokenResponse.class);

        log.info("Get response: {}", accessTokenResponse);

        if(accessTokenResponse.getResult().equals(1)) {
            redisTemplate.opsForValue().set(CODE_REDIS_PREFIX + TOKEN_REDIS,
                    accessTokenResponse, 1, TimeUnit.HOURS);
        }

        return accessTokenResponse;
    }

    public Map<String, String> getHeaderParamsMap(String token) {
        long unixTimestamp = Instant.now().getEpochSecond();
        System.out.println(unixTimestamp);

        Map<String, String> header = new HashMap<>();
        header.put("app_id", confProperties.getId());

        if(token != null) {
            header.put("access_token", token);
        }

        return header;
    }

    public Map<String, String> getSignMap(Object obj) throws Exception {
        Map<String, String> params = new TreeMap<>();
        params.put("app_id", confProperties.getId());

        List<Field> fields = ReflectUtils.getAllFields(obj.getClass());

        for (Field field : fields) {
            if(field.getName().equals("sign") || field.getName().equals("access_token")) {
                continue;
            }

            field.setAccessible(true);

            Object val = field.get(obj);

            if(val != null) {
                params.put(field.getName(), Tool.toString(val));
            }
        }

        return params;
    }

}
