package nl.han.oose;

import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.dto.Token;
import nl.han.oose.dto.Tracks;
import nl.han.oose.services.TrackService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import javax.naming.AuthenticationException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private TrackDAO trackDAO;

    @Mock
    private TokenDAO tokenDAO;

    @InjectMocks
    private TrackService sut;

    @Test
    public void testGetTracksNotInPlaylistReturnsTrackOverviewIfTokenIsCorrect() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();
        Token token = new Token("1234-1234-1234", "test");

        //TEST
        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(trackDAO.getAllTracksNotInPlaylist(anyInt())).thenReturn(tracksOverview);

        //VERIFY
        assertEquals(tracksOverview, sut.getAllTracksNotInPlaylist("123", 1));
    }

    @Test
    public void testGetTracksNotInPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        //SETUP
        Token token = new Token("1233-1234-1234", "test");
        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");


        //TEST
        when(tokenDAO.getTokenObject(anyString())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        //VERIFY
        sut.getAllTracksNotInPlaylist("1233-1234-1234", 1);
    }
}
