package nl.han.oose.objects;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAll {

    private List<Playlist> playlists = new ArrayList<>();
    private int length;

    public PlaylistAll() {}

    public PlaylistAll(List<Playlist> playlists) {
        this.playlists = playlists;
        this.length = 100;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
