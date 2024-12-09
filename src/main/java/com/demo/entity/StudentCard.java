package com.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@Entity
@Table(name = "student_card")
public class StudentCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 学生证编号
    private String cardNumber;

    // 发放日期
    private Date issueDate;

    @OneToOne(mappedBy = "studentCard")
    private Student student;

}
