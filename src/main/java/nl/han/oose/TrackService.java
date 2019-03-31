package nl.han.oose;

import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.objects.Token;
import nl.han.oose.objects.TrackOverview;

import javax.inject.Inject;
import javax.naming.AuthenticationException;

public class TrackService {

    @Inject
    private TokenDAO tokenDAO;
    @Inject
    private TrackDAO trackDAO;

    public TrackOverview getAllTracksNotInPlaylist(String token, int playlistId) throws AuthenticationException {
        Token userToken = tokenDAO.getTokenObject(token);
        if (tokenDAO.tokenValidation(userToken)) {
            return trackDAO.getAllTracksNotInPlaylist(playlistId);
        }
            throw new AuthenticationException("Token incorrect");
    }
}
