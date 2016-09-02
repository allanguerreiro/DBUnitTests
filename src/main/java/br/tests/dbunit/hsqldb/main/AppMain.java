package br.tests.dbunit.hsqldb.main;

import br.tests.dbunit.hsqldb.entity.Salary;
import br.tests.dbunit.hsqldb.repository.SalaryRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by allan on 02/09/16.
 */
@Slf4j
public class AppMain {
    public static void main(String[] args) {
        SalaryRepository repository = new SalaryRepository();
        try {
            for (Salary salary1 : repository.getAllSalaries()) {
                log.info(salary1.getId().toString() + " " + salary1.getValue().setScale(2) + " " + salary1.getBonus().setScale(2) + " " + salary1.getIncrement().setScale(2));
            }
            System.out.println("O Salário total é: " + repository.calculatorById(new Integer(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
