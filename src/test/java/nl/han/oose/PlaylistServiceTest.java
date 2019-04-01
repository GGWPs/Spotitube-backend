package nl.han.oose;

import nl.han.oose.DAO.PlaylistDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.dto.*;
import nl.han.oose.services.PlaylistService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.naming.AuthenticationException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private TrackDAO trackDAO;

    @Mock
    private PlaylistDAO playlistDAO;

    @Mock
    private TokenDAO tokenDAO;

    @InjectMocks
    private PlaylistService sut;

    @Test
    public void testGetTracksFromPlaylistReturnsTracksOverviewIfTokenIsCorrect() throws AuthenticationException {
        //SETUP
        Token token = new Token("1234-1234-1234", "test");
        Tracks tracksOverview = new Tracks();
        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(trackDAO.getAllTracks(anyInt())).thenReturn(tracksOverview);

        //TEST && VERIFY
        assertEquals(tracksOverview, sut.getPlaylistTracks("1234-1234-1234", 1));
    }

    @Test
    public void testGetTracksFromPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.getPlaylistTracks("1234-1234-1234", 1);
    }

    @Test
    public void testGetPlaylistsReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "test");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(playlistDAO.getAllPlaylists(any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.getPlaylists("1234-1234-12342"));
    }

    @Test
    public void testGetPlaylistsReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.getPlaylists("1234-1234-1234");
    }

    @Test
    public void testEditPlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "test");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(playlistDAO.getAllPlaylists(any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.editPlaylist("1234-1234-1234", new Playlist()));
    }

    @Test
    public void testEditPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.editPlaylist("1234-1234-1234", new Playlist());
    }

    @Test
    public void testDeletePlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll allPlaylists = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "Test");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(playlistDAO.getAllPlaylists(any())).thenReturn(allPlaylists);

        assertEquals(allPlaylists, sut.deletePlaylist("1234-1234-1234", 1));
    }

    @Test
    public void testDeletePlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.deletePlaylist("1234-1234-1234", 1);
    }

    @Test
    public void testAddPlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "test");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(playlistDAO.getAllPlaylists(any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.addPlaylist("1234-1234-1234", new Playlist()));
    }

    @Test
    public void testAddPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.addPlaylist("1234-1234-1234", new Playlist());
    }

    @Test
    public void testAddTrackToPlaylistReturnsTrackOverviewIfTokenIsCorrect() throws AuthenticationException {
        Tracks tracksOverview = new Tracks();
        Token token = new Token("1234-1234-1234", "Test");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(trackDAO.getAllTracks(anyInt())).thenReturn(tracksOverview);

        assertEquals(tracksOverview, sut.addTrackToPlaylist("123", 1, new Track()));
    }

    @Test
    public void testAddTrackToPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.addTrackToPlaylist("1234-1234-1234", 1, new Track());
    }

    @Test
    public void testDeleteTrackReturnsTrackOverviewIfTokenIsCorrect() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();
        Token token = new Token("1234-1234-1234", "Test");

        //TEST
        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(true);
        when(trackDAO.getAllTracks(anyInt())).thenReturn(tracksOverview);

        //VERIFY
        assertEquals(tracksOverview, sut.deleteTrack("1234-1234-1234", 1, 1));
    }

    @Test
    public void testDeleteTrackReturnsExceptionIfTokenIsincorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Test");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        when(tokenDAO.getTokenObject(any())).thenReturn(token);
        when(tokenDAO.tokenValidation(any(Token.class))).thenReturn(false);

        sut.deleteTrack("1234-1234-1234", 1, 1);
    }
}
