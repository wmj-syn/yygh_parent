package com.atguigu.yygh.cmn.service.Impl;

import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * @author hp
 * @create 2023-05-28-19:25
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Override
    public List<Dict> findChildData(Long id) {
        List<Dict> dictList = baseMapper.selectList(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, id));
        dictList.stream().forEach(item ->{
            boolean flag = isHasChildren(item.getId());
            item.setHasChildren(flag);
        });
        return dictList;
    }



    //判断id下面是否有子节点
    private boolean isHasChildren(Long id){
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, id));
        return count > 0;
    }

}
