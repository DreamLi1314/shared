package org.springblade.block.service.impl;

import org.springblade.block.constant.ChainType;
import org.springblade.block.service.IBlockChainService;
import org.springblade.block.service.IChainBaseService;
import org.springblade.block.service.impl.chain.ChainErc20ServiceImpl;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * 操作日志相关
 */
@Service
public class BlockChainServiceImpl implements IBlockChainService {


	/**
	 * 获取服务
	 * @param chainType
	 * @return
	 */
	private IChainBaseService getChainService(String chainType){
		if(Func.isEmpty(chainType)){
			throw new ServiceException("链路类型为空");
		}
		switch (chainType){
			case ChainType.ERC20 :
				return SpringUtil.getBean(ChainErc20ServiceImpl.class);

		}
		throw new ServiceException("链路类型不正确");
	}


	/**
	 * 生成地址
	 * @param chainType 链路类型
	 * @return
	 */
	@Override
	public String createAddress(String chainType){
		IChainBaseService chainService = this.getChainService(chainType);
		String address = chainService.createAddress();
		return address;
	}

	/**
	 * 通过导入助记词生成地址
	 * @param mnemonic
	 * @return
	 */
	@Override
	public boolean importAddressByMnemonic(String chainType,String mnemonic){
		IChainBaseService chainService = this.getChainService(chainType);
		boolean flag = chainService.importAddressByMnemonic(mnemonic);
		return flag;
	}


	/**
	 * 地址校验
	 * @param chainType 链路类型
	 * @param address
	 * @return
	 */
	@Override
	public boolean  checkAddress(String chainType,String address){
		if(Func.isEmpty(chainType) || Func.isEmpty(address)){
			throw new ServiceException("参数为空");
		}
		IChainBaseService chainService = this.getChainService(chainType);
		return chainService.checkAddress(address);
	}

	/**
	 * 获取链路高度
	 * @param chainType 链路类型
	 * @return
	 */
	@Override
	public String  getBlockHeight(String chainType){
		IChainBaseService chainService = this.getChainService(chainType);
		return chainService.getBlockHeight();
	}

	/**
	 * 获取当前Gas价格
	 * @param chainType 链路类型
	 * @return
	 */
	@Override
	public String  getGasPrice(String chainType){
		IChainBaseService chainService = this.getChainService(chainType);
		return chainService.getGasPrice();
	}

	/**
	 * 获取代币余额
	 * @param chainType 链路类型
	 * @return
	 */
	@Override
	public String getTokenBalance(String chainType,String fromAddress, String contractAddress){
		IChainBaseService chainService = this.getChainService(chainType);
		return chainService.getTokenBalance(fromAddress,contractAddress);
	}

	/**
	 * 扫块单块 - 块高度
	 */
	@Override
	public void getBlockInfoByNum(String chainType, BigInteger blockNumber){
		IChainBaseService chainService = this.getChainService(chainType);
		chainService.getBlockInfoByNum(blockNumber);
	}


}
