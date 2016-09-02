package br.tests.dbunit.hsqldb.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by allan on 02/09/16.
 */
@Data
public class Salary {

    private Integer id;
    private BigDecimal value;
    private BigDecimal bonus;
    private BigDecimal increment;

}
