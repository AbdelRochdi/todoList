package com.pragmatic.todoList.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SecondTaskRepository extends JpaRepository<SecondTaskEntity, Long> {

}
