package com.program.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.program.demo.model.User;


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
    private Integer id;
  
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
  
    
  
    @Column(name = "text", nullable = false)
    private String text;

    public int getId(){
        return this.id;
    }
    
    public String getText(){
        return this.text;
    }
   
    public void setText(String other){
        this.text = other;
    }
   
   

}