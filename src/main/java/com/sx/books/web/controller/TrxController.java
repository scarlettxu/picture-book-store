package com.sx.books.web.controller;

import com.sx.books.meta.Product;
import com.sx.books.meta.Trx;
import com.sx.books.meta.User;
import com.sx.books.service.impl.TrxService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by scarlettxu on 16-6-23.
 */
@Controller
public class TrxController {

    private WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();

    @RequestMapping("/account")
    public String checkAccount(HttpSession session,ModelMap map) throws IOException,ServletException {

        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
        }else map.addAttribute("user",null);

        TrxService service = context.getBean("trxService",TrxService.class);
        List<Product> trxes = service.getTrx();
        map.addAttribute("buyList",trxes);
        return "account";
    }

    @RequestMapping("/api/buy")
    @ResponseBody
    public String buy(HttpSession session,@RequestParam("id") int contentId,@RequestParam("personId") int personId,@RequestParam("price") int price,ModelMap map) throws IOException,ServletException{
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        Trx trx = new Trx();
        trx.setContentId(contentId);
        trx.setPersonId(personId);
        trx.setPrice(BigInteger.valueOf(price));
        TrxService service = context.getBean("trxService",TrxService.class);
        service.buy(trx);

        map.addAttribute("code",200);
        map.addAttribute("message","buy successful");
        map.addAttribute("result",true);
        return "buy";
    }

    @RequestMapping("/settleAccount")
    public String settleAccount(HttpSession session,ModelMap map){
        if (session.getAttribute("userName") !=null){
            System.out.println("session userName: "+session.getAttribute("userName"));
            User user = new User();
            user.setUserName((String) session.getAttribute("userName"));
            user.setUserType((Short) session.getAttribute("userType"));
            map.addAttribute("user", user);
            System.out.println("user name: "+user.getUserName()+", user type: "+user.getUserType());
        }else map.addAttribute("user",null);

        return "settleAccount";
    }
}
