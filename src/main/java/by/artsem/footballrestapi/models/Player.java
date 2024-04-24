package by.artsem.footballrestapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "id")
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

    @NotEmpty(message = "Player price should not be empty")
    @Column(name = "price")
    private Integer price;

    @JsonBackReference
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

    public Player(String name, Club club) {
        this.name = name;
        this.club = club;
    }

    public void addBrand(Brand brand) {
        if(brands == null) {
            brands = new ArrayList<>();
        }
        brands.add(brand);
    }
}
