package jframe.monitority.dados;

import java.sql.Connection;
import bd.java.ConexaoBDAzure;
import bd.java.ConexaoBDMysql;

import classes.tabelas.Totem;
import classes.tabelas.Dados;
import classes.tabelas.Empresa;
import classes.tabelas.Estabelecimento;

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
import java.util.List;
import java.text.DecimalFormat;

import java.util.Timer;
import java.util.TimerTask;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class DadosInterface extends javax.swing.JFrame {

    Looca looca;

    private ConexaoBDAzure conexaoBDAzure = new ConexaoBDAzure();
    private JdbcTemplate conAzure = conexaoBDAzure.getConexaoDoBancoAzure();

    private ConexaoBDMysql conexaoBDMysql = new ConexaoBDMysql();
    private JdbcTemplate conMysql = conexaoBDMysql.getConexaoDoBancoMysql();
    
    public Integer idTotem = 0;
    public Integer fkEstabelecimento = 0;
    public Integer fkMetricaAviso = 0;
   
    public Boolean SerialNumber = false;

    public DadosInterface() {
        initComponents();
        this.looca = new Looca();
        this.setUpOs();
    }

    private void setUpOs() {

        Processador processadorCpu = looca.getProcessador();
        String TotemSerial = processadorCpu.getId();

        String forSelectTotem = String.format("select * from [dbo].[totem] where "
                + "serialtotem ='%s'", TotemSerial);
        List<Totem> totens = conAzure.query(forSelectTotem,
                new BeanPropertyRowMapper<>(Totem.class));
        
        if (SerialNumber != true) {
            System.out.println(processadorCpu.getId());
            try {
                if (totens.get(0).getSerialTotem().equals(TotemSerial)) {
                    idTotem = totens.get(0).getIdTotem();
                    fkEstabelecimento = totens.get(0).getFkEstabelecimento();
                    
                    SerialNumber = true;
                    System.out.println(SerialNumber);
                    String forSelectEstabelecimento = String.format("select * from [dbo].[Estabelecimento] "
                            + "where idEstabelecimento ='%d'", totens.get(0).getFkEstabelecimento());

                    List<Estabelecimento> estabelecimentos = conAzure.query(forSelectEstabelecimento,
                            new BeanPropertyRowMapper<>(Estabelecimento.class));

                    fkMetricaAviso = estabelecimentos.get(0).getFkMetricaAviso();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Totem não listado");
            }

        }

        if (SerialNumber == true) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Sistema sistema = looca.getSistema();
                    

                    // Dados que vamos receber dos seguintes Objetos:
                    Memoria memoriaRam = looca.getMemoria();
                    Processador processadorCpu = looca.getProcessador();
                    ProcessoGrupo processosCpu = looca.getGrupoDeProcessos();
                    DiscoGrupo discoGrupo = looca.getGrupoDeDiscos();
                    Rede rede = looca.getRede();
                    JanelaGrupo janela = looca.getGrupoDeJanelas();
                    DispositivosUsbGrupo usb = looca.getDispositivosUsbGrupo();

                    //discoGrupo.getTamanhoTotal() / 1073741824 ));
                    //memoria.getTotal() / 1000000000));
                    Double processadorPorc = processadorCpu.getUso();
                    String processadorPorcFormatado = String.valueOf(processadorPorc).replace(",", ".");
                    String formatPorc = processadorPorcFormatado.substring(0, 5);
                    Long Cpuhz = processadorCpu.getFrequencia();
                    Integer TotalProcessos = processosCpu.getTotalProcessos();
                    Integer ThreadsCpu = processosCpu.getTotalThreads();
                    // Cpu
                    lblPorcCpuValue.setText(String.format("▶ %.2f%%",
                            processadorPorc));
                    lblHzCpuValue.setText(String.format("▶ %d hz",
                            Cpuhz));
                    lblProcessosCpuValue.setText(String.format("▶ %s",
                            TotalProcessos));
                    lblThreadsCpuValue.setText(String.format("▶ %s",
                            ThreadsCpu));

                    Long MemoriaTotal = memoriaRam.getTotal();
                    Double MemoriaTotalDouble = MemoriaTotal.doubleValue() / 1073741824.0;
                    String MemoriaTotalFormatado = String.valueOf(MemoriaTotalDouble).replace(",", ".");

                    Long MemoriaDisponivel = memoriaRam.getDisponivel();
                    Double MemoriaDisponivelDouble = MemoriaDisponivel.doubleValue()/ 1073741824.0;
                    String MemoriaDisponivelFormatado = String.valueOf(MemoriaDisponivelDouble).replace(",", ".");

                    Long MemoriaEmUso = memoriaRam.getEmUso();
                    Double MemoriaEmUsoDouble = MemoriaEmUso.doubleValue()/ 1073741824.0;
                    String MemoriaEmUsoFormatado = String.valueOf(MemoriaEmUsoDouble).replace(",", ".");
                    
                    Double memorioPorc = (MemoriaEmUsoDouble * 100) / MemoriaTotalDouble ;
                    String memorioPorcFormatado = String.valueOf(memorioPorc).replace(",", ".");
                    // Memória
                    lblTotalMemoriaRamValue.setText(String.format("▶ %.2f GB",
                            MemoriaTotalDouble));
                    lblDisponivelMemoriaRamValue.setText(String.format("▶ %.2f GB",
                            MemoriaDisponivelDouble));
                    lblEmUsoMemoriaValueValue.setText(String.format("▶ %.2f GB",
                            MemoriaEmUsoDouble));

                    Long TamanhoDisco = discoGrupo.getTamanhoTotal() / 1073741824;
                    Long LeituraDisco = discoGrupo.getDiscos().get(0).getLeituras();
                    Long EscritaDisco = discoGrupo.getDiscos().get(0).getEscritas();
                    Long TempoTransferencia = discoGrupo.getDiscos().get(0).getTempoDeTransferencia();
                    // Disco
                    lblTamanhoDiscoValue.setText(String.format("▶ %s GB",
                            TamanhoDisco));
                    lblLeituraDiscoValue.setText(String.format("▶ %d",
                            LeituraDisco));
                    lblEscritaDiscoValue.setText(String.format("▶ %d",
                            EscritaDisco));
                    lblTempoTransferenciaDiscoValue.setText(String.format("▶ %d",
                            TempoTransferencia));

                    String NomeRede = rede.getGrupoDeInterfaces().getInterfaces().get(1).getNome();
                    String Hostname = rede.getParametros().getHostName();
                    String NomeDeDominio = rede.getParametros().getNomeDeDominio();
                    List<String> ServidorDns = rede.getParametros().getServidoresDns();

                    // Rede
                    lblNomeRedeValue.setText(String.format("▶ %s",
                            NomeRede));
                    lblHostnameRedeValue.setText(String.format("▶ %s",
                            Hostname));
                    lblNomeDominioRedeValue.setText(String.format("▶ %s",
                            NomeDeDominio));
                    lblServidoresRedeValue.setText(String.format("▶ %s",
                            ServidorDns));

                    String dataAzure = String.format("Insert into Dados"
                            + "(processadorPorc,cpuhz,totalProcessos,threadsCpu,"
                            + "memoriaTotal,memoriaDisponivel,memoriaEmUso,TamanhoDisco,"
                            + "LeituraDisco,EscritaDisco,TempoTransferencia,NomeRede,Hostname,"
                            + "NomeDeDominio,fkTotem,fkEstabelecimento,fkMetricaAviso,memoriaPorc) "
                            + "values"
                            + "(%s,%d, %d, %d,"
                            + "%s,%s,%s,%s,"
                            + "%s,%s,%s,'%s','%s',"
                            + "'%s',%d,%d,%d,%s)",
                            formatPorc, Cpuhz, TotalProcessos, ThreadsCpu,
                            MemoriaTotalFormatado, MemoriaDisponivelFormatado, MemoriaEmUsoFormatado, TamanhoDisco,
                            LeituraDisco, EscritaDisco, TempoTransferencia, NomeRede, Hostname,
                            NomeDeDominio, idTotem, fkEstabelecimento, fkMetricaAviso,memorioPorcFormatado
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
                            + "NomeDeDominio,"
                            + "memoriaPorc)"
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
                            + "%d,"
                            + "'%s',"
                            + "'%s',"
                            + "'%s',"
                            + " %s)",
                            formatPorc,
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
                            NomeDeDominio,
                            memorioPorcFormatado
                    );
                    conMysql.update(dataSql);
                }
            }, 0, 2000);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblPorcCpuValue = new javax.swing.JLabel();
        lblHzCpuValue = new javax.swing.JLabel();
        lblProcessosCpuValue = new javax.swing.JLabel();
        lblThreadsCpuValue = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTamanhoDiscoValue = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblLeituraDiscoValue = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblEscritaDiscoValue = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblTempoTransferenciaDiscoValue = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTotalMemoriaRamValue = new javax.swing.JLabel();
        lblDisponivelMemoriaRamValue = new javax.swing.JLabel();
        lblEmUsoMemoriaValueValue = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblNomeRedeValue = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblHostnameRedeValue = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblNomeDominioRedeValue = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblServidoresRedeValue = new javax.swing.JLabel();

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setName(""); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 250));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 250));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(15, 63, 65));

        jPanel1.setBackground(new java.awt.Color(21, 32, 77));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 250));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(87, 87, 87));
        jLabel2.setText("CPU");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(87, 87, 87));
        jLabel5.setText("Utilização:");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(87, 87, 87));
        jLabel6.setText("Velocidade:");

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(87, 87, 87));
        jLabel7.setText("Processos:");

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(87, 87, 87));
        jLabel8.setText("Threads:");

        lblPorcCpuValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblPorcCpuValue.setForeground(new java.awt.Color(10, 10, 10));
        lblPorcCpuValue.setText("--");

        lblHzCpuValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblHzCpuValue.setForeground(new java.awt.Color(10, 10, 10));
        lblHzCpuValue.setText("--");

        lblProcessosCpuValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblProcessosCpuValue.setForeground(new java.awt.Color(10, 10, 10));
        lblProcessosCpuValue.setText("--");

        lblThreadsCpuValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblThreadsCpuValue.setForeground(new java.awt.Color(10, 10, 10));
        lblThreadsCpuValue.setText("--");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(lblPorcCpuValue)
                            .addComponent(lblHzCpuValue)
                            .addComponent(lblProcessosCpuValue)
                            .addComponent(lblThreadsCpuValue))))
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(lblPorcCpuValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHzCpuValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProcessosCpuValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThreadsCpuValue)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jPanel5.setName(""); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 250));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(87, 87, 87));
        jLabel3.setText("DISCO");

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(87, 87, 87));
        jLabel13.setText("Tamanho:");

        lblTamanhoDiscoValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTamanhoDiscoValue.setForeground(new java.awt.Color(10, 10, 10));
        lblTamanhoDiscoValue.setText("--");

        jLabel25.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(87, 87, 87));
        jLabel25.setText("Leitura disco:");

        lblLeituraDiscoValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblLeituraDiscoValue.setForeground(new java.awt.Color(10, 10, 10));
        lblLeituraDiscoValue.setText("--");

        jLabel27.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(87, 87, 87));
        jLabel27.setText("Escrita disco:");

        lblEscritaDiscoValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblEscritaDiscoValue.setForeground(new java.awt.Color(10, 10, 10));
        lblEscritaDiscoValue.setText("--");

        jLabel29.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(87, 87, 87));
        jLabel29.setText("Tempo de transferencia:");

        lblTempoTransferenciaDiscoValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTempoTransferenciaDiscoValue.setForeground(new java.awt.Color(10, 10, 10));
        lblTempoTransferenciaDiscoValue.setText("--");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTamanhoDiscoValue)
                            .addComponent(jLabel13)
                            .addComponent(jLabel25)
                            .addComponent(lblLeituraDiscoValue)
                            .addComponent(jLabel27)
                            .addComponent(lblEscritaDiscoValue)
                            .addComponent(jLabel29)
                            .addComponent(lblTempoTransferenciaDiscoValue))))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTamanhoDiscoValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLeituraDiscoValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEscritaDiscoValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTempoTransferenciaDiscoValue)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jPanel7.setName(""); // NOI18N
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 250));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(87, 87, 87));
        jLabel4.setText("MEMÓRIA RAM");

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(87, 87, 87));
        jLabel14.setText("Total:");

        jLabel16.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(87, 87, 87));
        jLabel16.setText("Disponivel:");

        jLabel17.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(87, 87, 87));
        jLabel17.setText("Em uso:");

        lblTotalMemoriaRamValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTotalMemoriaRamValue.setForeground(new java.awt.Color(10, 10, 10));
        lblTotalMemoriaRamValue.setText("--");

        lblDisponivelMemoriaRamValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblDisponivelMemoriaRamValue.setForeground(new java.awt.Color(10, 10, 10));
        lblDisponivelMemoriaRamValue.setText("--");

        lblEmUsoMemoriaValueValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblEmUsoMemoriaValueValue.setForeground(new java.awt.Color(10, 10, 10));
        lblEmUsoMemoriaValueValue.setText("--");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(135, 135, 135))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(lblEmUsoMemoriaValueValue)
                    .addComponent(lblDisponivelMemoriaRamValue)
                    .addComponent(lblTotalMemoriaRamValue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalMemoriaRamValue)
                .addGap(4, 4, 4)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDisponivelMemoriaRamValue)
                .addGap(5, 5, 5)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmUsoMemoriaValueValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jPanel8.setAlignmentX(1.0F);
        jPanel8.setName(""); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(400, 250));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(87, 87, 87));
        jLabel1.setText("REDE");

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(87, 87, 87));
        jLabel15.setText("Nome de rede:");

        lblNomeRedeValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblNomeRedeValue.setForeground(new java.awt.Color(10, 10, 10));
        lblNomeRedeValue.setText("--");

        jLabel18.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(87, 87, 87));
        jLabel18.setText("Nome de domínio:");

        lblHostnameRedeValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblHostnameRedeValue.setForeground(new java.awt.Color(10, 10, 10));
        lblHostnameRedeValue.setText("--");

        jLabel19.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(87, 87, 87));
        jLabel19.setText("Hostname:");

        lblNomeDominioRedeValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblNomeDominioRedeValue.setForeground(new java.awt.Color(10, 10, 10));
        lblNomeDominioRedeValue.setText("--");

        jLabel20.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(87, 87, 87));
        jLabel20.setText("Servidores DNS/IPv4:");

        lblServidoresRedeValue.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblServidoresRedeValue.setForeground(new java.awt.Color(10, 10, 10));
        lblServidoresRedeValue.setText("--");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(170, 170, 170))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblServidoresRedeValue)
                    .addComponent(jLabel20)
                    .addComponent(lblNomeDominioRedeValue)
                    .addComponent(jLabel18)
                    .addComponent(lblHostnameRedeValue)
                    .addComponent(lblNomeRedeValue)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNomeRedeValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHostnameRedeValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNomeDominioRedeValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblServidoresRedeValue)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new DadosInterface().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblDisponivelMemoriaRamValue;
    private javax.swing.JLabel lblEmUsoMemoriaValueValue;
    private javax.swing.JLabel lblEscritaDiscoValue;
    private javax.swing.JLabel lblHostnameRedeValue;
    private javax.swing.JLabel lblHzCpuValue;
    private javax.swing.JLabel lblLeituraDiscoValue;
    private javax.swing.JLabel lblNomeDominioRedeValue;
    private javax.swing.JLabel lblNomeRedeValue;
    private javax.swing.JLabel lblPorcCpuValue;
    private javax.swing.JLabel lblProcessosCpuValue;
    private javax.swing.JLabel lblServidoresRedeValue;
    private javax.swing.JLabel lblTamanhoDiscoValue;
    private javax.swing.JLabel lblTempoTransferenciaDiscoValue;
    private javax.swing.JLabel lblThreadsCpuValue;
    private javax.swing.JLabel lblTotalMemoriaRamValue;
    // End of variables declaration//GEN-END:variables
}
