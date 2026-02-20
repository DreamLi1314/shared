package com.geoviswtx.controller;

import com.geoviswtx.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Websocket Apis")
@RestController
public class GameController {

    @ApiOperation("建立连接")
    @PostMapping("/game/connect")
    public AppConnectResponse start(@RequestBody AppGameVo tmeGameVo) throws Exception {

        return null;
    }

}
