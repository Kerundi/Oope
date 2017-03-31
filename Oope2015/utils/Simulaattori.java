package utils;
import apulaiset.*;
import lotkot.*;
import java.io.*;
import java.util.*;
/**
 * Olio-ohjelmoinnin perusteet, Kevat 2015, harjoitustyo.
 * <p>
 * Harjoitustyon simulaatiosta vastaava luokka.
 * Oope2015HT luokasta kutsutaan simulaattorin metoja, ja kaikki listojen kasittely tapahtuu tassa luokassa.
 *@author Joni Sarikoski (Sarikoski.Joni.E@Student.uta.fi),
 * <p>
 */
public class Simulaattori{

    /**
    Lotkot.txt tiedoston alussa annetaan listan kanssa kaytettavaa
    siemenlukua seka maksimaaliset x ja y koordinaatiston tiedot.
    Kaikki lotkot tallennetaan LotkoLista nimiseen linkitettyyn listaan
     */
    private int maxX;
    private int maxY;
    private int siemenLuku;
    private OmaLista lotkoLista;


    public Simulaattori(){
        this.lotkoLista = new OmaLista();

    }
    public int getMaxX() {
        return maxX;
    }
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }
    public int getMaxY() {
        return maxY;
    }
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
    public int getSiemenLuku() {
        return siemenLuku;
    }
    public void setSiemenLuku(int siemenLuku) {
        this.siemenLuku = siemenLuku;
    }
    public OmaLista getLotkoLista() {
        return lotkoLista;
    }
    public void setLotkoLista(OmaLista lotkoLista) {
        this.lotkoLista = lotkoLista;
    }

    /**
    Metodi lataa lotkot.txt tiedoston ja ottaa talteen siemenluvun ja koordinaatit
    @param tiedoston nimi, josta lukija hakee lotkojen tiedot
     */
    public void lataa(String tiedosto){
        //alustetaan scanneri
        Scanner lukija = new Scanner("");
        try {

            // Laitetaan oliot ensin valiaikaiseen listaan,
            // jotta voidaan varmistua etta koko operaatio joko onnistuu tai epaonnistuu
            OmaLista temp = new OmaLista();
            FileReader file = new FileReader(tiedosto);
            lukija = new Scanner(file);

            // otetaan talteen siemenLuku, maxX ja maxY arvot loopin ulkopuolella tiedostosta
            String[] alkuosa = lukija.nextLine().split("[|]");
            siemenLuku = Integer.parseInt(alkuosa[0].trim());
            maxX = Integer.parseInt(alkuosa[1].trim());
            maxY = Integer.parseInt(alkuosa[2].trim());
            //alustetaan automaatti
            Automaatti.alusta(siemenLuku);
            // Luetaan loput tiedot tiedostosta
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                String[] osat = rivi.split("[|]");

                // Poistetaan turhat valilyonnit
                for (int i = 0; i < osat.length; i++) {
                    osat[i] = osat[i].trim();
                }
                int[] taulu = Automaatti.annaPaikka(maxX, maxY);

                // Lisaa osista oliolistaan kokonaisia lotkoja
                if ("Plantti".equals(osat[0])){
                    temp.lisaaLoppuun(new Plantti(temp.koko(), taulu[0], 
                            taulu[1], Integer.parseInt(osat[1]),osat[2], Boolean.parseBoolean(osat[3])));

                }
                else if ("Klimppi".equals(osat[0])){
                    temp.lisaaLoppuun(new Klimppi (temp.koko(), taulu[0], 
                            taulu[1], Integer.parseInt(osat[1]),osat[2], osat[3].charAt(0)));
                }
            }
            this.lotkoLista = temp;
        } 
        catch (IOException e) {
            // Jos tiedostoa ei loydy, otetaan errori talteen ja palautetaan virheilmoitus kayttajalle
            System.out.println("Tiedostoa ei loytynyt");
        }
        finally {
            lukija.close();
        }
    }

    /**
    Metodi joka listaa tietyn indeksin mukaiset oliot.
    @param indeksinumero, minkatyyppisia olioita etsitaan.
    @return palauttaa sopivista lotkoista koostetun listan
    @throw IllegalArgumentException mikali annettu indeksi ei ole sopiva
     */
    public OmaLista haeIndeksinMukaiset(int indeksi){
        //luodaan tilapainen lista, johon kerataan sopivat lotkot
        OmaLista tulosLista = new OmaLista();
        //tarkastetaan ettei koiteta etsia liian suuria indeksiarvoja
        if (indeksi > lotkoLista.koko() - 1 || indeksi<0){
            throw new IllegalArgumentException();
        }
        //kaydaan lapi lotkot, kunnes loydetaan oikean indeksin lotko 
        for (int i = 0; i < lotkoLista.koko(); i++){
            Lotko lotko = (Lotko) lotkoLista.alkio(i);
            //Kun loydetaan oikea olio
            if (lotko.getIndeksi() == indeksi){
                //kaydaan lapi uudestaan kaikki lotkot ja lisataan vastaavat (equals) lotkot listaan
                //nain listaan tulee myos lotko itse
                for (int j = 0; j < lotkoLista.koko(); j++){
                    if (lotko.equals(lotkoLista.alkio(j))){

                        tulosLista.lisaaLoppuun(lotkoLista.alkio(j));
                    }
                }
            }
        }
        //palautetaan kokoelma
        return tulosLista;
    }

    /**
    Haetaan tietyissa koordinaateissa olevat lotkot, ja palautetaan niista koostettu lista.
    @param xkoordinaatti ja ykoordinaatti
    @return palauttaa koordinaateissa olevista lotkoista koostetun listan
    @throws IllegalArgumentException mikali koitetaan hakea liian suurilla/pienilla koordinaateilla
     */
    public OmaLista haePaikanLotkot(int xKoord, int yKoord) {
        //luodaan tilapainen tuloslista
        OmaLista tulosLista = new OmaLista();
        //tarkastetaan etteivat koordinaatit ole pelialueen ulkopuolella
        if (xKoord > getMaxX() || yKoord > getMaxY() || xKoord < 0 || yKoord < 0) {
            throw new IllegalArgumentException();
        }
        //kaydaan kaikki lotkolistan lotkot lapi.
        for (int i = 0; i < lotkoLista.koko(); i++){
            Lotko lotko = (Lotko) lotkoLista.alkio(i);
            //Mikali koordinaatit ovat samat, kuin annetut koordinaatit lisataan lotko listaan.
            if (lotko.getxKoordinaatti() == xKoord && lotko.getyKoordinaatti() == yKoord){
                tulosLista.lisaaLoppuun(lotko);
            }
        }
        //palautetaan koordinaateissa olevien lotkojen lista.
        return tulosLista;
    }

    /**
    Opettajan antaman automaattiluokan avulla toteutettu liikkuminen.
    Metodissa pyydetaan automaatilta lotko kerrallaan uudet koordinaatit.
    Automaatin palauttamasta taulukosta otetaan uudet x ja y-koordinaatit.
     */
    public void liiku(){
        //kaydaan lotkot yksi kerrallaan lapi ja asetetaan uudet koordinaatit
        for (int i = 0; i<lotkoLista.koko(); i++){
            Lotko lotko = (Lotko) lotkoLista.alkio(i);
            int[] apuTaulu = new int[2];
            apuTaulu = Automaatti.annaPaikka(lotko.getxKoordinaatti(), lotko.getyKoordinaatti(), maxX, maxY);  
            lotko.setxKoordinaatti(apuTaulu[0]);
            lotko.setyKoordinaatti(apuTaulu[1]);
        }
    }

    /**
    Metodi jossa tietyn indeksin lotko siirretaan tiettyyn koordinaatiston pisteeseen.
    @param halutun lotkon indeksi ja toivotut koordinaatit
    @throws IllegalArgumentException mikali koitetaan liikkua pelialueen ulkopuolelle.
     */
    public void liiku(int inde, int xkoordi, int ykoordi){
        //tarkastetaan etta syotteet ovat oikeelliset
        if (xkoordi > getMaxX() || ykoordi > getMaxY() || xkoordi < 0 || ykoordi < 0) {
            throw new IllegalArgumentException();
        }
        //luodaan apulotko ja kaytetaan settereita, etta saadaan halutut koordinaatit asetettua.
        Lotko lotko = (Lotko) lotkoLista.alkio(inde);
        lotko.setxKoordinaatti(xkoordi);
        lotko.setyKoordinaatti(ykoordi);
    }

    /**
    Tallennetaan sen hetkisten lotkojen tilanne lotkot.txt tiedostoon ja poistetaan koordinaatit ennen tallennusta
    @param merkkijono esityksena kohdetiedoston nimen
    @throws FileNotFoundException ja UnsupportedEncodingException mikali tiedostoa ei loydy tai kaytetty merkisto ei kelpaa
     */
    public void tallenna(String tiedostoNimi) throws FileNotFoundException, UnsupportedEncodingException {
        //luodaan kirjoittaja, maaritellaan tiedosto ja merkisto
        PrintWriter writer = new PrintWriter(tiedostoNimi, "UTF-8"); 
        //kutsutaan apumetodia, jossa tallennetaan alkutiedot eli x y ja siemenluku
        writer.println(this.tallennaAlkutiedot());

        // Ei voida kayttaa listan toString-metodia, koska ei haluta kutsua olioiden toStringia
        //kirjotietaan lotkot yksi kerrallaan listaan ilman koordinaatteja
        for(int i = 0; i < this.lotkoLista.koko(); i++) {
            Lotko lotko = (Lotko) this.lotkoLista.alkio(i);
            writer.println(lotko.toStringIlmanIndeksiaJaKoordinaatteja());
        }
        //suljetaan kirjoitin.
        writer.close();
    }

    /**
    Lisaantymisesta vastaava metodi. Metodissa kerataan ensin kaikki klimpit ja Plantit omaan listaan jatkotarkastelun helpottamiseksi.
    Taman jalkeen kerataan kasiteltavan Lotkon koordinaateissa olevat lotkot omaan apulistaansa.
    Apulistan avulla kaydaan lapi mitka paikan Lotkot lisaantyvat.
     */
    public void luo(){
        //Luodaan apulistat
        OmaLista lisaantyneetLotkot = new OmaLista();
        //korvataan lisaantyneetLotkot, LotkoListan tiedoilla
        //Nain emme kasvata tarkasteltavien listojen kokoja, vaan kaymme lapi vain lisaantymiskelpoiset Lotkot.
        listoitus(lisaantyneetLotkot, lotkoLista);
        //apulistat planteille ja klimpeille
        OmaLista plantit = new OmaLista();
        OmaLista klimpit = new OmaLista();
        //Apulistat jo kasitellyille klimpeille ja planteille.
        //listoihin kootaan Kentta-olikoita, joihin merkitaan kasitellyt koordinaatit
        OmaLista lisaantyneetKlimpit = new OmaLista();
        OmaLista lisaantyneetPlantit = new OmaLista();

        //Kerataan klimpit ja lotkot omiksi listoiksi
        for (int i = 0; i < lotkoLista.koko(); i++){
            Lotko lotko1 = (Lotko) lisaantyneetLotkot.alkio(i);
            if (lotko1 instanceof Klimppi){
                klimpit.lisaaLoppuun(lotko1);
            }
            else if (lotko1 instanceof Plantti){
                plantit.lisaaLoppuun(lotko1);

            }
        }
        //aletaan kaymaan lapi lisaantyvia lotkoja
        for (int i = 0; i < lotkoLista.koko(); i++){
            Lotko kasiteltava = (Lotko) lotkoLista.alkio(i);
            //tilapaiset x ja y koordinaatit, eli ne koordinaatit jotka tassa tapauksessa kaydaan lapi
            int  temp_x = kasiteltava.getxKoordinaatti();
            int  temp_y = kasiteltava.getyKoordinaatti();

            //tilapainen lista, joka paivitetaan aina kun siirrytaan kasittelemaan seuraavaa Lotkoa
            OmaLista temp_paikassaOlevatPlantit = new OmaLista();          
            OmaLista temp_paikassaOlevatKlimpit = new OmaLista();

            //Kertaan listoihin paikassa olevat klimpit ja Plantit1
            temp_paikassaOlevatPlantit = koordinaatissaOlevat(plantit, temp_x, temp_y);
            temp_paikassaOlevatKlimpit = koordinaatissaOlevat(klimpit, temp_x, temp_y);

            //otetaan viel yhteislista, mihin kootaan kaikki paikassa olevat Lotkot
            OmaLista yhteensaPaikassa = new OmaLista();
            yhteensaPaikassa = koordinaatissaOlevat(lotkoLista, temp_x, temp_y);

            //aletaan kaymaan lapi paikassa olevia Lotkoja yksi kerrallaan.
            for(int c = 0; c < yhteensaPaikassa.koko(); c++){
                Lotko kasiteltava2 = (Lotko) yhteensaPaikassa.alkio(c);

                //mikali kasittelyvuorossa on Klimppi, kaydaan lapi koordinaateissa olevat klimpit ja tutkivaan voiko se lisaantya
                if (kasiteltava2 instanceof Klimppi){
                    Klimppi klimppi1 = (Klimppi) kasiteltava2;
                    for (int j = 0; j < temp_paikassaOlevatKlimpit.koko(); j++){
                        Klimppi klimppi2 = (Klimppi) temp_paikassaOlevatKlimpit.alkio(j);
                        //varmistetaan etta kyseessa on eri klimpit ja ettei kyseisessa paikassa ole jo klimpit lisaantyneet.
                        //Kun lisaantyminen on tapahtunut, ei ole enaa edes mahdollista luoda uusia klimppeja
                        if (klimppi1.getIndeksi() != klimppi2.getIndeksi() && !(onkoLisaannytty(lisaantyneetKlimpit, temp_x, temp_y))){
                            //Mikali kyseisessa paikassa ei ole lisaannytty, tarkastetaan onko mahdollista lisaannyttaa tata Klimppi paria
                            if (klimppi1.getVari() == klimppi2.getVari()){
                                //Mikali lisaantyminen onnistuu, luodaan uusi klimppi ja lisataan se apulistaan.
                                lisaantyneetLotkot.lisaaLoppuun(klimppi1.lisaanny(klimppi2, lisaantyneetLotkot.koko()));
                                //Luodaan viela jatkotarkistusta auttava lista lisaantyneista koordinaateista.
                                Kentta kentta = new Kentta(temp_x, temp_y);
                                lisaantyneetKlimpit.lisaaLoppuun(kentta);
                            }
                        }
                    }
                }

                //jos kyseessa onkin plantti, ja kyseinen plantti ei viela ole lisaantynyt
                else if (kasiteltava2 instanceof Plantti && !(onkoLisaannytty(lisaantyneetPlantit, temp_x, temp_y))){
                    //aletaan kaymaan lapi koordinaateista loytyvia plantteja
                    //luodaan lisaantymista varten jo kentta-tiedot
                    Kentta kentta2 = new Kentta(temp_x, temp_y);
                    //lisataan viela apulistaan merkinta, etta tama Plantti on jo kasitelty
                    lisaantyneetPlantit.lisaaLoppuun(kentta2);
                    //Luodaan apuoliot tarkastelua varten ja mahdollista lisaantymista varten
                    Plantti plantti1 = (Plantti) kasiteltava2;
                    Plantti isoin = new Plantti();
                    //apumuuttuja isoimman painon etsimiselle
                    isoin = null;
                    //lasketaan montako false ja true tyyppistä planttia on
                    int falseja = montako(temp_paikassaOlevatPlantit, false);
                    int trueja = montako(temp_paikassaOlevatPlantit, true);
                    //haetaan suurin paikasta loytyva paino
                    int suurin = palautaSuurin(temp_paikassaOlevatPlantit);

                    //Mikali paikassa on useampi, kuin yksi plantti tarkastetaan mika niista on isoin
                    if (temp_paikassaOlevatPlantit.koko() > 1){
                        //kaydaan kaikki pisteen Plantit lapi
                        for (int j = 0; j <temp_paikassaOlevatPlantit.koko(); j++){
                            //vertailuplantti
                            Plantti plantti2 = (Plantti) temp_paikassaOlevatPlantit.alkio(j);
                            //tarkastetaan ettemme vertaile planttia itsensa kanssa, ja etta kyseessa on eri muotoiset plantit.
                            //koska saman muotoiset plantit kumoavat toistensa lisaantymisoikeuden suoraan
                            if (plantti1.getIndeksi() != plantti2.getIndeksi() && plantti1.isMuoto() != plantti2.isMuoto()){
                                //vertaillaan plantteja keskenaan
                                //mikali plantti1 on isompi tai samankokoinen on se oikeutettu lisaantymaan
                                if (plantti1.compareTo(plantti2) >= 0){
                                    isoin = plantti1;
                                }
                                //mikali plantti2 on isompi on se oikeutettu lisaantymaan
                                else{
                                    isoin = plantti2;
                                }
                            }
                        }
                        //Mikali isoin plantti on loytynyt ja sen koko todella on ryhman suurin voi se koittaa lisaantya
                        if (isoin != null && isoin.getKoko()==suurin){
                            //tarkastetaan viela, onko samassa pisteessa samanmuotoisia plantteja, jolloin lisaantyminen ei ole mahdollista
                            if(isoin.isMuoto() == false && falseja == 1){
                                lisaantyneetLotkot.lisaaLoppuun(isoin.lisaanny(lisaantyneetLotkot.koko()));}
                            else if (isoin.isMuoto() == true && trueja == 1){
                                lisaantyneetLotkot.lisaaLoppuun(isoin.lisaanny(lisaantyneetLotkot.koko()));}
                        }
                    }
                    //jos paikassa on vain yksi plantti, se saa lisaantya suoraan
                    else if (temp_paikassaOlevatPlantit.koko() == 1){
                        lisaantyneetLotkot.lisaaLoppuun(plantti1.lisaanny(lisaantyneetLotkot.koko()));
                    }
                }
            }
        }
        //vaihdetaan lopuksi lotkoListan sisalto lisaantyneisiin lotkoihin.
        lotkoLista = listoitus(lotkoLista, lisaantyneetLotkot); 
    }

    /**
    Metodissa haetaan Siemenluku, max x ja max Y koordinaatit.
    @return String muotoisen tiedon alkutiedoista
     */
    public String haeAlkutiedot(){
        String alkutiedot = StringUtils.kentitaAlkutiedot(String.valueOf(this.siemenLuku));
        alkutiedot += StringUtils.kentitaAlkutiedot(String.valueOf(this.maxX));
        alkutiedot += StringUtils.kentitaAlkutiedot(String.valueOf(this.maxY));
        return alkutiedot;
    }

    /**
    Tallennusta varten oma alkutietojen luominen.
    @return Alkutietojen tekstiesitys
     */
    public String tallennaAlkutiedot(){
        String alkutiedot = StringUtils.kentita(String.valueOf(this.siemenLuku));
        alkutiedot += StringUtils.kentita(String.valueOf(this.maxX));
        alkutiedot += StringUtils.kentita(String.valueOf(this.maxY));
        return alkutiedot;
    }

    /**
    Viitevirheiden valttamiseksi tehty metodi, joka vaihtaa ensimmaisena annetun listan sisallon toisen listan sisalloksi
    @param lista1 on lista johon tallennetaan
    @param lista2 on lista jonka tiedot tallennetaan
    @return palauttaa uudelleentaytetyn listan
     */
    public OmaLista listoitus(OmaLista lista1, OmaLista lista2){
        for (int j = 0; j < lista2.koko(); j++){
            lista1.poistaAlusta();
        }
        for (int i = 0; i < lista2.koko(); i++){
            lista1.lisaaLoppuun(lista2.alkio(i));
        }
        return lista1;
    }

    /**
    Metodi kay lapi Listan jossa on kenttamuotoisia olioita. 
    Kenttaoliolla on atribuutteina lisaantyneiden lotkojen koordinaatteja.
    @param lista tarkastettavista kentista.
    @param tarkastettava x koordinaatti.
    @param tarkastettava y koordinaatti.
    @return palauttaa boolean tiedon siita, oliko listalla oliota, jonka x ja y vastasivat parametreina saatuja.
     */
    public boolean onkoLisaannytty(OmaLista koordinaatit, int x, int y){
        boolean palautus = false;
        //kaydaan lapi kaikki Kentta-oliot ja katsotaan vastaako niiden x ja y koordinaatti parametreja
        for (int i = 0; i < koordinaatit.koko(); i++){
            Kentta kentta = (Kentta) koordinaatit.alkio(i);
            if (kentta.getX() == x && kentta.getY() == y){
                palautus=true;
            }
        }
        return palautus;
    }

    /**
    Metodi keraa parametrinaan saadusta listasta listan Lotkoja, joilla on paramereina saadut koordinaatit
    @param Lista tutkittavista Lotkoista
    @param x koordinaatti
    @param y koordinaatti
    @return palauttaa listan jossa on koottuna halutun koordinaatin Lotkoista.
     */
    public OmaLista koordinaatissaOlevat(OmaLista lista, int xkoord, int ykoord){
        OmaLista temp = new OmaLista();
        for (int i = 0; i < lista.koko(); i++){
            Lotko lotko = (Lotko) lista.alkio(i);
            //Mikali lotkon x ja y vastaavat, lisataan listaan.
            if (lotko.getxKoordinaatti() == xkoord && lotko.getyKoordinaatti() == ykoord){
                temp.lisaaLoppuun(lotko);
            }
        }
        return temp;
    }

    /**
    Metodi laskee saamansa planttiListan planteista halutun muotoiset alkiot ja palauttaa niiden maaran.
    @param Lista planteista
    @param halutun plantin muoto
    @return palauttaa listasta loytyneiden haluttujen muotojen lukumaaran.    
     */
    public int montako(OmaLista lista, boolean arvo){
        int palautus = 0;
        for (int i = 0; i < lista.koko(); i++){
            Plantti plantti = (Plantti) lista.alkio(i);
            if (plantti.isMuoto() == arvo){
                palautus++;
            }
        }
        return palautus;
    }

    /**
    Metodi kay lapi saamansa listan ja etsii isoimman alkion.
    @param Lista tutkittavista lotkoista.
    @return palauttaa isoimman lotkon koon
     */    
    public int palautaSuurin(OmaLista lista){
        Plantti isoin = (Plantti) lista.alkio(0);
        for (int i = 1; i < lista.koko(); i++){
            // verrataan plantteja vuorotellen toisiinsa ja pidetaan isoin loydetty muistissa
            Plantti vertailtava = (Plantti) lista.alkio(i);
            if (vertailtava.compareTo(isoin) == 1)
                isoin = vertailtava; 
        }
        //palautetaan isoimman alkion koko
        return isoin.getKoko();
    }
}