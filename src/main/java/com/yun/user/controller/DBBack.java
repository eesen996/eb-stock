package com.yun.user.controller;

import com.yun.common.DateHelper;

import java.io.*;

public class DBBack {
    public static void main(String[] args) {
        /*Map<String,String> envmap=System.getenv();
        for(Iterator<String> iterator=envmap.keySet().iterator();iterator.hasNext();){
            String key=iterator.next();
            System.out.print(key+"--->");
            String value=envmap.get(key);
            System.out.println(value);
        }*/
        try {
            Runtime rt = Runtime.getRuntime();
            String currentDate=DateHelper.getCurrentDate("yyyyMMddHHmmss");

            String cmd="C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump.exe -uroot -p123456 ebstock ";
            Process process=rt.exec(cmd);
            InputStream in=process.getInputStream();
            OutputStream out=new FileOutputStream("d:/ebstock"+currentDate+".sql");
            byte[] buf=new byte[1024];
            int p=0;
            while((p=in.read(buf))!=-1){
                out.write(buf,0,p);
                buf=new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
