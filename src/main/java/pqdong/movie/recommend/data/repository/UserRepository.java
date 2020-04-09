package pqdong.movie.recommend.data.repository;


import org.apache.hadoop.hdfs.server.namenode.BlockPlacementPolicyWithNodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pqdong.movie.recommend.data.entity.UserEntity;

/**
 * @author pqdong
 * @description
 * @date 2020-03-03
 */

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT e FROM UserEntity e WHERE e.userMd = :userMd")
    UserEntity findByUserMd(@Param("userMd") String userMd);

    @Query("SELECT e FROM UserEntity e WHERE e.username = :userNickName")
    UserEntity findByUserNickName(@Param("userNickName") String userNickName);

    @Query("SELECT e FROM UserEntity e WHERE e.id = :userId")
    UserEntity findOneByUserID(@Param("userId") Long userId);
}
