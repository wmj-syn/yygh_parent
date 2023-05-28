package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    //2.导出
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response) throws IOException {
        dictService.exportData(response);
    }


    //3.导入
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        dictService.importData(file);
        return Result.ok();
    }


}
