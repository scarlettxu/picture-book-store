package com.sx.books.web.controller;

import com.sx.books.meta.Content;
import com.sx.books.meta.Product;
import com.sx.books.meta.User;
import com.sx.books.service.impl.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by scarlettxu on 16-6-23.
 */
@Controller
public class ContentController {

    private WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
        @RequestMapping("/")
        public String showAll(HttpSession session, ModelMap map,HttpServletResponse response) throws IOException,ServletException {
            if (session.getAttribute("userName") !=null){
                System.out.println("session userName: "+session.getAttribute("userName"));
                User user = new User();
                user.setUserName((String) session.getAttribute("userName"));
                user.setUserType((Short) session.getAttribute("userType"));
                map.addAttribute("user", user);
                System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
            }else map.addAttribute("user",null);

            List<Product> contents = null;
            try{
                ContentService service = context.getBean("contentService",ContentService.class);
                contents = service.showAll();
            }catch (Exception e){
                e.printStackTrace();
                response.sendError(500,e.getLocalizedMessage());
            }

//            for (Product product:contents
//                 ) {
//                System.out.println(product.getTitle() +", "+product.isBuy()+", "+product.isSell());
//            }
            map.addAttribute("productList",contents);

            return "index";

        }


    @RequestMapping("/show")
    public String show(HttpServletResponse response, HttpSession session, ModelMap map,@RequestParam("id") int id) throws IOException,ServletException{

        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        Product content = null;
        try {
            ContentService service = context.getBean("contentService",ContentService.class);
            content = service.show(id);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500,e.getLocalizedMessage());
        }

        map.addAttribute("product",content);

        return "show";
    }

    @RequestMapping("/{type:[1]}")
    public String listNotBuyContent(HttpServletResponse response,HttpSession session,ModelMap map)throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        List<Product> contents = null;
        try {
            ContentService service = context.getBean("contentService",ContentService.class);
            contents = service.listNotBuyContent();
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500,e.getLocalizedMessage());
        }

        map.addAttribute("contents",contents);
        return "";
    }

    @RequestMapping(value = "/publicSubmit",method = RequestMethod.POST)
    public String publishSubmit(HttpSession session,@RequestParam("title") String title, @RequestParam("summary") String abst, @RequestParam("image") String icon, @RequestParam("price") double price, @RequestParam("detail") String text, HttpServletResponse response,ModelMap map) throws IOException,ServletException{
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
            content.setPrice(price);
            content.setText(text);

            Product product = null;
            try {
                ContentService service = context.getBean("contentService",ContentService.class);
                service.publish(content);
                product = service.searchByTitle(title);
            }catch (Exception e){
                e.printStackTrace();
                response.sendError(500,e.getLocalizedMessage());
            }

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
    public String editSubmit(HttpSession session,@RequestParam("title") String title, @RequestParam("summary") String abst, @RequestParam("image") String icon, @RequestParam("price") double price, @RequestParam("detail") String text, @RequestParam("id") int id, HttpServletResponse response,ModelMap map) throws IOException,ServletException{
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
        content.setPrice(price);
        content.setText(text);
        content.setId(id);

        Product product = null;
        try {
            ContentService service = context.getBean("contentService",ContentService.class);
            service.edit(content);
            product = service.show(id);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500,e.getLocalizedMessage());
        }

        map.addAttribute("product",product);
        return "editSubmit";
    }

    @RequestMapping("/edit")
    public String edit(HttpSession session, ModelMap map,@RequestParam("id") int id,HttpServletResponse response) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        Product content = null;
        try {
            ContentService service = context.getBean("contentService",ContentService.class);
            content = service.show(id);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500,e.getLocalizedMessage());
        }

        map.addAttribute("product",content);
        return "edit";
    }

    @RequestMapping("/api/upload")
    public String upload(@RequestParam("file")MultipartFile file,ModelMap model,HttpSession session,HttpServletResponse response) throws IOException,ServletException{
        if (!file.isEmpty()){

//              String filePath = session.getServletContext().getRealPath("/")+"image/"+file.getOriginalFilename();
            String fileName = System.currentTimeMillis()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String filePath = session.getServletContext().getRealPath("/")+"image/"+fileName;
            try{file.transferTo(new File(filePath));
            }catch (Exception e){
                e.printStackTrace();
                response.sendError(500,e.getLocalizedMessage());
            }

            String imagePath = "/webapp/image/"+fileName;
            model.addAttribute("result",imagePath);
            model.addAttribute("code",200);
            model.addAttribute("message","Image Upload Successful");
        }
        return "upload";
    }

    @RequestMapping("/api/delete")
    public String delete(@RequestParam("id") int id,ModelMap map,HttpServletResponse response)throws IOException,ServletException{

        try {
            ContentService service = context.getBean("contentService",ContentService.class);
            service.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500,e.getLocalizedMessage());
        }

        map.addAttribute("code",200);
        map.addAttribute("message","Delete Successful");
        map.addAttribute("result",true);
        return "delete";
    }
}
