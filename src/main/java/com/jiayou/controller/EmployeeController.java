package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiayou.common.R;
import com.jiayou.entity.Employee;
import com.jiayou.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String passwrod = employee.getPassword();
        passwrod = DigestUtils.md5DigestAsHex(passwrod.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp == null){
            return R.error("失败");
        }
        if(!passwrod.equals(emp.getPassword())){
            return R.error("密码不一样");
        }
        if(emp.getStatus() == 0 ){
            return R.error("账号禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R employee(HttpServletRequest request,@RequestBody Employee employee){
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(1);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        long id = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);
        System.out.println(employee.toString());
        employeeService.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("员工分页");
        Page paInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(paInfo,queryWrapper);
        return R.success(paInfo);
    }

    //修改状态
    @PutMapping
    public R<String> upate(HttpServletRequest request,@RequestBody Employee employee){
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("状态修改成功");
    }

    @GetMapping("{id}")
    public R<Employee> xiugai(HttpServletRequest request,@PathVariable Long id){
//        System.out.println(request.getRequestURI());
        Employee byId = employeeService.getById(id);
        return R.success(byId);
    }
}
