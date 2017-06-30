package com.omottec.demoapp.view.statuslayout;

import java.io.Serializable;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public class GeoCoding implements Serializable {
    public double lon;
    public double lat;
    public int level;
    public String address;
    public String cityName;

    @Override
    public String toString() {
        return "lon:" + lon
                + ", lat:" + lat
                + ", level:" + level
                + ", address:" + address
                + ", cityName:" + cityName;
    }
}
