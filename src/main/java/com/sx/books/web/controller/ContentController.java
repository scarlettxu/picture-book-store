package com.sx.books.web.controller;

import com.sx.books.meta.Content;
import com.sx.books.meta.Product;
import com.sx.books.meta.User;
import com.sx.books.service.impl.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by scarlettxu on 16-6-23.
 */
@Controller
public class ContentController {

    private WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
        @RequestMapping("/")
        public String showAll(HttpServletRequest request, HttpSession session, ModelMap map) throws IOException,ServletException {
            if (session.getAttribute("userName") !=null){
                System.out.println("session userName: "+session.getAttribute("userName"));
                User user = new User();
                user.setUserName((String) session.getAttribute("userName"));
                user.setUserType((Short) session.getAttribute("userType"));
                map.addAttribute("user", user);
                System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
            }else map.addAttribute("user",null);

            ContentService service = context.getBean("contentService",ContentService.class);
            List<Product> contents = service.showAll();
            for (Product product:contents
                 ) {
                System.out.println(product.getTitle() +", "+product.isBuy()+", "+product.isSell());
            }
            map.addAttribute("productList",contents);

            return "index";

        }


    @RequestMapping("/show")
    public String show(HttpServletRequest request, HttpSession session, ModelMap map,@RequestParam("id") int id) throws IOException,ServletException{

        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        ContentService service = context.getBean("contentService",ContentService.class);
        Product content = service.show(id);
        map.addAttribute("product",content);

        return "show";
    }

    @RequestMapping("/{type:[1]}")
    public String listNotBuyContent(HttpServletRequest request,HttpSession session,ModelMap map)throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        ContentService service = context.getBean("contentService",ContentService.class);
        List<Product> contents = service.listNotBuyContent();
        map.addAttribute("contents",contents);
        return "";
    }

    @RequestMapping(value = "/publicSubmit",method = RequestMethod.POST)
    public String publishSubmit(HttpSession session,@RequestParam("title") String title, @RequestParam("summary") String abst, @RequestParam("image") String icon, @RequestParam("price") int price, @RequestParam("detail") String text, HttpServletResponse response,ModelMap map) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        if (title.length()>20 || abst.length()>100) {
            response.sendError(400, "Title or Abstract too long");
        }
        else {
            Content content = new Content();
            content.setTitle(title);
            content.setAbst(abst);
            content.setIcon(icon);
            content.setPrice(BigInteger.valueOf(price));
            content.setText(text);

            ContentService service = context.getBean("contentService",ContentService.class);
            service.publish(content);
            Product product = service.searchByTitle(title);
            map.addAttribute("product",product);
        }
            return "publicSubmit";
        }


    @RequestMapping("/public")
    public String publish(HttpSession session, ModelMap map) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        return "public";
    }

    @RequestMapping("/editSubmit")
    public String editSubmit(HttpSession session,@RequestParam("title") String title, @RequestParam("summary") String abst, @RequestParam("image") String icon, @RequestParam("price") int price, @RequestParam("detail") String text, @RequestParam("id") int id, HttpServletResponse response,ModelMap map) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        Content content = new Content();
        content.setTitle(title);
        content.setAbst(abst);
        content.setIcon(icon);
        content.setPrice(BigInteger.valueOf(price));
        content.setText(text);
        content.setId(id);

        ContentService service = context.getBean("contentService",ContentService.class);
        service.edit(content);
        Product product = service.show(id);
        map.addAttribute("product",product);
        return "editSubmit";
    }

    @RequestMapping("/edit")
    public String edit(HttpSession session, ModelMap map,@RequestParam("id") int id) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        ContentService service = context.getBean("contentService",ContentService.class);
        Product content = service.show(id);
        map.addAttribute("product",content);
        return "edit";
    }

    @RequestMapping("/api/delete")
    public void delete()throws IOException,ServletException{

    }
}
