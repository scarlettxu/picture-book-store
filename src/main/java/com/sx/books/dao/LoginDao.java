package com.sx.books.dao;

import com.sx.books.meta.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by scarlettxu on 16-6-22.
 */
public interface LoginDao {

    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userName",column = "userName"),
            @Result(property = "password",column = "password"),
            @Result(property = "userType",column = "userType")
    })
    @Select("select id,userName,password,userType from person where userName=#{userName} and password=#{password} limit 1")
    public User login(@Param("userName") String userName, @Param("password") String password);

}

