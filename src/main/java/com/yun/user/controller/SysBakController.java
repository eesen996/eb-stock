package com.yun.user.controller;

import com.yun.common.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Controller
public class SysBakController {

    @RequestMapping("/sysBakList")
    public String sysBakList(Model model){
        File dbFile=new File("dbbak");
        String[] files=dbFile.list();
        java.util.Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    Date date1=DateHelper.string2Date(o1.substring(7,21),"yyyyMMddHHmmss");
                    Date date2=DateHelper.string2Date(o2.substring(7,21),"yyyyMMddHHmmss");
                    int a=(int)(date2.getTime()-date1.getTime());
                    log.debug("a:"+a);
                    return a;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }

            }
        });
        model.addAttribute("baklist",files);
        return "user/sysBakList";
    }

    @RequestMapping("/sysBak")
    public void sysBak(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out=null;
        try {
            response.setContentType("text/html;charset=utf-8");
            out=response.getWriter();
            String currentDate=DateHelper.getCurrentDate("yyyyMMddHHmmss");

            Runtime rt = Runtime.getRuntime();
            String cmd="C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump.exe -uroot -p123456 ebstock ";
            Process process=rt.exec(cmd);
            InputStream in=process.getInputStream();
            OutputStream fout=new FileOutputStream("dbbak/ebstock"+currentDate+".sql");
            byte[] buf=new byte[1024];
            int p=0;
            while((p=in.read(buf))!=-1){
                fout.write(buf,0,p);
                buf=new byte[1024];
            }
            out.print("<script>alert('备份完成');window.location='sysBakList'</script>");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("备份数据出错"+e);
            out.print("<script>alert('备份完成');window.location='sysBakList'</script>");
        }
    }
    @RequestMapping("/deleteBakFile")
    public String deleteBakFile(String fileName){
        File file=new File("dbbak/"+fileName);
        if(file.exists()){
            file.delete();
        }
        return "redirect:sysBakList";
    }
}
