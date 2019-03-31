package nl.han.oose;

import nl.han.oose.endpoints.TrackController;
import nl.han.oose.objects.Token;
import nl.han.oose.objects.TrackOverview;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        TrackOverview tracksOverview = new TrackOverview();
        Token correctToken = new Token("1233-1234-1234", "testz");

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
