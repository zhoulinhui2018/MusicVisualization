package com.example.demo.MusicController;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("music")
public class MusicController {
    @GetMapping("play")
    public void playMusic() throws Exception {
        File file=new File("E:/云音乐/周杰伦 - 你听得到.mp3");
        FileInputStream fileInputStream=new FileInputStream(file);
        Player player=new Player(fileInputStream);
        player.play();
    }

    @GetMapping("hello")
    public ResponseEntity sayHello(){
        return ResponseEntity.ok("helloworld!");
    }
}
