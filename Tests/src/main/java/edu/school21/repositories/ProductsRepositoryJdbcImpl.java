package edu.school21.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import edu.school21.models.Product;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final String findQuery = "select * from product where id = ?";
    private final String insertQuery = "insert into " + 
        "product(name, price) values(?, ?)";
    private final String updateQuery = "update product set " +
        "name = ?, price = ? where id = ?";
    private final String deleteQuery = "delete from product where id = ?;";
    private final String findAllQuery = "select * from product";

    private final DataSource DATA;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        DATA = dataSource;
    }
    @Override
    public List<Product> findAll() {
        LinkedList<Product> all = new LinkedList<>();
        try (Connection conn = DATA.getConnection()) {
            PreparedStatement query = conn.prepareStatement(findAllQuery);
            query.execute();
            ResultSet queryResult = query.getResultSet();
            if (queryResult != null) {
                while (queryResult.next()) {
                    all.add(Product.fromResultSet(queryResult));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return all;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> found = Optional.empty();
        try (Connection conn = DATA.getConnection()) {
            PreparedStatement query = conn.prepareStatement(findQuery);
            query.setLong(1, id);
            query.execute();
            ResultSet queryResult = query.getResultSet();
            if (queryResult != null && queryResult.next()) {
                found = Optional.of(Product.fromResultSet(queryResult));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return found;
    }

    @Override
    public void update(Product product) {
        try (Connection conn = DATA.getConnection()) {
            PreparedStatement query = conn.prepareStatement(updateQuery);
            if (product.getID() == null) {
                query.setNull(3, Types.BIGINT);
            } else {
                query.setLong(3, product.getID());
            }
            if (product.getName() == null) {
                query.setNull(1, Types.VARCHAR);
            } else {
                query.setString(1, product.getName());
            }
            if (product.getID() == null) {
                query.setNull(2, Types.BIGINT);
            } else {
                query.setDouble(2, product.getPrice());
            }
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        try (Connection conn = DATA.getConnection()) {
            PreparedStatement query = conn.prepareStatement(insertQuery);
            query.setString(1, product.getName());
            query.setDouble(2, product.getPrice());
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = DATA.getConnection()) {
            PreparedStatement query = conn.prepareStatement(deleteQuery);
            query.setLong(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
