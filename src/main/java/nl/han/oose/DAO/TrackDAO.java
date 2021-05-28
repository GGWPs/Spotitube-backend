package nl.han.oose.DAO;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import nl.han.oose.ConnectionFactory;
import nl.han.oose.dto.Track;
import nl.han.oose.dto.Tracks;
import org.bson.Document;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO {

    private Tracks tracks = new Tracks();

    @Inject
    private ConnectionFactory connectionFactory;

    public Tracks getAllTracks(int playlistId) {
        Tracks tracks = new Tracks();
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("track");
        BasicDBObject query = new BasicDBObject();
        query.put("playlistId", playlistId);
        MongoCursor<Document> cursor = collection.find(query).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            Track track = new Track(document.getInteger("id"),
                    document.getString("title"),
                    document.getString("performer"),
                    document.getInteger("duration"),
                    document.getString("album"),
                    document.getInteger("playcount"),
                    document.getDate("publicationDate").toString(),
                    document.getString("description"),
                    document.getBoolean("offlineAvailable")
                    );
            tracks.getTracks().add(track);
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
