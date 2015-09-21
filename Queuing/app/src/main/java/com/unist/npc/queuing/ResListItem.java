package com.unist.npc.queuing;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2015. 7. 24..
 */
public class ResListItem extends ArrayList<ResListItem> {
    public String res_name;
    public String res_imgurl;
    public String res_imgurl_small;
    public String res_location;
    public Double res_x_coordinate;
    public Double res_y_coordinate;
    public String res_cuisine;
    public String res_phone_num;
    public String res_timing;
    public String res_distance;

    public String res_waittime;
    public String res_website="www.test.com";
    public String res_dummyname;

    public ResListItem(String imgurl,String name, String cuisine, String distance,String waittime,Double x_coordinate,Double y_coordinate,String location,String timing,String phone_num,String dummyname){
        this.res_imgurl = imgurl;
        this.res_name = name;
        this.res_cuisine = cuisine;
        this.res_distance = distance;
        this.res_waittime = waittime;
        this.res_x_coordinate = x_coordinate;
        this.res_y_coordinate = y_coordinate;
        this.res_location = location;
        this.res_timing = timing;
        this.res_phone_num = phone_num;
        this.res_dummyname = dummyname;
    }
}
