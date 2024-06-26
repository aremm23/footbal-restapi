package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final ClubService clubService;
    private final BrandService brandService;

    @Transactional
    public void savePlayer(Player player) {
        if (playerRepository.existsByName(player.getName())) {
            throw new DataNotCreatedException("Player with name " + player.getName() + " already exist");
        }
        if (player.getBrands() != null) {
            player.getBrands().forEach(brand -> brand.addPlayer(player));
        }
        playerRepository.save(player);
    }

    @CacheEvict(value = "UsersService::getUserById", key = "#id")
    @Transactional
    public void removePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new DataNotFoundedException("Player with id " + id + " not founded");
        }
        playerRepository.deleteById(id);
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Cacheable(value = "PlayerService::findById", key = "#id")
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id + " not founded")
        );
    }

    @Cacheable(value = "PlayerService::findByName", key = "#name")
    public Player findByName(String name) {
        return playerRepository.findByName(name).orElseThrow(() ->
                new DataNotFoundedException("Player with name " + name + " not founded")
        );
    }

    @Caching(
        put = {
                @CachePut(value = "PlayerService::findById", key = "#id"),
                @CachePut(value = "PlayerService::findByName", key = "#playerRepository.findById(id).get().name"),
        }
    )
    @Transactional
    public void update(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id + " not founded")
        );
        player.setName(updatedPlayer.getName());
        player.setPrice(updatedPlayer.getPrice());
    }


    public List<Player> findByNames(List<String> playersNames) {
        return playersNames.stream().map(playerStr -> playerRepository.findByName(playerStr).orElse(null)).toList();
    }

    @Transactional
    public void addExistClub(Long id, String clubName) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id + " not founded")
        );
        Club club = clubService.findByName(clubName);
        club.addPlayer(player);
        player.setClub(club);
    }

    @Transactional
    public void addExistBrand(Long id, String brandName) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id + " not founded")
        );
        Brand brand = brandService.findByName(brandName);
        brand.addPlayer(player);
        player.addBrand(brand);
    }
}
