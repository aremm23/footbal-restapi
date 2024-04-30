package by.artsem.footballrestapi.services;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.models.Brand;
import by.artsem.footballrestapi.models.Player;
import by.artsem.footballrestapi.repository.BrandRepository;
import by.artsem.footballrestapi.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private BrandService brandService;

    private Player testPlayer1;
    private Player testPlayer2;
    private Brand testBrand1;

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
    }

    @Test
    public void BrandService_SaveExist_ThrowsDataNotCreatedException() {
        init();
        testBrand1.setPlayers(Collections.emptyList());

        given(brandRepository.existsByName("TestBrand1")).willReturn(Boolean.TRUE);

        assertThrows(DataNotCreatedException.class,
                () -> brandService.saveBrand(testBrand1)
        );
    }

    @Test
    public void BrandService_Save_AssociatePlayersWithBrand() {
        init();
        List<Player> players = List.of(testPlayer1, testPlayer2);
        Brand brand = testBrand1;
        testBrand1.setPlayers(players);
        given(brandRepository.existsByName("TestBrand1")).willReturn(Boolean.FALSE);
        given(brandRepository.save(brand)).willReturn(brand);

        brandService.saveBrand(brand);

        assertEquals(players.get(0).getBrands().get(0), brand);
        assertEquals(players.get(1).getBrands().get(0), brand);
    }

    @Test
    public void BrandService_Remove_AssociatePlayersWithBrand() {
        init();
        testPlayer1.addBrand(testBrand1);
        testPlayer2.addBrand(testBrand1);
        List<Player> players = List.of(testPlayer1, testPlayer2);
        testBrand1.setPlayers(players);

        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));

        brandService.removeBrand(testBrand1.getId());

        assertEquals(testPlayer1.getBrands(), Collections.emptyList());
        assertEquals(testPlayer2.getBrands(), Collections.emptyList());
    }

    @Test
    public void BrandService_RemoveNotExist_ThrowsDataNotFoundedException() {
        given(brandRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(DataNotFoundedException.class,
                () -> brandService.removeBrand(1L)
        );
    }

    @Test
    public void BrandService_GetAll_ReturnsListOfBrands() {
        init();
        List<Brand> brands = List.of(testBrand1,
                Brand.builder().id(2L).name("TestBrand2").players(Collections.emptyList()).build()
        );
        given(brandRepository.findAll()).willReturn(brands);

        assertEquals(brandService.getBrands(), brands);
    }

    @Test
    public void BrandService_GetAll_EmptyList() {
        init();
        List<Brand> brands = Collections.emptyList();
        given(brandRepository.findAll()).willReturn(brands);

        assertEquals(brandService.getBrands(), brands);
    }


    @Test
    public void BrandService_FindById_ReturnsExpectedBrand() {
        init();
        testBrand1.setPlayers(Collections.emptyList());
        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));

        Brand foundedBrand = brandService.findById(testBrand1.getId());

        assertEquals(testBrand1, foundedBrand);
    }

    @Test
    public void BrandService_FindById_Throw_NotFoundException() {
        init();
        testBrand1.setPlayers(Collections.emptyList());
        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));

        brandService.findById(testBrand1.getId());

        assertThrows(DataNotFoundedException.class,
                () -> brandService.findById(2L)
        );
    }

    @Test
    public void BrandService_UpdateName_ChangesBrandName() {
        init();
        Brand testBrand2 = Brand.builder().id(1L).name("TestBrand2").players(Collections.emptyList()).build();
        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));

        brandService.updateName(testBrand1.getId(), testBrand2);

        assertEquals(testBrand1.getName(), testBrand2.getName());
    }

    @Test
    public void BrandService_UpdateName_ThrowsDataNotFoundedException() {
        init();
        Brand testBrand2 = Brand.builder().id(1L).name("TestBrand2").players(Collections.emptyList()).build();
        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.empty());

        assertThrows(DataNotFoundedException.class,
                () -> brandService.updateName(testBrand1.getId(), testBrand2)
        );
    }

    @Test
    public void BrandService_AddExistPlayer_SuccessfullyAddPlayerToBrand() {
        init();

        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));
        given(playerRepository.findByName(testPlayer1.getName())).willReturn(Optional.of(testPlayer1));

        brandService.addExistPlayer(testBrand1.getId(), testPlayer1.getName());

        assertEquals(testPlayer1.getBrands().get(0), testBrand1);
    }

    @Test
    public void BrandService_AddExistPlayer_ThrowBrandNotFound() {
        init();

        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.empty());

        assertThrows(DataNotFoundedException.class,
                () -> brandService.addExistPlayer(testBrand1.getId(), testPlayer1.getName())
        );
    }

    @Test
    public void BrandService_AddExistPlayer_ThrowPlayerNotFound() {
        init();

        given(playerRepository.findByName(testPlayer1.getName())).willReturn(Optional.empty());
        given(brandRepository.findById(testBrand1.getId())).willReturn(Optional.of(testBrand1));

        assertThrows(DataNotFoundedException.class,
                () -> brandService.addExistPlayer(testBrand1.getId(), testPlayer1.getName())
        );
    }


    @Test
    public void BrandService_FindByName_ReturnsExpectedBrand() {
        init();
        testBrand1.setPlayers(Collections.emptyList());
        given(brandRepository.findByName(testBrand1.getName())).willReturn(Optional.empty());

        assertThrows(DataNotFoundedException.class,
                () -> brandService.findByName(testBrand1.getName())
        );
    }


    @Test
    public void BrandService_FindAll() {
        init();
        testBrand1.setPlayers(Collections.emptyList());
        Brand testBrand2 = Brand.builder().id(1L).name("TestBrand2").players(Collections.emptyList()).build();
        List<Brand> brands = List.of(testBrand1, testBrand2);
        List<String> brandsNames = List.of(testBrand1.getName(), testBrand2.getName());
        given(brandRepository.findByName(testBrand1.getName())).willReturn(Optional.of(testBrand1));
        given(brandRepository.findByName(testBrand2.getName())).willReturn(Optional.of(testBrand2));

        assertEquals(brands, brandService.findByNames(brandsNames));
    }

}