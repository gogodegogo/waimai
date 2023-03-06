package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiayou.Dto.SetmealDto;
import com.jiayou.common.R;
import com.jiayou.entity.Setmeal;
import com.jiayou.entity.SetmealDish;
import com.jiayou.service.CategoryService;
import com.jiayou.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveAndDish(setmealDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize,String name){
        Page<Setmeal> page1 = new Page<>(page,pageSize);
        Page<SetmealDto> page2 = new Page<>();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null,Setmeal::getName,name);
        setmealService.page(page1,lambdaQueryWrapper);

        BeanUtils.copyProperties(page1,page2);
        List<Setmeal> list = page1.getRecords();
        List<SetmealDto> list1 = new ArrayList<>();
        for (Setmeal s : list){
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(s,setmealDto);
            Long id = s.getCategoryId();
            String name1 = categoryService.getById(id).getName();
            setmealDto.setCategoryName(name1);
            list1.add(setmealDto);
        }
        page2.setRecords(list1);
        return R.success(page2);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeAndDish(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        log.info(setmeal.getCategoryId()+" "+setmeal.getStatus());
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(list);
    }
}
