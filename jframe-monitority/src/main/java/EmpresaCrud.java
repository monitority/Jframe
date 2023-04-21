/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import classes.tabelas.Empresa;
import java.util.List;
import java.util.Map;
import classes.map.EmpresaRowMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class EmpresaCrud {

    private JdbcTemplate jdbcTemplate;

    private EmpresaCrud() {
        BasicDataSource dataSource = new BasicDataSource();
        // configuração do dataSource, como visto antes

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public List listarTodos() {
        List<Empresa> lista = jdbcTemplate.query("select * from [dbo].[empresa]", new EmpresaRowMapper());
        return lista;
    }

}
