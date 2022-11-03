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

    private static final String PLAYER_GET_QUERY = "SELECT * FROM Player WHERE id = ";
    private static final String PLAYER_GETALL_QUERY = "SELECT * FROM Player";
    private static final String PLAYER_INSERT_QUERY = "INSERT INTO Player VALUES(?,?,?,?,?,?) ";
    private static final String PLAYER_DELETE_QUERY = "DELETE FROM Player p WHERE p.id =:id" ;

    private static final String LEADERBOARD_GETBYID_QUERY = "SELECT p FROM Leaderboard p WHERE p.player_id = :player_id";
    //private static final String LEADERBOARD_GETTOPPLAYERS_QUERY = "SELECT TOP 3 p player.name FROM player, leaderboard p WHERE p.id = :id ORDER BY p.highestPoints";
    private static final String LEADERBOARD_GETTOPPLAYERS_QUERY = "SELECT p FROM Leaderboard p";


    /**
     * player methods
     */
    public Optional<Player> getPlayer(Long playerID) {
        Query getPlayerTypeQuery = entityManager.createNativeQuery(PLAYER_GET_QUERY + playerID, Player.class);
        getPlayerTypeQuery.setParameter("id", playerID);
        return getPlayerTypeQuery.getResultList()
                .stream()
                .findFirst();
    }

    public List<Player> getAllPlayers(){
        Query getAllPlayerSTypeQuery = entityManager.createNativeQuery(PLAYER_GETALL_QUERY, Player.class);
        return getAllPlayerSTypeQuery.getResultList();

    }

    public Player insertStudent(Player player) {
        entityManager.createNativeQuery(PLAYER_INSERT_QUERY)
                .setParameter(1, player.getId())
                .setParameter(2, player.getName())
                .setParameter(3, player.getFirstName())
                .setParameter(4, player.getLastName())
                .setParameter(5, player.getEmail())
                .setParameter(6, player.getHighestPoint())
                .executeUpdate();
        return player;
        /**entityManager.persist(player);
        return player;
         */
    }

    public Player updateStudent(Player player) {
        return entityManager.merge(player);
    }

    public int deleteStudent(Long playerId) {
        TypedQuery<Player> deletePlayerTypeQuery = entityManager.createQuery(PLAYER_DELETE_QUERY, Player.class);
        deletePlayerTypeQuery.setParameter("id", playerId);
        int deletedCount = deletePlayerTypeQuery.executeUpdate();
        return deletedCount;
    }

    /**
     * leaderboard methods
     */
    public Optional<Leaderboard> getPlayerByPlace(Long leaderboardPlayerPlace) {
        TypedQuery<Leaderboard> getPlayerByPlaceTypeQuery = entityManager.createQuery("SELECT p FROM Leaderboard p LIMIT 1 OFFSET "
                                                                                        + leaderboardPlayerPlace + " ORDER BY p.highestPoints "
                                                                                        , Leaderboard.class);
        getPlayerByPlaceTypeQuery.setParameter("place", leaderboardPlayerPlace);
        return getPlayerByPlaceTypeQuery.getResultList()
                .stream()
                .findFirst();
    }

    public Optional<Leaderboard> getPlayerById(Long leaderboardPlayerId) {
        TypedQuery<Leaderboard> getPlayerByIdTypeQuery = entityManager.createQuery(LEADERBOARD_GETBYID_QUERY, Leaderboard.class);
        getPlayerByIdTypeQuery.setParameter("player_id", leaderboardPlayerId);
        return getPlayerByIdTypeQuery.getResultList()
                .stream()
                .findFirst();
    }

    public List<Leaderboard> getTopPlayers() {
        TypedQuery<Leaderboard> getTopPlayersTypeQuery = entityManager.createQuery(LEADERBOARD_GETTOPPLAYERS_QUERY, Leaderboard.class);
        return getTopPlayersTypeQuery.getResultList() ;
    }
}
