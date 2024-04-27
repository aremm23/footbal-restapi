package by.artsem.footballrestapi.repository;

import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByName(String name);
    Boolean existsByName(String name);
    @Query("SELECT c FROM Club c JOIN c.players p GROUP BY c ORDER BY SUM(p.price) DESC LIMIT 1")
    Optional<Club> findClubWithHighestTotalPlayerValue();
}
