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

    private static final String PLAYER_GET_QUERY = "SELECT * FROM Player WHERE id = ? ";
    private static final String PLAYER_GETALL_QUERY = "SELECT * FROM Player";
    private static final String PLAYER_INSERT_QUERY = "INSERT INTO Player VALUES(?,?,?,?,?,?) ";
    private static final String PLAYER_UPDATE_QUERY = "UPDATE Player SET name=?,first_name=?," +
                                                        "last_name=?,email=?,highest_points=?" +
                                                        " WHERE id=?" ;
    private static final String PLAYER_DELETE_QUERY = "DELETE FROM Player WHERE id = ?" ;


    private static final String LEADERBOARD_GET_BY_ID_QUERY = "SELECT * FROM Leaderboard WHERE player_id = ?";
    //private static final String LEADERBOARD_GET_BY_PLACE_QUERY = "SELECT * FROM Leaderboard LIMIT 1 OFFSET ? order by highestPoints";

    private static final String LEADERBOARD_GET_TOP_PLAYERS_QUERY = "SELECT* FROM (SELECT *, row_number() over " +
            "(order by highest_points desc) FROM Leaderboard) Leaderboard WHERE row_number <= 10";



    private static final String LEADERBOARD_INSERT_QUERY = "INSERT INTO Leaderboard VALUES(?,?,?) ";
    private static final String LEADERBOARD_UPDATE_QUERY = "UPDATE Leaderboard SET player_name=?," +
            "highest_points=?" +
            " WHERE player_id=?" ;
    private static final String LEADERBOARD_DELETE_QUERY = "DELETE FROM Leaderboard WHERE player_id = ?" ;


    /**
     * player methods
     */
    public Optional<Player> getPlayer(Long playerID) {
        Query getPlayerQuery = entityManager.createNativeQuery(PLAYER_GET_QUERY, Player.class);
        getPlayerQuery.setParameter(1, playerID);
        Optional<Player> result = getPlayerQuery.getResultList().stream().findFirst() ;

        return result ;
    }

    public List<Player> getAllPlayers(){
        Query getAllPlayerSQuery = entityManager.createNativeQuery(PLAYER_GETALL_QUERY, Player.class);
        return getAllPlayerSQuery.getResultList();

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
        entityManager.createNativeQuery(LEADERBOARD_INSERT_QUERY)
                .setParameter(1, player.getId())
                .setParameter(2, player.getName())
                .setParameter(3, player.getHighestPoint())
                .executeUpdate();
        return player;
    }

    public Integer updateStudent(Player player) {

        int updateCount = entityManager.createNativeQuery(PLAYER_UPDATE_QUERY)
                .setParameter(1, player.getName())
                .setParameter(2, player.getFirstName())
                .setParameter(3, player.getLastName())
                .setParameter(4, player.getEmail())
                .setParameter(5, player.getHighestPoint())
                .setParameter(6, player.getId())
                .executeUpdate() ;
        entityManager.createNativeQuery(LEADERBOARD_UPDATE_QUERY)
                .setParameter(1, player.getName())
                .setParameter(2, player.getHighestPoint())
                .setParameter(3, player.getId())
                .executeUpdate();
        return updateCount;

    }

    public int deleteStudent(Long playerId) {
        int deletedCount = entityManager.createNativeQuery(PLAYER_DELETE_QUERY)
                .setParameter(1, playerId)
                .executeUpdate();
        entityManager.createNativeQuery(LEADERBOARD_DELETE_QUERY)
                .setParameter(1, playerId)
                .executeUpdate();
        return deletedCount;
    }

    /**
     * leaderboard methods
     */
//    public Optional<Leaderboard> getPlayerByPlace(Long leaderboardPlayerPlace) {
//        Query getPlayerByPlaceQuery = entityManager.createNativeQuery(LEADERBOARD_GET_BY_PLACE_QUERY);
//        getPlayerByPlaceQuery.setParameter(1, leaderboardPlayerPlace);
//        return getPlayerByPlaceQuery.getResultList()
//                .stream()
//                .findFirst();
//    }

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
}
