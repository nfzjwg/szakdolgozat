package com.program.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * This class represents the motorcycles.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "motobikes")
public class Motobikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "ccm", nullable = false)
    private Integer ccm;


    @Column(name = "rented")
    private Boolean rendted ;
    
    @OneToMany(mappedBy = "motobike", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rents> rent;
    }
    