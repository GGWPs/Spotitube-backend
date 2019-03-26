package nl.han.oose.DAO;


import nl.han.oose.ConnectionFactory;
import nl.han.oose.objects.Tracks;
import nl.han.oose.objects.TracksList;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO {

    private TracksList tracksList = new TracksList();

    @Inject
    private ConnectionFactory connectionFactory;

    public TracksList getAllTracks(int playlistId) {
        TracksList tracksList = new TracksList();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track INNER JOIN tracksinplaylist ON id = track_id WHERE playlist_id = ?")
        ) {
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    tracksList = trackInfo(resultSet);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracksList;
    }

//    public TracksList getAllTracksNotInPlaylist(int playlistId) {
//        TracksList tracksList = new TracksList();
//
//        try (
//                Connection connection = connectionFactory.getConnection();
//                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE id NOT IN(SELECT track_id FROM tracksinplaylist WHERE playlist_id = ?)")
//        ) {
//            statement.setInt(1, playlistId);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                try {
//                    tracksList = trackInfo(resultSet);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return tracksList;
//    }

    private TracksList trackInfo(ResultSet resultSet) throws Exception {
        int id = resultSet.getByte("id");
        String title = resultSet.getString("title");
        String performer = resultSet.getString("performer");
        long duration = resultSet.getLong("duration");
        String album = resultSet.getString("album");
        long playcount = resultSet.getLong("playcount");
        String publicationDate = resultSet.getString("publicationDate");
        String description = resultSet.getString("description");
        Boolean offlineAvailable = resultSet.getBoolean("offlineAvailable");

        tracksList.getTracks().add(new Tracks(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable));
        return tracksList;
    }

    public void addTrackToPlaylist(int playlistId, Tracks tracks) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tracksinplaylist (playlist_id, track_id, offlineAvailable) VALUES(?,?,?)");
        ) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setLong(2, tracks.getId());
            preparedStatement.setBoolean(3, tracks.getOfflineAvailable());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrack(int playlistId, int trackId) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tracksinplaylist WHERE playlist_id = ? AND track_id = ?");
        ) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

