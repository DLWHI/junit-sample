package edu.school21.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    Long id;
    String name;
    double price;

    private Product() {

    }

    public Product(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public static Product fromResultSet(ResultSet queryResult) 
            throws SQLException {
        Product created = new Product();
        created.id = queryResult.getLong("id");
        created.name = queryResult.getString("name");
        created.price = queryResult.getDouble("price");
        return created;
    }

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void updateName(String new_name) {
        name = new_name;
    }

    public void updatePrice(double new_price) {
        price = new_price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product))
            return this == obj;
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
