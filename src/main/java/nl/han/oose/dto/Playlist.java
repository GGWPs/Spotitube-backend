package nl.han.oose.dto;

import java.util.List;

public class Playlist{
    private int id;
    private String name;
    private boolean owner;
    private List<Track> tracks;

    public Playlist(){}

    public Playlist(int id, String name, boolean owner, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public boolean getOwner(){
        return owner;
    }

    public void setOwner(boolean owner){
        this.owner = owner;
    }

    public List<Track> getTracks(){
        return tracks;
    }

    public void setTracks(List<Track> tracks){
        this.tracks = tracks;
    }

}
