package com.jiayou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiayou.entity.TestEntity;
import com.jiayou.mapper.TestMapper;
import com.jiayou.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestEntity> implements TestService {
}
