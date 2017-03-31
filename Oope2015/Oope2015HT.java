import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import utils.*;
import apulaiset.*;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Paaluokka, jossa otetaan kayttajan antamat komennot
 * @author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class Oope2015HT{
    /**Ennalta maaratyt komennot ja merkit.*/
    static final char REUNAMERKKI = '*';
    static final String ALKUTEKSTI = "ALKULIMA";
    static final String LOPPUTEKSTI = "Ohjelma lopetettu.";
    static final String LOPETUS = "lopeta";
    static final String VIRHE = "Virhe!";
    static final String LATAA = "lataa";
    static final String LUO = "luo";
    static final String LIIKU = "liiku";
    static final String LISTAA = "listaa";
    static final String TIEDOSTO = "lotkot.txt";
    static final String TALLENNA = "tallenna";
    static final String KYSY = "Kirjoita komento:";

    /**
     *Ohjelman main-metodi jossa kysytaan kayttajalta syotteita, kunnes kayttaja paattaa lopettaa
     *Syotteella kutsutaan kasitteleKomento() metodia, jossa kayttajan syote tarkastellaan.
     *Lippumuuttujana toimii merkkijono "valinta".
     */
    public static void main(String[] args){
        //tulostetaan alkuteksti
        alkutervehdys(REUNAMERKKI, ALKUTEKSTI);
        boolean lopetus = false;
        String valinta;
        //luodaan simulaattori ja ladataan suoraan lotkolista.txt
        Simulaattori simulaattori = new Simulaattori();
        simulaattori.lataa(TIEDOSTO);   

        //Varsinainen looppi, jossa ohjelma pyorii, kunnes kauttaja lopettaa
        while (lopetus == false){
            System.out.println(KYSY);
            valinta = In.readString();
            //tarkastellaan mahdolliset virheet
            //kasitellaan samalla try:lla myos kaikki syotevirheet
            try{
                lopetus = kasitteleKomento(valinta, simulaattori);
            }
            //kaikki vaaranlaiset syotteet toteuttavat geneerisen virheilmoituksen
            catch(Exception e){
                System.out.println(VIRHE);
            }
        }
        //tulostetaan lopetus
        System.out.println(LOPPUTEKSTI);
    }   

    /**    
     * Tulostaa kaynnistyksessa tervehdystekstin
     * @param reuna merkki, vakioksi maaritelty merkki reunan piirtoon.
     * @param teksti, vakioksi maaritelty introteksti
     */
    public static void alkutervehdys(char reuna, String teksti){
        for (int i = 0; i<12; i++) //Ylareuna
            System.out.print(reuna); 

        System.out.println(""); 
        System.out.println(reuna + " " + teksti + " " + reuna); //Keskus

        for (int i = 0; i<12; i++)//alareuna
            System.out.print(reuna);
        System.out.println("");
    }

    /** Metodissa kasitellaan kayttajan atnama syote.
     * @param valinta, kayttajan syote.
     * @param class simulaattori. Simulaattori jossa listojen kasittely suoritetaan.
     * @return jos valinta on lopeta palautetaan lippumuuttujaan true
        mikali jokin menee odottamattomasti palauttaa lippumuuttujaan false. 
     * @throws UnsupportedOperationException mikali komento ei vastaa mitaan ennalta asetettua kaskya
     * tai mikali kaskyssa on vaarat parametrit.*/
    private static boolean kasitteleKomento(String valinta, Simulaattori simulaattori) throws FileNotFoundException, UnsupportedEncodingException{

        //lataa lotkolista.txt tiedoston
        if (valinta.equals(LATAA)){
            simulaattori.lataa(TIEDOSTO);
        }       

        //jos kyseessa on jokin liikkumiseen liittyva komento
        else if (valinta.startsWith(LIIKU)){
            //parsitaan syote osiksi
            String[] palat = valinta.split(" ");
            //kaikki komennot verrataan vakioihin, ettei nullpointerexceptionia synny
            //Mikali kyseessa on pelkka liikukomento
            if (LIIKU.equals(valinta)){
                simulaattori.liiku();
            }
            //mikali kyseessa on parametrillinen komento, eli tietyn lotkon siirtyminen tiettyyn pisteeseen
            else if (palat.length == 4){
                simulaattori.liiku(Integer.parseInt(palat[1]), Integer.parseInt(palat[2]), Integer.parseInt(palat[3]));
            }
            //kasitellaan kaikki virheelliset liiku komennot samalla elsella
            else{
                throw new UnsupportedOperationException("Vaara maara paramatereja");
            }
        }
        //Luodaan lotkoja
        else if (valinta.equals(LUO)){
            simulaattori.luo();
        }
        //Mikali kyseessa listaa komento, tehdaan sama kuin liikkumisessa
        else if (valinta.startsWith(LISTAA)){
            String[] osat = valinta.split(" ");
            //kaikilla komennoilla vaan tulostetaan saatu listan toStrig komento
            if (LISTAA.equals(valinta)){
                System.out.println(simulaattori.haeAlkutiedot());
                System.out.print(simulaattori.getLotkoLista().toString());
            }
            else if (osat.length == 2) {
                System.out.print(simulaattori.haeIndeksinMukaiset(Integer.parseInt(osat[1])));
            }
            else if (osat.length == 3){
                System.out.print(simulaattori.haePaikanLotkot(Integer.parseInt(osat[1]), Integer.parseInt(osat[2])));
            }
            //jalleen kasitellaan kaikki virheelliset syotteet
            else{
                throw new UnsupportedOperationException("Vaara maara paramatereja");
            }
        }
        //tallentaminen lotko.txt tiedostoon
        else if (TALLENNA.equals(valinta)) {
            simulaattori.tallenna(TIEDOSTO);
        }        
        //jos kayttaja haluaa lopettaa
        else if (LOPETUS.equals(valinta)){
            return true;
        }
        //Mikali komento ei ole mikaan sallituista, heitetaan virhe
        else {
            throw new UnsupportedOperationException("Komentoa ei loydy");
        }
        //mikali tapahtuu jotain ennalta-arvaamatonta palataan looppiin 
        return false;
    }
}