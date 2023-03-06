package com.jiayou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiayou.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
