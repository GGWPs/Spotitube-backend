package nl.han.oose.objects;

import java.util.List;

public class Playlist{
    private int id;
    private String name;
    private boolean owner;
    private List<Tracks> tracks;

    public Playlist(){}

    public Playlist(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Playlist(int id, String name, boolean owner, List<Tracks> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean getOwner(){
        return owner;
    }

    public void setOwner(boolean owner){
        this.owner = owner;
    }

    public List<Tracks> getTracks(){
        return tracks;
    }

    public void setTracks(List<Tracks> tracks){
        this.tracks = tracks;
    }

}
