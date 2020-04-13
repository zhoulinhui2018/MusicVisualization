package com.example.demo.controller;

import com.example.demo.entity.Music;
import com.example.demo.service.MusicService;
import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("")
public class MusicController {

    @Autowired
    private MusicService musicService;

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

    @ResponseStatus
    @ResponseBody
    @PostMapping("upload")
    public String uploadMusic(@RequestParam("filename") MultipartFile multipartFile) throws IOException {
        System.out.println("上传文件===");
        if(multipartFile.isEmpty()){
            return "文件为空";
        }
        String filename=multipartFile.getOriginalFilename();
        filename=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+filename;
        System.out.println("上传文件名为："+filename);

        String path="E:/Java/UploadFiles/"+filename;
        System.out.println("文件保存绝对路径"+path);

        File destfile=new File(path);

        if(destfile.exists()){
            return "文件已存在";
        }
        if(!destfile.getParentFile().exists()){
            destfile.getParentFile().mkdir();
        }

        String url="http://localhost:8080/music/"+filename;
        try {
            multipartFile.transferTo(destfile);
            Music music=new Music();
            music.setName(destfile.getName());
            music.setUrl(url);
            music.setId(1);
            musicService.insert(music);
        } catch (IOException e) {
            return "上传失败";
        }
        return "上传成功,文件url:  "+url;
    }

    @ResponseBody
    @ResponseStatus
    @GetMapping("lists")
    public List<String> getMusicNamesList(){
        return null;
    }
}
