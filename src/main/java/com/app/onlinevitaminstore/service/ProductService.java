package com.app.onlinevitaminstore.service;

import com.app.onlinevitaminstore.model.Product;
import com.app.onlinevitaminstore.repository.ProductRepository;
import com.app.onlinevitaminstore.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product product, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setPhoto(fileName);

        Product savedProduct = productRepository.save(product);

        String uploadDir = "product-photos/" + savedProduct.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(Long productId){
        return productRepository.getById(productId);
    }

/*    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setDeleteStatus(true);
            productRepository.save(product.get());
        }
    }*/
}
