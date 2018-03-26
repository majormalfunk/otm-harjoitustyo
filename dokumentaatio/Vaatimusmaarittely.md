# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on mukaelma Atarin vuonna 1980 kehittämästä Missile Command -pelistä [https://en.wikipedia.org/wiki/Missile_Command](https://en.wikipedia.org/wiki/Missile_Command). Pelissä pelaaja puolustaa kuutta kaupunkia ampumalla torjuntaohjuksia kolmesta eri ohjustukikohdasta yrittäen tuhota lähestyvät ballistiset ohjukset. Tarkoituksena on pystyä pelaamaan mahdollisimman pitkään. Peli päättyy, kun kaikki puolustettavat kaupungit ovat tuhoutuneet

## Käyttäjät

Sovellus ei vaadi kirjautumista ja siinä on vain yksi käyttäjärooli eli pelaaja.

## Käyttöliittymäluonnos

Sovellus koostuu viidestä eri näkymästä

![Käyttöliittymäluonnos](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/kayttoliittymaluonnos.png)

1. Aloitusnäkymä
   Sovellus aukeaa aloitusnäkymään, josta pääsee siirtymään pelinäkymään PLAY-nappia painamalla. Aloitusnäkymässä näytetään parhaat pelitulokset.  
2. Pelinäkymä
   Pelinäkymässä käyttäjä pelaa tasoja. Pelinäkymässä näytetään kertyneet pisteet ja meneillään olevan tason numero. Tason päättyessä mikäli koko peli ei päättynyt näytetään bonuspistenäkymä. Mikäli peli päättyy kokonaan näytetään joko loppunäkymä tai huipputulosnäkymä.  
3. Bonuspistenäkymä
   Bonuspistenäkymässä pelaajalle näytetään kuinka paljon hän sai bonuspisteitä. Pienen aikaviiveen jälkeen siirrytään takaisin pelinäkymään. 
4. Loppunäkymä
   Pelin päättyessä, jos pelaaja ei tehnyt huipputulosta siirrytään loppunäkymään, missä näytetään teksti pelin päättymisestä. Pienen aikaviiveen jälkeen siirrytään takaisin aloitusnäkymään.  
5. Huipputulosnäkymä
   Jos peli päättyi huipputulokseen siirrytään huipputulosnäkymään, missä pelaaja pääsee lisäämään nimikirjaimensa huipputuloslistalle. OK-nappia painamalla nimikirjaimet tallentuvat ja palataan aloitusnäkymään.  

## Perusversion tarjoama toiminnallisuus

Aloitusnäkymässä näytetään huipputulosten lista. Huipputulokset tallennetaan tiedostoon, josta ne luetaan aina, kun näytetään aloitusnäkymä. Aloitusnäkymästä pääsee käynnistämään uuden pelin PLAY-nappia painamalla.

Pelissä käytetään hiirtä ja näppäimistöä. Hiirellä ohjataan osoitinta, joka osoittaa kohdan taivaalla, mihin torjuntaohjus ammutaan. Näppäimistön kolmella eri näppäimellä (esim vasen, ylös, oikea -näppäimet) ammutaan ohjus jostain kolmesta tukikohdasta. Lähestyvät ohjukset tuhoutuvat jos ne lentävät torjuntaohjuksen räjähdykseen. Kaikissa tukikohdissa on vain rajallinen määrä ohjuksia tasoa kohti. Tukikohdan kohdalla näytetään jäljellä olevien ohjusten määrä. Mikäli kaikki ohjukset loppuvat tai ohjustukikohdat tuhoutuvat, pelaaja ei voi tehdä muuta kuin odottaa tason loppumista.

Taso loppuu, kun pelaaja saa tuhottua kaikki tason lähestyneet ohjukset tai kun kuluneen tason aikana kolme kaupunkia on tuhoutunut. Koko peli päättyy mikäli kaikki kaupungit ovat tuhoutuneet. Mikäli kaupunkeja on jäljellä näytetään bonuspistenäkymä, jossa pelaaja saa bonuspisteitä jäljellä olevista kaupungeista ja ohjuksista. Bonuspistenäkymän yhteydessä torjuntaohjusvarastot täydennetään. Tuhoutuneet kaupungit ja ohjustukikohdat rakennetaan uudelleen ylitettäessä tiettyjä tasapistemääriä (tbd).

Kun koko peli päättyy, näytetään joko lopputeksti tai huippupistenäkymä riippuen siitä tekikö pelaaja huippupisteet. Huippupistenäkymässä pelaaja saa tallentaa nimikirjaimensa parhaiden tulosten listalle. Parhaiden tulosten lista tallennetaan tiedostoon.

Loppunäkymästä ja huippupistenäkymästä palataan takaisin aloitusnäkymään, josta pääsee käynnistämään uuden pelin.
