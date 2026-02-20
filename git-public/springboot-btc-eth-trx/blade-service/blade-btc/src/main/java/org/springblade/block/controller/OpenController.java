/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.block.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springblade.block.constant.ChainType;
import org.springblade.block.service.IBlockChainService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Demo控制器
 *
 * @author Chill
 */
@RestController
@RequestMapping("block/open")
@Api(value = "开放接口", tags = "开放接口")
public class OpenController {

	@Autowired
	private IBlockChainService blockChainService;

	@ApiOperationSupport(order = 1)
	@GetMapping({"/getTest"})
	@ApiOperation(value = "测试", notes = "测试")
	public R getTest(String param) {

		String blockHeight = blockChainService.getBlockHeight(ChainType.ERC20);

		blockChainService.getBlockInfoByNum(ChainType.ERC20, BigInteger.valueOf(Func.toLong(blockHeight)));

		return R.data("成功");
	}
}
