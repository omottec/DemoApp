package com.omottec.demoapp.gson;

import android.content.Intent;

/**
 * Created by qinbingbing on 21/06/2017.
 */

public class GsonObject {
    public int primaryPrice;
    public int primaryPrice1;
    public Integer integerPrice;

    @Override
    public String toString() {
        return "{primaryPrice: " + primaryPrice
                + ", primaryPrice1: " + primaryPrice1
                + ", integerPrice: " + integerPrice
                + ", hashCode: " + hashCode()
                + "}";
    }
}
