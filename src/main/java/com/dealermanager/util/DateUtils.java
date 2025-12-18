package com.dealermanager.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Autor:mowenhao
 * @Description
 * @Date:2021-12-2118:47
 * @Modified By:mowenhao_cs
 **/
@Slf4j
public class DateUtils {


    /**
      *@Author:mowenhao 获取上个月第一天
      *@Description:
      *@Date:18:49 2021-12-21
    **/
    public static String getPreMonthFirstDay(SimpleDateFormat format){
        Calendar   cale=Calendar.getInstance();//获取当前日期
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cale.getTime());
        System.out.println("-----1------firstDay:"+firstDay);
        return firstDay;
    }

    /**
     *@Author:mowenhao 获取上个月最后一天
     *@Description:
     *@Date:18:49 2021-12-21
     **/
    public static String getPreMonthLastDay(SimpleDateFormat format){
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);
        return lastDay;
    }

    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        //这里要注意 有的需求可能是用2020-01来表示20年第一周 格式就应该为yyyy-MM
        //如果是用2020-1来表示20年第一周 格式就应该为yyyy-M
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        try {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

            // 实现排序方法
            Collections.sort(result, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String str1 = (String) o1;
                    String str2 = (String) o2;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(str1);
                        date2 = format.parse(str2);
                    } catch (ParseException e) {
                        log.error("日期转换异常",e);
                    }

                    if (date2.compareTo(date1) > 0) {
                        return -1;
                    }
                    return 1;
                }
            });
        } catch (ParseException e) {
           log.error("日期转换异常",e);
        }
        return result;
    }
}
