package by.artsem.footballrestapi.repository;

import by.artsem.footballrestapi.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    Boolean existsByName(String name);
}
