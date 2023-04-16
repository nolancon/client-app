package com.bank.client.controllers;

public class Account
{
   
    private Long id;

    private String type;

    private Long balance;

    private Customer customer;


    public Account(){}

    public Account(String type, Long balance){
        this.type = type;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance)
    {
        this.balance = balance;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
}

