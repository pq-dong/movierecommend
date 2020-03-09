package pqdong.movie.recommend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pqdong.movie.recommend.data.entity.ConfigEntity;

/**
 * @author pqdong
 * @description
 * @date 2020-03-02
 */


public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {
    @Query("SELECT e FROM ConfigEntity e WHERE e.key = :keys")
    ConfigEntity findConfigByKey(@Param("keys") String keys);
}
