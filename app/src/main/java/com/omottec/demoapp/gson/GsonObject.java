package com.omottec.demoapp.gson;

/**
 * Created by qinbingbing on 21/06/2017.
 */

public class GsonObject {
    public int intPrice;
    public int intPrice1;
    public Integer integerPrice;
    public Integer integerPrice1;
    private String str;
    private String str1;
    private String str2 = "";
    private boolean b;
    private boolean b1;
    private Boolean refBoolean;
    private Boolean refBoolean1;
    public static final String LAT_LON = "";

    @Override
    public String toString() {
        return "{intPrice: " + intPrice
                + ", intPrice1: " + intPrice1
                + ", integerPrice: " + integerPrice
                + ", integerPrice1: " + integerPrice1
                + ", str:" + str
                + ", str1:" + str1
                + ", str2:" + str2
                + ", b:" + b
                + ", b1:" + b1
                + ", refBoolean:" + refBoolean
                + ", refBoolean1:" + refBoolean1
                + ", hashCode: " + hashCode()
                + "}";
    }
}
