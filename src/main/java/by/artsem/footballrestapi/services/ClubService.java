package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.ClubRepository;
import by.artsem.footballrestapi.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private PlayerRepository playerRepository;

    @Transactional
    public void saveClub(Club club) {
        if (clubRepository.existsByName(club.getName())) {
            throw new DataNotCreatedException("Club with name " + club.getName() + " already exist");
        }
        clubRepository.save(club);
    }

    @Transactional
    public void removeClub(Long id) {
        if (!clubRepository.existsById(id)) {
            throw new DataNotFoundedException("Club with id " + id + " not founded");
        }
        clubRepository.deleteById(id);
    }

    public List<Club> getClubs() {
        return clubRepository.findAll();
    }


    public Club findById(Long id) {
        return clubRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Club with id " + id + " not founded")
        );
    }

    @Transactional
    public void addExistPlayer(Long id, String playerName) {
        Club club = clubRepository.findById(id).orElseThrow(() ->
                new DataNotFoundedException("Club with id " + id + " not founded")
        );
        Player player = playerRepository.findByName(playerName).orElseThrow(() ->
                new DataNotFoundedException("Player with name " + playerName + " not founded")
        );
        if (player.getClub() != null) {
            throw new DataNotCreatedException("Player " + player.getName() + " already has a club. Update it in /player/{id}/update-club");
        }
        club.addPlayer(player);
        player.setClub(club);
    }


    public Club findByName(String name) {
        return clubRepository.findByName(name).orElseThrow(() ->
                new DataNotFoundedException("Club with name " + name + " not founded")
        );
    }


    public List<String> getClubWithExpensivePlayers() {
        return clubRepository.findAllByPlayersPrice().orElse(null);
    }
}
