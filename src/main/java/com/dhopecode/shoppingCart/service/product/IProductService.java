package com.dhopecode.shoppingCart.service.product;

import com.dhopecode.shoppingCart.dto.ProductDto;
import com.dhopecode.shoppingCart.model.Product;
import com.dhopecode.shoppingCart.requests.ProductUpdateRequest;
import com.dhopecode.shoppingCart.requests.AddProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById (Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String brand,String category);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProduct(List<Product> products);

    ProductDto convertToDto(Product product);
}
