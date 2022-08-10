package com.organicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organicstore.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
