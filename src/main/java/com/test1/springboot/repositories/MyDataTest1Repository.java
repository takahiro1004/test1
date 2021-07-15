package com.test1.springboot.repositories;


import com.test1.springboot.MyDataTest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDataTest1Repository  extends JpaRepository<MyDataTest, Integer> {
	public Optional<MyDataTest> findById(Integer id);
	
	}
