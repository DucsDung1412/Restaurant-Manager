package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.OrderDetails;

@Repository
public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Long> {

}
