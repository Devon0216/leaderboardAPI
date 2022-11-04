package za.co.nedj.app.nedj.leaderboardapi.model;

import lombok.*;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "leaderboard")
@Entity
public class Leaderboard {
    @Id
    @Column(name = "player_id")
    private long playerId ;
    @Column(name = "player_name")
    private String playerNames ;
    @Column(name = "highest_points")
    private Long highestPoint ;




}
