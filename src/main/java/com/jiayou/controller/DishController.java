package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiayou.Dto.DishDto;
import com.jiayou.common.R;
import com.jiayou.entity.Category;
import com.jiayou.entity.Dish;
import com.jiayou.entity.DishFlavor;
import com.jiayou.service.CategoryService;
import com.jiayou.service.DishFlavorService;
import com.jiayou.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R save(@RequestBody DishDto dishDto){
        dishService.saveflavor(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> zhenpage = new Page<>();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName,name);
        lambdaQueryWrapper.orderByDesc(Dish::getCreateTime);

        dishService.page(pageInfo,lambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo,zhenpage);

        for (Dish d : pageInfo.getRecords()){
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(d,dishDto);
            Long id = d.getCategoryId();
            Category category = categoryService.getById(id);
            if(null != category) {
                String name1 = category.getName();
                dishDto.setCategoryName(name1);
            }
        }
        return R.success(zhenpage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable long id){
        DishDto dishDto = dishService.getByIdAndFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R update(@RequestBody DishDto dishDto){
        dishService.updateAndFlavor(dishDto);
        return R.success("更新成功");
    }

    @GetMapping("list")
    public R list(Dish dish){
        Long id = dish.getCategoryId();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Dish::getStatus,1);
        lambdaQueryWrapper.eq(id != null,Dish::getCategoryId,id);
        lambdaQueryWrapper.orderByAsc(Dish::getCreateTime);
        List<Dish> dishList = dishService.list(lambdaQueryWrapper);
        List<DishDto> list = new ArrayList<>();
        for (Dish d : dishList){
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(d,dishDto);
            Long ids = d.getCategoryId();
            Category category = categoryService.getById(ids);
            if(null != category) {
                String name1 = category.getName();
                dishDto.setCategoryName(name1);
            }
            Long dishId = d.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> list1 = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(list1);
            list.add(dishDto);
        }
        return R.success(list);
    }

}
