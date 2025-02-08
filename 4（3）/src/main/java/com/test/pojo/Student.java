package com.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    Long id;
    String name;
    String gender;
    String className;
    String phone;
    String email;


    public String string() {

        return "<tr>" +
                "<td>" + id.toString() +
                "</td>" +
                "<td>" + name +
                "</td>" +
                "<td>" + gender +
                "</td>" +
                "<td>" + className +
                "</td>" +
                "<td>" + phone +
                "</td>" +
                "<td>" + email +
                "</td>" +

                "</tr>";
    }
}
