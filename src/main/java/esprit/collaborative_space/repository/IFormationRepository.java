package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFormationRepository extends JpaRepository<Formation,Long> {
}
