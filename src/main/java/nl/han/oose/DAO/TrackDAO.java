package nl.han.oose.DAO;


import nl.han.oose.ConnectionFactory;
import nl.han.oose.dto.Track;
import nl.han.oose.dto.Tracks;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

public class TrackDAO {

    private Tracks tracks = new Tracks();

    @Inject
    private ConnectionFactory connectionFactory;

    public Tracks getAllTracks(int playlistId) {
        Tracks tracks = new Tracks();
        try (Session session = connectionFactory.getNeo4JConnection().session() )
        {
            tracks = session.readTransaction(transaction -> {
                    Result result = transaction.run( "MATCH (p:Playlist {id: $playlistId})-[:CONTAINS]->(t:Track) return t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publicationDate, t.description, t.offlineAvailable",
                            parameters( "playlistId", playlistId));
                    Tracks tempTracks = new Tracks();
                    while (result.hasNext()) {
                        Record record = result.next();
                        Track track = new Track(record.get(0).asInt(),
                                record.get(1).asString(),
                                record.get(2).asString(),
                                record.get(3).asInt(),
                                record.get(4).asString(),
                                record.get(5).asInt(),
                                record.get(6).asLocalDate() + "",
                                record.get(7).asString(),
                                record.get(8).asBoolean());
                        tempTracks.getTracks().add(track);
                    }
                    return tempTracks;
                });
        }
        return tracks;
    }

    public Tracks getAllTracksNotInPlaylist(int playlistId) {
        Tracks tracks = new Tracks();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE id NOT IN(SELECT track_id FROM tracksinplaylist WHERE playlist_id = ?)")
        ) {
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    tracks = trackInfo(resultSet);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracks;
    }

    private Tracks trackInfo(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String performer = resultSet.getString("performer");
        int duration = resultSet.getInt("duration");
        String album = resultSet.getString("album");
        int playcount = resultSet.getInt("playcount");
        String publicationDate = resultSet.getString("publicationDate");
        String description = resultSet.getString("description");
        Boolean offlineAvailable = resultSet.getBoolean("offlineAvailable");

        tracks.getTracks().add(new Track(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable));
        return tracks;
    }

    public void addTrackToPlaylist(int playlistId, Track track) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tracksinplaylist (playlist_id, track_id, offlineAvailable) VALUES(?,?,?)");
        ) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, track.getId());
            preparedStatement.setBoolean(3, track.getOfflineAvailable());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrack(int playlistId, int trackId) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tracksinplaylist WHERE playlist_id = ? AND track_id = ?")) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}