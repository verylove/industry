package com.fable.industry.api.page;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 */
public class Result {

    public static Map<String,Object> success(String message) {
        Map<String ,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message",message);
        return map;
    }

    public static Map<String,Object> fail(String message) {
        Map<String ,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("message",message);
        return map;
    }
}
