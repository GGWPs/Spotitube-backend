package nl.han.oose.DAO;


import nl.han.oose.ConnectionFactory;
import nl.han.oose.objects.Track;
import nl.han.oose.objects.TrackOverview;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO {

    private TrackOverview trackOverview = new TrackOverview();

    @Inject
    private ConnectionFactory connectionFactory;

    public TrackOverview getAllTracks(int playlistId) {
        TrackOverview trackOverview = new TrackOverview();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track INNER JOIN tracksinplaylist ON id = track_id WHERE playlist_id = ?")
        ) {
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    trackOverview = trackInfo(resultSet);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trackOverview;
    }

    public TrackOverview getAllTracksNotInPlaylist(int playlistId) {
        TrackOverview trackOverview = new TrackOverview();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE id NOT IN(SELECT track_id FROM tracksinplaylist WHERE playlist_id = ?)")
        ) {
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    trackOverview = trackInfo(resultSet);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trackOverview;
    }

    private TrackOverview trackInfo(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String performer = resultSet.getString("performer");
        int duration = resultSet.getInt("duration");
        String album = resultSet.getString("album");
        int playcount = resultSet.getInt("playcount");
        String publicationDate = resultSet.getString("publicationDate");
        String description = resultSet.getString("description");
        Boolean offlineAvailable = resultSet.getBoolean("offlineAvailable");

        trackOverview.getTracks().add(new Track(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable));
        return trackOverview;
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