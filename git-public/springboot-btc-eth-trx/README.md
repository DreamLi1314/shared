# 重要提示：<br/>

 _本项目只用作学习，不做任何投资建议，一切风险自行承担，敬请广大开发者注意_ 

#### 介绍
使用springboot+web3j方式进行对ETH链，BTC链，币安链，波场链等热门区块链进行链上开发，开箱即可用，大大缩小了入门门槛。
如有帮助请记得 **点击右上角star** 。

```
1.项目才刚刚开始，后面还有很多丰富的功能
2.请关注我们了解后续的情况（不定期更新）
```

## 区块链项目推荐：<br/>
  **1.Brc20铭文管理系统:**<br/>
       　　　<a href='http://insc.wechatqun.cn:8088' target="_blank" >http://insc.wechatqun.cn:8088 </a> <br/>
  **2.区块链数字货币交易所:** <br/>
       　　　<a href='http://coin-pc.mj.ink/' target="_blank" >http://coin-pc.mj.ink </a> <br/>

# 1.数字货币合约交易所

1.演示环境: <br/>
       　　　PC端： <a href='http://coin-pc.mj.ink/' target="_blank" >http://coin-pc.mj.ink </a>　账号：coinexpro@gmail.com 密码：123456qq 验证码：123456 <br/>
       　　　后台端： <a href='http://coin-ht.mj.ink/' target="_blank" >http://coin-ht.mj.ink </a>　账号：test 密码：123456 <br/>
       　　　H5端： <a href='http://coin-h5.mj.ink/#/' target="_blank" >http://coin-h5.mj.ink </a>　账号：coinexpro@gmail.com 密码：123456qq 验证码：123456 <br/>

2.项目地址: <a href='https://github.com/CoinExPro/CoinExchange' target="_blank" >https://github.com/CoinExPro/CoinExchange </a><br/>
<img  src="https://ai.oss.mj.ink/chatgpt/insc/coinexpro.png"/>

# 2.BRC20铭文诊断系统 **<a href='https://gitee.com/Linriqiang/springboot-insc' target="_blank" >全开源</a>**

1. 演示环境：<a href='http://insc.wechatqun.cn:8088' target="_blank" >http://insc.wechatqun.cn:8088 </a><br/>
2. 项目地址：<a href='https://gitee.com/Linriqiang/springboot-insc' target="_blank" >https://gitee.com/Linriqiang/springboot-insc </a><br/>
 <div align=center >
    <img  src="https://ai.oss.mj.ink/chatgpt/insc/mw1.png"/>
    <img  src="https://ai.oss.mj.ink/chatgpt/insc/mw2.png"/>
 </div>

# 项目关系

| 项目                                                              | Star                                                                                                                                                                                                                                                                                             | 简介                          |
|-----------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| [数字货币交易所](https://github.com/CoinExPro/CoinExchange)  |  [![GitHub stars](https://img.shields.io/github/stars/CoinExPro/CoinExchange.svg?style=social&label=Stars)](https://github.com/CoinExPro/CoinExchange)       | 基于 Spring Cloud 微服务架构        |
| [铭文诊断管理系统](https://gitee.com/Linriqiang/springboot-insc)  | [![Gitee star](https://gitee.com/Linriqiang/springboot-insc/badge/star.svg)](https://gitee.com/Linriqiang/springboot-insc)       | 对Brc20 Ordinals的铭文数据进行了诊断分析      |

# 部分代码
**1.创建钱包**

```
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
```
**2.导入助记词生成地址**

```
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
```
**3.获取代币余额**

```
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
```




# 添加小编：

 <div align=center >
    <td ><img height="350" width="250" src="https://ai.oss.mj.ink/chatgpt/insc/wx.jpg"/></td>
 </div>


