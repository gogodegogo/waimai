package com.jiayou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiayou.Dto.DishDto;
import com.jiayou.common.R;
import com.jiayou.entity.Category;
import com.jiayou.entity.Dish;
import com.jiayou.entity.DishFlavor;
import com.jiayou.mapper.DishMapper;
import com.jiayou.service.CategoryService;
import com.jiayou.service.DishFlavorService;
import com.jiayou.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService flavorService;


    @Transactional
    public void saveflavor(DishDto dishDto) {
        this.save(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long id = dishDto.getId();
        for (DishFlavor flavor : flavors){
            flavor.setDishId(id);
        }
        flavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdAndFlavor(long id) {
        Dish byId = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);

        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavor = flavorService.list(lambdaQueryWrapper);
        dishDto.setFlavors(dishFlavor);
        return dishDto;
    }

    @Override
    public void updateAndFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        flavorService.remove(lambdaQueryWrapper);
        List<DishFlavor> list = dishDto.getFlavors();
        for (DishFlavor flavor : list){
            flavor.setDishId(dishDto.getId());
        }
        flavorService.saveBatch(list);
    }


}
