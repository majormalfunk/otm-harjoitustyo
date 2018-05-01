# Arkkitehtuurikuvaus

## Rakenne

Ohjelmassa on kolmeosainen pakkausrakenne:

* *mizzilekommand.logics*,
* *mizzilekommand.layout* ja
* *mizzilekommand.nodes*.

Osassa *.logics* on pelin käynnistävä *MizzileKommand*-luokka, luokat, jotka hoitavat sovelluslogiikan sekä *SceneController*-luokka, joka toimii välittäjänä sovelluslogiikan ja käyttöliittymän välillä. Osassa *layout* on mm. käyttöliittymän toteuttavat [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html "Javadoc javafx.scene:stä")-olioista periytyvät näkymät. Osassa *.nodes* on pelioliot (kaupungit, tukikohdat, ohjukset ja räjähdykset), jotka periytyvät [Node](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html "Javadoc javafx.scene.node:sta")-olioista.

## Käyttöliittymä

Käyttöliittymä sisältää viisi näkymää:

* *aloitusnäkymä* [StartScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/StartScene.java "StartScene-luokka")
* *pelinäkymä* [GamePlayScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/GamePlayScene.java "GamePlayScene-luokka")
* *bonusnäkymä* [BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka")
* *loppunäkymä* [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka")
* *huipputulosnäkymä* [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka")

Näkymistä on käytössä vain yksi kerrallaan sovelluksen [stageen](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html "Javadoc javafx.stage:sta") sijoitettuna. Näytettävä näkymä asetetaan pelitilanteen perusteella [SceneSelector](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/SceneSelector.java "SceneSelector-luokka")-oliossa.

Pelin käynnistyessä näytetään aloitusnäkymä (*StartScene*). Kun pelaaja aloittaa pelin, vaihdetaan näkymäksi pelinäkymä (*GamePlayScene*). Mikäli pelaaja läpäisee pelatessaan kulloisenkin pelitason, näytetään bonusnäkymä (*BonusScene*) jossa lasketaan bonuspisteet tasosta suoritumisen perusteella. Bonusnäkymästä siirrytään takaisin pelinäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Mikäli pelaaja ei läpäise pelitasoa näytetään joko loppunäkymä (*EndScene*) tai huipputulosnäkymä (*TopScoreScene*), jos pelaaja on tehnyt huipputuloksen. Loppunäkymästä siirrytään takaisin aloitusnäkymään (TBD: aikaviiveen jälkeen tai pelaajan painaessa nappia). Huipputulosnäkymästä siirrytään takaisin aloitusnäkymään, kun pelaaja on tallentanut nimimerkkinsä tuloksensa yhteyteen.

Aloitusnäkymässä on nappi, jota klikkaamalla pääsee aloittamaan pelin.

Pelinäkymässä ohjataan tähtäintä hiirellä ja ammutaan ohjus jostain kolmesta tukikohdasta painamalla näppäimistöltä kyseistä tukikohtaa (vasen, keski, oikea) vastaavaa näppäintä (1/&larr;, 2/&uarr;/&darr;, 3/&rarr;). Ammuttu ohjus räjähtää osoitetussa kohteessa. Vihollisen ohjus tuhoutuu, jos se lentää räjähdyskuvioon ennen kuin räjähdys on haihtunut. Ammutulla ohjuksella ei voi osua vihollisen ohjukseen. Omat tukikohdat ja kaupungit tuhoutuvat, jos vihollisen ohjus räjähtää niin lähellä tukikohtaa tai kaupunkia, että räjähdyskuvio ulottuu niiden päälle ennen haihtumistaan. Pelin säännöt on kuvattu kokonaisuudessaan käyttöohjeessa.

## Sovelluslogiikka


### Luokkakaavio

![Luokkakaavio](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/luokkakaavio.png)

### Luokka/pakkauskaavio tärkeimmistä luokista

![Luokka/pakkauskaavio](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/LuokkaPakkauskaavio.png)

## Tietojen pysyväistallennus



### Tiedostot



## Päätoiminnallisuudet

### Pelin käynnistyminen sekvenssikaaviona

Peli käynnistyy alla olevan sekvenssikaavion mukaisesti:

![Sekvenssikaavio pelin käynnistymisestä](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/SekvenssikaavioKaynnistymisesta.png)

## Ohjelman rakenteeseen jääneet heikkoudet



