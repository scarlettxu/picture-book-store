package com.sx.books.dao;

import com.sx.books.meta.Content;
import com.sx.books.meta.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by scarlettxu on 16-6-22.
 */
public interface ContentDao {

    @Select("select c.id,c.price,c.title,c.icon,c.abstract,c.text,t.price as buyPrice from content c left join trx t on c.id=t.contentId")
    @Results({
            @Result(property = "summary",column = "abstract"),
            @Result(property = "image",column = "icon"),
            @Result(property = "detail",column = "text"),
            @Result(property = "buyPrice",column = "buyPrice")
    })
    public List<Product> showAll();

    @Results({
            @Result(property = "summary",column = "abstract"),
            @Result(property = "image",column = "icon"),
            @Result(property = "detail",column = "text"),
            @Result(property = "buyPrice",column = "buyPrice")
    })
    @Select("select c.id,c.price,c.title,c.icon,c.abstract,c.text,t.price as buyPrice from content c left join trx t on c.id=t.contentId where c.id=#{id}")
    public Product show(@Param("id") int id);

    @Select("select c.id,c.price,c.title,c.icon,c.abstract,c.text from content c where c.id not in(select contentId from trx)")
    @Results({
            @Result(property = "summary",column = "abstract"),
            @Result(property = "image",column = "icon"),
            @Result(property = "detail",column = "text"),
            @Result(property = "buyPrice",column = "buyPrice")
    })
    public List<Product> listNotBuyContent();

    @Results({
            @Result(property = "summary",column = "abstract"),
            @Result(property = "image",column = "icon"),
            @Result(property = "detail",column = "text"),
            @Result(property = "buyPrice",column = "buyPrice")
    })
    @Select("select c.id,c.price,c.title,c.icon,c.abstract,c.text,t.price as buyPrice from content c left join trx t on c.id=t.contentId where c.title=#{title}")
    public Product searchByTitle(@Param("title") String title);

    @Insert("insert into content(price,title,icon,abstract,text) values (#{price},#{title},#{icon},#{abst},#{text})")
    public int publish(Content content);

    @Update("update content set price=#{price},title=#{title},icon=#{icon},abstract=#{abst},text=#{text} where id=#{id}")
    public int edit(Content content);

    @Delete("delete from content where id=#{id}")
    public int delete(int id);
}
