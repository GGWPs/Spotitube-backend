package nl.han.oose.services;


import nl.han.oose.DAO.PlaylistDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.dto.*;

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

    public Tracks getPlaylistTracks(String token, int playlistId) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public PlaylistAll getPlaylists(String token) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }


    public PlaylistAll editPlaylist(String token, Playlist playlist) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.editPlaylist(playlist);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public PlaylistAll deletePlaylist(String token, int playlistId) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.deletePlaylist(playlistId);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public PlaylistAll addPlaylist(String token, Playlist playlist) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            playlistDAO.addPlaylist(userToken, playlist);
            return playlistDAO.getAllPlaylists(userToken);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public Tracks addTrackToPlaylist(String token, int playlistId, Track track) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            trackDAO.addTrackToPlaylist(playlistId, track);
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

    public Tracks deleteTrack(String token, int playlistId, int trackId) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            trackDAO.deleteTrack(playlistId, trackId);
            return trackDAO.getAllTracks(playlistId);
        } else {
            throw new AuthenticationException("Token incorrect");
        }
    }

}
