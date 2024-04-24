package by.artsem.footballrestapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "players")
@Entity
@Table(name = "club")
@NoArgsConstructor
public class Club {
    @Id
    @SequenceGenerator(name = "clubIdSeqGen", sequenceName = "club_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "clubIdSeqGen")
    private Long id;

    @NotEmpty(message = "Club name should not be empty")
    @Column(name = "name")
    private String name;

    //@JsonBackReference
    @OneToMany(mappedBy = "club")
    private List<Player> players;

    public Club(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        if(players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }
}
