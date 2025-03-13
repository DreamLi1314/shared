package org.springblade.block.service.impl.chain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springblade.block.props.BlockChainProperties;
import org.springblade.block.service.IChainBaseService;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;

import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ERC20 链路相关服务
 */
@Slf4j
@Service
public class ChainErc20ServiceImpl implements IChainBaseService {

	private Web3j web3j;

	private Web3j getWeb3j() {
		if (Func.isNotEmpty(web3j)) {
			return web3j;
		}
		String erc20_url = BlockChainProperties.getErc20_url();
		web3j = Web3j.build(new HttpService(erc20_url));
		return web3j;
	}

	/**
	 * 生成地址
	 *
	 * @return
	 */
	@Override
	public String createAddress() {
		try {

			String ethKeystorePath = BlockChainProperties.getErc20_keystore_path();
			String password = Func.randomUUID();
			String filename = WalletUtils.generateNewWalletFile(password, new File(ethKeystorePath), true);
			String fileUrl = ethKeystorePath + filename;
			Credentials credentials = WalletUtils.loadCredentials(password, new File(fileUrl));
			String address = credentials.getAddress();//地址
			String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);//私钥


			Bip39Wallet wallet = WalletUtils.generateBip39Wallet(password, new File(ethKeystorePath));
			String filename2 = wallet.getFilename();
			String fileUrl2 = ethKeystorePath + filename2;

			Credentials credentials2 = WalletUtils.loadCredentials(password, new File(fileUrl2));
			String address2 = credentials2.getAddress();//地址

			String mnemonic = wallet.getMnemonic();//这里是助记词

			System.out.println("mnemonic============" + mnemonic);
			System.out.println("address2========" + address2);
			System.out.println(JsonUtil.toJson(credentials));


			return address;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 通过导入助记词生成地址
	 *
	 * @param mnemonic
	 * @return
	 */
	public boolean importAddressByMnemonic(String mnemonic) {
		String ETH_TYPE = "m/44'/60'/0'/0/0";
		try {
			List<String> list = Func.toStrList(" ", mnemonic);
			String password = IdWorker.getIdStr();

			String passphrase = "";
			long creationTimeSeconds = System.currentTimeMillis() / 1000;
			DeterministicSeed ds = new DeterministicSeed(list, null, passphrase, creationTimeSeconds);

			String[] pathArray = ETH_TYPE.split("/");

			//根私钥
			byte[] seedBytes = ds.getSeedBytes();

			DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
			for (int i = 1; i < pathArray.length; i++) {
				ChildNumber childNumber;
				if (pathArray[i].endsWith("'")) {
					int number = Integer.parseInt(pathArray[i].substring(0, pathArray[i].length() - 1));
					childNumber = new ChildNumber(number, true);
				} else {
					int number = Integer.parseInt(pathArray[i]);
					childNumber = new ChildNumber(number, false);
				}
				dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
			}


			ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
			WalletFile walletFile = Wallet.createLight(password, keyPair);
			System.out.println("地址：" + "0x" + walletFile.getAddress());
			System.out.println("私钥：" + keyPair.getPrivateKey().toString(16));
			;


		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	/**
	 * 地址校验
	 *
	 * @param address
	 * @return
	 */
	@Override
	public boolean checkAddress(String address) {
		return WalletUtils.isValidAddress(address);
	}

	/**
	 * 获取链路高度
	 *
	 * @return
	 */
	@Override
	public String getBlockHeight() {
		try {
			Request<?, EthBlockNumber> ethBlockNumberRequest = this.getWeb3j().ethBlockNumber();
			EthBlockNumber send = ethBlockNumberRequest.send();
			BigInteger blockNumber = send.getBlockNumber();
			return blockNumber.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取高度失败");
		}

	}


	/**
	 * 获取当前Gas价格
	 *
	 * @return
	 */
	@Override
	public String getGasPrice() {
		try {
			EthGasPrice ethGasPrice = this.getWeb3j().ethGasPrice().send();
			BigInteger gasPrice = ethGasPrice.getGasPrice();
			System.out.println(gasPrice);
			return gasPrice.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取当前Gas价格失败");
		}

	}

	/**
	 * 获取代币余额
	 */
	@Override
	public String getTokenBalance(String fromAddress, String contractAddress) {

		String methodName = "balanceOf";
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();
		Address address = new Address(fromAddress);
		inputParameters.add(address);

		TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
		};
		outputParameters.add(typeReference);
		Function function = new Function(methodName, inputParameters, outputParameters);
		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);

		EthCall ethCall;
		BigInteger balanceValue = BigInteger.ZERO;
		try {
			ethCall = this.getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			balanceValue = (BigInteger) results.get(0).getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return balanceValue.toString();
	}

	@Override
	public void getBlockInfoByNum(BigInteger blockNumber) {
		try{
			EthBlock.Block block = this.getWeb3j().ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send().getBlock();
			if(Func.isEmpty(block)){
				throw new ServiceException("区块信息为空");
			}

			List<EthBlock.TransactionResult> transactionList = block.getTransactions();
			if (Func.isEmpty(transactionList)) {
				return;
			}

			// 遍历block中的交易
			for (EthBlock.TransactionResult tx : transactionList) {
				if (!(tx instanceof EthBlock.TransactionObject)) {
					continue;
				}
				//transaction: 块中的单笔交易
				EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) tx;
				org.web3j.protocol.core.methods.response.Transaction transaction = transactionObject.get();
				this.handleTransaction(transaction);
			}


		}catch (Exception e){
			e.printStackTrace();
		}




	}

	/**
	 * 处理每一笔交易信息
	 *
	 * @param transaction
	 */
	private void handleTransaction(org.web3j.protocol.core.methods.response.Transaction transaction) {
		try {
			//transaction: 块中的单笔交易
			String to = transaction.getTo();//到账地址为空
			if (Func.isEmpty(to)) {
				return;
			}

			String hashCode = transaction.getHash();
			if (Func.isEmpty(hashCode)) {//交易哈希
				return;
			}

			if (transaction.getValue().compareTo(BigInteger.ZERO) == 1) {//ETH
				BigDecimal amount = Convert.fromWei(new BigDecimal(transaction.getValue()), Convert.Unit.ETHER);
				String toAddress = to;

				System.out.println(toAddress+"============"+amount.toPlainString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 查询代币精度
	 *
	 * @param contractAddress
	 * @return
	 */
	private int getTokenDecimals(String contractAddress) {
		String emptyAddress = "0x0000000000000000000000000000000000000000";
		String methodName = "decimals";
		String fromAddr = emptyAddress;
		int decimal = 0;
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();

		TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {};
		outputParameters.add(typeReference);

		Function function = new Function(methodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

		EthCall ethCall;
		try {
			ethCall = this.getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			decimal = Integer.parseInt(results.get(0).getValue().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decimal;
	}


}
