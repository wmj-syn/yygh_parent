package com.atguigu.yygh.cmn.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.BeanCopyUtils;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * @author hp
 * @create 2023-05-28-19:25
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        List<Dict> dictList = baseMapper.selectList(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, id));
        dictList.stream().forEach(item ->{
            boolean flag = isHasChildren(item.getId());
            item.setHasChildren(flag);
        });
        return dictList;
    }

    @Override
    public void exportData(HttpServletResponse response) throws IOException {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

        //查询数据库
        List<Dict> dicts = baseMapper.selectList(null);

        List<DictEeVo> dictEeVos = BeanCopyUtils.copyBeanList(dicts, DictEeVo.class);


        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                .doWrite(dictEeVos);

    }

    @Override
    @CacheEvict(value = "dict",allEntries = true)
    public void importData(MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //判断id下面是否有子节点
    private boolean isHasChildren(Long id){
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, id));
        return count > 0;
    }

}
