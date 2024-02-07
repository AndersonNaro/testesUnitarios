package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setup() {
        calculadora = new Calculadora();
    }

    @Test
    public void somaDoisValores() {
        //cenario

        int a = 5;
        int b = 9;
        //ação
        int resultado = calculadora.soma(a, b);
        //verificação
        assertEquals(14, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        //cenario

        int a = 5;
        int b = 9;
        //ação
        int resultado = calculadora.subtrair(a, b);
        //Verificação
        assertEquals(-4, resultado);

    }

    @Test(expected = DivisaoPorZeroException.class)
    public void deveLançarExcessãoDividirPorZero() throws DivisaoPorZeroException {
        //cenario

        int a = 6;
        int b = 0;
        //ação
        int resultado = calculadora.dividir(a, b);
        //Verificação
    }

    @Test
    public void deveDividirDoisValores() throws DivisaoPorZeroException {
        //cenario

        int a = 9;
        int b = 3;
        //ação
        int resultado = calculadora.dividir(a, b);
        //Verificação
        assertEquals(3, resultado);

    }
}
