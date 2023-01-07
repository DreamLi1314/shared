package org.example.yiji.dolphin.constant;

/**
 * @author Jack Li
 * @date 2022/3/30 10:53 下午
 * @description
 */
public interface SystemConstant {

   String FLAG = "__^^__";

   /**
    * 施工窗口期要素 KEY
    */
   String WINDOW_PERIOD_KEY = "summary";

   /**
    * 台风要素 KEY
    */
   String TYPHOON_ELEM_KEY = "typhoon";

   /**
    * 当前登录用户在 Session 中的 key
    */
   String USER_SESSION_KEY = "user";

   /**
    * 超级管理员角色
    */
   String ROLE_ROOT_NAME = "超级管理员";

   /**
    * 管理员角色
    */
   String ROLE_ADMIN_NAME = "管理员";

   /**
    * 台风短信通知模板 id
    */
   String TYPHOON_SMS_TEMPLATE = "typhoon";
   /**
    * 雷电短信通知模板 id
    */
   String THUNDER_SMS_TEMPLATE = "thunder";
   /**
    * 短临台风短信通知模板 id
    */
   String STORM_WIND_SMS_TEMPLATE = "stormWind";

   /**
    * 小程序无法传入 Cookie 请求头, 自定义兼容 {@code Cookie} 及 {@link COOKIE_HEADER_KEY}
    */
   String COOKIE_HEADER_KEY = "cookiekey";
}
