package com.hnt.reportautoemail.model;

public class ProductInfo {

    private int id;
    private String productName;
    private Double Amount;
    private int quantity;
    private String currency;

    public ProductInfo() {
    }

    public ProductInfo(int id, String productName, Double amount, int quantity, String currency) {
        this.id = id;
        this.productName = productName;
        Amount = amount;
        this.quantity = quantity;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
