package by.artsem.footballrestapi.dto.mappers;

import by.artsem.footballrestapi.dto.BrandDTO;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BrandMapper {
    PlayerService playerService;

    public BrandDTO mapToDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName(brand.getName());
        brandDTO.setPlayers(brand.getPlayers().stream().map(Player::getName).collect(Collectors.toList()));
        return brandDTO;
    }

    public Brand mapFromDTO(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        if(brandDTO.getPlayers() != null) {
            brand.setPlayers(playerService.findByNames(brandDTO.getPlayers()));
        }
        return brand;
    }
}
