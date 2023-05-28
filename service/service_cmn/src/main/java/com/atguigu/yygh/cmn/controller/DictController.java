package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hp
 * @create 2023-05-28-19:26
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
public class DictController {


    @Autowired
    private DictService dictService;



    //1. 根据数据的id查询子数据列表
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);

    }


}
