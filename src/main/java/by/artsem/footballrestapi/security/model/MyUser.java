package by.artsem.footballrestapi.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @SequenceGenerator(name = "userIdSeqGen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "userIdSeqGen")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;

    @Column(name = "created_who")
    private String createdWho;

    @Column(name = "role")
    private String role;

}
