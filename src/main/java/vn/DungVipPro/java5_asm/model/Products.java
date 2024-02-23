package vn.DungVipPro.java5_asm.model;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "category")
    private String category;
    @Column(name = "season")
    private String season;
    @Column(name = "country")
    private String country;
    @Column(name = "sale")
    private int sale;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> list;

    public Products() {
    }

    public Products(String name, Double price, String category, String season, String country, int sale, Boolean status, String image) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.season = season;
        this.country = country;
        this.sale = sale;
        this.status = status;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<OrderDetails> getList() {
        return list;
    }

    public void setList(List<OrderDetails> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", season='" + season + '\'' +
                ", country='" + country + '\'' +
                ", sale=" + sale +
                ", status=" + status +
                ", image=" + image +
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
