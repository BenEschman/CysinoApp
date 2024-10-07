package coms309.Cycino.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface followRepository extends JpaRepository<follow, Integer> {
}
