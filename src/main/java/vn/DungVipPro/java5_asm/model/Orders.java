package vn.DungVipPro.java5_asm.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;
    @Column(name = "date_add")
    private Date dateAdd;
    @Column(name = "status_pay")
    private Boolean statusPay;
    @Column(name = "status_transport")
    private Boolean statusTransport;
    @Column(name = "address")
    private String address;
    @Column(name = "status_orders")
    private Boolean statusOrders;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
            , fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> list;

    public Orders() {
    }

    public Orders(Date dateAdd, Boolean statusPay, Boolean statusTransport, String address, Boolean statusOrders, UserInfo userInfo) {
        this.dateAdd = dateAdd;
        this.statusPay = statusPay;
        this.statusTransport = statusTransport;
        this.address = address;
        this.statusOrders = statusOrders;
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Boolean getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(Boolean statusPay) {
        this.statusPay = statusPay;
    }

    public Boolean getStatusTransport() {
        return statusTransport;
    }

    public void setStatusTransport(Boolean statusTransport) {
        this.statusTransport = statusTransport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Boolean getStatusOrders() {
        return statusOrders;
    }

    public void setStatusOrders(Boolean statusOrders) {
        this.statusOrders = statusOrders;
    }

    public List<OrderDetails> getList() {
        return list;
    }

    public void setList(List<OrderDetails> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", dataAdd=" + dateAdd +
                ", statusPay='" + statusPay + '\'' +
                ", statusTransport='" + statusTransport + '\'' +
                ", Address='" + address + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public List<OrderDetails> addList(OrderDetails orderDetails){
        if(this.getList() == null){
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            this.setList(orderDetailsList);
        }
        this.getList().add(orderDetails);
        return this.getList();
    }
}
