package com.sx.books.service.impl;

import com.sx.books.dao.ContentDao;
import com.sx.books.meta.Content;
import com.sx.books.meta.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scarlettxu on 16-6-22.
 */
@Repository
public class ContentService {
    @Autowired
    private ContentDao contentDao;

    public void setContentDao(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    public List<Product> showAll(){
        List<Product> products = contentDao.showAll();
        for (Product product: products) fillProduct(product);
        return products;
    }

    public Product show(int id){
        Product product= contentDao.show(id);
        fillProduct(product);
        return product;
    }

    public List<Product> listNotBuyContent(){
        List<Product> products = contentDao.listNotBuyContent();
        for (Product product: products) fillProduct(product);
        return products;
    }

    public Product searchByTitle(String title){
        Product product = contentDao.searchByTitle(title);
        fillProduct(product);
        return product;
    }

    public int publish(Content content){
        return contentDao.publish(content);
    }

    public int edit(Content content){
        System.out.println(content.getAbst());
        return contentDao.edit(content);
    }

    public int delete(int id){
        return contentDao.delete(id);
    }

    private void fillProduct(Product product){
        if (product.getBuyPrice()!=null) {
            product.setBuy(true);
            product.setSell(true);
            product.setBuyNum(1);
        }else {
            product.setBuy(false);
            product.setSell(false);
        }

    }
}
