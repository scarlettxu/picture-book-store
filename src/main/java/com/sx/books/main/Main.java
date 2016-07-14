package com.sx.books.main;

import com.sx.books.meta.Content;
import com.sx.books.meta.Product;
import com.sx.books.meta.Trx;
import com.sx.books.meta.User;
import com.sx.books.service.impl.LoginService;
import com.sx.books.service.impl.ContentService;
import com.sx.books.service.impl.TrxService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scarlettxu on 16-6-22.
 */
public class Main {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        // check login
        User user = new User();
        user.setUserName("seller");
        user.setPassword("981c57a5cfb0f868e064904b8745766f");
        user.setUserType((short) 1);
        LoginService loginService = context.getBean("loginService",LoginService.class);
        User result = loginService.login(user);
        System.out.println(result);

        List<Product> contents = new ArrayList<Product>();
        ContentService contentService = context.getBean("contentService",ContentService.class);
        contents = contentService.showAll();
        for (Product content:contents
             ) {
            System.out.println(content.getTitle()+", "+content.getImage()+", "+content.getDetail()+", "+content.getPrice());
        }

        TrxService trxService = context.getBean("trxService",TrxService.class);
        List<Product> trxs = new ArrayList<Product>();
        trxs=trxService.getTrx();
        for (Product trx:trxs
             ) {
            System.out.println(trx.getId()+", "+trx.getTitle()+", "+trx.getPrice()+", "+trx.getBuyTime());
        }


        Content content = new Content();
        content.setTitle("安的种子");
        content.setAbst("等待的智慧");
        content.setIcon("http://18731036777.davdian.com/1032.html");
        content.setPrice(BigInteger.valueOf(204));
        content.setText("《安的种子》以本、静与安三个小和尚为叙述线索，讲述了一个有关大自然规律的寓言故事。本书文字精准而乾脆利落，图像简纯而突显人物性格，像急躁的本、刻意经营的静与泰然的安。此外，文图结合所展示的说故事魅力，更令人击赏。");

//        contentService.publish(content);
        content.setId(3);
        contentService.edit(content);

        Trx trx =  new Trx();
        trx.setPrice(BigInteger.valueOf(204));
        trx.setContentId(3);
        trx.setPersonId(2);
        trxService.buy(trx);

        contents=contentService.listNotBuyContent();
        for (Product content1:contents
             ) {
            System.out.println(content1.getId()+", "+content1.getTitle());
        }

        ((ConfigurableApplicationContext) context).close();
    }
}
