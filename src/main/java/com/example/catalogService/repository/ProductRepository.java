package com.example.catalogService.repository;

import com.example.catalogService.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends CrudRepository<Product, Integer> {
    //1 way
//    @Query(value = "select p from Product p where p.title ilike :filter")
    //2 way
//@Query(value = "select * from t_product where c_title ilike :filter", nativeQuery = true)
    // 3 way
    @Query(name = "Product.findAllByTitleLikeIgnoringCase", nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
