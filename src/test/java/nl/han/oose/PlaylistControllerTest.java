package nl.han.oose;

import nl.han.oose.controllers.PlaylistController;
import nl.han.oose.dto.Playlist;
import nl.han.oose.dto.PlaylistAll;
import nl.han.oose.dto.Track;
import nl.han.oose.dto.Tracks;
import nl.han.oose.services.PlaylistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import javax.naming.AuthenticationException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController sut;

    @Test
    public void testReturnAllPlaylistsAndStatusOkOnCorrectToken() throws AuthenticationException {
        //SETUP
        PlaylistAll playlistOverview = new PlaylistAll();
        when(playlistService.getPlaylists(any())).thenReturn(playlistOverview);

        //TEST
        Response response = sut.getPlaylist("1234-1234-1234");


        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(playlistOverview, response.getEntity());
    }

    @Test
    public void testGetAllPlaylistsOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.getPlaylists(any())).thenThrow(new AuthenticationException("Token incorrect"));

        //TEST
        Response response = sut.getPlaylist("1234-1234-1234");

        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEditPlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        PlaylistAll playlistOverview = new PlaylistAll();

        when(playlistService.editPlaylist(any(), any(Playlist.class))).thenReturn(playlistOverview);

        //TEST
        Response response = sut.editPlaylist("1234-1234-1234", new Playlist());

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(playlistOverview, response.getEntity());
    }

    @Test
    public void testEditPlaylistOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.editPlaylist(any(), Mockito.any(Playlist.class))).thenThrow(new AuthenticationException("Token incorrect"));

        //TEST
        Response response = sut.editPlaylist("1234-1234-1234", new Playlist());

        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    // Tests GetTracksFromPlaylist
    @Test
    public void testGetTracksFromPlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();
        when(playlistService.getPlaylistTracks(any(), anyInt())).thenReturn(tracksOverview);

        //TEST
        Response response = sut.getTracksFromPlaylist("1234-1234-1234", 1);

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(tracksOverview, response.getEntity());
    }

    //testing neede
    @Test
    public void testGetTracksFromPlaylistOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.getPlaylistTracks(any(), anyInt())).thenThrow(new AuthenticationException("Token incorrect"));

        //TEST
        Response response = sut.getTracksFromPlaylist("1234-1234-1234", 1);

        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }


    @Test
    public void testAddPlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        PlaylistAll playlistOverview = new PlaylistAll();
        when(playlistService.addPlaylist(any(), any(Playlist.class))).thenReturn(playlistOverview);

        //TEST
        Response response = sut.addPlaylist("1234-1234-1234", new Playlist());

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(playlistOverview, response.getEntity());
    }

    @Test
    public void testAddPlaylistOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.addPlaylist(any(), any(Playlist.class))).thenThrow(new AuthenticationException("Usertoken incorrect"));
        //TEST
        Response response = sut.addPlaylist("1234-1234-1234", new Playlist());
        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }


    @Test
    public void testDeletePlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        PlaylistAll playlistOverview = new PlaylistAll();
        when(playlistService.deletePlaylist(any(), anyInt())).thenReturn(playlistOverview);

        //TEST
        Response response = sut.deletePlaylist("1234-1234-1234", 1);

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(playlistOverview, response.getEntity());
    }

    @Test
    public void testDeletePlaylistOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.deletePlaylist(any(), anyInt())).thenThrow(new AuthenticationException("Token incorrect"));
        //TEST
        Response response = sut.deletePlaylist("1234-1234-1234", 1);
        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }


    @Test
    public void testAddTrackToPlaylistOnCorrectToken() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();
        when(playlistService.addTrackToPlaylist(any(), anyInt(), any(Track.class))).thenReturn(tracksOverview);

        //TEST
        Response response = sut.addTrackToPlaylist("1234-1234-1234", 1, new Track());

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(tracksOverview, response.getEntity());
    }

    @Test
    public void testAddTrackToPlaylistOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.addTrackToPlaylist(any(), anyInt(), any(Track.class))).thenThrow(new AuthenticationException("Usertoken incorrect"));
        //TEST
        Response response = sut.addTrackToPlaylist("1234-1234-1234", 1, new Track());
        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }


    @Test
    public void testDeleteTrackOnCorrectToken() throws AuthenticationException {
        //SETUP
        Tracks tracksOverview = new Tracks();

        when(playlistService.deleteTrack(any(), anyInt(), anyInt())).thenReturn(tracksOverview);
        //TEST
        Response response = sut.deleteTrack("1234-1234-1234", 1, 1);
        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(tracksOverview, response.getEntity());
    }

    @Test
    public void testDeleteTrackOnIncorrectToken() throws AuthenticationException {
        //SETUP
        when(playlistService.deleteTrack(any(), anyInt(), anyInt())).thenThrow(new AuthenticationException("Token incorrect"));
        //TEST
        Response response = sut.deleteTrack("1234-1234-1234", 1, 1);
        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
