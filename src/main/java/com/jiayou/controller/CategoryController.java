package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiayou.common.R;
import com.jiayou.entity.Category;
import com.jiayou.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R xinzeng(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("添加成功");
    }

    @PutMapping
    public R xiugai(@RequestBody Category category) {
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
//        log.info(String.valueOf(category.getId()));
//        lambdaQueryWrapper.eq(Category::getId,category.getId());
//        Category c = categoryService.getOne(lambdaQueryWrapper);
//        log.info(c.toString());
//        categoryService.update(lambdaQueryWrapper);
        categoryService.updateById(category);
        return R.success("更新成功");
    }


    @GetMapping("page")
    public R page(int page, int pageSize) {
        System.out.println(page + "  " + pageSize);
        Page page1 = new Page();
        page1.setCurrent(page);
        page1.setSize(pageSize);
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(page1, lambdaQueryWrapper);
        return R.success(page1);
    }

    @DeleteMapping
    public R<String> deletecategory(Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @GetMapping("list")
    public R list(Category category){
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getCreateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    

}
