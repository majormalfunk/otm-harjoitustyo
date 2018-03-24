package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void metodiSaldoToimiiOikein() {
        assertEquals(1000, kortti.saldo());
    } 
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(2000);
        // kortilla saldoa nyt 3000
        assertEquals("saldo: 30.0", kortti.toString());
    }
    
    @Test
    public void eiVoiMaksaaNegatiivistaSummaa() {
        assertFalse(kortti.otaRahaa(-1000));
    }
    @Test
    public void saldoEiMuutuJosRahaEiRiita() {
        kortti.otaRahaa(1500);
        // kortilla saldoa nyt 1000
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void palauttaaTrueJosRahatRiittivat() {
        assertTrue(kortti.otaRahaa(800));
    }
    
    @Test
    public void palauttaaFalseJosRahatEivätRiittaneet() {
        assertFalse(kortti.otaRahaa(1500));
    }
    
}
