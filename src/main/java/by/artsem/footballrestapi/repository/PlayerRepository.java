package by.artsem.footballrestapi.repository;

import by.artsem.footballrestapi.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Boolean existsByName(String name);
    Optional<Player> findByName(String name);
}
