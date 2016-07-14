package com.sx.books.service.impl;

import com.sx.books.dao.LoginDao;
import com.sx.books.meta.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by scarlettxu on 16-6-22.
 */
@Repository
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public User login(User user){
        return loginDao.login(user.getUserName(),user.getPassword());
    }
}
