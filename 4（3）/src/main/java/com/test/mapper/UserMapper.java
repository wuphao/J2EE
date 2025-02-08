package com.test.mapper;

import com.test.pojo.Student;
import com.test.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{username} and passwd = #{password}")
    public User login(@Param("username") String username, @Param("password") String password);

}
