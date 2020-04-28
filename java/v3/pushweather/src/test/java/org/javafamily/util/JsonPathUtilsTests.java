package org.javafamily.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPathUtilsTests {

   @Test
   public void testParsePath() {
      String jsonPath = "$.reason";
      String reason = JsonPathUtils.parsePath(JSON, jsonPath);

      Assertions.assertTrue("Succes".equals(reason), "JSON Parse Error: " + jsonPath);
   }

   private static final String JSON = "{\"result\":{\"realtime\":{\"wind\":{\"windspeed\":\"\",\"direct\":\"西风\",\"power\":\"5级\",\"offset\":\"\"},\"time\":\"18:00:00\",\"weather\":{\"humidity\":\"8\",\"img\":\"00\",\"info\":\"晴\",\"temperature\":\"30\"},\"dataUptime\":\"1587895524\",\"date\":\"2020-04-26\",\"city_code\":\"101110101\",\"city_name\":\"西安\",\"week\":\"0\",\"moon\":\"四月初四\"},\"life\":{\"date\":\"2020-04-26\",\"info\":{\"kongtiao\":[\"部分时间开启\",\"天气热，到中午的时候您将会感到有点热，因此建议在午后较热时开启制冷空调。\"],\"yundong\":[\"较适宜\",\"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。\"],\"ziwaixian\":[\"很强\",\"紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。\"],\"ganmao\":[\"少发\",\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"],\"xiche\":[\"较适宜\",\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"],\"wuran\":null,\"chuanyi\":[\"热\",\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\"]}},\"weather\":[{\"date\":\"2020-04-26\",\"week\":\"日\",\"nongli\":\"四月初四\",\"info\":{\"dawn\":[\"0\",\"晴\",\"11\",\"西南风\",\"微风\",\"19:23\"],\"day\":[\"0\",\"晴\",\"30\",\"西南风\",\"3-5级\",\"06:01\"],\"night\":[\"0\",\"晴\",\"15\",\"西南风\",\"3-5级\",\"19:24\"]}},{\"date\":\"2020-04-27\",\"week\":\"一\",\"nongli\":\"四月初五\",\"info\":{\"dawn\":[\"0\",\"晴\",\"15\",\"西南风\",\"3-5级\",\"19:24\"],\"day\":[\"0\",\"晴\",\"28\",\"东北风\",\"微风\",\"06:00\"],\"night\":[\"0\",\"晴\",\"16\",\"东南风\",\"微风\",\"19:25\"]}},{\"date\":\"2020-04-28\",\"week\":\"二\",\"nongli\":\"四月初六\",\"info\":{\"dawn\":[\"0\",\"晴\",\"16\",\"东南风\",\"微风\",\"19:25\"],\"day\":[\"1\",\"多云\",\"30\",\"东南风\",\"3-5级\",\"05:59\"],\"night\":[\"0\",\"晴\",\"15\",\"东南风\",\"微风\",\"19:26\"]}},{\"date\":\"2020-04-29\",\"week\":\"三\",\"nongli\":\"四月初七\",\"info\":{\"dawn\":[\"0\",\"晴\",\"15\",\"东南风\",\"微风\",\"19:26\"],\"day\":[\"1\",\"多云\",\"31\",\"东南风\",\"微风\",\"05:58\"],\"night\":[\"1\",\"多云\",\"15\",\"南风\",\"微风\",\"19:27\"]}},{\"date\":\"2020-04-30\",\"week\":\"四\",\"nongli\":\"四月初八\",\"info\":{\"dawn\":[\"1\",\"多云\",\"15\",\"南风\",\"微风\",\"19:27\"],\"day\":[\"1\",\"多云\",\"34\",\"东南风\",\"微风\",\"05:57\"],\"night\":[\"1\",\"多云\",\"15\",\"南风\",\"微风\",\"19:27\"]}}],\"pm25\":{\"key\":\"西安\",\"show_desc\":null,\"pm25\":{\"curPm\":\"56\",\"pm25\":\"19\",\"pm10\":\"61\",\"level\":\"2\",\"quality\":\"良\",\"des\":\"空气良好，可以正常参加户外活动。\"},\"dateTime\":\"2020年04月26日17时\",\"cityName\":\"西安\"},\"isForeign\":0},\"error_code\":0,\"reason\":\"Succes\"}";

   private static final Logger LOGGER = LoggerFactory.getLogger(JsonPathUtilsTests.class);
}
