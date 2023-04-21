/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd.azure;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Gabriel Kohatu
 */
public class ConexaoBD {
    
    private JdbcTemplate conexaoDoBanco;
    
    public ConexaoBD() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource​.setDriverClassName("org.h2.Driver");

        dataSource​.setUrl("jdbc:h2:file:./banco_teste");

        dataSource​.setUsername("sa");

        dataSource​.setPassword("");

        this.conexaoDoBanco = new JdbcTemplate(dataSource);

    }

    public JdbcTemplate getConexaoDoBanco() {

        return conexaoDoBanco;

    }
}
