package nl.han.oose.objects;

import java.util.ArrayList;
import java.util.List;

public class TrackOverview {

    private List<Track> tracks = new ArrayList<>();

    public TrackOverview(){}

    public List<Track> getTracks(){
        return tracks;
    }

}
