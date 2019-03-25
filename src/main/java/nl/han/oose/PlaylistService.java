package nl.han.oose;


import nl.han.oose.DAO.PlaylistDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.objects.*;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.naming.AuthenticationException;

@Default
public class PlaylistService {

    @Inject
    private TokenDAO tokenDAO;
    @Inject
    private PlaylistDAO playlistDAO;
    @Inject
    private TrackDAO trackDAO;

    public TracksList getTracksFromPlaylist(String userToken, int playlistId) throws AuthenticationException {
        Token token = tokenDAO.getToken(userToken);
        if (tokenDAO.tokenValidation(token)) {
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public PlaylistAll getPlaylists(String token) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public PlaylistAll editPlaylist(String token, Playlist playlist) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.editPlaylist(playlist);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public PlaylistAll deletePlaylist(String token, int playlistId) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.deletePlaylist(playlistId);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public PlaylistAll addPlaylist(String token, Playlist playlist) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.addPlaylist(userToken, playlist);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public TracksList addTrackToPlaylist(String token, int playlistId, Tracks tracks) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            trackDAO.addTrackToPlaylist(playlistId, tracks);
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public TracksList deleteTrack(String token, int playlistId, int trackId) throws AuthenticationException {
        Token userToken = tokenDAO.getToken(token);
        if (tokenDAO.tokenValidation(userToken)) {
            trackDAO.deleteTrack(playlistId, trackId);
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }
}
