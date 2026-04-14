package com.dxunvrs.soundgrave.repository;

import com.dxunvrs.soundgrave.exception.TrackNotFoundException;
import com.dxunvrs.soundgrave.model.Track;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MusicStorage {
    private final Map<String, List<Track>> playlistTracks = new ConcurrentHashMap<>();
    private final Map<Long, Track> allTracks = new ConcurrentHashMap<>();

    public void addTrack(String playlistName, Track track) {
        playlistTracks.computeIfAbsent(playlistName, k -> new CopyOnWriteArrayList<>()).add(track);
        allTracks.put(track.id(), track);
    }

    public List<Track> getTracksByPlaylistName(String playlistName) {
        return playlistTracks.getOrDefault(playlistName, Collections.emptyList());
    }

    public String getFilePath(Long trackId) {
        Track track = allTracks.get(trackId);
        if (track == null) {
            throw new TrackNotFoundException("Трек не найден");
        }
        return track.path().toString();
    }
}
