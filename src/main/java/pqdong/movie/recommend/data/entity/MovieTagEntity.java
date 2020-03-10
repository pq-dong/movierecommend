package pqdong.movie.recommend.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movie_tags")
public class MovieTagEntity {
    // 电影标签名称
    @Id
    @Column(name = "movie_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    // 电影标签
    @Column(name = "value")
    private String value;
}
