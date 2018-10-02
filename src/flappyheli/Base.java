package flappyheli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Base {

    protected int x;
    protected int y;
    protected int incX = 1;
    protected int incY = 1;
    protected int largura = 30;
    protected int altura = 30;
    protected Color cor = Color.RED;
    protected Rectangle rect = new Rectangle(0,0,30,30);
    protected ImageIcon img;

    public Base(){
    }
    public Base(String url){
       img     = new ImageIcon(this.getClass().getResource("/"+url));
       largura = img.getIconWidth();
       altura  = img.getIconHeight();
       rect.height = altura;
       rect.width = largura;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.rect.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.rect.y =y;
    }

    public int getIncX() {
        return incX;
    }

    public void setIncX(int incX) {
        this.incX = incX;
    }

    public int getIncY() {
        return incY;
    }

    public void setIncY(int incY) {
        this.incY = incY;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
        this.rect.width = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
        this.rect.height = altura;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public void desenhar(Graphics g){
       
        if(img != null){
         g.drawImage(img.getImage(), x, y, null);
        }
       
    }
    
    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
    
    public Colisao trataColisao(int width, int height, Base b) {
        Colisao resp = Colisao.NONE;
        if (x < 0) {
            resp = Colisao.LEFT;
        } else if (x > width - b.getLargura()) {
            resp = Colisao.RIGHT;
        }

        if (y < 28) {
            
            resp = Colisao.UP;
        } else if (y > height - b.getAltura()) {
            
            resp = Colisao.DOWN;
        }
        
        return resp;
    }
    
    public boolean colisao(Base outraBase){
        if(this.equals(outraBase))
            return false;
        else            
            return rect.intersects(outraBase.rect);
    }
    
    public void mover() {
        x = x + incX;
        y = y + incY;
        this.rect.x = x;
        this.rect.y = y;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Base other = (Base) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override 
    public String toString() {
        return "Base{" + "x=" + x + ", y=" + y + ", incX=" + incX + ", incY=" + incY + ", cor=" + cor + ", largura=" + largura + ", altura=" + altura + ", rect=" + rect + ", img=" + img + '}';
    }
}