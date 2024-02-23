package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.DungVipPro.java5_asm.dao.OrderDetailsDAO;
import vn.DungVipPro.java5_asm.model.OrderDetails;

@Service
public class OrderDetailsService {
    private OrderDetailsDAO orderDetailsDAO;

    @Autowired
    public OrderDetailsService(OrderDetailsDAO orderDetailsDAO) {
        this.orderDetailsDAO = orderDetailsDAO;
    }

    @Transactional
    public void save(OrderDetails orderDetails){
        this.orderDetailsDAO.save(orderDetails);
    }

    @Transactional
    public void delete(OrderDetails orderDetails){
        OrderDetails od = this.orderDetailsDAO.findById(orderDetails.getId()).get();
        this.orderDetailsDAO.delete(od);
    }

    public OrderDetails findById(Long id){
        return this.orderDetailsDAO.findById(id).get();
    }
}
