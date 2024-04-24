package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.PlayerRepository;
import by.artsem.footballrestapi.util.DataNotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void savePlayer(Player player) {
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
        return playerRepository.findById(id).orElseThrow(DataNotFoundedException::new);
    }

    public Player findByName(String name) {
        return playerRepository.findByName(name).orElseThrow(DataNotFoundedException::new);
    }

    public List<Player> findByNames(List<String> playersNames) {
        return playersNames.stream().map(playerStr -> playerRepository.findByName(playerStr).orElse(null)).toList();
    }
}
