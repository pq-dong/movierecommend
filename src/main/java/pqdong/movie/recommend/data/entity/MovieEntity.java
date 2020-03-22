package pqdong.movie.recommend.data.entity;

/*
* 电影
* @author pqdong
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
@Entity
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    // 电影名称
    @Column(name = "name")
    private String name;

    // 主演
    @Column(name = "actors")
    private String actors;

    // 电影图片封面
    @Column(name = "cover")
    private String cover;

    // 导演
    @Column(name = "directors")
    private String directors;

    // 类型
    @Column(name = "genres")
    private String genres;

    //播放地址
    @Column(name = "official_site")
    private String officialSite;

    //电影制片地
    @Column(name = "regions")
    private String regions;

    //电影语言
    @Column(name = "languages")
    private String languages;

    //片长
    @Column(name = "mins")
    private Integer mins;

    //评分
    @Column(name = "score")
    private Float score;

    //投票数
    @Column(name = "votes")
    private Integer votes;

    //标签
    @Column(name = "tags")
    private String tags;

    //电影描述
    @Column(name = "storyline")
    private String storyline;

    //年份
    @Column(name = "year")
    private Integer year;

    //演员
    @Column(name = "actor_ids")
    private String actorIds;

    //导演
    @Column(name = "director_ids")
    private String directorIds;

    // 电影上映时间
    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;
}
