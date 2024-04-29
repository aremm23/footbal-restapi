package by.artsem.footballrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
@ToString(exclude = "players")
@EqualsAndHashCode(exclude = "players")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @SequenceGenerator(name = "brandIdSeqGen", sequenceName = "brand_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "brandIdSeqGen")
    private Long id;

    @NotEmpty(message = "Brand name should not be empty")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "brands")
    private List<Player> players;

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }
}
