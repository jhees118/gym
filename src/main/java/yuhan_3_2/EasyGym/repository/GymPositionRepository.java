package yuhan_3_2.EasyGym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yuhan_3_2.EasyGym.entity.GymPosition;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public interface GymPositionRepository extends JpaRepository<GymPosition,Long> {
    List<GymPosition> findByPosition(String position);
    Page<GymPosition> findByPosition(String position,Pageable pageable);
}
