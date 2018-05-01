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
Aloitusnäkymästä siirrytään pelinäkymään, kun PLAY-nappia on painettu. Pelinäkymässä on alalaidassa kuusi kaupunkia, joita pelaajan on tarkoitus puolustaa sekä kolme ohjustukikohtaa, joista voi ampua ohjuksia torjumaan näytön ylälaidasta lähestyviä vihollisen ohjuksia. 
Pelinäkymässä ohjataan tähtäintä hiirellä ja ammutaan ohjus jostakin näytön alalaidan kolmesta tukikohdasta painamalla näppäimistöltä kyseistä tukikohtaa (vasen, keski, oikea) vastaavaa näppäintä (1/&larr;, 2/&uarr;/&darr;, 3/&rarr;).

Ammuttu ohjus räjähtää hiirellä ohjattavalla tähtäimellä osoitetussa kohteessa. Vihollisen ohjus tuhoutuu, jos se lentää räjähdyskuvioon ennen kuin räjähdys on haihtunut. Tuhotusta vihollisohjuksesta saa 5 pistettä. Ammutulla ohjuksella ei voi osua vihollisen ohjukseen. Pelaajalla on käytettävissään tason aikana 10 ohjusta kussakin tukikohdassa.

Vihollisen ohjukset lähestyvät näytön ylälaidasta alalaidassa olevia puolustuskohteita eli pelaajan kaupunkeja ja tukikohtia. Vihollisen ohjusten lentorata on suora ja se voi alkaa ja päättyä vaakasuunnassa missä tahansa kohdassa pelialuella lukuunottamatta aivan äärimmäisiä laitoja. Vihollisen ohjukset räjähtävät, kun ne saavuttavat korkeuden, jolla räjähtäessään ne tuhoavat pelaajan tukikohdan tai kaupungin. Pelaajan tukikohdat ja kaupungit tuhoutuvat, jos vihollisen ohjus räjähtää niin lähellä tukikohtaa tai kaupunkia, että räjähdyskuvio ulottuu niiden päälle ennen haihtumistaan.

Pelinäkymän ylälaidassa näytetään pelaajan pisteet (SCORE), käynnissä oleva taso (LEVEL) sekä tasossa vielä tulevien vihollisohjusten lukumäärä (INCOMING). Alalaidassa näytetään kunkin tukikohdan alla siinä tukikohdassa jäljellä olevat ohjukset.

Taso päättyy, kun jäljellä ei ole enää vihollisohjuksia tai kun tasossa on tuhoutunut 3 kaupunkia tai kun kaikki kaupungit ovat tuhoutuneet. Kaikkien kaupunkien tuhoutuminen päättää myös koko pelin. Tason aikana voi tuhoutua korkeintaan 3 kaupunkia. Matkalla olevat vihollisohjukset eivät enää tuhoa kaupunkeja kuluvassa tasossa, mikäli 3 kaupunkia on jo tuhoutunut tason aikana. Myöskään uusia vihollisohjuksia ei enää tule, jos 3 kaupunkia on jo tuhoutunut tason aikana.

#### Bonusnäkymä
Mikäli pelaaja läpäisee pelatessaan kulloisenkin pelitason ilman, että kaikki kaupungit ovat tuhoutuneet, näytetään bonusnäkymä. Bonusnäkymässä pelaajalle lasketaan bonuspisteitä jäljellä olevien kaupunkien ja käyttämättömien ohjusten perusteella.

[BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka") jossa lasketaan bonuspisteet tasosta suoritumisen perusteella. Bonusnäkymästä siirrytään takaisin pelinäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Mikäli pelaaja ei läpäise pelitasoa näytetään joko loppunäkymä [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka") tai huipputulosnäkymä [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka"), jos pelaaja on tehnyt huipputuloksen. Loppunäkymästä siirrytään takaisin aloitusnäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Huipputulosnäkymästä siirrytään takaisin aloitusnäkymään, kun pelaaja on tallentanut nimimerkkinsä tuloksensa yhteyteen.

