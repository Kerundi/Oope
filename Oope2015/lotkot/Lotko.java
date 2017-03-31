package lotkot;
import utils.StringUtils;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Abstrakti apuluokka, joka toimii ylaluokkana harjoitustyon olioille
 * @author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public abstract class Lotko{

    /**
     *Atribuutit
     *jokaisella Lotkolla on:
     *koko (int) 
     *indeksi (int) 
     *Perima (String)
     *paikat xy-koordinaatisossa:
     *xKoordinaatti (int)
     *yKoordinaatti (int)
     */
    private int koko;
    private int indeksi;
    private String perima;
    private int xKoordinaatti;
    private int yKoordinaatti;
    //RAKENTAJA
    public Lotko(int ind, int xkoord, int ykoord, int annettuKoko, String annettuPerima){
        setIndeksi(ind);
        setxKoordinaatti (xkoord);
        setyKoordinaatti (ykoord);
        setKoko(annettuKoko);
        setPerima(annettuPerima);
    }

    public Lotko(){}

    public int getIndeksi() {
        return indeksi;
    }
    public void setIndeksi(int indeksi) {
        this.indeksi = indeksi;
    }   

    public int getKoko() {
        return koko;
    }

    public void setKoko(int koko) {
        this.koko = koko;
    }
    public String getPerima() {
        return perima;
    }

    public void setPerima(String perima) {
        this.perima = perima;
    }

    public int getxKoordinaatti() {
        return xKoordinaatti;
    }

    public void setxKoordinaatti(int xKoordinaatti) {
        this.xKoordinaatti = xKoordinaatti;
    }

    public int getyKoordinaatti() {
        return yKoordinaatti;
    }

    public void setyKoordinaatti(int yKoordinaatti) {
        this.yKoordinaatti = yKoordinaatti;
    }
    /**
     *Korvattu toString metodi, jossa luodaan jokaisen Lotkon perustiedot
     *@return merkkijonoesityksen Lotkon tiedoista
     */
    public String toString(){
        String palautus = StringUtils.kentitaAlkutiedot(String.valueOf(this.getIndeksi()));     
        palautus += StringUtils.kentitaAlkutiedot(String.valueOf(this.getxKoordinaatti()));
        palautus += StringUtils.kentitaAlkutiedot(String.valueOf(this.getyKoordinaatti()));
        palautus += StringUtils.kentita(this.getNimi());
        palautus += StringUtils.kentita(String.valueOf(this.getKoko()));
        palautus += StringUtils.kentita(this.getPerima());
        return palautus;
    }
    /**
    Tallennusta varten tehtava merkkijonoesitys ilman koordinaatteja
    @return Merkkijonoesityksen Lotkosta ilman koordinaatteja.
     */
    public String toStringIlmanIndeksiaJaKoordinaatteja() {
        String palautus = StringUtils.kentita(this.getNimi());      
        palautus += StringUtils.kentita(String.valueOf(this.getKoko()));
        palautus += StringUtils.kentita(this.getPerima());
        return palautus;
    }
    /**
     *Alaluokkien on pakko toteuttaa getteri, josta selviaa mita Lotkoja ne ovat
     */
    public abstract String getNimi();
    /**
     *Korvattu compareTo metodi, kossa verrataan Lotkojen kokoa
     *@param vertailtava Lotko
     *@return vertailun tulokset: 
     *jos alkuperainen olio isompi: 1
     *jos toinen Lotko isompi: -1
     *jos saman kokoiset: 0
     */
    public int compareTo(Lotko lotko2){
        if (this.getKoko() > lotko2.getKoko() ){
            return 1;
        }
        else if (this.getKoko() == lotko2.getKoko()){
            return 0;
        }
        else{
            return -1;
        }
    }
}