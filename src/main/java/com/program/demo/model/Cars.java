package com.program.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * This class represents the cars
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cars")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
  
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;
  
    @Column(name = "doors")
    private Integer doors;
    
    @Column(name = "engine")
    private String engine;

    @Column(name = "ccm", nullable = false)
    private int ccm;

    @Column(name = "ac")
    private Boolean ac;

    @Column(name = "rented")
    private Boolean rented ;

    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;


    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rents> rent;
    public void setManufacturer( String  other){
        this.manufacturer = other;
    }   
    public void setModel(String  other){
        this.model = other;
    }   
    public void setDoors( int  other){
        this.doors = other;
    }   
    public void setEngine( String  other){
        this.engine = other;
    }   
    public void setCcm( int  other){
        this.ccm = other;
    }  
    public void setAc( Boolean  other){
        this.ac = other;
    }    
    public void setRented( Boolean  other){
        this.rented = other;
    }   
    public void setOwner( User other){
        this.owner = other;
    }   
    public String getManufacturer(){
        return this.manufacturer;
    }
    public String getModel(){
        return this.model;
    }
    public int getDoors(){
        return this.doors;
    }
    public String getEngine(){
        return this.engine;
    }
    public int getCcm(){
        return this.ccm;
    }
    public boolean getAc(){
        return this.ac;
    }
    public boolean getRented(){
        return this.rented;
    }
    public User getOwner(){
        return this.owner;
    }
}
    