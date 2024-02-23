package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.Orders;

@Repository
public interface OrdersDAO extends JpaRepository<Orders, Long> {
    @EntityGraph(attributePaths = {"list"}) // Định nghĩa fetch join ở đây
    public Orders findOrdersById(Long id);
}
