/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 16/04/2008 - 23:09:08
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 16/04/2008 - 23:09:08
 * 
 */

package org.jrimum.bopepo.campolivre;

import static org.jrimum.vallia.digitoverificador.Modulo.MOD10;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * 
 * 
 * <p>
 * Campo livre padrão do Banco Itaú
 * </p>
 * 
 * <p>
 * <h2>Layout do Banco Itaú para o campo livre PADRÃO</h2>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" id="campolivre">
 * <thead>
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td >20 a 22</td>
 * <td >3</td>
 * <td >9(03)</td>
 * <td >Carteira</td>
 * </tr>
 * <tr>
 * <td >23 a 30</td>
 * <td >8</td>
 * <td >9(08)</td>
 * <td >Nosso número</td>
 * </tr>
 * <tr>
 * <td >31 a 31</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >DAC [Agência /Conta/Carteira/Nosso Número]</td>
 * </tr>
 * <tr>
 * <td >32 a 35</td>
 * <td >4</td>
 * <td >9(04)</td>
 * <td >N.º da Agência cedente</td>
 * </tr>
 * <tr>
 * <td >36 a 40</td>
 * <td >5</td>
 * <td >9(05)</td>
 * <td >N.º da Conta Corrente</td>
 * </tr>
 * <tr>
 * <td >41 a 41</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >DAC [Agência/Conta Corrente]</td>
 * </tr>
 * <tr>
 * <td >42 a 44</td>
 * <td >3</td>
 * <td >9(03)</td>
 * <td >Zeros</td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:fernandohsmartin@gmail.com">Fernando Martin</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLItauPadrao {

	/**
	 * Carteiras "exceção".
	 */
	private static final Set<Integer> CARTEIRAS_ESCRITURAIS = new HashSet<Integer>(8);
	private static final Set<Integer> CARTEIRAS_MODALIDADE_DIRETA = new HashSet<Integer>(5);

	static {
		CARTEIRAS_ESCRITURAIS.add(104);
		CARTEIRAS_ESCRITURAIS.add(105);
		CARTEIRAS_ESCRITURAIS.add(112);
		CARTEIRAS_ESCRITURAIS.add(113);
		CARTEIRAS_ESCRITURAIS.add(114);
		CARTEIRAS_ESCRITURAIS.add(147);
		CARTEIRAS_ESCRITURAIS.add(166);
		CARTEIRAS_ESCRITURAIS.add(212);

		CARTEIRAS_MODALIDADE_DIRETA.add(126);
		CARTEIRAS_MODALIDADE_DIRETA.add(131);
		CARTEIRAS_MODALIDADE_DIRETA.add(146);
		CARTEIRAS_MODALIDADE_DIRETA.add(150);
		CARTEIRAS_MODALIDADE_DIRETA.add(168);
	}

	/**
	 * <p>
	 * Dado um título, cria o campo livre padrão do Banco Itaú.
	 * </p>
	 * 
	 * @param titulo
	 *            título com as informações para geração do campo livre
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final ContaBancaria contaBancaria = titulo.getContaBancaria();

		final CampoLivre campoLivre = new CampoLivre(7);
		campoLivre.addZeroLeft(contaBancaria.getCarteira().getCodigo(), 3);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 8);
		campoLivre.add(calculaDigitoCampoLivrePadrao31(titulo), 1);
		campoLivre.addZeroLeft(contaBancaria.getAgencia().getCodigo(), 4);
		campoLivre.addZeroLeft(contaBancaria.getNumeroDaConta().getCodigoDaConta(), 5);
		campoLivre.add(calculaDigitoCampoLivrePadrao41(titulo), 1);
		campoLivre.addZero(3);
		return campoLivre;
	}

	private static Integer calculaDigitoCampoLivrePadrao31(final Titulo titulo) {
		final ContaBancaria contaBancaria = titulo.getContaBancaria();
		final Integer codigoDaCarteira = contaBancaria.getCarteira().getCodigo();

		final StringBuilder value = new StringBuilder();
		if (!CARTEIRAS_MODALIDADE_DIRETA.contains(codigoDaCarteira)
				&& !CARTEIRAS_ESCRITURAIS.contains(codigoDaCarteira)) {
			value.append(StringUtils.leftPad(contaBancaria.getAgencia().getCodigo().toString(), 4, '0'));
			value.append(StringUtils.leftPad(contaBancaria.getNumeroDaConta().getCodigoDaConta().toString(), 5, '0'));
		}
		value.append(StringUtils.leftPad(codigoDaCarteira.toString(), 3, '0'));
		value.append(StringUtils.leftPad(titulo.getNossoNumero(), 8, '0'));

		return calculaDigitoVerificador(value.toString());
	}

	private static Integer calculaDigitoCampoLivrePadrao41(final Titulo titulo) {
		final ContaBancaria contaBancaria = titulo.getContaBancaria();
		final StringBuilder value = new StringBuilder();
		value.append(StringUtils.leftPad(contaBancaria.getAgencia().getCodigo().toString(), 4, '0'));
		value.append(StringUtils.leftPad(contaBancaria.getNumeroDaConta().getCodigoDaConta().toString(), 5, '0'));
		return calculaDigitoVerificador(value.toString());
	}

	/**
	 * <p>
	 * Método auxiliar para calcular o dígito verificador dos campos 31 e 41. O
	 * dígito é calculado com base em um campo fornecido pelos métodos que o
	 * chamam (<code>calculeDigitoDaPosicao31</code> e
	 * <code>calculeDigitoDaPosicao41</code>)
	 * </p>
	 * <p>
	 * O cálculo é feito através do módulo 10.
	 * </p>
	 * 
	 * @param campo
	 * @return Dígito verificador do campo fornecido.
	 */
	private static Integer calculaDigitoVerificador(final String campo) {
		int restoDivisao = Modulo.calculeMod10(campo, 1, 2);
		int digito = MOD10 - restoDivisao;
		if (digito > 9) {
			digito = 0;
		}
		return new Integer(digito);
	}
}
