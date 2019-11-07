package com.program.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "messages")
public class Messages{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "sender", nullable = false)
    private int sender;

    @Column(name = "reciver", nullable = false)
    private int reciver;

    public int getId(){
        return this.id;
    }
    
    public String getText(){
        return this.text;
    }
    public int getSender(){
        return this.sender;
    }
    public int getReciver(){
        return this.reciver;
    }
   
    public void setText(String other){
        this.text = other;
    }
    public void setSender(int other){
        this.sender = other;
    }
    public void setReciver(int other){
        this.reciver = other;
    }
   

}