package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.parametro.ParametroBancoSofisa;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeMoeda;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

public class BancoSofisaBoletoTest {

	private Boleto boleto;

	@Before
	public void setUp() throws Exception {

		final Sacado sacado = new Sacado("Sacado");
		final Cedente cedente = new Cedente("Cedente");

		final ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_SOFISA.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1, "0"));

		final Agencia agencia = new Agencia(1, "9");
		contaBancaria.setAgencia(agencia);

		contaBancaria.setCarteira(new Carteira(112, TipoDeCobranca.COM_REGISTRO));

		final Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("0008026642");
		titulo.setDigitoDoNossoNumero("4");
		titulo.setTipoDeMoeda(TipoDeMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(1000.00));
		titulo.setDataDoVencimento(new GregorianCalendar(2002, Calendar.MARCH, 25).getTime());
		titulo.setParametrosBancarios(new ParametrosBancariosMap(ParametroBancoSofisa.CODIGO_DO_CONVENIO, 120));

		boleto = new Boleto(titulo);
	}

	@Test
	public void testGetLinhaDigitavel() {
		assertNotNull(boleto.getLinhaDigitavel());
		assertEquals("63790.00117 12000.012000 00802.664243 9 16300000100000", boleto.getLinhaDigitavel().write());
	}

}
