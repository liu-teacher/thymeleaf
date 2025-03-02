package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;//用户名
    private String realname;//真实姓名
    private String password;//密码
    private String gender;//性别

}
