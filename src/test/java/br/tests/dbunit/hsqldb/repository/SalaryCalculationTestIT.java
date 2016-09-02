package br.tests.dbunit.hsqldb.repository;

import br.tests.dbunit.hsqldb.entity.Salary;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by allan on 02/09/16.
 */
@Slf4j
public class SalaryCalculationTestIT {
    private static IDatabaseTester databaseTester;
    private static SalaryRepository repository;
    private static Salary salary;

    @BeforeClass
    public static void init() throws Exception {
        databaseTester = new JdbcDatabaseTester(org.hsqldb.jdbcDriver.class
                .getName(), "jdbc:hsqldb:file:/home/allan/testdb", "sa", "");
        dropTablesSinceDbUnitDoesNot(databaseTester.getConnection().getConnection());
        createTablesSinceDbUnitDoesNot(databaseTester.getConnection().getConnection());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("input.xml");

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new BufferedInputStream(input));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();
        repository = new SalaryRepository();
        repository.setConnection(databaseTester.getConnection().getConnection());
    }

    private static void dropTablesSinceDbUnitDoesNot(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS SALARY");
        statement.execute();
        statement.close();
    }

    private static void createTablesSinceDbUnitDoesNot(Connection connection) throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("CREATE TABLE SALARY (id INT, value DOUBLE, bonus DOUBLE, increment DOUBLE)");
        statement.execute();
        statement.close();
    }

    @Test
    public void testCalculator() throws SQLException {
        assertEquals(new BigDecimal(30000), repository.calculatorById(1).setScale(0));
    }

    @Test
    public void testIdNotFoud() throws SQLException {
        salary = new Salary();
        salary = repository.getSalaryById(new Integer(2));
        assertEquals(new Integer(2), salary.getId());
    }

    @Test
    public void testSalariesNotNull() throws SQLException {
        for (Salary salary1 : repository.getAllSalaries()) {
            log.info(salary1.getId().toString() + " " + salary1.getValue().setScale(2) + " " + salary1.getBonus().setScale(2) + " " + salary1.getIncrement().setScale(2));
        }
        assertNotNull(repository.getAllSalaries());
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        repository = null;
        databaseTester.onTearDown();
        databaseTester = null;
    }
}