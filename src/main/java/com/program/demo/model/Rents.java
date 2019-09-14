package com.program.demo.model;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    private Integer id;
  
   /* @Column(name = "renter", nullable = false)
    private String renter;
  
    @Column(name ="rendted_item", nullable = false)
    private String rentedItem;
  */
    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "end")
    private String end;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    private Cars car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bike_id")
    private Bikes bike;
  
   
    }
    