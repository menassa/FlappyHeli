package flappyheli;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormGame extends javax.swing.JFrame implements Runnable {
    
    private boolean up, down, right, left, tiro, keyRestart, fimJogo;
    private long ultimoTiro, ultimoCano;
    private int tipoObstaculo, score=0;
    
    Font normal = new Font ("Digiface", Font.BOLD, 15);
    
    public FormGame() {
        initComponents();
        createBufferStrategy(2);
        Thread t = new Thread(this);
        t.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch(evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_LEFT:
                left = true;
                break;
            case com.sun.glass.events.KeyEvent.VK_RIGHT:
                right = true;
                break;
            case com.sun.glass.events.KeyEvent.VK_UP:
                up = true;
                break;
            case com.sun.glass.events.KeyEvent.VK_DOWN:
                down = true;
                break;
            case com.sun.glass.events.KeyEvent.VK_SPACE:
                tiro = true;
                break;
            case com.sun.glass.events.KeyEvent.VK_R:
                keyRestart = true;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_LEFT:
                left = false;
                break;
            case com.sun.glass.events.KeyEvent.VK_RIGHT:
                right = false;
                break;
            case com.sun.glass.events.KeyEvent.VK_UP:
                up = false;
                break;
            case com.sun.glass.events.KeyEvent.VK_DOWN:
                down = false;
                break;
            case com.sun.glass.events.KeyEvent.VK_SPACE:
                tiro = false;
                break;
            case com.sun.glass.events.KeyEvent.VK_R:
                keyRestart = false;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_formKeyReleased

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new FormGame().setVisible(true);
        });
    }

    @Override
    public void run() {
        Graphics graph;
        
        ArrayList <Base> lista = new ArrayList();
        ArrayList <Base> lista_tiros = new ArrayList();
        ArrayList<Base> lixo = new ArrayList();
        
        fimJogo = false;
        
        Heli player = new Heli("img/heli.png");
        player.setAltura(28);
        player.setLargura(60);
        player.setX(getWidth()/6);
        player.setY(getHeight()/2);
        lista.add(player);
        
        Random gerador = new Random();
        
        while(true) {
            graph = getBufferStrategy().getDrawGraphics();
            graph.setColor(Color.cyan);
            graph.fillRect(0,0,getWidth(), getHeight());
            
            long tempo = System.currentTimeMillis();
            
            lista.forEach((base) -> {
                base.mover();
            });
            
            for(Base base: lista)
                base.desenhar(graph);
            
            //Colisão com Obstáculos
            for (Base b : lista) {
                if (player.colisao(b)) {
                    lixo.add(player);
                    fimJogo = true;
                }
            }
            
            for (Base b : lista_tiros) {
                 for (Base c : lista){
                    if (b.colisao(c) && !c.toString().equals("Cano")) {
                    lixo.add(c);
                    score++;
                    }
                 }
            }
            
            //Colisão com Bordas
            for (Base b : lista) {
                Colisao aux = player.trataColisao(getWidth(), getHeight(), player);
                if (aux == Colisao.DOWN || aux == Colisao.UP || aux == Colisao.RIGHT || aux == Colisao.LEFT) {
                    lixo.add(player);
                    fimJogo = true;
                }
            }
            
            if(!fimJogo && tempo > ultimoCano + 3000){
                ultimoCano = tempo;
                
                Obstáculo obstaculo1 = new Obstáculo("img/caixa.png");
                Obstáculo obstaculo2 = new Obstáculo("img/caixa.png");
                Obstáculo obstaculo3 = new Obstáculo("img/caixa2.png");
                tipoObstaculo = gerador.nextInt(2);
                
                Cano canoCima = new Cano();
                canoCima.setCor(Color.green);
                canoCima.setY(30);
                canoCima.setX(getWidth());
                canoCima.setLargura(30);
                canoCima.setAltura(gerador.nextInt((getHeight()-(getHeight()/3) - (getHeight()/4))) + (getHeight()/4));
                canoCima.setIncX(-1);
                canoCima.setIncY(0);
                lista.add(canoCima);
                
                Cano canoBaixo = new Cano();
                canoBaixo.setCor(Color.green);
                canoBaixo.setY(canoCima.getAltura() + 100);
                canoBaixo.setX(getWidth());
                canoBaixo.setAltura(getHeight() - canoCima.getAltura());
                canoBaixo.setLargura(30);
                canoBaixo.setIncX(-1);
                canoBaixo.setIncY(0);
                lista.add(canoBaixo);
                
                if(tipoObstaculo == 0){
                    obstaculo1.setX(getWidth());
                    obstaculo1.setY(canoCima.getAltura() + 37);
                    obstaculo1.setAltura(24);
                    obstaculo1.setLargura(30);
                    obstaculo1.setIncX(-1);
                    obstaculo1.setIncY(0);
                    lista.add(obstaculo1);
                    
                    obstaculo2.setX(getWidth());
                    obstaculo2.setY(canoCima.getAltura() + 68);
                    obstaculo2.setAltura(24);
                    obstaculo2.setLargura(30);
                    obstaculo2.setIncX(-1);
                    obstaculo2.setIncY(0);
                    lista.add(obstaculo2);
                }
                
                else {
                    obstaculo3.setX(getWidth());
                    obstaculo3.setY(canoCima.getAltura() + 40);
                    obstaculo3.setAltura(50);
                    obstaculo3.setLargura(30);
                    obstaculo3.setIncX(-1);
                    obstaculo3.setIncY(0);
                    lista.add(obstaculo3);
                }
            }//if
                
            if(!fimJogo && tiro && tempo > ultimoTiro + 300){
                ultimoTiro = tempo;
                Tiro t = new Tiro();
                t.setCor(Color.red);
                t.setIncX(3);
                t.setIncY(0);
                t.setX(player.getX() + 58);
                t.y = player.y + 18;
                t.setAltura(2);
                t.setLargura(6);
                lista.add(t);
                lista_tiros.add(t);
            }
            
            lista.removeAll(lixo);
            lixo.clear();
            
            if (fimJogo) {
                graph.setColor(Color.black);
                graph.setFont(normal);
                graph.drawString("FIM DE JOGO | Tecle 'R' para continuar.", (getWidth()/2)-140, (getHeight()/2));
            }
            
            graph.setFont(normal);
            graph.setColor(Color.black);
            graph.drawString("Score: " + score, 20, (getHeight() - 20));
            
            if (fimJogo && keyRestart) {
                player = new Heli("img/heli.png");
                player.setAltura(28);
                player.setLargura(60);
                player.setX(getWidth()/6);
                player.setY(getHeight()/2);
                lista.add(player);
                fimJogo = false;
                score = 0;
            }
            
            if(up) {
                player.setIncY(-1);
            } else if(down) {
                player.setIncY(1);
            } else if(left) {
                player.setIncX(-1);
            } else if(right) {
                player.setIncX(1);
            } else{
                player.setIncY(0);
                player.setIncX(0);
            }
            
            graph.dispose();
            getBufferStrategy().show();
            try {
                Thread.sleep(6);
            } catch(InterruptedException ex) {
                Logger.getLogger(FormGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}