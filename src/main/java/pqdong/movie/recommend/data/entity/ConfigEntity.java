package pqdong.movie.recommend.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ConfigEntity
 * 配置实体
 * @author pqdong
 * @since 2020/03/31
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "config")
public class ConfigEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;
}
