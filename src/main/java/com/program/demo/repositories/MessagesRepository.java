package com.program.demo.repositories;
import com.program.demo.model.Messages;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    Optional<Messages> findBySender(Integer sender);
    List<Messages> findAllBySender(Integer sender);
    List<Messages> findAllByReciver(Integer reciver);
}
