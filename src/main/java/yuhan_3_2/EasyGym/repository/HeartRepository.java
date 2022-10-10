package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.Heart;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {


}
