package com.yun.user.controller;

import com.yun.common.Constract;
import com.yun.user.model.UserInfo;
import com.yun.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/login")
    public void login(String loginName, String loginPass, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        UserInfo userInfo=userInfoService.findUserInfoByNameAndPass(loginName,loginPass);
        if(null!=userInfo){
            session.setAttribute(Constract.USER_SESSION_KEY,userInfo);
            response.sendRedirect("main");
        }
        response.getWriter().print("<script>alert('用户名或密码不正确');window.location='/'</script>");
    }

    @RequestMapping("/userList")
    public String userList(Model model){
        List<UserInfo> userInfoList=userInfoService.findUserInfoList();
        model.addAttribute("userInfoList",userInfoList);
        return "user/userList";
    }

    @RequestMapping("/userEdit")
    public String userEdit(){
        return "user/userEdit";
    }

    @RequestMapping("/userSave")
    public String userSave(UserInfo userInfo){
        userInfoService.insertUserInfo(userInfo);
        return "redirect:userList";
    }

    @RequestMapping("/deleteUserInfo")
    public String userDelete(int userId){
        userInfoService.deleteUserInfo(userId);
        return "redirect:userList";
    }

}
