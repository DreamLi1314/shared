package club.javafamily.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class Dept implements Serializable {
	private Long deptno;
	private String dname;
	private String db_source; // 来自哪个数据库, 因为微服务架构可以一个服务对应一个数据库, 同一个信息被存储到不同数据库
}
