package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.BrandRepository;
import by.artsem.footballrestapi.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public void saveBrand(Brand brand) {
        if (brandRepository.existsByName(brand.getName())) {
            throw new DataNotCreatedException("Brand with name " + brand.getName() + " already exist");
        }
        brand.getPlayers().forEach(player -> player.addBrand(brand));
        brandRepository.save(brand);
    }

    @Transactional
    public void removeBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Brand with id " + id + " not founded")
        );
        brand.getPlayers().forEach(player -> player.deleteBrand(brand));
        brandRepository.deleteById(id);
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Brand with id " + id + " not founded")
        );
    }

    @Transactional
    public void updateName(Long id, Brand updatedBrand) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Brand with name " + id + " not founded")
        );
        brand.setName(updatedBrand.getName());
    }

    @Transactional
    public void addExistPlayer(Long id, String playerName) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Brand with id " + id + " not founded")
        );
        Player player = playerRepository.findByName(playerName).orElseThrow(() ->
                new DataNotFoundedException("Player with name " + playerName + " not founded")
        );
        player.addBrand(brand);
    }

    public Brand findByName(String name) {
        return brandRepository.findByName(name).orElseThrow(() ->
                new DataNotFoundedException("Brand with name" + name + " not founded")
        );
    }

    public List<Brand> findByNames(List<String> brandNames) {
        return brandNames.stream().map(brandStr -> brandRepository.findByName(brandStr).orElse(null)).toList();
    }
}
