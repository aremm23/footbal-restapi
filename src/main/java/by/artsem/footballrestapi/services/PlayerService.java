package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.PlayerRepository;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        player.getBrands().forEach(brand -> brand.addPlayer(player));
        playerRepository.save(player);
    }

    @Transactional
    public void removePlayer(Player player) {
        playerRepository.delete(player);
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id +" not founded")
        );
    }

    public Player findByName(String name) {
        return playerRepository.findByName(name).orElseThrow(() ->
                new DataNotFoundedException("Player with name " + name +" not founded")
        );
    }

    @Transactional
    public void update(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id +" not founded")
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
                new DataNotFoundedException("Player with id " + id +" not founded")
        );
        Club club = clubService.findByName(clubName);
        club.addPlayer(player);
        player.setClub(club);
    }

    @Transactional
    public void addExistBrand(Long id, String brandName) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Player with id " + id +" not founded")
        );
        Brand brand = brandService.findByName(brandName);
        brand.addPlayer(player);
        player.addBrand(brand);
    }
}
