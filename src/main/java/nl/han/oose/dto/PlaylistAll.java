package nl.han.oose.dto;

import java.util.ArrayList;
import java.util.List;



public class PlaylistAll {

    private List<Playlist> allPlaylists = new ArrayList<>();
    private int length = 10;

    public PlaylistAll() {}


    public List<Playlist> getPlaylists() {
        return allPlaylists;
    }


    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
