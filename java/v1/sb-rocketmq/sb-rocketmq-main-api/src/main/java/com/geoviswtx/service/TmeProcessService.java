package com.geoviswtx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoviswtx.common.Tool;
import com.geoviswtx.dto.TmeCallbackDto;
import com.geoviswtx.properties.TmeConfProperties;
import com.geoviswtx.utils.HttpPostUtils;
import com.geoviswtx.utils.SignatureUtils;
import com.geoviswtx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TmeProcessService {

    private final ObjectMapper objectMapper;
    private final TmeConfProperties confProperties;
    private final RedisTemplate<String, Serializable> redisTemplate;

    private static final String SIGNATURE_HEADER = "x-tme-signature";
    private static final String CODE_REDIS_PREFIX = "tme-code-";
    private static final String TOKEN_REDIS_PREFIX = "tme-token-";

    public TmeProcessService(ObjectMapper objectMapper,
                             TmeConfProperties confProperties,
                             RedisTemplate<String, Serializable> redisTemplate)
    {
        this.objectMapper = objectMapper;
        this.confProperties = confProperties;
        this.redisTemplate = redisTemplate;
    }

    public TmePingResponse ping(TmeGameVo gameVo) throws Exception {
        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/access/ping");

        TmeConnectItem connectResponse = connectAndGetToken(gameVo);
        Map<String, String> header = getHeaderParamsMap(connectResponse.getToken());

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

        TmeCodeItem tmeCodeItem = queryCode(gameVo);
        gameVo.setCode(tmeCodeItem.getCode());
        TmePingResponse response = HttpPostUtils.post(url, header, gameVo, TmePingResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    public TmePingResponse disConnect(TmeGameVo gameVo) throws Exception {
        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/access/disconnect");

        TmeCodeItem tmeCodeItem = queryCode(gameVo);
        gameVo.setCode(tmeCodeItem.getCode());

        TmeConnectItem connectResponse = connectAndGetToken(gameVo);
        Map<String, String> header = getHeaderParamsMap(connectResponse.getToken());

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

        TmePingResponse response = HttpPostUtils.post(url, header, gameVo, TmePingResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    /**
     * 多次调用返回同一 token
     * @param gameVo    vo
     * @return          TmeConnectItem
     */
    public TmeConnectItem connectAndGetToken(TmeGameVo gameVo) throws Exception {
        TmeConnectItem tmeConnectItem = (TmeConnectItem) redisTemplate.opsForValue().get(
                TOKEN_REDIS_PREFIX + gameVo.toString());

        if(tmeConnectItem != null) {
            return tmeConnectItem;
        }

        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/access/connect");

        TmeCodeItem tmeCodeItem = queryCode(gameVo);
        gameVo.setCode(tmeCodeItem.getCode());
        Map<String, String> header = getHeaderParamsMap(null);

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

        TmeConnectResponse response = HttpPostUtils.post(url, header, gameVo, TmeConnectResponse.class);

        log.info("Get response: {}", response);

        if(response.getErrorCode().equals(0)) {
            tmeConnectItem = response.getData();
            redisTemplate.opsForValue().set(
                    TOKEN_REDIS_PREFIX + gameVo, tmeConnectItem, 10, TimeUnit.MINUTES);
        }

        return tmeConnectItem;
    }

    public TmeCodeItem queryCode(TmeGameVo gameVo) throws Exception {
        TmeCodeItem tmeCodeItem = (TmeCodeItem) redisTemplate.opsForValue().get(
                CODE_REDIS_PREFIX + gameVo.toString());

        if(tmeCodeItem != null) {
            return tmeCodeItem;
        }

        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/mock/get_code");

        Map<String, String> header = getHeaderParamsMap(null);

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

        TmeGetCodeResponse response = HttpPostUtils.post(url, header, gameVo, TmeGetCodeResponse.class);

        log.info("Get response: {}", response);

        if(response.getErrorCode().equals(0)) {
            tmeCodeItem = response.getData();
            redisTemplate.opsForValue().set(CODE_REDIS_PREFIX + gameVo,
                    tmeCodeItem, 29, TimeUnit.MINUTES);
        }

        return tmeCodeItem;
    }

    public Map<String, String> getHeaderParamsMap(String token) {
        long unixTimestamp = Instant.now().getEpochSecond();
        System.out.println(unixTimestamp);

        Map<String, String> header = new HashMap<>();
        header.put("x-tme-appid", confProperties.getId());
        header.put("x-tme-timestamp", String.valueOf(unixTimestamp));
        header.put("x-tme-signature-method", "HMAC-SHA256");
        header.put("x-tme-signature-version", "1.0");

        if(token != null) {
            header.put("x-tme-token", token);
        }

        return header;
    }

    /**
     * 测试点赞
     * @param gameVo vo
     * @return dto
     */
    public TmeTestResponse testLike(TmeGameVo gameVo) throws Exception {
        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/mock/like");

        TmeConnectItem connectResponse = connectAndGetToken(gameVo);
        Map<String, String> header = getHeaderParamsMap(connectResponse.getToken());

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

//        TmeCodeItem tmeCodeItem = queryCode(gameVo);
//        gameVo.setCode(tmeCodeItem.getCode());
        TmeTestResponse response = HttpPostUtils.post(url, header, gameVo, TmeTestResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    /**
     * 测试弹幕消息
     * @param gameVo vo
     * @return dto
     */
    public TmeTestResponse testComment(TestTmeGameVo gameVo) throws Exception {
        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/mock/comment");

        TmeConnectItem connectResponse = connectAndGetToken(gameVo);
        Map<String, String> header = getHeaderParamsMap(connectResponse.getToken());

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

//        TmeCodeItem tmeCodeItem = queryCode(gameVo);
//        gameVo.setCode(tmeCodeItem.getCode());
        TmeTestResponse response = HttpPostUtils.post(url, header, gameVo, TmeTestResponse.class);

        log.info("Get response: {}", response);

        return response;
    }

    /**
     * 测试礼物消息
     * @param gameVo vo
     * @return dto
     */
    public TmeTestResponse testGift(TestTmeGameVo gameVo) throws Exception {
        String url = Tool.appendPath(confProperties.getBasePath(), "/api/msg_open/mock/send_gift");

        TmeConnectItem connectResponse = connectAndGetToken(gameVo);
        Map<String, String> header = getHeaderParamsMap(connectResponse.getToken());

        String signature = SignatureUtils.generateSignature(header, confProperties.getSecret());
        header.put(SIGNATURE_HEADER, signature);

//        TmeCodeItem tmeCodeItem = queryCode(gameVo);
//        gameVo.setCode(tmeCodeItem.getCode());
        TmeTestResponse response = HttpPostUtils.post(url, header, gameVo, TmeTestResponse.class);

        log.info("Get response: {}", response);

        return response;
    }
}
