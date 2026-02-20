
export namespace DateUtils {
   /**
    * 获取指定日期的周的第一天、月的第一天、季的第一天、年的第一天
    * @param date new Date()形式，或是自定义参数的new Date()
    * @returns 返回值为格式化的日期，yy/mm/dd
    */
   //日期格式化，返回值形式为 yy/mm/dd
   export function dateFormat(date: Date): string {
      if (!date) {
         console.log("参数异常，请检查...");
      }

      var y = date.getFullYear(); //年
      var m = date.getMonth() + 1; //月
      var d = date.getDate(); //日

      return y + "/" + m + "/" + d + " "
         + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
   }

   //获取这周的周一
   export function getFirstDayOfWeek (date: Date): string {
      var weekday = date.getDay()||7; //获取星期几,getDay()返回值是 0（周日） 到 6（周六） 之间的一个整数。0||7为7，即weekday的值为1-7

      date.setDate(date.getDate()-weekday+1);//往前算（weekday-1）天，年份、月份会自动变化

      return dateFormat(date);
   }

   //获取当月第一天
   export function getFirstDayOfMonth(date: Date): string {
      date.setDate(1);
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);

      return dateFormat(date);
   }

   //获取上月第一天
   export function getFirstDayOfPreviousMonth(date: Date): string {
      date.setMonth(date.getMonth() - 1, 1);

      return getFirstDayOfMonth(date);
   }

   //获取当季第一天
   export function getFirstDayOfQuarter(date: Date): string {
      var month = date.getMonth();
      if(month <3 ){
         date.setMonth(0);
      }else if(2 < month && month < 6){
         date.setMonth(3);
      }else if(5 < month && month < 9){
         date.setMonth(6);
      }else if(8 < month && month < 11){
         date.setMonth(9);
      }
      date.setDate(1);
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);

      return dateFormat(date);
   }

   //获取当年第一天
   export function getFirstDayOfYear (date: Date): string {
      date.setDate(1);
      date.setMonth(0);
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);

      return dateFormat(date);
   }
}
