## Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- että integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

### Yksikkö ja integraatiotestaus

#### Sovelluslogiikka

Automatisoiduissa testeissä on testattu pakkausten *mizzilekommand.logics* ja *mizzilekommand.nodes* toimintaa automatisoiduilla testeillä [ActionSelectorTest.java](), [GameLoopTest.java]() ja [GameStatusTEst.java](). Testeillä on testattu sekä yksittäisiä ominaisuuksia että integroidusti esimerkiksi peliolioiden elinkaarta pelitapahtuman aikana.

Testeissä on käytetty pysyväistallennuksen DAO-rajapinnan testaamiseen [FakeDao]() keskusmuistitoteutusta.

#### Testauskattavuus

Käyttöliittymätestausta lukuunottamatta sovelluksen testauksen rivikattavuus on  % ja haaraumakattavuus  %. Testauskattavuutta rajoittaa graafisen käyttöliittymän käyttö.

