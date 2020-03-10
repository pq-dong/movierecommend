package pqdong.movie.recommend.data.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 导演，演员
 * @author pqdong
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "person")
public class PersonEntity {


    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 演员名称
    @Column(name = "name")
    private String name;

    // 演员性别
    @Column(name = "sex")
    private String sex;

    // 演员英文名称
    @Column(name = "name_en")
    private String nameEn ;

    // 演员中文名称
    @Column(name = "name_zn")
    private String nameZn ;

    // 出生日期
    @Column(name = "birth")
    private String  birth;

    // 出生地
    @Column(name = "birth_place")
    private String birthPlace;

    // 星座
    @Column(name = "constellation")
    private String constellation;

    // 职业
    @Column(name = "profession")
    private String profession;

    // 简介
    @Column(name = "biography")
    private String biography;

    // 头像
    @Column(name = "avatar")
    private String avatar;
}
