/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.tabelas;

public class Dados {
    
    private Integer id;
    private Double processadorPorc;
    private Long Cpuhz;
    private Integer TotalProcessos;
    private Integer ThreadsCpu;
    private Long MemoriaTotal;
    private Long MemoriaDisponivel;
    private Long MemoriaEmUso;
    private Long TamanhoDisco;
    private Long LeituraDisco;
    private Long EscritaDisco;
    private Long TempoTransferencia;
    private String NomeRede;
    private String Hostname;
    private String NomeDeDominio;

    public Double getProcessadorPorc() {
        return processadorPorc;
    }

    public void setProcessadorPorc(Double processadorPorc) {
        this.processadorPorc = processadorPorc;
    }

    public Long getCpuhz() {
        return Cpuhz;
    }

    public void setCpuhz(Long Cpuhz) {
        this.Cpuhz = Cpuhz;
    }

    public Integer getTotalProcessos() {
        return TotalProcessos;
    }

    public void setTotalProcessos(Integer TotalProcessos) {
        this.TotalProcessos = TotalProcessos;
    }

    public Integer getThreadsCpu() {
        return ThreadsCpu;
    }

    public void setThreadsCpu(Integer ThreadsCpu) {
        this.ThreadsCpu = ThreadsCpu;
    }

    public Long getMemoriaTotal() {
        return MemoriaTotal;
    }

    public void setMemoriaTotal(Long MemoriaTotal) {
        this.MemoriaTotal = MemoriaTotal;
    }

    public Long getMemoriaDisponivel() {
        return MemoriaDisponivel;
    }

    public void setMemoriaDisponivel(Long MemoriaDisponivel) {
        this.MemoriaDisponivel = MemoriaDisponivel;
    }

    public Long getMemoriaEmUso() {
        return MemoriaEmUso;
    }

    public void setMemoriaEmUso(Long MemoriaEmUso) {
        this.MemoriaEmUso = MemoriaEmUso;
    }

    public Long getTamanhoDisco() {
        return TamanhoDisco;
    }

    public void setTamanhoDisco(Long TamanhoDisco) {
        this.TamanhoDisco = TamanhoDisco;
    }

    public Long getLeituraDisco() {
        return LeituraDisco;
    }

    public void setLeituraDisco(Long LeituraDisco) {
        this.LeituraDisco = LeituraDisco;
    }

    public Long getEscritaDisco() {
        return EscritaDisco;
    }

    public void setEscritaDisco(Long EscritaDisco) {
        this.EscritaDisco = EscritaDisco;
    }

    public Long getTempoTransferencia() {
        return TempoTransferencia;
    }

    public void setTempoTransferencia(Long TempoTransferencia) {
        this.TempoTransferencia = TempoTransferencia;
    }

    public String getNomeRede() {
        return NomeRede;
    }

    public void setNomeRede(String NomeRede) {
        this.NomeRede = NomeRede;
    }

    public String getHostname() {
        return Hostname;
    }

    public void setHostname(String Hostname) {
        this.Hostname = Hostname;
    }

    public String getNomeDeDominio() {
        return NomeDeDominio;
    }

    public void setNomeDeDominio(String NomeDeDominio) {
        this.NomeDeDominio = NomeDeDominio;
    }

    @Override
    public String toString() {
        String fraseFormatada = String.format("Dados:\n"
                + "{\n"
                + "ProcessadorPorc = %s,\n"
                + "Cpuhz = %s,\n"
                + "TotalProcessos = %d,\n"
                + "ThreadsCpu = %d,\n"
                + "MemoriaTotal = %s,\n"
                + "MemoriaDisponivel = %s,\n"
                + "MemoriaEmUso = %s,\n"
                + "TamanhoDisco = %s,\n"
                + "LeituraDisco = %s,\n"
                + "EscritaDisco = %s,\n"
                + "TempoTransferencia = %s,\n"
                + "NomeRede = %s, \n"
                + "Hostname = %s, \n"
                + "NomeDeDominio = %s, \n"
                + "}",
                processadorPorc,
                Cpuhz,
                TotalProcessos,
                ThreadsCpu,
                MemoriaTotal,
                MemoriaDisponivel,
                MemoriaEmUso,
                TamanhoDisco,
                LeituraDisco,
                EscritaDisco,
                TempoTransferencia,
                NomeRede,
                Hostname,
                NomeDeDominio
        );
        return fraseFormatada;

    }
}