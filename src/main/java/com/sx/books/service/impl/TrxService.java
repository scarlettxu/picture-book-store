package com.sx.books.service.impl;

import com.sx.books.dao.TrxDao;
import com.sx.books.meta.Product;
import com.sx.books.meta.Trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scarlettxu on 16-6-22.
 */
@Repository
public class TrxService {

    @Autowired
    private TrxDao trxDao;

    public void setTrxDao(TrxDao trxDao) {
        this.trxDao = trxDao;
    }

    public List<Product> getTrx(){
        return trxDao.getTrx();
    }

    public int buy(Trx trx){
        return trxDao.buy(trx);
    }
}
