package com.program.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.program.demo.model.User;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

  private User user;
  public void setUser(User other){
    this.user = other;
  }
  public User getUser(){
    return this.user;
  }
}
