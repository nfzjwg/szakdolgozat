package com.program.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "favourites")
public class Favourites{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "car_id")
    private Cars car;
  
    @ManyToOne(optional = true)
    @JoinColumn(name = "motobike_id")
    private Motobikes motobike;

    public int getId(){
        return this.id;
    }
    
    public User getUser(){
        return this.user;
    }
    public Motobikes getFavouriteBike(){
        return this.motobike;
    }

    public Cars getFavouriteCar(){
        return this.car;
    }
    public void setId(int other){
        this.id = other;
    }
    public void setUser(User other){
        this.user = other;
    }
    public void setFavouriteBike(Motobikes other){
        this.motobike= other;
    }
    public void setFavouriteCar(Cars other){
        this.car= other;
    }
   

}