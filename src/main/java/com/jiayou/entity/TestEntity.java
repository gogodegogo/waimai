package com.jiayou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int noid;

    private Long id;

}
