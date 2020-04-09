package pqdong.movie.recommend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.entity.RatingEntity;

import java.util.List;

/**
 * @author pqdong
 * @description
 * @date 2020-03-02
 */

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Query("SELECT e FROM RatingEntity e WHERE e.ratingId = :ratingId")
    RatingEntity findOneByRatingID(@Param("ratingId") Long ratingId);

    List<RatingEntity> findAllByUserId(Long userId);
}
