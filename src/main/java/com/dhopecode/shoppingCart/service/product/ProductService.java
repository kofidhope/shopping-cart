package com.dhopecode.shoppingCart.service.product;

import com.dhopecode.shoppingCart.dto.ImageDto;
import com.dhopecode.shoppingCart.dto.ProductDto;
import com.dhopecode.shoppingCart.exceptions.ProductNotFoundException;
import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.Category;
import com.dhopecode.shoppingCart.model.Image;
import com.dhopecode.shoppingCart.model.Product;
import com.dhopecode.shoppingCart.repository.CategoryRepository;
import com.dhopecode.shoppingCart.repository.ImageRepository;
import com.dhopecode.shoppingCart.repository.ProductRepository;
import com.dhopecode.shoppingCart.requests.ProductUpdateRequest;
import com.dhopecode.shoppingCart.requests.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in the db
        // if yes set it the new product category
        // if no then save it as a new category
        // then set as a new product category
        Category category = Optional.ofNullable(categoryRepository
                        .findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
                request.setCategory(category);
                return productRepository.save(createProduct(request, category));
    }
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void  deleteProductById(Long id) {
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete,
                        ()->{throw new ProductNotFoundException("Product not found!");});

    }

    @Override
    public Product  updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String brand, String category) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
    

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProduct(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto =modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image,ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
