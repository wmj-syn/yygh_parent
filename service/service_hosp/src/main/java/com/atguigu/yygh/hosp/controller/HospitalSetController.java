package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author hp
 * @create 2023-05-28-14:44
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {



    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 1.查询医院设置表的所有信息
     * @return
     */
    @GetMapping("findAll")
    public Result findAllHospitalSet(){

        List<HospitalSet> list = hospitalSetService.list();

        return Result.ok(list);


    }

    /**
     * 2.根据id删除医院的接口
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id){


        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }else
            return Result.fail();
    }

    //3.条件查询带分页
    @GetMapping("/findPage/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current, @PathVariable long limit, HospitalSetQueryVo hospitalSetQueryVo){

        Page<HospitalSet> page = new Page<>(current,limit);

        LambdaQueryWrapper<HospitalSet> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(hospitalSetQueryVo.getHosname())){

            wrapper.like(HospitalSet::getHosname,hospitalSetQueryVo.getHosname());
        }
        if (StringUtils.hasText(hospitalSetQueryVo.getHoscode())) {
            wrapper.eq(HospitalSet::getHoscode,hospitalSetQueryVo.getHoscode());

        }

        hospitalSetService.page(page, wrapper);

        return Result.ok(page);

    }



    //4.添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){

        //设置状态
        hospitalSet.setStatus(1);
        //签名密钥
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+new Random().nextInt(1000)));

        hospitalSetService.save(hospitalSet);
        return Result.ok();
    }


    //5.根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6.修改医院设置
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag){
            return Result.ok();
        }else
            return Result.fail();
    }


    //7.批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean flag = hospitalSetService.removeByIds(ids);

        if (flag){
            return Result.ok();
        }else
            return Result.fail();
    }


    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,Integer status){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }


    //9 发送签名密钥


}
