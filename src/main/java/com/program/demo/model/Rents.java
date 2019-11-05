package com.program.demo.model;
import javax.persistence.JoinColumn;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "rents")
public class Rents {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
  
   /* @Column(name = "renter", nullable = false)
    private String renter;
  
    @Column(name ="rendted_item", nullable = false)
    private String rentedItem;
  */
    @Column(name = "start", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;

    @Column(name = "end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    

    @ManyToOne(optional = true)
    @JoinColumn(name = "car_id")
    private Cars car;

    @ManyToOne(optional = true)
    @JoinColumn(name = "motobike_id")
    private Motobikes motobike;

    public int  getId(){
      return this.id;
    }
    public Date getStart(){
      return this.start;
    }
    public Date getEnd(){
      return this.end;
    }
    public User getUser(){
      return this.user;
    }
    public Cars getCar(){
      return this.car;
    }
    public Motobikes getMotobike(){
      return this.motobike;
    }
    public void setId(int id){
      this.id = id;
    }
    public void setStart(Date other){
      this.start = other;
    }
    public void setEnd(Date other){
      this.end = other;
    }
    public void setUser(User other){
      this.user = other;
    }
    public void setCar(Cars other) {
      this.car = other;
    }
    public void setMotobike(Motobikes other){
      this.motobike = other;
    }

   
    }
    