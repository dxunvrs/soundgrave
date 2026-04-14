package com.dxunvrs.soundgrave.controller;

import com.dxunvrs.soundgrave.model.Track;
import com.dxunvrs.soundgrave.repository.MusicStorage;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    private final MusicStorage musicStorage;

    private final Environment env;

    public PlaylistController(MusicStorage musicStorage, Environment env) {
        this.musicStorage = musicStorage;
        this.env = env;
    }

    @GetMapping(value = "/{playlistName}", produces = "audio/x-mpegurl")
    public String getPlaylist(@PathVariable String playlistName) {
        List<Track> tracks = musicStorage.getTracksByPlaylistName(playlistName);

        StringBuilder playlist = new StringBuilder("#EXTM3U\n");
        for (Track track : tracks) {
            playlist.append("#EXTINF:-1,").append(track.path().getFileName()).append("\n");
            playlist.append("http://localhost:")
                    .append(env.getProperty("server.port", String.class, "8080"))
                    .append("/api/tracks/")
                    .append(track.id())
                    .append("/stream\n");
        }
        return playlist.toString();
    }
}
