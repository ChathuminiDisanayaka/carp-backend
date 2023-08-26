package com.carp.carpentry.services;

import Dto.ProductRequestDto;
import com.carp.carpentry.entity.Product;
import com.carp.carpentry.repository.ProductRepository;
import com.carp.carpentry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ProductDetailService {
    private final ProductRepository productRepository;

    public ProductDetailService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public com.carp.carpentry.entity.Product createProduct(String name,String price,Integer quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        return productRepository.save(product);
    }

    public com.carp.carpentry.entity.Product updateProduct(Long productId, String name, String price, Integer quantity) {
        Optional<Product> checkProduct = productRepository.findById(productId);

        if (checkProduct.isPresent()) {
            Product product = checkProduct.get();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);

            return productRepository.save(product);
        } else {
            throw new NoSuchElementException("Product not found with ID: " + productId);
        }
    }

    public boolean deleteProduct(Long productId) {
        Optional<Product> checkProduct = productRepository.findById(productId);

        if (checkProduct.isPresent()) {
            productRepository.delete(checkProduct.get());
            return true;
        } else {
            return false;
        }
    }
}
