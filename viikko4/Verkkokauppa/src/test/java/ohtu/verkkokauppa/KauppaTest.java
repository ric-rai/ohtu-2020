package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    //Ostoskori ostoskori;
    Kauppa kauppa;

    @Before
    public void setUp(){
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        //ostoskori = mock(Ostoskori.class);
        kauppa = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void test1() {
        kauppa.aloitaAsiointi();
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), anyInt(), eq("numero"), anyString(), eq(5));
    }

    @Test
    public void test2() {
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "piimä", 6));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), anyInt(), eq("numero"), anyString(), eq(11));
    }

    @Test
    public void test3() {
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), anyInt(), eq("numero"), anyString(), eq(10));
    }

    @Test
    public void test4() {
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), anyInt(), eq("numero"), anyString(), eq(5));
    }

    @Test
    public void test5() {
        when(viite.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), anyInt(), eq("numero"), anyString(), eq(5));
    }

    @Test
    public void test6() {
        when(viite.uusi()).thenReturn(1);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), eq(1), eq("numero"), anyString(), eq(5));

        when(viite.uusi()).thenReturn(2);

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("nimi", "numero");
        verify(pankki).tilisiirto(eq("nimi"), eq(2), eq("numero"), anyString(), eq(5));
    }

    @Test
    public void test7() {
        when(viite.uusi()).thenReturn(1);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);

        verify(varasto, times(1)).palautaVarastoon(eq(new Tuote(1, "maito", 5)));
    }

}
