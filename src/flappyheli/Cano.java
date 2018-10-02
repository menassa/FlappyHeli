/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyheli;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 141140
 */
public class Cano extends Base{

    @Override
    public void desenhar(Graphics g) {
        g.setColor(cor);
        g.fillRect(x, y, largura, altura);
        g.setColor(Color.BLACK);
        g.drawRect(x,y,largura,altura);
    }
    
    @Override 
    public String toString() {
        return "Cano";
    }
}
