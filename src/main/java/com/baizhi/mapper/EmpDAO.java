package com.baizhi.mapper;

import com.baizhi.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpDAO {

    List<Emp> findAll(String userid);

    void save(Emp emp);

    void delete(String id);

    Emp find(String id);

    void update(Emp emp);
}
