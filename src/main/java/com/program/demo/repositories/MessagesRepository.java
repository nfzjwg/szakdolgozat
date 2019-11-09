package com.program.demo.repositories;
import com.program.demo.model.Messages;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    List<Messages> findAllBySender(Integer sender);
    List<Messages> findAllByReciver(Integer reciver);
}
