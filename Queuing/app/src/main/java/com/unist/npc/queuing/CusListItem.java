package com.unist.npc.queuing;

/**
 * Created by mintaewon on 2015. 8. 12..
 */
public class CusListItem {
    public String cus_priority;
    public String cus_name;
    public String cus_party;
    public String cus_method;
    public String cus_time;
    public String cus_regid;

    public CusListItem(String priority,String name, String party, String time, String method, String regid){
        this.cus_priority = priority;
        this.cus_name = name;
        this.cus_party = party;
        this.cus_method = method;
        this.cus_time = time;
        this.cus_regid = regid;

    }

    public String getCus_name() { return cus_name;}

}
