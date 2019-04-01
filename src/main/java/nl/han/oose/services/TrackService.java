package nl.han.oose.services;

import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.dto.Token;
import nl.han.oose.dto.Tracks;

import javax.inject.Inject;
import javax.naming.AuthenticationException;

public class TrackService {

    @Inject
    private TokenDAO tokenDAO;
    @Inject
    private TrackDAO trackDAO;

    public Tracks getAllTracksNotInPlaylist(String token, int playlistId) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            return trackDAO.getAllTracksNotInPlaylist(playlistId);
        }
        throw new AuthenticationException("Token incorrect");
    }
}
