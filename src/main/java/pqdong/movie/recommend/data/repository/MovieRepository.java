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

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE name LIKE CONCAT('%',?1,'%') limit ?2")
    List<MovieEntity> findAllByName(@Param("keys") String keys, @Param("total") int total);

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE tags LIKE CONCAT('%',?1,'%') limit ?2")
    List<MovieEntity> findAllByTag(@Param("keys") String keys, @Param("total") int total);

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE 1=1 ORDER BY score DESC limit 12")
    List<MovieEntity> findAllByHighScore();

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE actor_ids LIKE CONCAT('%',:keys,'%')")
    List<MovieEntity> findAllByPersonName(@Param("keys") String keys);

    @Query("SELECT e FROM MovieEntity e WHERE e.movieId = :movieId")
    MovieEntity findOneByMovieID(@Param("movieId") Long movieId);
}
