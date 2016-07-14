package com.sx.books.meta;

import java.math.BigInteger;

/**
 * Created by scarlettxu on 16-7-14.
 */
public class Buy {

    private int id;
    private int number;
    private BigInteger price;

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
