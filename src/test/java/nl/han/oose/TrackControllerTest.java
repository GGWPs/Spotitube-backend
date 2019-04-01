package nl.han.oose;

import nl.han.oose.controllers.TrackController;
import nl.han.oose.dto.Token;
import nl.han.oose.dto.Tracks;
import nl.han.oose.services.TrackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import javax.naming.AuthenticationException;
import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class TrackControllerTest {

    @Mock
    private TrackService trackService;

    @InjectMocks
    private TrackController sut;

    @Test
    public void testReturnTracksNotInPlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();
        Token correctToken = new Token("1233-1234-1234", "test");

        //TEST
        when(trackService.getAllTracksNotInPlaylist(any(), anyInt())).thenReturn(tracksOverview);
        Response response = sut.getAllTracksNotInPlaylist(correctToken.getToken(), 1);

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        //assertEquals(tracksOverview, response.getEntity());
    }

    @Test
    public void testReturnAllTracksNotInPlaylistUnauthorizedOnIncorrectToken() throws AuthenticationException {
        //SETUP
        Token badToken = new Token("1234-1234-1234", "test");

        //TEST
        when(trackService.getAllTracksNotInPlaylist(any(), anyInt())).thenThrow(new AuthenticationException("Token incorrect"));
        Response response = sut.getAllTracksNotInPlaylist(badToken.getToken(), 1);

        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
