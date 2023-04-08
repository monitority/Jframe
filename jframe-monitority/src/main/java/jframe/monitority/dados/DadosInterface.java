
package jframe.monitority.dados;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;

public class DadosInterface extends javax.swing.JFrame {

   Looca looca;
   
    public DadosInterface() {
        initComponents();
        this.setResizable(false);
        this.looca = new Looca();
        this.setUpOs();
    }
    
    private void setUpOs() {
    Sistema sistema = looca.getSistema();
    Memoria memoria = looca.getMemoria();
    Processador processador = looca.getProcessador();
    DiscoGrupo discoGrupo = looca.getGrupoDeDiscos();
    
    
    lblArmazenamentoValue.setText(String.format("▶ %s", discoGrupo.getTamanhoTotal()));
    lblCpuValue.setText(String.format("▶ %s", processador.getNome()));
    lblDateTimeValue.setText(String.format("▶ %d bits", sistema.getTempoDeAtividade()));
    lblMemoriaRamValue.setText(String.format("▶ %s", memoria.getTotal()));
    lblModeloValue.setText(String.format("▶ %s", sistema.getArquitetura()));
    //lblRedeValue.setText(String.format("▶ %d bits", redeInterface.getNome()));
  }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblModelo = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        lblCpu = new javax.swing.JLabel();
        lblMemoriaRam = new javax.swing.JLabel();
        lblArmazenamento = new javax.swing.JLabel();
        lblRede = new javax.swing.JLabel();
        lblDateTimeValue = new javax.swing.JLabel();
        lblModeloValue = new javax.swing.JLabel();
        lblCpuValue = new javax.swing.JLabel();
        lblMemoriaRamValue = new javax.swing.JLabel();
        lblArmazenamentoValue = new javax.swing.JLabel();
        lblRedeValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(15, 63, 65));

        jPanel1.setBackground(new java.awt.Color(21, 32, 77));

        jPanel2.setBackground(new java.awt.Color(21, 32, 75));

        lblModelo.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("Modelo");

        lblDateTime.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblDateTime.setForeground(new java.awt.Color(255, 255, 255));
        lblDateTime.setText("Data e Horário");

        lblCpu.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblCpu.setForeground(new java.awt.Color(255, 255, 255));
        lblCpu.setText("CPU");

        lblMemoriaRam.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblMemoriaRam.setForeground(new java.awt.Color(255, 255, 255));
        lblMemoriaRam.setText("Memória RAM");

        lblArmazenamento.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblArmazenamento.setForeground(new java.awt.Color(255, 255, 255));
        lblArmazenamento.setText("Armazenamento");

        lblRede.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        lblRede.setForeground(new java.awt.Color(255, 255, 255));
        lblRede.setText("Rede");

        lblDateTimeValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblDateTimeValue.setForeground(new java.awt.Color(255, 255, 255));
        lblDateTimeValue.setText("--");

        lblModeloValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblModeloValue.setForeground(new java.awt.Color(255, 255, 255));
        lblModeloValue.setText("--");

        lblCpuValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblCpuValue.setForeground(new java.awt.Color(255, 255, 255));
        lblCpuValue.setText("--");

        lblMemoriaRamValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblMemoriaRamValue.setForeground(new java.awt.Color(255, 255, 255));
        lblMemoriaRamValue.setText("--");

        lblArmazenamentoValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblArmazenamentoValue.setForeground(new java.awt.Color(255, 255, 255));
        lblArmazenamentoValue.setText("--");

        lblRedeValue.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        lblRedeValue.setForeground(new java.awt.Color(255, 255, 255));
        lblRedeValue.setText("--");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblModelo)
                    .addComponent(lblMemoriaRam)
                    .addComponent(lblMemoriaRamValue)
                    .addComponent(lblCpu)
                    .addComponent(lblCpuValue)
                    .addComponent(lblModeloValue))
                .addGap(216, 216, 216)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblArmazenamentoValue)
                    .addComponent(lblArmazenamento)
                    .addComponent(lblRede)
                    .addComponent(lblRedeValue)
                    .addComponent(lblDateTime)
                    .addComponent(lblDateTimeValue))
                .addContainerGap(244, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(lblArmazenamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModeloValue)
                    .addComponent(lblArmazenamentoValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRede)
                    .addComponent(lblCpu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCpuValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRedeValue)
                .addGap(80, 80, 80)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateTime)
                    .addComponent(lblMemoriaRam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateTimeValue)
                    .addComponent(lblMemoriaRamValue))
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 163, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    public static void main(String args[]) {
     
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DadosInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblArmazenamento;
    private javax.swing.JLabel lblArmazenamentoValue;
    private javax.swing.JLabel lblCpu;
    private javax.swing.JLabel lblCpuValue;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblDateTimeValue;
    private javax.swing.JLabel lblMemoriaRam;
    private javax.swing.JLabel lblMemoriaRamValue;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblModeloValue;
    private javax.swing.JLabel lblRede;
    private javax.swing.JLabel lblRedeValue;
    // End of variables declaration//GEN-END:variables
}
