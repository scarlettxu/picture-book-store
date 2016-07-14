package com.sx.books.dao;

import com.sx.books.meta.Product;
import com.sx.books.meta.Trx;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by scarlettxu on 16-6-22.
 */
public interface TrxDao {

//    @Results({
//            @Result(column = "")
//    })
    @Select("select c.id,c.icon as image,c.title,t.time as buyTime,t.price as buyPrice,t.num as buyNum from content c,trx t where c.id=t.contentId")
    public List<Product> getTrx();

    @Insert("insert into trx(contentId,personId,price,num) values(#{contentId},#{personId},#{price},#{num})")
    public int buy(Trx trx);
}
