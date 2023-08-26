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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ProductDetailService {
    private final ProductRepository productRepository;
    private final FirebaseService firebaseService;

    public ProductDetailService(ProductRepository productRepository,FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.firebaseService = firebaseService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public com.carp.carpentry.entity.Product createProduct(String name,String price,Integer quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setImageUrl("https://storage.googleapis.com/carp-web.appspot.com/products/fbaaec8e-9cc8-4562-ad24-b9ccf63ffdd6_road-sign-361514_960_720.png?GoogleAccessId=firebase-adminsdk-fx1xx@carp-web.iam.gserviceaccount.com&Expires=1787644562&Signature=YCmFJ5IDeUBjk%2BA1p0RUFnWq8y69J2ZjsbqjRGGlUUoKEHOnO8E0SssPt0OgaSSxvK4FG%2FJCPa06z8B4xne%2B08aKwULs25D23WBxRCWeVR%2Fjdo9qi8hRlKd%2FDMMjeQvy0j52QX3LqhhmAhguJlFXZ1l0hoM%2FX%2FCvIb%2B3%2BtQ85P%2FxxnT8OBr84AXyc9R6ARpcmW81RIT9YC8kVh%2BdBfLWTe4j3PavWQ7bspo56cpbbMqTCxLvAAhF1Wc82lrzrKy1tLhA6hBRFTwm2HvdKpf4rW%2BqNKLiBnNxhdy2J9WaRxmTAMTXGam97Ngsk1Xknznh%2F2DM6gnlyxAt%2BNMicphLug%3D%3D");
        return productRepository.save(product);
    }

    public com.carp.carpentry.entity.Product createProduct(String name, String price, Integer quantity, MultipartFile file) {
        String imageUrl ="https://storage.googleapis.com/carp-web.appspot.com/products/fbaaec8e-9cc8-4562-ad24-b9ccf63ffdd6_road-sign-361514_960_720.png?GoogleAccessId=firebase-adminsdk-fx1xx@carp-web.iam.gserviceaccount.com&Expires=1787644562&Signature=YCmFJ5IDeUBjk%2BA1p0RUFnWq8y69J2ZjsbqjRGGlUUoKEHOnO8E0SssPt0OgaSSxvK4FG%2FJCPa06z8B4xne%2B08aKwULs25D23WBxRCWeVR%2Fjdo9qi8hRlKd%2FDMMjeQvy0j52QX3LqhhmAhguJlFXZ1l0hoM%2FX%2FCvIb%2B3%2BtQ85P%2FxxnT8OBr84AXyc9R6ARpcmW81RIT9YC8kVh%2BdBfLWTe4j3PavWQ7bspo56cpbbMqTCxLvAAhF1Wc82lrzrKy1tLhA6hBRFTwm2HvdKpf4rW%2BqNKLiBnNxhdy2J9WaRxmTAMTXGam97Ngsk1Xknznh%2F2DM6gnlyxAt%2BNMicphLug%3D%3D";
        Product product = new Product();
        try {
            imageUrl =  firebaseService.uploadImageAndSaveToDB(file);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setImageUrl(imageUrl);
        }
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
