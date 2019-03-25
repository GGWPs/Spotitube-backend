package nl.han.oose.objects;

import java.util.ArrayList;
import java.util.List;

public class TracksList {

    private List<Tracks> tracks = new ArrayList<>();

    public TracksList() {
    }

    public TracksList(List<Tracks> tracks) {
        this.tracks = tracks;
    }

    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }
}
