package lotkot;
import utils.StringUtils;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Lotkoluokan konkreettinen jalkelainen. 
 * @author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class Klimppi extends Lotko{
    /**
     *Klimppien tunnusominaisuus
     *voi olla joko Sininen (S) tai Punainen (P))
     */
    private char vari;

    public Klimppi (int ind, int xkoord, int ykoord, int annettuKoko, String annettuPerima, char annettuVari){
        super (ind, xkoord, ykoord, annettuKoko, annettuPerima);
        setVari(annettuVari);
    }
    public char getVari() {
        return vari;
    }

    public void setVari(char vari) {
        this.vari = vari;
    }
    /**
     *Lotkoluokan abstraktin getNimi metodin toteutus
     *@return merkkijonesityksen "Klimppi"
     */
    public String getNimi() {
        return "Klimppi";
    }

    /**
     *korvattu equals metodi, jossa tarkastellaan klimppien varien samanlaisuutta
     *@param vertailtava olio
     *@return palauttaa olioiden varien vertailun tuloksen.
     */
    public boolean equals(Object tutkittava){
        if (!(tutkittava instanceof Klimppi)) {
            return false;
        }
        Klimppi testiKlimppi = (Klimppi) tutkittava;
        return (this.getVari() == testiKlimppi.getVari());
    }

    /**
     *Korvattu toString() metodi, jossa tuotetaan Klimpin tiedot
     *@return tiedoista muodostettu merkkijonoesitys
     */
    public String toString(){
        return super.toString() + StringUtils.kentita(String.valueOf(this.getVari()));
    }

    /**
     *Tallennusta varten luotu toString jossa luodaan Klimpin tiedot ilman koordinaatteja
     *@return ilman koordinaatteja tuotettu Klimpin esitys
     */
    public String toStringIlmanIndeksiaJaKoordinaatteja() {
        return super.toStringIlmanIndeksiaJaKoordinaatteja() + StringUtils.kentita(String.valueOf(this.getVari()));
    }

    /**
     *Tutkitaan voiko Klimppi lisaantya toisen klimpin kanssa. 
     *Edellytyksena sama vari ja koordinaatit
     *@param Vertailtava Klimppi
     *@return palauttaa varien ja koordinaattien vertailun booleanina
     */
    public boolean voikoLisaantya(Lotko toinenLotko){
        if(!(toinenLotko instanceof Klimppi)) {
            return false;
        }
        //voimme siis olettaa, etta kyseessa on aina klimppi
        Klimppi toinenKlimppi = (Klimppi) toinenLotko;

        if(this != toinenKlimppi && this.equals(toinenKlimppi) &&
                this.getxKoordinaatti() == toinenKlimppi.getxKoordinaatti() &&
                this.getyKoordinaatti() == toinenKlimppi.getyKoordinaatti() &&
                this.getVari() == toinenKlimppi.getVari()) {
            return true;
        }
        return false;
    }

    /**
     *Lisaantymiseen tarkoitettu metodi. 
     *@param Lisaantymisessa kaytetty toinen klimppi.
     *@param Lisaantymisen tuloksena tuotettavan Klimpin indeksiarvo.
     *@return palauttaa muodostetun Klimpin
     */
    public Lotko lisaanny(Klimppi toinenKlimppi, int indeksi) {
        //Koko = Klimppi1 ja Klimppi2 painot summattuna ja jaettuna kahdella
        int koko = (this.getKoko() + toinenKlimppi.getKoko()) / 2;
        //perimassa otetaan ensimmaisen klimpin 4 ensimmaista, ja toisen klimpin nelja viimeista merkkia
        String perima = this.getPerima().substring(0, 4) + toinenKlimppi.getPerima().substring(4);
        //kutsutaan parametrillista rakentjaa
        Klimppi klimppi = new Klimppi(indeksi, this.getxKoordinaatti(), this.getyKoordinaatti(),
                koko, perima, this.getVastaVari());
        //palautetaan muodostettu klimppi
        return klimppi;
    }

    /**
     *Metodi joka tuottaa uuden lotkon varin.
     *@return Kasitellyn Klimpin varin vastavarin
     *@throws UnsupportedOperationException mikali Klimpin vari ei ole joko sininen tai punainen
     */
    private char getVastaVari() {
        if(this.vari == 'S') {
            return 'P';
        }
        else if(this.vari == 'P') {
            return 'S';
        }
        else {
            throw new UnsupportedOperationException("Varia " + this.vari + "ei tueta.");
        }
    }
}