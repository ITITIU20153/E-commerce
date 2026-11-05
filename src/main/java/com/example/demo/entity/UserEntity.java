package com.example.demo.entity;

import java.util.List;

import com.example.demo.enums.UserRoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UserRoleEnum role;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id") 
    private CartEntity cartEntity;
    
    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntities;
    
    
    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }
    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
    public CartEntity getCartEntity() {
        return cartEntity;
    }
    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
    protected  UserEntity() {}
    public UserEntity(String username,String password, UserRoleEnum role) {
        this.username= username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRoleEnum getRole() {
        return role;
    }
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
