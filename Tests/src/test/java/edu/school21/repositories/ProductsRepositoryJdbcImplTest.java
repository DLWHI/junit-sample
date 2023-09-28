package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import edu.school21.models.Product;

@Parameters(separators = "=")
public class ProductsRepositoryJdbcImplTest {
    @Parameter(names = {"DB_HOST"})
    private String dbHost;
    @Parameter(names = {"DB_PORT"})
    private int dbPort;
    @Parameter(names = {"DB_NAME"})
    private String dbName;
    @Parameter(names = {"DB_USERNAME"})
    private String dbUser;
    @Parameter(names = {"DB_PASSWD"})
    private String dbPasswd;

    private EmbeddedDatabase db;
    
    @BeforeEach
    public void init(){
        LinkedList<String> options = new LinkedList<String>();
        try (Scanner reader = new Scanner(
                    EmbeddedDataSourceTest.class.getResourceAsStream("/.env")
                )) {
            while (reader.hasNextLine()) {
                options.add(reader.nextLine());
            }
        }
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(options.toArray(new String[0]));
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build();
    }

    @ParameterizedTest(name = "Test finiding {0}")
    @ValueSource(longs = {0, 1, 2, 3, 4})
    public void findTest(long id) {
        ProductsRepositoryJdbcImpl pRepo = new ProductsRepositoryJdbcImpl(db);
        Optional<Product> found = pRepo.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getID());
    }

    @Test
    public void findAllTest() {
        ProductsRepositoryJdbcImpl pRepo = new ProductsRepositoryJdbcImpl(db);
        List<Product> allProducts = pRepo.findAll();
        assertEquals(allProducts.size(), 5);
    }

    @Test
    public void insertTest() {
        ProductsRepositoryJdbcImpl pRepo = new ProductsRepositoryJdbcImpl(db);
        Product new_entry = new Product("Alcatratz", 2000000);
        pRepo.save(new_entry);
        Optional<Product> found = pRepo.findById(5l);
        assertTrue(found.isPresent());
        assertEquals(new_entry.getName(), found.get().getName());
        assertEquals(new_entry.getPrice(), found.get().getPrice());
    }

    @Test
    public void updateTest() {
        ProductsRepositoryJdbcImpl pRepo = new ProductsRepositoryJdbcImpl(db);
        Optional<Product> found = pRepo.findById(0l);
        assertTrue(found.isPresent());
        found.get().updatePrice(5000000);
        pRepo.update(found.get());
        found = pRepo.findById(0l);
        assertTrue(found.isPresent());
        assertEquals(5000000, found.get().getPrice());
    }

    @Test
    public void deleteTest() {
        ProductsRepositoryJdbcImpl pRepo = new ProductsRepositoryJdbcImpl(db);
        Optional<Product> found = pRepo.findById(0l);
        assertTrue(found.isPresent());
        pRepo.delete(found.get().getID());
        found = pRepo.findById(0l);
        assertFalse(found.isPresent());
    }
}
