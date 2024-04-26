package by.artsem.footballrestapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"club", "brands"})
@EqualsAndHashCode(exclude = {"club", "brands"})
@Entity
@Table(name = "player")
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @SequenceGenerator(name = "playerIdSeqGen", sequenceName = "player_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "playerIdSeqGen")
    private Long id;

    @NotEmpty(message = "Player name should not be empty")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Price can't be less then 0 millions euro")
    @Max(value = 500, message = "Price can't be more then 500 millions euro")
    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany
    @JoinTable(
            name = "player_brand",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private List<Brand> brands;

    public void addBrand(Brand brand) {
        if (brands == null) {
            brands = new ArrayList<>();
        }
        brands.add(brand);
    }

    public void deleteBrand(Brand brand) {
        brands.remove(brand);
    }
}
