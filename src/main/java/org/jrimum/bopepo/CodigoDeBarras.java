/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:04:23
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:04:23
 * 
 */

package org.jrimum.bopepo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * É um número único para cada Boleto composto dos seguintes campos:
 * </p>
 * <div>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="100%">
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead> <tbody style="text-align:center">
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td style="text-align:right;padding-right:10px">9(3)</td>
 * <td style="text-align:left;padding-left:10px">Identificação do banco</td>
 * </tr>
 * <tr>
 * <td>04-04</td>
 * <td>1</td>
 * <td style="text-align:right;padding-right:10px">9</td>
 * <td style="text-align:left;padding-left:10px">Código moeda (9-Real)</td>
 * </tr>
 * <tr>
 * <td>05-05</td>
 * <td>1</td>
 * <td style="text-align:right;padding-right:10px">9</td>
 * <td style="text-align:left;padding-left:10px">Dígito verificador do
 * composição de barras (DV)</td>
 * </tr>
 * <tr>
 * <td>06-09</td>
 * <td>4</td>
 * <td style="text-align:right;padding-right:10px">9(4)</td>
 * <td style="text-align:left;padding-left:10px">Posições 06 a 09 – fator de
 * vencimento</td>
 * </tr>
 * <tr>
 * <td>10-19</td>
 * <td>10</td>
 * <td style="text-align:right;padding-right:10px">9(08)v99</td>
 * <td style="text-align:left;padding-left:10px">Posições 10 a 19 – valor
 * nominal do título&nbsp;</td>
 * </tr>
 * <tr>
 * <td>20-44</td>
 * <td>25</td>
 * <td style="text-align:right;padding-right:10px">9(25)</td>
 * <td style="text-align:left;padding-left:10px">FixedField livre – utilizado de
 * acordo com a especificação interna do banco emissor</td>
 * </tr>
 * </tbody>
 * </table>
 * </div>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CodigoDeBarras implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 748913164143978133L;

	private static Logger log = LoggerFactory.getLogger(CodigoDeBarras.class);

	/**
	 * 
	 */
	private static final Integer FIELDS_SIZE = 5;

	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 43;

	private final String value;

	private final int digitoVerificadorGeral;
	/**
	 * <p>
	 * Cria um Código de Barras a partir do título e campo livre passados.
	 * </p>
	 * 
	 * @param titulo
	 * @param campoLivre
	 * 
	 * @see CampoLivre
	 */
	CodigoDeBarras(final Titulo titulo, final CampoLivre campoLivre) {
		log.trace("Instanciando o CodigoDeBarras");
		log.debug("titulo instance : " + titulo);
		log.debug("campoLivre instance : " + campoLivre);

		// digitoVerificadorGeral = new FixedField<Integer>(0, 1,
		// Fillers.ZERO_LEFT);
		// valorNominalDoTitulo = new FixedField<BigDecimal>(new BigDecimal(0),
		// 10,DecimalFormat.NUMBER_DD_BR.copy(),Fillers.ZERO_LEFT);

		// Preparando o conjunto de informações que será a base para o cálculo
		// do dígito verificador, conforme normas da FEBRABAN.
		final CampoLivre values = new CampoLivre(STRING_LENGTH, FIELDS_SIZE);
		// Código do Banco
		values.addZeroLeft(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), 3);
		// Código da Moeda
		values.addZeroLeft(titulo.getTipoDeMoeda().getCodigo(), 1);
		// Fator de Vencimento
		values.addZeroLeft(FatorDeVencimento.toFator(titulo.getDataDoVencimento()), 4);
		// Valor Nominal
		values.addZeroLeft(titulo.getValor(), 10);
		// Campo Livre
		values.add(campoLivre.write(), CampoLivre.STRING_LENGTH);
		final String toCalculateDV = values.write();

		// Dígito Verificador Geral
		this.digitoVerificadorGeral = calculateDigitoVerificadorGeral(toCalculateDV);
		StringUtils.left(toCalculateDV, 4);
		this.value = StringUtils.left(toCalculateDV, 4) + this.digitoVerificadorGeral + StringUtils.right(toCalculateDV, 39);

		log.debug("codigoDeBarra instanciado : " + this);
	}

	private int calculateDigitoVerificadorGeral(final String toCalculateDV) {
		log.trace("Calculando Digito Verificador Geral");

		// Instanciando o objeto irá calcular o dígito verificador do boleto.
		final BoletoCodigoDeBarrasDV calculadorDV = new BoletoCodigoDeBarrasDV();

		// Realizando o cálculo dígito verificador e em seguida armazenando
		// a informação no campo "digitoVerificadorGeral".
		final int digitoVerificadorGeral = calculadorDV.calcule(toCalculateDV.toString());

		log.debug("Digito Verificador Geral calculado : " + digitoVerificadorGeral);
		return digitoVerificadorGeral;
	}

	public int getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}

	public String write() {
		return this.value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
