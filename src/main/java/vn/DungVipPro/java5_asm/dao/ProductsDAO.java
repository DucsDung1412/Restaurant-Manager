package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.Products;

@Repository
public interface ProductsDAO extends JpaRepository<Products, Long> {
    public Page<Products> findAll(Pageable pageable);
    public Page<Products> findByCategory(String category,Pageable pageable);

    public Page<Products> findByNameContains (String name, Pageable pageable);
}
