package com.dxunvrs.soundgrave.controller;

import com.dxunvrs.soundgrave.repository.MusicStorage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    private final MusicStorage musicStorage;

    public TrackController(MusicStorage musicStorage) {
        this.musicStorage = musicStorage;
    }

    @GetMapping("/{trackId}/stream")
    public ResponseEntity<Resource> streamTrack(@PathVariable Long trackId) {
        String filePath = musicStorage.getFilePath(trackId);
        Path path = Paths.get(filePath);

        try {
            String contentType = Files.probeContentType(path);
            Resource audioFile = new FileSystemResource(filePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(audioFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}