/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakkovilenius
 */
public class KassapaateTest {

    Kassapaate kassapaate;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
    
    @Test
    public void alussaRahatOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void alussaEiMyytyEdullisiaLounaita() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void alussaEiMyytyMaukkaitaLounaita() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void edullisenOstoKateisellaToimiiOikein() {
        int vaihtorahat = kassapaate.syoEdullisesti(300);
        assertEquals("Kassassa rahaa 100240", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Edullisia lounaita myyty 1", "Edullisia lounaita myyty " + kassapaate.edullisiaLounaitaMyyty());
        assertEquals("Vaihtorahat 60", "Vaihtorahat " + vaihtorahat);
    }
    @Test
    public void edullisenOstoKunRahatEiRiitaToimiiOikein() {
        int vaihtorahat = kassapaate.syoEdullisesti(100);
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Edullisia lounaita myyty 0", "Edullisia lounaita myyty " + kassapaate.edullisiaLounaitaMyyty());
        assertEquals("Vaihtorahat 100", "Vaihtorahat " + vaihtorahat);
    }
    @Test
    public void maukkaanOstoKateisellaToimiiOikein() {
        int vaihtorahat = kassapaate.syoMaukkaasti(500);
        assertEquals("Kassassa rahaa 100400", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Maukkaita lounaita myyty 1", "Maukkaita lounaita myyty " + kassapaate.maukkaitaLounaitaMyyty());
        assertEquals("Vaihtorahat 100", "Vaihtorahat " + vaihtorahat);
    }
    @Test
    public void maukkaanOstoKunRahatEiRiitaToimiiOikein() {
        int vaihtorahat = kassapaate.syoMaukkaasti(100);
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Maukkaita lounaita myyty 0", "Maukkaita lounaita myyty " + kassapaate.maukkaitaLounaitaMyyty());
        assertEquals("Vaihtorahat 100", "Vaihtorahat " + vaihtorahat);
    }
    @Test
    public void edullisenOstoKortillaToimiiOikein() {
        Maksukortti maksukortti = new Maksukortti(1000);
        boolean ostoOnnistui = kassapaate.syoEdullisesti(maksukortti);
        assertTrue(ostoOnnistui);
        assertEquals("Kortilla rahaa 760", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Edullisia lounaita myyty 1", "Edullisia lounaita myyty " + kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void edullisenOstoKortillaKunRahatEiRiitaToimiiOikein() {
        Maksukortti maksukortti = new Maksukortti(100);
        boolean ostoOnnistui = kassapaate.syoEdullisesti(maksukortti);
        assertFalse(ostoOnnistui);
        assertEquals("Kortilla rahaa 100", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Edullisia lounaita myyty 0", "Edullisia lounaita myyty " + kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void maukkaanOstoKortillaToimiiOikein() {
        Maksukortti maksukortti = new Maksukortti(1000);
        boolean ostoOnnistui = kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(ostoOnnistui);
        assertEquals("Kortilla rahaa 600", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Maukkaita lounaita myyty 1", "Maukkaita lounaita myyty " + kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void maukkaanOstoKortillaKunRahatEiRiitaToimiiOikein() {
        Maksukortti maksukortti = new Maksukortti(100);
        boolean ostoOnnistui = kassapaate.syoMaukkaasti(maksukortti);
        assertFalse(ostoOnnistui);
        assertEquals("Kortilla rahaa 100", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
        assertEquals("Maukkaita lounaita myyty 0", "Maukkaita lounaita myyty " + kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kortinSaldonLataaminenToimiiOikein() {
        Maksukortti maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, 10000);
        assertEquals("Kortilla rahaa 10000", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 110000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
    }
    @Test
    public void kortilleEiVoiLadataNegatiivistaSaldoa() {
        Maksukortti maksukortti = new Maksukortti(1000);
        kassapaate.lataaRahaaKortille(maksukortti, -500);
        assertEquals("Kortilla rahaa 1000", "Kortilla rahaa " + maksukortti.saldo());
        assertEquals("Kassassa rahaa 100000", "Kassassa rahaa " + kassapaate.kassassaRahaa());
    }

}
