package nl.han.oose.objects;

import java.util.ArrayList;
import java.util.List;



public class PlaylistAll {

    private List<Playlist> allPlaylists = new ArrayList<>();
    private int length;

    public PlaylistAll() {}

    public PlaylistAll(List<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
        this.length = 100;
    }

    public List<Playlist> getAllPlaylists() {
        return allPlaylists;
    }

    public void setAllPlaylists(List<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
