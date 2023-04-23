package com.bank.client.controllers;

import javax.persistence.*;

public class Employee
{
   
    private Long id;  
    private String name;
    private String title;
    private Long salary;
   
    public Employee(){}

    public Employee(String name, String title, Long salary){
    	this.name = name;
        this.title = title;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary)
    {
        this.salary = salary;
    }
}
