package com.organicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organicstore.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{

	Admin findByusername(String username);
}
