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
    private List<Messages> message;
    
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
      public String getRole(){
        return this.role.toString();
      }
      public void setPassword(String other){
        this.password= other;
      }
    }
