## Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- että integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

### Yksikkö ja integraatiotestaus

#### Sovelluslogiikka

Automatisoiduissa testeissä on testattu pakkausten *mizzilekommand.logics* ja *mizzilekommand.nodes* toimintaa automatisoiduilla testeillä [ActionSelectorTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/ActionSelectorTest.java), [GameLoopTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/GameLoopTest.java) ja [GameStatusTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/GameStatusTest.java). Testeillä on testattu sekä yksittäisiä ominaisuuksia että integroidusti esimerkiksi peliolioiden elinkaarta pelitapahtuman aikana.

Testeissä on käytetty pysyväistallennuksen DAO-rajapinnan testaamiseen [FakeDao](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/FakeDao.java) keskusmuistitoteutusta.

#### Testauskattavuus

Käyttöliittymätestausta lukuunottamatta sovelluksen testauksen rivikattavuus on 86 % ja haaraumakattavuus 75%. Testauskattavuutta rajoittaa graafisen käyttöliittymän käyttö.

![Testauskattavuus](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Testauskattavuus.png)

### Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

#### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Kayttoohje.md) kuvaamalla tavalla sekä macOS että Windows 10 -ympäristössä.

#### Toiminnallisuudet

Kaikki [vaatimusmäärittelyn](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien kohdalla on yritetty käyttää sovellusta myös käyttöohjeen vastaisesti.

### Sovellukseen jääneet laatuongelmat

Sovellus ei pysty toistamaan laadukkaasti useampaa räjähdysääntä samaan aikaan vaan edellinen katkeaa seuraavan alkaessa tai myöhemmin alkanut ei kuulu ollenkaan.


