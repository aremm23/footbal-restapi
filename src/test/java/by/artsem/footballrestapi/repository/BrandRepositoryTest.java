package by.artsem.footballrestapi.repository;

import by.artsem.footballrestapi.models.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrandRepositoryTest {
    @Autowired
    private BrandRepository brandRepository;

    private Brand testBrand1;

    @BeforeEach
    public void init() {
        testBrand1 = Brand.builder()
                .id(1L)
                .players(null)
                .name("TestBrand1")
                .build();
    }

    @Test
    void testFindBrandByName() {
        brandRepository.save(testBrand1);

        Optional<Brand> optionalBrand = brandRepository.findByName(testBrand1.getName());

        assertNotNull(optionalBrand.orElse(null));
        assertEquals(optionalBrand.get().getName(), testBrand1.getName());
    }

    @Test
    void testFindBrandByNonexistentName() {
        brandRepository.save(testBrand1);

        Optional<Brand> optionalBrand = brandRepository.findByName("EmptyName");

        assertNull(optionalBrand.orElse(null));
    }

    @Test
    void existsByName() {
        brandRepository.save(testBrand1);

        boolean isExist = brandRepository.existsByName(testBrand1.getName());

        assertTrue(isExist);
    }
}