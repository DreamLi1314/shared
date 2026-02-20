package com.inetsoft.base;

import java.util.Properties;

public abstract class Config <T> {
    protected Properties props = new Properties();

    protected Config() {
    }

    public void setProperty(T key, T value) {
        this.props.put(key, value);
    }

    public T getProperty(T key) {
        return (T) this.props.get(key);
    }

}
