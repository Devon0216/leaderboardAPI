package za.co.nedj.app.nedj.leaderboardapi.service;

import za.co.nedj.app.nedj.leaderboardapi.model.Leaderboard;
import za.co.nedj.app.nedj.leaderboardapi.model.Player;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class DataService {

    @PersistenceContext(name = "nfpePU")
    private EntityManager entityManager;

    private static final String PLAYER_GETALL_QUERY = "SELECT p FROM Player p";


    private static final String LEADERBOARD_GET_BY_ID_QUERY = "SELECT * FROM Leaderboard WHERE player_id = ?";
    private static final String LEADERBOARD_GET_TOP_PLAYERS_QUERY = "SELECT* FROM (SELECT *, row_number() over " +
            "(order by highest_points desc) FROM Leaderboard) Leaderboard WHERE row_number <= 10";


    /**
     * player methods
     */
    public Optional<Player> getPlayer(Long playerID) {
        Player getResult = entityManager.find(Player.class, playerID) ;
        Optional<Player> result = Optional.ofNullable(getResult) ;
        return result ;
    }

    public List<Player> getAllPlayers(){
        TypedQuery<Player> getAllPlayersTypeQuery = entityManager.createQuery(PLAYER_GETALL_QUERY, Player.class);
        return getAllPlayersTypeQuery.getResultList();
    }

    public Player insertPlayer(Player player) {
        entityManager.persist(player);
        return player;
    }

    public Player updatePlayer(Player player) {
        entityManager.merge(player);
        return player;
    }

    public Player deletePlayer(Long playerId) {
        Player player = entityManager.find(Player.class, playerId);
        entityManager.remove(player);
        return player;
    }

    /**
     * leaderboard methods
     */

    public Optional<Leaderboard> getPlayerById(Long leaderboardPlayerId) {
        Query getPlayerByIdQuery = entityManager.createNativeQuery(LEADERBOARD_GET_BY_ID_QUERY, Leaderboard.class);
        getPlayerByIdQuery.setParameter(1, leaderboardPlayerId);
        Optional<Leaderboard> result = getPlayerByIdQuery.getResultList().stream().findFirst() ;
        return result;
    }

    public List<Leaderboard> getTopPlayers() {
        Query getTopPlayersQuery = entityManager.createNativeQuery(LEADERBOARD_GET_TOP_PLAYERS_QUERY, Leaderboard.class);
        return getTopPlayersQuery.getResultList() ;
    }

    public Leaderboard insertLeaderboardPlayer(Leaderboard leaderboard) {
        entityManager.persist(leaderboard);
        return leaderboard;
    }

    public Leaderboard updateLeaderboardPlayer(Leaderboard leaderboard) {
        entityManager.merge(leaderboard);
        return leaderboard;
    }

    public Leaderboard deleteLeaderboardPlayer(Long playerId) {
        Leaderboard leaderboardPlayer = entityManager.find(Leaderboard.class, playerId);
        entityManager.remove(leaderboardPlayer);
        return leaderboardPlayer;
    }
}
