package coms309.Cycino.Games.GameLogic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandsRepo extends JpaRepository<PlayerHands, Long> {
}