package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.el.parser.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author 周林辉
 * @since 2020-05-06 16:36:39
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
    * @Description: 用户登录
    * @Param: [userName, password, model]
    * @return: java.lang.String
    * @Author: Zhou Linhui
    * @Date: 2020/5/6
    */
    @RequestMapping("/user/login")
    public String login(String userName, String password, Model model){
        Subject subject= SecurityUtils.getSubject();
        System.out.println("test 123");
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
        try{
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    /** 
    * @Description: 用户注册 
    * @Param: [userName, password] 
    * @return: java.lang.String 
    * @Author: Zhou Linhui
    * @Date: 2020/5/6 
    */ 
    @ResponseBody
    @ResponseStatus
    @PostMapping("/user/signup")
    public String signUp(@RequestParam("userName") String userName, @RequestParam("password") String password){
        if("".equals(userName)){
            return "用户名不能为空";
        }else if ("".equals(password)){
            return "密码不能为空";
        }

        try {
            userService.insert(new User(1,userName,password));
            return "注册成功";
        } catch (Exception e) {
            return "注册失败";
        }
    }

    /** 
    * @Description: 登出 
    * @Param: []
    * @return: void 
    * @Author: Zhou Linhui
    * @Date: 2020/5/6 
    */ 
    @PostMapping("/user/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @RequestMapping("/toSignup")
    public String toSignup(){
        return "signup";
    }


    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello shiro");
        return "index";
    }
    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/noauth")
    public String unauthorized(){
        return "未授权";
    }
}