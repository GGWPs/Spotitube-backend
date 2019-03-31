package nl.han.oose.objects;

import java.util.ArrayList;
import java.util.List;



public class PlaylistAll {

    private List<Playlist> allPlaylists = new ArrayList<>();
    private int length = 10;

    public PlaylistAll() {}

    public PlaylistAll(List<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    public List<Playlist> getPlaylists() {
        return allPlaylists;
    }

    public void setPlaylists(List<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
