package com.example.dawn.car.domain;

import java.io.Serializable;

/**
 * Created by WANXIAO on 2015/9/12.
 */
public class BuyerData implements Serializable {
    private String address;
    private String model;


    public BuyerData(String address/*, String model*/) {
        this.address = address;
//        this.model = model;

    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}

