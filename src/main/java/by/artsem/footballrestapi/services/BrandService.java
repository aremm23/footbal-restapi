package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.BrandRepository;
import by.artsem.footballrestapi.util.DataNotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void removeBrand(Brand brand) {
        brandRepository.delete(brand);
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(DataNotFoundedException::new);
    }

    @Transactional
    public Brand findByName(String name) {
        return brandRepository.findByName(name).orElseThrow(DataNotFoundedException::new);
    }
}
