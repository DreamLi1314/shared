package org.springblade.block.model;

import lombok.Data;

import java.util.List;

@Data
public class EthWalletModel {
	String privateKey;
	String publicKey;
	List<String> mnemonic;
	String mnemonicPath;
	String Address;
	String keystore;
}
