# OTM-harjoitustyö

## Harjoitustyö

Harjoitustyön aiheena on Atarin Missile Command -pelin mukaelma.

### Dokumentaatio

[Käyttöohje](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Kayttoohje.md)  

[Vaatimusmäärittely](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md)  

[Arkkitehtuurikuvaus](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Arkkitehtuurikuvaus.md)  

[Testausdokumentti](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Testausdokumentti.md)

[Tuntikirjanpito](https://github.com/majormalfunk/otm-harjoitustyo/blob/master/dokumentaatio/Tuntikirjanpito.md)

### Komentorivitoiminnot

+ Testit suoritetaan komennolla
```
mvn test
```

+ Testikattavuusraportti luodaan komennolla
```
mvn jacoco:report
```
   Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston *target/site/jacoco/index.html*  


+ Koodin tyylitarkastusraportti luodaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
   Tyylitarkastusraporttia voi tarkastella avaamalla selaimella tiedoston *target/site/checkstyle.html*  


+ Javadoc generoidaan komennolla
```
mvn javadoc:javadoc
```
   Javadocsia voi tarkastella avaamalla selaimella tiedoston *target/site/apidocs/index.html*  


+ Suoritettava jar luodaan komennolla
```
mvn package
```
   Komento generoihakemistoon *target* suoritettavan jar-tiedoston *MizzileKommand-1.0.jar*  

---------------------

### Release-versiot

[Release-versiot](https://github.com/majormalfunk/otm-harjoitustyo/releases)
