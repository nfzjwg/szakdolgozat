package com.program.demo.model;

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
import java.util.List;
//import org.springframework.security.core.GrantedAuthority;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
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

  /*  @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
  */
    @Column(name = "gender")
    private Gender gender;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rents> rent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Messages> message;
  
   /* public enum Role implements GrantedAuthority {
        ROLE_GUEST,
        ROLE_AUTHOR;
    
        @Override
        public String getAuthority() {
          return this.toString();
        }
      }
    */
      public enum Gender {
        MALE,
        FEMALE
      }
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
      public  Gender getGender(){
        return this.gender;
      }

    }
    