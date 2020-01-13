package com.omottec.demoapp.gson;

public class GoodsSku {
    public int skuId;
    public String name;
    public float price;

    @Override
    public String toString() {
        return "GoodsSku{" +
                "skuId=" + skuId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
