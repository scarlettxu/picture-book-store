package com.sx.books.web.controller;

import com.sx.books.meta.User;
import com.sx.books.service.impl.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by scarlettxu on 16-6-23.
 */
@Controller
public class LoginController {
    private WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
    @RequestMapping("/api/login")
    public String loginsubmit(@RequestParam("userName") String userName,
                      @RequestParam("password") String password,
                      HttpServletResponse response,
                      HttpSession session,ModelMap map) throws IOException,ServletException {

        User loginUser = new User();
        System.out.println("user name is: "+userName+", password is: "+password);
        loginUser.setUserName(userName);
        loginUser.setPassword(password);

        LoginService service = context.getBean("loginService",LoginService.class);
        User user=service.login(loginUser);

        if (user!=null) {
            System.out.println("get user from db, user name: "+user.getUserName()+", user type: "+user.getUserType());
            session.setAttribute("password",user.getPassword());
            session.setAttribute("userName",user.getUserName());
            session.setAttribute("userType",user.getUserType());
            session.setAttribute("id",user.getId());

            map.addAttribute("code",200);
            map.addAttribute("message","Login Successful");
            map.addAttribute("result",true);
        }
        else {
            map.addAttribute("code",404);
            map.addAttribute("message","Login Fail");
            map.addAttribute("result",false);
        }
//        response.sendRedirect("/webapp/template/");

        return "postlogin";
    }

    @RequestMapping("/login")
    public String login(ModelMap map) throws IOException,ServletException{
        return "login";
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies
             ) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        response.sendRedirect("/webapp/template/login");
    }
}
