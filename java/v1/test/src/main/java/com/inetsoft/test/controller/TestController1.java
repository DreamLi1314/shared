package com.inetsoft.test.controller;/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class TestController1 {

   @GetMapping("/callback")
   public String callback(HttpServletRequest request) {
      Map<String, String[]> parameterMap = request.getParameterMap();

      StringJoiner sj = new StringJoiner(",");

      parameterMap.forEach((l, u) -> {
         System.out.println("======l===" + l + "======u===" + Arrays.asList(u));
         sj.add(l + " = " + Arrays.asList(u));
      });

      return sj.toString();
   }

}
