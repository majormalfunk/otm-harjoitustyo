# Arkkitehtuurikuvaus

## Rakenne

Ohjelmassa on kolmeosainen pakkausrakenne:

* *mizzilekommand.logics*,
* *mizzilekommand.layout* ja
* *mizzilekommand.nodes*.

Osassa *.logics* on pelin käynnistävä *MizzileKommand*-luokka, luokat, jotka hoitavat sovelluslogiikan sekä *SceneController*-luokka, joka toimii välittäjänä sovelluslogiikan ja käyttöliittymän välillä. Osassa *layout* on mm. käyttöliittymän toteuttavat [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html "Javadoc javafx.scene:stä")-olioista periytyvät näkymät. Osassa *.nodes* on pelioliot (kaupungit, tukikohdat, ohjukset ja räjähdykset), jotka periytyvät [Node](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html "Javadoc javafx.scene.node:sta")-olioista.

## Käyttöliittymä

Käyttöliittymä sisältää viisi näkymää:

* aloitusnäkymä [StartScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/StartScene.java "StartScene-luokka")
* pelinäkymä [GamePlayScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/GamePlayScene.java "GamePlayScene-luokka")
* bonusnäkymä [BonusScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/BonusScene.java "BonusScene-luokka")
* loppunäkymä [EndScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/EndScene.java "EndScene-luokka")
* huipputulosnäkymä [TopScoreScene](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/main/java/mizzilekommand/layout/TopScoreScene.java "TopScoreScene-luokka")

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



