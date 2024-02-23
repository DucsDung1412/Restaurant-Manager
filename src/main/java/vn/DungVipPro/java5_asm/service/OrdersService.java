package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.DungVipPro.java5_asm.dao.OrdersDAO;
import vn.DungVipPro.java5_asm.model.Orders;

@Service
public class OrdersService {
    private OrdersDAO ordersDAO;

    @Autowired
    public OrdersService(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    public void save(Orders orders){
        this.ordersDAO.save(orders);
    }

    public Orders findById(Long id){
        return this.ordersDAO.findOrdersById(id);
    }
}
