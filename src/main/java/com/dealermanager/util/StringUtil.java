package com.dealermanager.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtil {
    public static boolean isEmpty(String str) {
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }

    public static String getStrDate (String dateStr){
        String datestr=null;
        try{
            DateFormat format=new SimpleDateFormat("yyyyMMdd");
            Date date=format.parse(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            datestr= sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return datestr;
    }

    public static void main(String[] args) {
        getUuid();
    }

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
        return uuid;
    }
}
