/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.bd;

import bd.azure.BasicDataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Gabriel Kohatu
 */
public class MetricasCrud {

        private JdbcTemplate jdbcTemplate;

        private MetricasCrud() {
            BasicDataSource dataSource = new BasicDataSource();
// configuração do dataSource, como visto antes
            jdbcTemplate = new JdbcTemplate((DataSource) dataSource);
        }

}
