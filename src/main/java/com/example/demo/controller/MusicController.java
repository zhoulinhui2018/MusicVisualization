package com.example.demo.controller;

import com.example.demo.entity.Music;
import com.example.demo.entity.User;
import com.example.demo.service.MusicService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javazoom.jl.player.Player;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Api("音乐相关接口")
@RestController
@RequestMapping("music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 服务器上的保存路径为 ： /root/UploadFiles/
     */
    @Value("${zhou.musicPath}")
    private String absolutePath;

    /**
     * @Description: 在服务器上播放音乐
     * @Param: []
     * @return: void
     * @Author: Zhou Linhui
     * @Date: 2020/4/14
     */
    @GetMapping("play")
    public void playMusic() throws Exception {
        File file = new File("E:/云音乐/周杰伦 - 你听得到.mp3");
        FileInputStream fileInputStream = new FileInputStream(file);
        Player player = new Player(fileInputStream);
        player.play();
    }


    /**
     * @Description: 上传音乐文件，返回值是文件保存url
     * @Param: [multipartFile]
     * @return: java.lang.String
     * @Author: Zhou Linhui
     * @Date: 2020/4/13
     */
    @ResponseStatus
    @ResponseBody
    @ApiOperation("上传音乐")
    @PostMapping("upload")
    public String uploadMusic(@RequestParam("file")
                              @ApiParam("文件")
                                      MultipartFile multipartFile) throws IOException {
        System.out.println("上传文件===");
        if (multipartFile.isEmpty()) {
            return "文件为空";
        }
        String filename = multipartFile.getOriginalFilename();
//        filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + filename;
        System.out.println("上传文件名为：" + filename);
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        String path = absolutePath + loginUser.getName() + "/" + filename;
        System.out.println("文件保存绝对路径" + path);

        File destfile = new File(path);

        if (destfile.exists()) {
            return "文件已存在";
        }
        if (!destfile.getParentFile().exists()) {
            destfile.getParentFile().mkdir();
        }
//        服务器IP：62.234.154.66
        String url = "http://localhost:8080/music/" + filename;
        try {
            multipartFile.transferTo(destfile);
            Music music = new Music();
            music.setName(destfile.getName());
            music.setUrl(url);
            music.setId(1);
            musicService.insert(music);
        } catch (IOException e) {
            return "上传失败";
        }
        return "上传成功,文件url:  " + url;
    }

    /**
     * @Description: 用来返回音乐名称与Url的键值对
     * @Param: [page, limit] 分页
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: Zhou Linhui
     * @Date: 2020/4/13
     */
    @ResponseBody
    @ResponseStatus
    @GetMapping("lists")
    public Map<String, String> getMusicNamesList(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "15") int limit) {
//        PageHelper.startPage(page, limit);不能使用是因为pagehelper底层很有可能也是用到了limit去数据库中查的
        List<Music> musicList = musicService.queryAll(page, limit);
        Map<String, String> map = new TreeMap<>();
        for (Music music : musicList) {
            StringBuilder name = new StringBuilder(music.getName());
            map.put(name.substring(name.indexOf("_") + 1), music.getUrl());
        }
        return map;
    }
}
