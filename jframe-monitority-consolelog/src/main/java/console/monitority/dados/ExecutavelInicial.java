package console.monitority.dados;

import bd.java.ConexaoBDAzure;
import bd.java.ConexaoBDMysql;
import classes.tabelas.Dados;
import classes.tabelas.Empresa;
import classes.tabelas.Estabelecimento;
import classes.tabelas.Totem;
import classes.tabelas.Usuario;
import java.util.List;
import java.util.Scanner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Gabriel Kohatu
 */
public class ExecutavelInicial {

    private static Boolean SerialNumber = false;
    private static Boolean acessoTotem = false;
    private static Integer fkEstabelecimento;
    private static Integer fkMetricaAviso;
    private static Integer fkConfigPC;
    private static Integer idTotem;

     public static void main(String[] args) {
        
        Looca looca = new Looca();

        // Variáveis da API looca
        Memoria memoriaRam = looca.getMemoria();
        Processador processadorCpu = looca.getProcessador();
        ProcessoGrupo processosCpu = looca.getGrupoDeProcessos();
        DiscoGrupo discoGrupo = looca.getGrupoDeDiscos();
        Rede rede = looca.getRede();

        // Variáveis dos dados
        Double processadorPorc = processadorCpu.getUso();
        String processadorPorcFormatado = String.valueOf(processadorPorc).replace(",", ".");
        
        Long Cpuhz = processadorCpu.getFrequencia();
        Integer TotalProcessos = processosCpu.getTotalProcessos();
        Integer ThreadsCpu = processosCpu.getTotalThreads();
        Long MemoriaTotal = memoriaRam.getTotal();
        Double MemoriaTotalDouble = MemoriaTotal.doubleValue() / 1073741824.0;
        String MemoriaTotalFormatado = String.valueOf(MemoriaTotalDouble).replace(",", ".");

        Long MemoriaDisponivel = memoriaRam.getDisponivel();
        Double MemoriaDisponivelDouble = MemoriaDisponivel.doubleValue() / 1073741824.0;
        String MemoriaDisponivelFormatado = String.valueOf(MemoriaDisponivelDouble).replace(",", ".");
       

        Long MemoriaEmUso = memoriaRam.getEmUso();
        Double MemoriaEmUsoDouble = MemoriaEmUso.doubleValue() / 1073741824.0;
        String MemoriaEmUsoFormatado = String.valueOf(MemoriaEmUsoDouble).replace(",", ".");
        

        Double memorioPorc = MemoriaTotalDouble / (MemoriaEmUso * 100);
        String memorioPorcFormatado = String.valueOf(memorioPorc).replace(",", ".");
        
        Long TamanhoDisco = discoGrupo.getTamanhoTotal() / 1073741824;
         
        
       
        Long LeituraDisco = discoGrupo.getDiscos().get(0).getLeituras();
        Long EscritaDisco = discoGrupo.getDiscos().get(0).getEscritas();
        Long TempoTransferencia = discoGrupo.getDiscos().get(0).getTempoDeTransferencia();
    
        String NomeRede = rede.getGrupoDeInterfaces().getInterfaces().get(1).getNome();
        
         
        String Hostname = rede.getParametros().getHostName();
        String NomeDeDominio = rede.getParametros().getNomeDeDominio();
        List<String> ServidorDns = rede.getParametros().getServidoresDns();

        // Boolean SerialNumber = false;
        String TotemSerial = processadorCpu.getId();

        // Variáveis das conexões do Banco
        ConexaoBDAzure conexaoBDAzure = new ConexaoBDAzure();
        JdbcTemplate conAzure = conexaoBDAzure.getConexaoDoBancoAzure();

        ConexaoBDMysql conexaoBDMysql = new ConexaoBDMysql();
        JdbcTemplate conMysql = conexaoBDMysql.getConexaoDoBancoMysql();

        Scanner leitor = new Scanner(System.in);
        Boolean acessoPermitido = false;

        // Ve se existe o código do totem
        do {
            String forSelectTotem = String.format("select * from [dbo].[totem] where serialtotem ='%s'", TotemSerial);

            List<Totem> totens = conAzure.query(forSelectTotem,
                    new BeanPropertyRowMapper<>(Totem.class));

            try {
                if (!totens.isEmpty() && totens.get(0).getSerialTotem().equals(TotemSerial)) {
                    SerialNumber = true;
                    idTotem = totens.get(0).getIdTotem();
                    fkEstabelecimento = totens.get(0).getFkEstabelecimento();
                    fkConfigPC = totens.get(0).getFkConfigPc();
                    
                }
                
                if (SerialNumber) {
                String forSelectEstabelecimento = String.format("select * from [dbo].[Estabelecimento] "
                        + "where idEstabelecimento ='%d'", totens.get(0).getFkEstabelecimento());
                List<Estabelecimento> estabelecimentos = conAzure.query(forSelectEstabelecimento,
                        new BeanPropertyRowMapper<>(Estabelecimento.class));
                if (!estabelecimentos.isEmpty()) {
                    fkMetricaAviso = estabelecimentos.get(0).getFkMetricaAviso();
                    }
                }
                

            } catch (IndexOutOfBoundsException e) {
                if (acessoTotem == false) {
                    System.out.println("Totem não registrado! Logue para acessar o serial do totem.");
                    System.out.println("Digite o seu email:");
                    String email = leitor.next();
                    System.out.println("Digite a sua senha:");
                    String senha = leitor.next();

                    try {
                        String forSelectLogin = String.format("select * from [dbo].[Empresa] where email ='%s' and senha ='%s'", email, senha);
                        List<Empresa> empresas = conAzure.query(forSelectLogin,
                                new BeanPropertyRowMapper<>(Empresa.class));
                        for (Empresa empresa : empresas) {
                            if (empresa.getEmail().equals(email) && empresa.getSenha().equals(senha)) {
                                System.out.println(processadorCpu.getId());
                                acessoTotem = true;
                            }
                        }
                    } catch (IndexOutOfBoundsException er) {
                        String forSelectUsuario = String.format("select * from [dbo].[Usuario] where email ='%s' and senha ='%s'", email, senha);
                        List<Usuario> usuarios = conAzure.query(forSelectUsuario,
                                new BeanPropertyRowMapper<>(Usuario.class));
                        for (Usuario usuario : usuarios) {
                            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                                System.out.println(processadorCpu.getId());
                                acessoTotem = true;
                            }

                        }
                    }
                    return;
                }
                return;
            }
        } while (SerialNumber == false);

        // Parte do Login
        do {
            System.out.println("Digite o seu email:");
            String email = leitor.next();
            System.out.println("Digite a sua senha:");
            String senha = leitor.next();

            try {
                String forSelectLogin = String.format("select * from [dbo].[Empresa] where email ='%s' and senha ='%s'", email, senha);
                List<Empresa> empresas = conAzure.query(forSelectLogin,
                        new BeanPropertyRowMapper<>(Empresa.class));
                for (Empresa empresa : empresas) {
                    if (empresa.getEmail().equals(email) && empresa.getSenha().equals(senha)) {
                        acessoPermitido = true;
                    }
                }
                String forSelectUsuario = String.format("select * from [dbo].[Usuario] where email ='%s' and senha ='%s'", email, senha);
                List<Usuario> usuarios = conAzure.query(forSelectUsuario,
                        new BeanPropertyRowMapper<>(Usuario.class));
                for (Usuario usuario : usuarios) {
                    if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                        acessoPermitido = true;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Login não encontrado.");
            }
        } while (acessoPermitido != true);

        if (acessoPermitido == true) {
        }
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("ta aparecendo aqui");
                String dataAzure = String.format("Insert into Dados"
                        + "(processadorPorc,cpuhz,totalProcessos,threadsCpu,"
                        + "memoriaTotal,memoriaDisponivel,memoriaEmUso,TamanhoDisco,"
                        + "LeituraDisco,EscritaDisco,TempoTransferencia,NomeRede,Hostname,"
                        + "NomeDeDominio,fkTotem,fkEstabelecimento,fkConfigPC,fkMetricaAviso, memoriaPorc) "
                        + "values"
                        + "(%s,%d, %d, %d,"
                        + "%s,%s,%s,%s,"
                        + "%s,%s,%s,'%s','%s',"
                        + "'%s',%d,%d,%d,%d,%s)",
                        processadorPorcFormatado, Cpuhz, TotalProcessos, ThreadsCpu,
                        MemoriaTotalFormatado, MemoriaDisponivelFormatado, MemoriaEmUsoFormatado, TamanhoDisco,
                        LeituraDisco, EscritaDisco, TempoTransferencia, NomeRede, Hostname,
                        NomeDeDominio, idTotem, fkEstabelecimento, fkConfigPC, fkMetricaAviso,memorioPorcFormatado
                );

                conAzure.update(dataAzure);

                String dataSql = String.format("Insert into Dados("
                        + "processadorPorc,"
                        + "cpuhz,"
                        + "totalProcessos,"
                        + "threadsCpu,"
                        + "memoriaTotal,"
                        + "memoriaDisponivel,"
                        + "memoriaEmUso,"
                        + "TamanhoDisco,"
                        + "LeituraDisco,"
                        + "EscritaDisco,"
                        + "TempoTransferencia,"
                        + "NomeRede,"
                        + "Hostname,"
                        + "NomeDeDominio)"
                        + " values ("
                        + "%s,"
                        + "%s,"
                        + "%d,"
                        + "%d,"
                        + "%s,"
                        + "%s,"
                        + "%s,"
                        + "%s,"
                        + "%s,"
                        + "%s,"
                        + "%s,"
                        + "'%s',"
                        + "'%s',"
                        + "'%s')",
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
                conMysql.update(dataSql);
                List<Dados> totens = conMysql.query("select * from Dados",
                        new BeanPropertyRowMapper<>(Dados.class));

                if (!totens.isEmpty()) {
                    System.out.println(totens.get(totens.size() - 1).toString());
                } 

            }
        }, 0, 10000);

    }

}
