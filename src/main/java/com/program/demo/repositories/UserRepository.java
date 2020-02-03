package com.program.demo.repositories;
import com.program.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findAll();
  Optional<User> findByUsername(String username);
}
