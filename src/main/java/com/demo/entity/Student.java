package com.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@ToString
@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 姓名
    private String name;

    // 邮箱
    private String email;

    // 年龄
    private Integer age;

    // 所属班级
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    // 学生证
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_card_id", referencedColumnName = "id")
    private StudentCard studentCard;

}
