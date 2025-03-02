package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private String id;
    private String name;//名字
    private Double salary;//工资
    private Date birthday;//生日
    private String photo;//照片路径
    private String userid;
}
