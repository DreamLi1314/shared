/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.block.service;


import java.math.BigInteger;

/**
 * 区块链相关
 */
public interface IBlockChainService {


	/**
	 * 生成地址
	 * @param chainType 链路类型
	 * @return
	 */
	String createAddress(String chainType);

	/**
	 * 通过导入助记词生成地址
	 * @param mnemonic
	 * @return
	 */
	boolean importAddressByMnemonic(String chainType,String mnemonic);


	/**
	 * 地址校验
	 * @param chainType 链路类型
	 * @param address 地址
	 * @return
	 */
	boolean  checkAddress(String chainType,String address);

	/**
	 * 获取链路高度
	 * @param chainType 链路类型
	 * @return
	 */
	String  getBlockHeight(String chainType);

	/**
	 * 获取当前Gas价格
	 * @param chainType 链路类型
	 * @return
	 */
	String  getGasPrice(String chainType);


	/**
	 * 获取代币余额
	 * @param chainType 链路类型
	 * @return
	 */
	String getTokenBalance(String chainType,String fromAddress, String contractAddress);


	/**
	 * 扫块单块 - 块高度
	 */
	void getBlockInfoByNum(String chainType,BigInteger blockNumber);


}
