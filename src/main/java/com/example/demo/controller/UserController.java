package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.el.parser.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;

/**
 * (User)表控制层
 *
 * @author 周林辉
 * @since 2020-05-06 16:36:39
 */

@Api(tags = "用户相关接口")
@Controller
@CrossOrigin(origins = "http://62.234.154.66:3000",maxAge = 3600,allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${zhou.musicPath}")
    private String absolutePath;

    @ResponseBody
    @ApiOperation("用户未登录，这是当用户未登录时请求其他接口会被拦截并调用此接口")
    @GetMapping("/user/unlogin")
    public String unlogin() {
        return "user not logged in";
    }

    /**
     * @Description: 用户登录
     * @Param: [userName, password, model]
     * @return: java.lang.String
     * @Author: Zhou Linhui
     * @Date: 2020/5/6
     */
    @ApiOperation("用户登录")
    @ResponseBody
    @PostMapping("/user/login")
    public String login(@RequestBody
                            @ApiParam("用户信息")
                                    User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            return "success";
        } catch (UnknownAccountException e) {//用户名不存在
//            model.addAttribute("msg","用户名错误");
            return "user name does not exist";
        } catch (IncorrectCredentialsException e) {
//            model.addAttribute("msg","密码错误");
            return "password error";
        }
    }

    @ApiOperation("调取此接口获得当前登录用户的用户名")
    @ResponseBody
    @GetMapping("/user/name")
    public String getUserName(){

        Subject subject= SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        if (currentUser!=null){
            return currentUser.getUserName();
        }else {
            return "user not logged in";
        }
    }

    /**
     * @Description: 用户注册
     * @Param: [userName, password]
     * @return: java.lang.String
     * @Author: Zhou Linhui
     * @Date: 2020/5/6
     */
    @ApiOperation("用户注册")
    @ResponseBody
    @PostMapping("/user/signup")
    public String signUp(@RequestBody
                             @ApiParam("用户信息")
                                     User user) {
        if ("".equals(user.getUserName())) {
            return "用户名不能为空";
        } else if ("".equals(user.getPassword())) {
            return "密码不能为空";
        }

        User oriUser = userService.queryUserByName(user.getUserName());
        if (oriUser != null) {
            return "user name already exists";
        }
        try {
            synchronized (this) {
                userService.insert(user);
                File musicDirectory = new File(absolutePath + "/" + user.getUserName());
                if (!musicDirectory.exists()) {
                    musicDirectory.mkdir();
                }
                return "success";
            }
        } catch (Exception e) {
            return "failure";
        }
    }

    /**
     * @Description: 登出
     * @Param: []
     * @return: void
     * @Author: Zhou Linhui
     * @Date: 2020/5/6
     */
    @ApiOperation("用户注销")
    @ResponseBody
    @PostMapping("/user/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }

    @RequestMapping("/toSignup")
    public String toSignup() {
        return "signup";
    }


    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String tologin() {
        return "login";
    }

    @RequestMapping("/noauth")
    public String unauthorized() {
        return "未授权";
    }
}