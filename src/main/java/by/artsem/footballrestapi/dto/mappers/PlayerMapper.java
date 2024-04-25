package by.artsem.footballrestapi.dto.mappers;

import by.artsem.footballrestapi.dto.PlayerDTO;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.BrandService;
import by.artsem.footballrestapi.services.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PlayerMapper {
    private BrandService brandService;
    private ClubService clubService;

    public PlayerDTO mapToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setPrice(player.getPrice());
        if (player.getBrands() != null) {
            playerDTO.setBrands(player.getBrands().stream().map(Brand::getName).collect(Collectors.toList()));
        }
        if (player.getClub() != null) {
            playerDTO.setClub(player.getClub().getName());
        }
        return playerDTO;
    }

    public Player mapFromDTO(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setPrice(playerDTO.getPrice());
        if (playerDTO.getClub() != null) {
            player.setClub(clubService.findByName(playerDTO.getClub()));
        }
        if (playerDTO.getBrands() != null) {
            player.setBrands(brandService.findByNames(playerDTO.getBrands()));
        }
        return player;
    }
}
