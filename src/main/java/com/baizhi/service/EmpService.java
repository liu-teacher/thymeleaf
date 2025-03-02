package com.baizhi.service;

import com.baizhi.entity.Emp;

import java.util.List;

public interface EmpService {

    List<Emp> findAll(String userid);

    void save(Emp emp);

    void delete(String id);

    Emp find(String id);

    void update(Emp emp);
}
