package pqdong.movie.recommend.data.entity;

/*
* 用户表
* @author pqdong
* @since 2020/03/28
 */

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 用户唯一标签，普通索引，md5值，长字符串为索引并不是很好
    @Column(name = "user_md")
    private String userMd;

    // 用户昵称
    @Column(name = "user_nickname")
    private String username;

    // 用户头像
    @Column(name = "user_avatar")
    private String userAvatar;

    // 用户密码
    @JsonIgnore
    @Column(name = "password")
    private String password;

    // 用户标签
    @Column(name = "user_tags")
    private String userTags;

    // 用户手机号
    @Column(name = "phone")
    private String phone;

    // 个人宣言
    @Column(name = "motto")
    private String motto;

    @Column(name = "sex")
    private String sex;

    public List<String> getFormatTag(){
        return JSONObject.parseArray(this.userTags, String.class);
    }
}
