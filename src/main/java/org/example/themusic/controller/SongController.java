package org.example.themusic.controller;




import org.example.themusic.model.Song;
import org.example.themusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    private static String UPLOADED_FOLDER = "uploads/";

    @GetMapping("")
    public String listSongs(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("song", new Song());
        return "/add";
    }

    @PostMapping
    public String saveSong(@ModelAttribute Song song, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                song.setFilePath(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Song song = songService.findById(id);
        model.addAttribute("song", song);
        return "/edit";
    }

    @PostMapping("/{id}")
    public String updateSong(@PathVariable Long id, @ModelAttribute Song song, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                song.setFilePath(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("/{id}")
    public String viewSong(@PathVariable Long id, Model model) {
        Song song = songService.findById(id);
        model.addAttribute("song", song);
        return "/view";
    }

    @GetMapping("/{id}/delete")
    public String deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return "redirect:/songs";
    }
}


