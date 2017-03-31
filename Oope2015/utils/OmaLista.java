package utils;
import fi.uta.csjola.oope.lista.*;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Harjoitustyon listoista vastaavan linkitetyn listan periva omaLista luokka
 * Oope2015HT luokasta kutsutaan simulaattorin metoja, ja kaikki listojen kasittely tapahtuu tassa luokassa.
 *@author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class OmaLista extends LinkitettyLista{
    OmaLista(){
        super();
    }

    /**
    Korvatto toString metodi, joka kutsuu linkitetyn listan toString metodia.
    Luo palautukseen merkkijonoesityksen listan tiedoista.
    @return merkkijonoesityksen listan tiedoista.
     */
    public String toString(){
        String tulokset = "";

        for (int i = 0; i < super.koko(); i++){
            tulokset += super.alkio(i) + "\n";
        }
        return tulokset;
    }

    /**
    Tarkastetaan sisaltaako jokin lista tietyn olion
    @param Tarkastettava olion
    @return palauttaa tiedon siita, loytyyko olio listalta vai ei
     */
    public boolean sisaltaako(Object object) {
        for(int i = 0; i < this.koko; i++) {
            if(this.alkio(i).equals(object)) {
                return true;
            }
        }
        return false;
    }
}

