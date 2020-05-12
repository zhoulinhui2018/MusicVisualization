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

@Api(tags = "用户相关接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
            userService.insert(user);
            return "success";
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
    @PostMapping("/user/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
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