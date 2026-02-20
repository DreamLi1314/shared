package org.springblade.block.service;

import org.springblade.core.tool.utils.Func;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;

/**
 * 链路公共服务
 */
public interface IChainBaseService {
	/**
	 * 生成地址
	 * @return
	 */
	String createAddress();

	/**
	 * 通过导入助记词生成地址
	 * @param mnemonic
	 * @return
	 */
	boolean importAddressByMnemonic(String mnemonic);

	/**
	 * 地址校验
	 * @param address
	 * @return
	 */
	boolean  checkAddress(String address);

	/**
	 * 获取链路高度
	 * @return
	 */
	String getBlockHeight();

	/**
	 * 获取当前Gas价格
	 * @return
	 */
	String  getGasPrice();

	/**
	 * 获取代币余额
	 */
	String getTokenBalance(String fromAddress, String contractAddress);

	/**
	 * 扫块单块 - 块高度
	 */
	void getBlockInfoByNum(BigInteger blockNumber);



}
