package flappyheli;

import java.awt.Color;
import java.awt.Graphics;

public class Tiro extends Base {
    
    @Override
    public void desenhar(Graphics g){
        g.setColor(cor);
        g.fillRect(x, y, largura, altura);
        g.setColor(Color.yellow);
        g.drawRect(x,y,largura,altura);
    }
}