package com.carp.carpentry.repository;

import com.carp.carpentry.entity.Product;
import com.carp.carpentry.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
}
