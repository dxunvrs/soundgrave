package com.dxunvrs.soundgrave.service;

import com.dxunvrs.soundgrave.model.Track;
import com.dxunvrs.soundgrave.repository.MusicStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicScannerService {
    private final MusicStorage musicStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    private static final String MUSIC_DIR = "/app/music";

    @EventListener(ApplicationReadyEvent.class)
    public void scanMusicFolder() {
        Path root = Paths.get(MUSIC_DIR);
        if (!Files.exists(root)) {
            log.error("Папка не найдена");
            return;
        }

        try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(Files::isRegularFile)
                    .filter(this::isAudioFile)
                    .forEach(path -> {
                        String playlistName = path.getParent().getFileName().toString();
                        Track track = new Track(idGenerator.getAndIncrement(), path.getFileName().toString(), path);
                        musicStorage.addTrack(playlistName, track);
                    });
        } catch (IOException e) {
            log.error("Ошибка при сканировании папки", e);
        }
    }

    private boolean isAudioFile(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".flac");
    }
}
