package by.artsem.footballrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"players"})
@EqualsAndHashCode(exclude = {"players"})
@Builder
@AllArgsConstructor
@Entity
@Table(name = "club")
@NoArgsConstructor
public class Club implements Serializable {
    @Id
    @SequenceGenerator(name = "clubIdSeqGen", sequenceName = "club_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "clubIdSeqGen")
    private Long id;

    @NotEmpty(message = "Club name should not be empty")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    private List<Player> players;

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }
}
