package pqdong.movie.recommend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pqdong.movie.recommend.data.entity.PersonEntity;

/**
 * @author pqdong
 * @description
 * @date 2020-03-02
 */

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
