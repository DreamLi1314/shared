package hello;

/**
 * @Description: 客户端发送给 Server 的对象 
 * @Warning: 
 * @Author: dreamli
 * @Package: initial - hello.HelloMessage.java
 * @Date: Jan 17, 2019 12:13:11 AM
 * @Version: 1.0.0
 */
public class HelloMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public java.lang.String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}