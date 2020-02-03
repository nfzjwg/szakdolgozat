package com.program.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * This class represents the users.
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Table(name = "users")
public class User {

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
  
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "ratingValue")
  private int ratingValue;

  @Column(name = "ratingNumber")
  private int ratingNumber;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Cars> cars;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Motobikes> motobikes;

  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Rents> rent;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Favourites> favourites;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Receipt> receipt;
  
  
    public enum Role implements GrantedAuthority {
      ROLE_GUEST,
      ROLE_COMPANY,
      ROLE_ADMIN;

      @Override
      public String getAuthority() {
        return this.toString();
      }
    }
    public String getUsername(){
      return this.username;
    }
    public String getPassword(){
      return this.password;
    }
    public String getEmail(){
      return this.email;
    }
    public int getId(){
      return this.id;
    }
    
    
    public int getRatingNumber(){
      return this.ratingNumber;
    }
    public int getRatingValue(){
      return this.ratingValue;
    }
    public Role getRole(){
      return this.role;
    }
    public List<Cars> getCars(){
      return this.cars;
    }
    public List<Motobikes> getMotobikes(){
      return this.motobikes;
    }
    public List<Rents> getRent(){
      return this.rent;
    }
    public List<Favourites> getFavourites(){
      return this.favourites;
    }
    
    public List<Receipt> getReceipt(){
      return this.receipt;
    }
  
    public void setCars(List<Cars> other){
      this.cars = other;
    }
    public void setMotobikes(List<Motobikes> other){
      this.motobikes = other;
    }
    public void setRent(List<Rents> other){
      this.rent = other;
    }
    public void setFavourites(List<Favourites> other){
      this.favourites = other;
    }
    public void setReceipt(List<Receipt> other){
      this.receipt = other;
    }

    public void setId(int other){
      this.id = other;
    }
    public void setUsername(String other){
      this.username = other;
    }
    public void setEmail(String other){
      this.email = other;
    }
    public void setPassword(String other){
      this.password= other;
    }

    public void setRatingNumber(int other){
      this.ratingNumber = other;
    }
    public void setRatingValue(int other){
      this.ratingValue = other;
    }
    public void setRole(Role other){
      this.role = other;
    }
    @Override
    public String toString() { 
        return String.format(id + " " + username+ " " + password + " " + ratingNumber+ "  " + ratingValue); 
    } 
  }
