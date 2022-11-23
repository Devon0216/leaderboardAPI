package za.co.nedj.app.nedj.leaderboardapi.model;

import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player")
@Entity
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.Identity)
    private long id ;
    @Column(name = "name")
    private String name ;
    @Column(name = "first_name")
    private String firstName ;
    @Column(name = "last_name")
    private String lastName ;
    @Column(name = "email")
    private String email ;
    @Column(name = "highest_points")
    private Long highestPoint ;

    @ManyToOne
    @JoinColumn(name = "leader_board")
    private Leaderboard leaderboard ;
}
