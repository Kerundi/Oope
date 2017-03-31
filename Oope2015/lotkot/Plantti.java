package lotkot;
import utils.StringUtils;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Lotkoluokan konkreettinen jalkelainen. 
 * @author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class Plantti extends Lotko {
    /**
     *Planttien uniikki ominaisuus on muoto.
     *Plantti joko on soikea tai ei ole soike (true/false)
     */
    private boolean muoto;

    //rakentaja
    public Plantti(int ind, int xkoord, int ykoord, int annettuKoko, String annettuPerima, boolean annettuMuoto){
        super (ind, xkoord, ykoord, annettuKoko, annettuPerima);
        setMuoto(annettuMuoto);
    }
    public Plantti() {
    }
    public boolean isMuoto() {
        return muoto;
    }
    public void setMuoto(boolean muoto) {
        this.muoto = muoto;
    }

    /**
     *Korvattu equals-metodi jossa tutkikaan Planttien muotoja
     *@param toinen Plantti
     *@return boolean vertailu Planttien muodoista
     */
    public boolean equals(Object tutkittava){
        if (!(tutkittava instanceof Plantti)) {
            return false;
        }

        Plantti testiPlantti = (Plantti) tutkittava;

        return (this.isMuoto() == testiPlantti.isMuoto());
    }

    /**
     *Lotkon abstrakti getNimi metodin toteutus
     *@return merkkijonoesityksena "plantti"    
     */
    public String getNimi() {
        return "Plantti";
    }

    /**
     *Korvattu toString metodi jossa muodostetaan Plantin tiedot merkkijonoesityksena
     *@return Plantin tiedot merkkijonona
     */
    public String toString(){
        return super.toString() + StringUtils.kentita(String.valueOf(this.isMuoto()));
    }

    /**
    Tallennusta varten tuotettava merkkijonesitys Plantin tiedoista
     */
    public String toStringIlmanIndeksiaJaKoordinaatteja() {
        return super.toStringIlmanIndeksiaJaKoordinaatteja() + StringUtils.kentita(String.valueOf(this.isMuoto()));
    }

    /**
     *Lisaantymisesta vastaava metodi.
     *@param indeksi luku uudelle plantille
     *@retun palauttaa uuden olion joka luodaan palautuksessa
     */
    public Plantti lisaanny(int indeksi) {
        //palautetaan suoraan uusi plantti
        return new Plantti(indeksi, this.getxKoordinaatti(),
                this.getyKoordinaatti(), this.getKoko()/2,
                StringUtils.reverseString(this.getPerima()), this.muoto);
    }
}