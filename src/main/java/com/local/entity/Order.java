package com.local.entity;

import java.util.Date;

/**
 * @ClassName Order
 * @Description
 * @Author jiangxy
 * @Date 2018\10\15 0015 10:26
 * @Version 1.0.0
 */
public class Order {

    private Integer orderNo;

    private Date orderDate;

    private String orderContent;

    private Boolean payed;

    public Order() {
    }

    public Order(Integer orderNo, Date orderDate, String orderContent, Boolean payed) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderContent = orderContent;
        this.payed = payed;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderContent() {
        return this.orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", orderDate=" + orderDate +
                ", OrderContent='" + orderContent + '\'' +
                ", payed=" + payed +
                '}';
    }
}
