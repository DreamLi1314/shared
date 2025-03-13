package org.springblade.block.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 公共配置
 */
@Component
@ConfigurationProperties(prefix = "block")
public class BlockChainProperties {
	/**
	 * erc20
	 */
	private static String erc20_url;

	private static String erc20_keystore_path;

	public static String getErc20_url() {
		return erc20_url;
	}

	public void setErc20_url(String erc20_url) {
		BlockChainProperties.erc20_url = erc20_url;
	}

	public static String getErc20_keystore_path() {
		return erc20_keystore_path;
	}

	public void setErc20_keystore_path(String erc20_keystore_path) {
		BlockChainProperties.erc20_keystore_path = erc20_keystore_path;
	}
}
