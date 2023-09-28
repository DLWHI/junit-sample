package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class EmbeddedDataSourceTest {
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

    @Test
    public void connectionTest() {
        assertDoesNotThrow(() -> assertNotEquals(db.getConnection(), null));
    }
}
