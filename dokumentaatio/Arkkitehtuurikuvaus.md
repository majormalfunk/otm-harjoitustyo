# Arkkitehtuurikuvaus

## Rakenne

Ohjelmassa on neliosainen pakkausrakenne:

* *mizzilekommand.dao*,
* *mizzilekommand.logics*,
* *mizzilekommand.layout* ja
* *mizzilekommand.nodes*.

Osassa *.logics* on pelin käynnistävä [MizzileKommand](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/MizzileKommand.java "MizzileKommand-luokka")-luokka, sovelluslogiikkaa hoitavat luokat sekä [SceneController](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/SceneController.java "SceneController-luokka")-luokka, joka toimii välittäjänä sovelluslogiikan ja käyttöliittymän välillä. Osassa *layout* on mm. käyttöliittymän toteuttavat [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html "Javadoc javafx.scene:stä")-olioista periytyvät näkymät. Osassa *.nodes* on pelioliot (kaupungit, tukikohdat, ohjukset ja räjähdykset), jotka periytyvät [Node](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html "Javadoc javafx.scene.node:sta")-olioista. Osassa *.dao* on tiedostojen lataamiseen sekä huipputulosten tallentamiseen tarkoitetut luokat.

## Käyttöliittymä

Käyttöliittymä sisältää viisi näkymää:

* *aloitusnäkymä* [StartScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/StartScene.java "StartScene-luokka")
* *pelinäkymä* [GamePlayScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/GamePlayScene.java "GamePlayScene-luokka")
* *bonusnäkymä* [BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka")
* *loppunäkymä* [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka")
* *huipputulosnäkymä* [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka")

Näkymistä on käytössä vain yksi kerrallaan sovelluksen [stageen](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html "Javadoc javafx.stage:sta") sijoitettuna. Näytettävä näkymä asetetaan pelitilanteen perusteella [SceneSelector](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/SceneSelector.java "SceneSelector-luokka")-oliossa.

Pelin käynnistyessä näytetään aloitusnäkymä [StartScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/StartScene.java "StartScene-luokka"). Kun pelaaja aloittaa pelin, vaihdetaan näkymäksi pelinäkymä [GamePlayScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/GamePlayScene.java "GamePlayScene-luokka"). Mikäli pelaaja läpäisee pelatessaan kulloisenkin pelitason, näytetään bonusnäkymä [BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka") jossa lasketaan bonuspisteet tasosta suoritumisen perusteella. Bonusnäkymästä siirrytään takaisin pelinäkymään pelaajan painaessa nappia. Mikäli pelaaja ei läpäise pelitasoa näytetään joko loppunäkymä [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka") tai huipputulosnäkymä [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka"), jos pelaaja on tehnyt huipputuloksen. Loppunäkymästä siirrytään takaisin aloitusnäkymään aikaviiveen jälkeen. Huipputulosnäkymästä siirrytään takaisin aloitusnäkymään, kun pelaaja on tallentanut nimimerkkinsä tuloksensa yhteyteen.

Aloitusnäkymässä on nappi, jota klikkaamalla pääsee aloittamaan pelin.

Pelinäkymässä ohjataan tähtäintä hiirellä ja ammutaan ohjus jostain kolmesta tukikohdasta painamalla näppäimistöltä kyseistä tukikohtaa (vasen, keski, oikea) vastaavaa näppäintä (1/&larr;, 2/&uarr;/&darr;, 3/&rarr;). Ammuttu ohjus räjähtää osoitetussa kohteessa. Vihollisen ohjus tuhoutuu, jos se lentää räjähdyskuvioon ennen kuin räjähdys on haihtunut. Ammutulla ohjuksella ei voi osua vihollisen ohjukseen. Omat tukikohdat ja kaupungit tuhoutuvat, jos vihollisen ohjus räjähtää niin lähellä tukikohtaa tai kaupunkia, että räjähdyskuvio ulottuu niiden päälle ennen haihtumistaan. Pelin säännöt on kuvattu tarkemmin käyttöohjeessa.

## Sovelluslogiikka

Sovelluksen tärkeimmät luokat ovat käynnistyksen jälkeen

* [GameLoop](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/GameLoop.java "GameLoop-luokka")
* [GameStatus](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/GameStatus.java "GameStatus-luokka")
* [SceneController](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/SceneController.java "SceneController-luokka")

Näistä luodaan vain yhdet oliot sovelluksen käynnistyessä.

[GameLoop](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/GameLoop.java "GameLoop-luokka")-luokka, jonka startLoop() metodi käynnistää [AnimationTimer](https://docs.oracle.com/javafx/2/api/javafx/animation/AnimationTimer.html "Javadoc javafx.animation.AnimationTimer:sta"):in, kun pelaaja aloittaa *aloitusnäkymä*:ssä pelin. AnimationTimerin sisällä on toeuteutettu metodikutsut jotka huolehtivat peliolioiden toiminnallisuuksista ja elinkaarista. GameLoop-oliolla on viittaus GameStatus-olioon ja SceneController-olioon.

[GameStatus](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/GameStatus.java "GameStatus-luokka") pitää yllä pelitilannetta ja laskee tason vaihtuessa uudet parametriarvot seuraavaa tasoa varten. 

[SceneController](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/logics/SceneController.java "SceneController-luokka")-luokka huolehtii oikean näkymän näyttämisestä saaden tähän komennon GameLoop:lta. SceneController olio luo GameLoop-olion. SceneController-oliolla on viittaus GameLoop-olioon ja aktiiviseen näkymä-olioon. Näkymän vaihtuessa luodaan uusi aktiivinen näkymä-olio. Javan garbage collector huolehtii edeltävän näkymän siivoamisesta pois muistista.

### Luokkakaavio

![Luokkakaavio](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/luokkakaavio.png)

### Luokka/pakkauskaavio tärkeimmistä luokista

![Luokka/pakkauskaavio](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/LuokkaPakkauskaavio.png)

## Tietojen pysyväistallennus

Pysyväistallennusta on käytetään huipputulosten tallentamiseen paikalliselle levylle. Pysyväistallennus tehdään paikalliselle levylle, koska se on sovelluksen kannalta luontevin ratkaisu.

### Tiedostot

Sovellus käyttää 5 tiedostoa. Tiedostot ovat

* *config.properties*, joka kertoo minkä nimisestä tiedostota löytyy huipputulokset tallennettuna
* *highscores.txt*, johon oletusarvoisesti tallennetaan huipputulokset
* *MizzileKommand.wav*, joka on pelin taustaääni
* *Explosion.wav*, joka on pelin räjähdysääni
* *citylights.png*, jota käytetään kaupunkeja kuvaavien grafiikoiden värittämiseen

Sovellus toimii vaikka näitä tiedostoja ei olisikaan. Tiedostot *config.properties* ja *highscores.txt* sovellus luo käynnistyessään, jos niitä ei ole. Äänitiedostojen puuttuminen johtaa siihen, että ääniä ei kuulu. Kuvatiedoston puuttuminen johtaa siihen, että kaupungit näytetään vain mustina hahmoina.

## Päätoiminnallisuudet

### Pelin käynnistyminen sekvenssikaaviona

Peli käynnistyy alla olevan sekvenssikaavion mukaisesti:

![Sekvenssikaavio pelin käynnistymisestä](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/SekvenssikaavioKaynnistymisesta.png)

## Ohjelman rakenteeseen jääneet heikkoudet

* Ohjelman rakennetta voisi miettiä uudelleen SceneControllerin ja GameLoopin välisen suhteen kannalta. Lähinnä siinä mielessä, että mikä voisi olla parempi paikka, tapa ja järjestys näiden olioiden luomiseen. GameLoop on myös rivimäärältään kohtalaisen pitkä luokka. Toisaalta rivimäärään sisältyy paljon kommentointia.

