package com.dreamli.po;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @ConfigurationProperties 标识这个类的属性和配置文件中 prefix 声明的属性进行绑定.
 *  注解要启用, 被标注的类必须在容器中, 且属性必须有 getter 和 setter, 默认从主配置文
 *  件中读取 prefix.attr 的值, 也可以通过
 *
 * @Validated 启用数据校验
 * @PropertySource 加载指定的 properties 文件与当前 bean 进行属性绑定.
 *                 此加载与主配置文件形成互补配置. 且该文件优先级更低.
 */
@PropertySource("classpath:po.properties")
@Component
@ConfigurationProperties(prefix = "sb.person")
@Validated
public class Person {
    private String id;
    private String name;
    private int age;
    private List<String> likes;
    @Email
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", likes=" + likes +
                ", email='" + email + '\'' +
                '}';
    }
}
