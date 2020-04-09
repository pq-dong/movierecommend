package pqdong.movie.recommend.data.entity;

/*
* 评分
* @author pqdong
* @since 2020/04/06
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rating")
@Entity
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    // 用户id
    @Column(name = "user_id")
    private Long userId;

    // 电影id
    @Column(name = "movie_id")
    private Long movieId;

    // 评分
    @Column(name = "rating")
    private Integer rating;

    // 电影上映时间
    @Temporal(TemporalType.DATE)
    @Column(name = "time")
    @CreatedDate
    private Date releaseDate;
}
