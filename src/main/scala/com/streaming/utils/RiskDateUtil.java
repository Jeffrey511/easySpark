package com.streaming.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lichangyue on 2016/10/21.
 */
public class RiskDateUtil {

    private static SimpleDateFormat sp  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sp2 = new SimpleDateFormat("yyyyMMdd");

    public static  String getYesterday(){

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
//        cal.add(Calendar.HOUR ,8);
        Date d=cal.getTime();

        SimpleDateFormat sp=new SimpleDateFormat("yyyyMMdd");
        String ZUOTIAN=sp.format(d);//获取昨天日期

        return ZUOTIAN;
    }

    public static  String getTodayday(){

        Calendar cal=Calendar.getInstance();
//        cal.add(Calendar.HOUR ,8);
        Date d=cal.getTime();
        SimpleDateFormat sp=new SimpleDateFormat("yyyyMMdd");
        String today=sp.format(d);
        return today;
    }


    public static  String getTodayday(Date date){
        SimpleDateFormat sp=new SimpleDateFormat("yyyyMMdd");
        String today=sp.format(date);
        return today;
    }


    public static String formatDate(String date){
        if(date == null || date.trim().equals("")) return "";
        try{
            Date tt = sp.parse(date);
            return sp2.format(tt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateyyyyMMddHHmmss(Date date){
        if(date == null) return "";
        try{
            return sp.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateyyyyMMdd(Date date){
        if(date == null) return "";
        try{
            return sp2.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}