package com.test.mapper;

import com.test.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from student where id like concat('%', #{value}, '%') " +
            "or name like concat('%', #{value}, '%') or gender like concat('%', #{value}, '%') " +
            "or phone like concat('%', #{value}, '%') or email like concat('%', #{value}, '%')" +
            "or class_name like concat('%',#{value},'%')")
    List<Student> queryStudent(@Param("value") String value);

    @Insert("insert into student(name, gender, class_name,phone, email) values(#{name}, #{gender}, #{className}, #{phone}, #{email})")
    void addStudent(Student student);

    @Update("update student set name = #{name}, gender = #{gender}, phone = #{phone}, email = #{email},class_name=#{className} where id = #{id}")
    void modifyStudent(Student student);

    @Delete("delete from student where id = #{id}")
    void deleteStudent(Long id);

    @Select("select * from student where id = #{id}")
    Student queryStudentById(Long id);

}
