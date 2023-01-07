package com.mlog.test01.hj7;

import java.io.InputStream;

public class Main2 {
   public static void main(String[] args) throws Exception {
      InputStream in = System.in;
      int c = in.read();
      int ans=0;
      int temp=0;

      while(c != '\n'){
         // 有小数点
         if(temp==1){
            if(c > 52){
               ans+=1;
            }

            // 求的整数, 因此只需要判断小数点后第一位
            System.out.println(ans);
            break;
         }
         else {
            if(c=='.'){
               temp=1;
            }else{
               ans=ans*10+(int) (c-48);
            }
         }

         c = (char) in.read();
      }

      // 处理整数
      if(temp==0){
         System.out.println(ans);
      }
   }
}
