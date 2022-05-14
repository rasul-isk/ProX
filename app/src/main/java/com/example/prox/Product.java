package com.example.prox;

public class Product {
    String name = "";
    String store = "";
    String price = "";

    public Product(String name, String store, String price) {
        this.name = name;
        this.store = store;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
