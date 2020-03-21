package pqdong.movie.recommend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.entity.PersonEntity;

import java.util.List;

/**
 * @author pqdong
 * @description
 * @date 2020-03-02
 */

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    @Query(nativeQuery = true, value = "select * from movie where 1=1 limit ?1")
    List<MovieEntity> findAllByCountLimit(@Param("num") int num);

    @Query("SELECT e FROM MovieEntity e WHERE e.name like :keys")
    List<MovieEntity> findAllByName(@Param("keys") String keys);
}
