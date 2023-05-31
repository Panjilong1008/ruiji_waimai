package com.waimai.common;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
* 通用类，用于返回结果
*
* */
@Data
public class R<T> {
    private Integer code;//编码：1代表成功，0为失败
    private  String msg;//错误信息
    private T data;//T类型数据
    private Map map=new HashMap();//动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

//    在泛型方法声明中中，第一个<T>作用是声明T是一个泛型，随后在返回值类型中才可以用T
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }


}
