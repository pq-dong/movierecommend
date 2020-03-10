package pqdong.movie.recommend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pqdong.movie.recommend.data.entity.PersonEntity;
import pqdong.movie.recommend.data.entity.UserEntity;

import java.util.List;

/**
 * @author pqdong
 * @description
 * @date 2020-03-02
 */

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    @Query(nativeQuery = true, value = "select * from person where 1=1 limit ?1")
    List<PersonEntity> findAllByCountLimit(@Param("id") int num);

    @Query("SELECT e FROM PersonEntity e WHERE e.name like :keys")
    List<PersonEntity> findAllByName(@Param("keys") String keys);
}
