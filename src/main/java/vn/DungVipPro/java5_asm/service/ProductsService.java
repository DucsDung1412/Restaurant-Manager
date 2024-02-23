package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.DungVipPro.java5_asm.dao.ProductsDAO;
import vn.DungVipPro.java5_asm.model.Products;

import java.util.Optional;

@Service
public class ProductsService {
    private ProductsDAO productsDAO;

    @Autowired
    public ProductsService(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Transactional
    public void save(Products products){
        if(products.getId() != null){
            Products p = this.productsDAO.findById(products.getId()).get();
            products.setId(p.getId());
            products.setStatus(p.getStatus());
        }
        this.productsDAO.save(products);
    }

    public Optional<Products> findById(Long id){
        return this.productsDAO.findById(id);
    }

    public Page<Products> findAll(Pageable pageable){
        return this.productsDAO.findAll(pageable);
    }

    public Page<Products> findByCategory(String category, Pageable pageable){
        return this.productsDAO.findByCategory(category, pageable);
    }

    public Page<Products> findByNameContains(String name, Pageable pageable){
        return this.productsDAO.findByNameContains(name, pageable);
    }

    public void delete(Products products) {
        this.productsDAO.findById(products.getId()).ifPresent(p -> {
            this.productsDAO.delete(this.productsDAO.findById(products.getId()).get());
        });
    }
}
