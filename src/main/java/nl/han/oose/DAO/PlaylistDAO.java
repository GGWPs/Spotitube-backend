package nl.han.oose.DAO;


import nl.han.oose.ConnectionFactory;
import nl.han.oose.objects.Playlist;
import nl.han.oose.objects.PlaylistAll;
import nl.han.oose.objects.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {
    private int playlistLength;
    private ConnectionFactory connectionFactory;

    public PlaylistDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public PlaylistAll getAllPlaylists(Token token) {
        PlaylistAll playlistAll = new PlaylistAll();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM playlist")
        ) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getByte("id");
                String name = resultSet.getString("name");
                Boolean owner = resultSet.getBoolean("owner");

                playlistAll.getPlaylists().add(new Playlist(id, name, owner, new ArrayList<>()));
                playlistLength += getLength(id);
            }
            playlistAll.setLength(playlistLength);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistAll;
    }

    private int getLength(int playlistId) {
        int playlistLength = 0;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select sum(duration)  AS length from track where id in(select track_id from tracksinplaylist where playlist_id = ?)")) {
            preparedStatement.setInt(1, playlistId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                playlistLength += resultSet.getInt("length");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistLength;
    }

    public void editPlaylist(Playlist playlist) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET naam = ? WHERE id = ?")) {
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(int playlistId) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM playlist WHERE id = ?")
        ) {
            statement.setInt(1, playlistId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlaylist(Token token, Playlist playlist) {
        playlist.setOwner(true);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("select count(*) as total FROM playlist")
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int newId = resultSet.getInt("total") + 1;
                PreparedStatement insert = connection.prepareStatement("INSERT INTO playlist (id, name, owner) VALUES (?,?,?)");
                insert.setInt(1, newId);
                insert.setString(2, playlist.getName());
                insert.setBoolean(3, playlist.getOwner());
                insert.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
