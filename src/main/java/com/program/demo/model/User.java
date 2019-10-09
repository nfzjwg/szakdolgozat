package com.program.demo.model;

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
import java.util.List;
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
    private Integer id;
  
    @Column(name = "username", nullable = false)
    private String username;
  
    @Column(name = "password", nullable = false)
    private String password;
  
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
  
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rents> rent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Messages> message;

    public  int getId(){
      return this.id;
    }
    public  String getUsername(){
      return this.username;
    }
    public  String getPassword(){
      return this.password;
    }
    public  String getEmail(){
      return this.email;
    }
    public Role getRole(){
      return this.role;
    }

    public void setPassword(String pw){
      this.password= pw;
    }
    public void setUsername(String name){
      this.username= name;
    }
    public void setEmail(String email){
      this.email= email;
    }
    public void setUser(User other){
      this.username = other.username;
      this.password = other.password;
      this.email = other.email;
      this.id = other.id;
      this.role= other.role;
      }
      public enum Role implements GrantedAuthority {
        ROLE_GUEST,
        ROLE_COMPANY,
        ROLE_ADMIN;
  
        @Override
        public String getAuthority() {
          return this.toString();
        }
      }
    }
    