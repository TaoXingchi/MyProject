package xyz.txcplus.redis.all;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: UserEntity <br/>
 * Description: <br/>
 * date: 2020/7/7 17:23<br/>
 *
 * @author Administrator<br />
 */
@Data
public class UserEntity implements Serializable {
    private Long id;
    private String guid;
    private String name;
    private String age;
    private Date createTime;
}
