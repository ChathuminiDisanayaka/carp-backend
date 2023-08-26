package com.carp.carpentry.controller;

import Dto.ProductRequestDto;
import com.carp.carpentry.entity.Product;
import com.carp.carpentry.repository.ProductRepository;
import com.carp.carpentry.services.FirebaseService;
import com.carp.carpentry.services.ProductDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductDetailService productDetailService;
    private ProductRepository productRepository;



    public ProductController(ProductDetailService productDetailService,ProductRepository productRepository,FirebaseService firebaseService){
        this.productDetailService = productDetailService;
        this.productRepository = productRepository;
    }

    @GetMapping("/getProducts")
    public List<Product> getAllProduct(){
        return productDetailService.getAllProducts();
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<String> createProduct(@RequestParam("name") String name,
                                @RequestParam("price") String price,
                                                @RequestParam("quantity") int quantity
            ,@RequestParam("file") MultipartFile file){
        // Validate the request data (optional)
        if ( StringUtils.isEmpty(name) || StringUtils.isEmpty(quantity)) {
            return ResponseEntity.badRequest().body("Invalid product data");
        }
        if (file.isEmpty()) {
            productDetailService.createProduct(
                    name,
                    price,
                    quantity
            );
        }else {

            productDetailService.createProduct(
                    name,
                    price,
                    quantity,
                    file
            );
        }


        return ResponseEntity.ok("Product created successfully");
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId,@RequestBody ProductRequestDto productRequestDto){
        // Validate the request data (optional)
        if (productRequestDto == null || StringUtils.isEmpty(productRequestDto.getName()) || StringUtils.isEmpty(productRequestDto.getPrice()) || StringUtils.isEmpty(productRequestDto.getQuantity())) {
            return ResponseEntity.badRequest().body("Invalid product data");
        }

        productDetailService.updateProduct(
                productId,
                productRequestDto.getName(),
                productRequestDto.getPrice(),
                productRequestDto.getQuantity()
        ) ;
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productDetailService.deleteProduct(productId);

        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
