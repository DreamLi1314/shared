package com.inetsoft.runtime;

import com.inetsoft.base.Config;

public class RuntimeConfig extends Config<String> {

    private static RuntimeConfig config = new RuntimeConfig();

    public static RuntimeConfig getInstance() {
        return config;
    }

    /**
     * check server running status.
     * @return
     */
    public boolean isServerRunning() {
        return !"true".equals(super.getProperty("server.stop"));
    }

}
