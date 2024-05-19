package org.example.themusic.service;

import org.example.themusic.model.Song;

import java.util.List;

public interface ISongService {
    List<Song> findAll();
    Song findById(Long id);
    void save(Song song);
    void delete(Long id);
}
