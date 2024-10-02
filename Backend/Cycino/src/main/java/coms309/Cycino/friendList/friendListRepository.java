package coms309.Cycino.friendList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface friendListRepository extends JpaRepository<friendList, Long> {
}
