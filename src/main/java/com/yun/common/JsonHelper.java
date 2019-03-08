package com.yun.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    public static String getJsonString(int code,String msg,Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setObj(obj);
        try {
            return objectMapper.writeValueAsString(result);
        }catch (Exception e){
            return "解析对象到json出错";
        }
    }

    public static String getJsonString(int code,String msg){
        ObjectMapper objectMapper=new ObjectMapper();
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        try {
            return objectMapper.writeValueAsString(result);
        }catch (Exception e){
            return "解析对象到json出错";
        }
    }
}
