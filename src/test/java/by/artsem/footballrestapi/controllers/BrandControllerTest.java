package by.artsem.footballrestapi.controllers;

import by.artsem.footballrestapi.dto.BrandDTO;
import by.artsem.footballrestapi.dto.mappers.BrandMapper;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.services.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandController brandController;

    private Player testPlayer1;
    private Player testPlayer2;
    private Brand testBrand1;
    private Brand testBrand2;

    private void init() {
        testPlayer1 = Player.builder()
                .id(1L).price(100).name("PlayerTest2")
                .brands(null).club(null)
                .build();
        testPlayer2 = Player.builder()
                .id(2L).price(200).name("PlayerTest1")
                .brands(null).club(null)
                .build();
        testBrand1 = Brand.builder()
                .id(1L)
                .players(null)
                .name("TestBrand1")
                .build();
        testBrand2 = Brand.builder()
                .id(2L)
                .players(null)
                .name("TestBrand2")
                .build();
    }

    @Test
    void findAll() {
        init();
        testBrand1.setPlayers(List.of(testPlayer1, testPlayer2));
        testBrand2.setPlayers(List.of(testPlayer2));
        List<Brand> brands = List.of(
                testBrand1, testBrand2
        );
        List<BrandDTO> brandDTOS = List.of(
                new BrandDTO(testBrand1.getName(), List.of(testPlayer1.getName(), testPlayer2.getName())),
                new BrandDTO(testBrand2.getName(), List.of(testPlayer2.getName()))
        );
        given(brandMapper.mapToDTO(testBrand1)).willReturn(brandDTOS.get(0));
        given(brandMapper.mapToDTO(testBrand2)).willReturn(brandDTOS.get(1));
        given(brandService.getBrands()).willReturn(brands);

        ResponseEntity<List<BrandDTO>> responseEntity = brandController.findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(brandDTOS, responseEntity.getBody());
    }

    @Test
    void findById() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void getAllWithId() {
    }

    @Test
    void create() {
    }
}