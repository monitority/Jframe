/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.tabelas;

/**
 *
 * @author Gabriel Kohatu
 */
public class Totem {
    
    private Integer idTotem;
    private String serialTotem;
    private Integer fkEstabelecimento;
    private Integer fkMetricaAviso;

    public Integer getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(Integer idTotem) {
        this.idTotem = idTotem;
    }

    public String getSerialTotem() {
        return serialTotem;
    }

    public void setSerialTotem(String serialTotem) {
        this.serialTotem = serialTotem;
    }

    public Integer getFkEstabelecimento() {
        return fkEstabelecimento;
    }

    public void setFkEstabelecimento(Integer fkEstabelecimento) {
        this.fkEstabelecimento = fkEstabelecimento;
    }

    public Integer getFkMetricaAviso() {
        return fkMetricaAviso;
    }

    public void setFkMetricaAviso(Integer fkMetricaAviso) {
        this.fkMetricaAviso = fkMetricaAviso;
    }

    @Override
    public String toString() {
        return "Totem{" + "idTotem=" + idTotem + ", serialTotem=" + serialTotem + ", fkEstabelecimento=" + fkEstabelecimento + ", fkMetricaAviso=" + fkMetricaAviso + '}';
    }

   
}
