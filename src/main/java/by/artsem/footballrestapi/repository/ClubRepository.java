package by.artsem.footballrestapi.repository;

import by.artsem.footballrestapi.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByName(String name);
    Boolean existsByName(String name);
}
