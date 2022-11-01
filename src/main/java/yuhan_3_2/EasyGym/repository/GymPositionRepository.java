package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yuhan_3_2.EasyGym.entity.GymPosition;

import java.nio.file.Files;
import java.util.Optional;

public interface GymPositionRepository extends JpaRepository<GymPosition,Long> {
    Optional<GymPosition> findById(Long id);
}
