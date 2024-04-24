package by.artsem.footballrestapi.dto.mappers;

import by.artsem.footballrestapi.dto.ClubDTO;
import by.artsem.footballrestapi.models.Club;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ClubMapper {

    private PlayerService playerService;

    public ClubDTO mapToDTO(Club club) {
        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setName(club.getName());
        clubDTO.setPlayersNames(club.getPlayers().stream().map(Player::getName).collect(Collectors.toList()));
        return clubDTO;
    }

    public Club mapFromDTO(ClubDTO clubDTO) {
        Club club = new Club();
        club.setName(clubDTO.getName());
        if(clubDTO.getPlayersNames() != null) {
            club.setPlayers(playerService.findByNames(clubDTO.getPlayersNames()));
        }
        return club;
    }
}
