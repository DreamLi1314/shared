package com.geoviswtx.controller;

import com.geoviswtx.dto.TmeCallbackDto;
import com.geoviswtx.service.TmeProcessService;
import com.geoviswtx.vo.TestTmeGameVo;
import com.geoviswtx.vo.TmeGameVo;
import com.geoviswtx.vo.TmeTestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试 Api")
@RestController
@Slf4j
public class TestController {

    private final TmeProcessService processService;

    public TestController(TmeProcessService processService) {
        this.processService = processService;
    }

    @ApiOperation("测试点赞")
    @PostMapping("/tme/game/test/like")
    public TmeTestResponse testLike(@RequestBody TmeGameVo tmeGameVo) throws Exception {
        return processService.testLike(tmeGameVo);
    }

    @ApiOperation("测试弹幕")
    @PostMapping("/tme/game/test/comment")
    public TmeTestResponse testComment(@RequestBody TestTmeGameVo tmeGameVo) throws Exception {
        return processService.testComment(tmeGameVo);
    }

    @ApiOperation("测试礼物")
    @PostMapping("/tme/game/test/gift")
    public TmeTestResponse testGift(@RequestBody TestTmeGameVo tmeGameVo) throws Exception {
        return processService.testGift(tmeGameVo);
    }

}
