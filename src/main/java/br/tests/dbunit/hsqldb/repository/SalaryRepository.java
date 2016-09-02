package br.tests.dbunit.hsqldb.repository;

import br.tests.dbunit.hsqldb.entity.Salary;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 02/09/16.
 */
@Slf4j
public class SalaryRepository {
    @Setter
    private Salary salary;

    private List<Salary> salaryList;

    @Setter
    private Connection connection;
    private static final String QUERY_BY_ID = "select * from salary where id = ?";
    private static final String QUERY = "select * from salary";

    public SalaryRepository() {
        try {
            this.connection = this.getSQLConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating Connection
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection getSQLConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/struts2", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Salary Calculation
     *
     * @param id Integer
     * @return salary
     * @throws SQLException
     */
    public BigDecimal calculatorById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            salaryList = new ArrayList<Salary>();
            salaryList = returnSalaries(resultSet);
            salary = new Salary();
            for (Salary sal : salaryList) {
                salary = sal;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return (salary.getValue().add(salary.getBonus()).add(salary.getIncrement()));
    }

    public Salary getSalaryById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            salaryList = returnSalaries(resultSet);
            salary = new Salary();
            salary = salaryList.iterator().next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return salary;
    }

    public List<Salary> getAllSalaries() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            return returnSalaries(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private List<Salary> returnSalaries(ResultSet resultSet) {
        List<Salary> salaries = new ArrayList<Salary>();
        try {
            while (resultSet.next()) {
                salary = new Salary();
                salary.setId(resultSet.getInt("id"));
                salary.setValue(resultSet.getBigDecimal("value"));
                salary.setBonus(resultSet.getBigDecimal("bonus"));
                salary.setIncrement(resultSet.getBigDecimal("increment"));
                salaries.add(salary);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
        return salaries;
    }
}
