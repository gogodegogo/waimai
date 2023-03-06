package com.jiayou.Test;

import java.util.Comparator;

public class Student implements Comparable<Student> {
    private int age;
    private String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(Student o) {
        int i = this.getAge()-o.getAge();
        i = i == 0 ? o.getName().compareTo(this.getName()) : i;
        System.out.println("this--->"+this);
        System.out.println("o--->"+o);
        System.out.println();
        return i;
    }
}
