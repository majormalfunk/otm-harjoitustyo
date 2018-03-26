# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on mukaelma Atarin vuonna 1980 kehittämästä Missile Command -pelistä [https://en.wikipedia.org/wiki/Missile_Command](https://en.wikipedia.org/wiki/Missile_Command). Pelissä pelaaja puolustaa kuutta kaupunkia kolmella ohjustukikohdalla lähestyviä ballistisia ohjuksia vastaan. Tarkoituksena on pystyä pelaamaan mahdollisimman pitkään. Peli päättyy, kun kaikki puolustettavat kaupungit ovat tuhoutuneet

## Käyttäjät

Sovellus ei vaadi kirjautumista ja siinä on vain yksi käyttäjärooli eli pelaaja.

## Käyttöliittymäluonnos

Sovellus koostuu viidestä eri näkymästä

![Käyttöliittymäluonnos](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/kayttoliittymaluonnos.png)

Sovellus aukeaa aloitusnäkymään, josta pääsee siirtymään pelinäkymään PLAY-nappia painamalla. Aloitusnäkymässä näytetään parhaat pelitulokset.
Pelinäkymässä käyttäjä pelaa tasoja. Pelinäkymässä näytetään kertyneet pisteet ja meneillään olevan tason numero. Tason päättyessä mikäli koko peli ei päättynyt näytetään bonuspistenäkymä. Mikäli peli päättyy kokonaan näytetään joko loppunäkymä tai huipputulosnäkymä.
Bonuspistenäkymässä pelaajalle näytetään kuinka paljon hän sai bonuspisteitä. Pienen aikaviiveen jälkeen siirrytään takaisin pelinäkymään. 
Pelin päättyessä, jos pelaaja ei tehnyt huipputulosta siirrytään loppunäkymään, missä näytetään teksti pelin päättymisestä. Pienen aikaviiveen jälkeen siirrytään takaisin aloitusnäkymään.
Jos peli päättyi huipputulokseen siirrytään huipputulosnäkymään, missä pelaaja pääsee lisäämään nimikirjaimensa huipputuloslistalle. OK-nappia painamalla nimikirjaimet tallentuvat ja palataan aloitusnäkymään.

## Perusversion tarjoama toiminnallisuus

