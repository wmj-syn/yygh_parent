package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hp
 * @create 2023-05-28-19:24
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);
}
