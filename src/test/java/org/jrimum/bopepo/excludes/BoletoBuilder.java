/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 08/09/2013 - 00:05:33
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 08/09/2013 - 00:05:33
 * 
 */

package org.jrimum.bopepo.excludes;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.Boleto;

/**
 * Criação de dados para testes.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoBuilder {

	private Boleto boleto;

	public BoletoBuilder() {
		this.boleto = newDefaultValue();
	}

	public static Boleto defaultValue() {
		return newDefaultValue();
	}

	public static Boleto defaultValueSacadorAvalista() {
		return newDefaultValueSacadorAvalista();
	}

	public Boleto build() {
		return this.boleto;
	}

	private static Boleto newDefaultValue() {
		Boleto boleto = new Boleto(TituloBuilder.defaultValue());
		setDefaultValues(boleto);
		return boleto;
	}

	private static Boleto newDefaultValueSacadorAvalista() {
		Boleto boleto = new Boleto(TituloBuilder.defaultValueSacadorAvalista());
		setDefaultValues(boleto);
		return boleto;
	}

	private static void setDefaultValues(Boleto boleto) {
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado(
				"Senhor sacado, sabemos sim que o valor cobrado não é o esperado, aproveite o DESCONTÃO!");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.addInstrucaoAoCaixa("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
		boleto.addInstrucaoAoCaixa("APÓS o Vencimento, Pagável Somente na Rede X.");
		boleto.setDataDeProcessamento(new GregorianCalendar(2020, Calendar.JANUARY, 14).getTime());
	}

}
