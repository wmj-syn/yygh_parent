package com.atguigu.yygh.common;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hp
 * @create 2023-02-14-21:50
 */
public class BeanCopyUtils {


    private BeanCopyUtils(){

    }

    public static <V> V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        V c = null;
        try {
             c = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source,c);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //返回结果
        return c;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream().map(item ->{
            return copyBean(item,clazz);
        }).collect(Collectors.toList());
    }


//    public static <V> V copyBean(Object source,Class<V> clazz) {
//        //创建目标对象
//        V result = null;
//        try {
//            result = clazz.newInstance();
//            //实现属性copy
//            BeanUtils.copyProperties(source, result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //返回结果
//        return result;
//    }
//    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
//        return list.stream()
//                .map(o -> copyBean(o, clazz))
//                .collect(Collectors.toList());
//    }



}
