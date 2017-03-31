package utils;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Harjoitustyon apuluokka, jossa tehdaan merkkijonoille tarvittavia muutoksia.
 *@author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class StringUtils {

    /**
    Metodi tuottaa oikean kokoisen kenttatietueen (harjoitustyossa 8)
    @param haluttu jaottelua vaille oleva merkkijono (harjoitustyossa lotkojen tiedot)
    @return palauttaa 8 merkin kokoisen merkkijonon joka paattyy putkimerkkiin
     */
    public static String kentita(String kentta){
        for(int i = kentta.length() ; i < 8; i++) {
            kentta += " ";
        }
        return kentta + "|";
    }

    /**
    Metodissa kasitellaan alkutietojen ja koordinaattien vaatimat 3 merkin mittaiset kentat
    @param Haluttu tietokentta
    @return oikeanmittainen merkkijono, johon lisatty putkimerkki
     */
    public static String kentitaAlkutiedot(String kentta){
        for (int i = kentta.length(); i < 3; i++){
            kentta += " ";
        }
        return kentta + "|";
    }

    /**
    Planttien lisaantymisessa tarvittava merkkijonon kaantaja
    @param kaannettava merkkijono (plantin perima)
    @return parametrin merkkijono kaannettyna
     */
    public static String reverseString(String kaannettava) {
        return new StringBuilder(kaannettava).reverse().toString();
    }
}
