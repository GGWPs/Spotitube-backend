package nl.han.oose;

import nl.han.oose.DAO.PlaylistDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.objects.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.AuthenticationException;

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
        Token token = new Token("1234-1234-1234", "Nick");
        TrackOverview tracksOverview = new TrackOverview();

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(trackDAO.getAllTracks(Mockito.anyInt())).thenReturn(tracksOverview);

        assertEquals(tracksOverview, sut.getPlaylistTracks("1234-1234-1234", 1));
    }

    @Test
    public void testGetTracksFromPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.getPlaylistTracks("1234-1234-1234", 1);
    }

    //Tests getPlaylists
    @Test
    public void testGetPlaylistsReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "Nick");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(playlistDAO.getAllPlaylists(Mockito.any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.getPlaylists("1234-1234-12342"));
    }

    @Test
    public void testGetPlaylistsReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.getPlaylists("1234-1234-1234");
    }

    //Tests editPlaylist
    @Test
    public void testEditPlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "Nick");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(playlistDAO.getAllPlaylists(Mockito.any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.editPlaylist("1234-1234-1234", new Playlist()));
    }

    @Test
    public void testEditPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.editPlaylist("1234-1234-1234", new Playlist());
    }

    //Tests deletePlaylist
    @Test
    public void testDeletePlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "Nick");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(playlistDAO.getAllPlaylists(Mockito.any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.deletePlaylist("1234-1234-1234", 1));
    }

    @Test
    public void testDeletePlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.deletePlaylist("1234-1234-1234", 1);
    }

    //Tests addPlaylist
    @Test
    public void testAddPlaylistReturnsPlaylistOverviewIfTokenIsCorrect() throws AuthenticationException {
        PlaylistAll playlistOverview = new PlaylistAll();
        Token token = new Token("1234-1234-1234", "Nick");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(playlistDAO.getAllPlaylists(Mockito.any())).thenReturn(playlistOverview);

        assertEquals(playlistOverview, sut.addPlaylist("1234-1234-1234", new Playlist()));
    }

    @Test
    public void testAddPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.addPlaylist("1234-1234-1234", new Playlist());
    }

    //Tests addTrackToPlaylist
    @Test
    public void testAddTrackToPlaylistReturnsTrackOverviewIfTokenIsCorrect() throws AuthenticationException {
        TrackOverview tracksOverview = new TrackOverview();
        Token token = new Token("1234-1234-1234", "Nick");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(trackDAO.getAllTracks(Mockito.anyInt())).thenReturn(tracksOverview);

        assertEquals(tracksOverview, sut.addTrackToPlaylist("123", 1, new Track()));
    }

    @Test
    public void testAddTrackToPlaylistReturnsExceptionIfTokenIsIncorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.addTrackToPlaylist("1234-1234-1234", 1, new Track());
    }

    @Test
    public void testDeleteTrackReturnsTrackOverviewIfTokenIsCorrect() throws AuthenticationException {
        //SETUP
        TrackOverview tracksOverview = new TrackOverview();
        Token token = new Token("1234-1234-1234", "Nick");

        //TEST
        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(true);
        Mockito.when(trackDAO.getAllTracks(Mockito.anyInt())).thenReturn(tracksOverview);

        //VERIFY
        assertEquals(tracksOverview, sut.deleteTrack("1234-1234-1234", 1, 1));
    }

    @Test
    public void testDeleteTrackReturnsExceptionIfTokenIsincorrect() throws AuthenticationException {
        Token token = new Token("1234-1234-1234", "Nick");

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage("Token incorrect");

        Mockito.when(tokenDAO.getTokenObject(Mockito.any())).thenReturn(token);
        Mockito.when(tokenDAO.tokenValidation(Mockito.any(Token.class))).thenReturn(false);

        sut.deleteTrack("1234-1234-1234", 1, 1);
    }
}
