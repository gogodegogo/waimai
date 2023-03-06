package com.jiayou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiayou.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
