package com.jiayou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiayou.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
