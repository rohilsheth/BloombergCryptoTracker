package com.example.a10011878.orientationproject;

import java.io.Serializable;

/**
 * Created by rohilsheth on 12/6/17.
 */

public class Cryptocurrency implements Serializable {
    int year,ID;
    String details, marketCap, name;
    String price;
    String ticker;
    String colorvis;

    public Cryptocurrency(String name, int year, String marketCap, String details, int ID, String price, String ticker, String colorvis) {
        this.name = name;
        this.year = year;
        this.marketCap = marketCap;
        this.details = details;
        this.ID = ID;
        this.price = price;
        this.ticker = ticker;
        this.colorvis = colorvis;
    }
    public int getYear() {
        return year;
    }
    public String getMarketCap() {
        return marketCap;
    }
    public String getDetails() {
        return details;
    }
    public String getName() {
        return name;
    }
    public int getID() { return ID; }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getTicker() {
        return ticker;
    }

    public String getColorvis() {
        return colorvis;
    }

    public void setColorvis(String colorvis) {
        this.colorvis = colorvis;
    }
}

