## Käyttöohje

Lataa tiedosto MizzileKommand.jar (julkaisuversio, kun se valmistuu)

Siihen asti ladattavissa oleva uusin pre-release versio:

* [MizzileKommand-0.6.jar](https://github.com/majormalfunk/otm-harjoitustyo/releases)

### Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

```
java -jar MizzileKommand-0.6.jar
```

### Kirjautuminen

Ohjelma ei edellytä kirjautumista eikä siinä luoda käyttäjiä

### Pelaaminen

Pelissä on tarkoitus puolustaa kaupunkeja lähestyviltä vihollisohjuksilta. Puolustaminen tapahtuu ampumalla vihollisen ohjusten kulkureitille ohjuksia, jotka räjähtäessään tuhoavat vihollisohjukset. Peliä pelataan taso kerrallaan. Jokaisen tason jälkeen peli vaikeutuu: vihollisohjuksia tulee enemmän, useammin ja ne lentävät nopeammin.

#### Aloitusnäkymä
Peli alkaa aloitusnäkymästä. Aloitusnäkymässä on nappi (PLAY), jota klikkaamalla pääsee aloittamaan pelin, sekä ohjeet tähtäämiseen ja ampumiseen. Lisäksi aloitusnäkymässä näytetään siihen asti saavutetut huipputulokset (*Tätä ei ole vielä toteutettu*)

#### Pelinäkymä
Aloitusnäkymästä siirrytään pelinäkymään, kun PLAY-nappia on painettu. Pelinäkymässä on alalaidassa kuusi kaupunkia, joita pelaajan on tarkoitus puolustaa sekä kolme ohjustukikohtaa, joista voi ampua ohjuksia torjumaan näytön ylälaidasta lähestyviä vihollisen ohjuksia. Pelinäkymässä ohjataan tähtäintä hiirellä ja ammutaan ohjus jostakin näytön alalaidan kolmesta tukikohdasta painamalla näppäimistöltä kyseistä tukikohtaa (vasen, keski, oikea) vastaavaa näppäintä (1/&larr;, 2/&uarr;/&darr;, 3/&rarr;).

Ammuttu ohjus räjähtää hiirellä ohjattavalla tähtäimellä osoitetussa kohteessa. Vihollisen ohjus tuhoutuu, jos se lentää räjähdyskuvioon ennen kuin räjähdys on haihtunut. Ammutulla ohjuksella ei voi osua vihollisen ohjukseen.

Vihollisen ohjukset lähestyvät näytön ylälaidasta alalaidassa olevia puolustuskohteita eli pelaajan kaupunkeja ja tukikohtia. Vihollisen ohjusten lentorata on suora ja se voi alkaa ja päättyä vaakasuunnassa mihin kohtaan tahansa pelialuella lukuunottamatta aivan äärimmäisiä laitoja. Vihollisen ohjukset räjähtävät, kun ne saavuttavat korkeuden, jolla räjähtäessään ne tuhoavat pelaajan tukikohdan tai kaupungin, jos se on niin lähellä, että ohjuksen räjähdyskuvio ulottuu jompaan kumpaan tai molempiin. Pelaajan tukikohdat ja kaupungit tuhoutuvat, jos vihollisen ohjus räjähtää niin lähellä tukikohtaa tai kaupunkia, että räjähdyskuvio ulottuu niiden päälle ennen haihtumistaan.

#### Bonusnäkymä
Mikäli pelaaja läpäisee pelatessaan kulloisenkin pelitason, näytetään bonusnäkymä [BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka") jossa lasketaan bonuspisteet tasosta suoritumisen perusteella. Bonusnäkymästä siirrytään takaisin pelinäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Mikäli pelaaja ei läpäise pelitasoa näytetään joko loppunäkymä [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka") tai huipputulosnäkymä [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka"), jos pelaaja on tehnyt huipputuloksen. Loppunäkymästä siirrytään takaisin aloitusnäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Huipputulosnäkymästä siirrytään takaisin aloitusnäkymään, kun pelaaja on tallentanut nimimerkkinsä tuloksensa yhteyteen.

