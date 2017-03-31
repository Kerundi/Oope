package utils;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Harjoitustyon lotkojen koordinaattitiedoista vastaava luokka
 * luodaan kenttaolioita, joissa on lisaantyneiden Lotkojen koordinaatteja tallennettuna
 *@author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class Kentta {
    int x;
    int y;
    public Kentta(int xk,int yk){
        x = xk;
        y = yk;
    }
    public Kentta() {
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    } 
}
