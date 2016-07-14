package com.sx.books.meta;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by scarlettxu on 16-7-10.
 */
public class Product {

    private int id;
    private String title;
    private String summary;
    private BigInteger price;
    private BigInteger buyPrice;
    private String image;
    private int buyNum;
    private boolean isBuy;
    private boolean isSell;
    private String detail;
    private Long buyTime;

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigInteger buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
