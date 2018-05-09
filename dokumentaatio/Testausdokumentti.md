## Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- että integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

### Yksikkö ja integraatiotestaus

#### Sovelluslogiikka

Automatisoiduissa testeissä on testattu pakkausten *mizzilekommand.logics* ja *mizzilekommand.nodes* toimintaa automatisoiduilla testeillä [ActionSelectorTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/ActionSelectorTest.java), [GameLoopTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/GameLoopTest.java) ja [GameStatusTest.java](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/MizzileKommand/src/test/java/mizzilekommand/tests/GameStatusTest.java). Testeillä on testattu sekä yksittäisiä ominaisuuksia että integroidusti esimerkiksi peliolioiden elinkaarta pelitapahtuman aikana.

Testeissä on käytetty pysyväistallennuksen DAO-rajapinnan testaamiseen [FakeDao]() keskusmuistitoteutusta.

#### Testauskattavuus

Käyttöliittymätestausta lukuunottamatta sovelluksen testauksen rivikattavuus on  % ja haaraumakattavuus  %. Testauskattavuutta rajoittaa graafisen käyttöliittymän käyttö.

