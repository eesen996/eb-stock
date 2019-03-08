package com.yun.common;

import java.util.Random;

public class ColorHelper {

    public static String[] saleColor={"255, 99, 132",
            "54, 162, 235",
            "255, 206, 86",
            "75, 192, 192",
            "153, 102, 255",
            "255, 159, 64"};

    public static String getRBGColor(double opacity){
        Random random=new Random();
        int colorValue=(int)(random.nextDouble()*saleColor.length);
        String result="rgba("+saleColor[colorValue]+","+opacity+")";
        return result;
    }
}
