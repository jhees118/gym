package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yuhan_3_2.EasyGym.entity.*;

import java.util.List;

@Repository
public interface GymHeartRepository extends JpaRepository<GymHeart,Long> {
    GymHeart findByUserAndGymPosition(User user, GymPosition gymPosition);
    List<GymHeart> findByGymPosition(GymPosition gymPosition);

}
