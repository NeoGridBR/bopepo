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
 * Created at: 30/03/2008 - 18:04:37
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
 * Criado em: 30/03/2008 - 18:04:37
 * 
 */

package org.jrimum.bopepo;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.jrimum.vallia.digitoverificador.BoletoLinhaDigitavelDV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Representa a linha digitável do boleto, embora a linha digitável contenha a
 * mesma informação do código de barras, essa informação é disposta de uma forma
 * diferente e são acrescentados 3 dígitos verificadores. <br />
 * <br />
 * Modelo: <br />
 * <br />
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="90%" id="linhaDigitável">
 * <thead>
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead>
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td>Identificação do banco</td>
 * </tr>
 * <tr>
 * <td>04-04</td>
 * <td>1</td>
 * <td>Código de moeda (9 – Real)</td>
 * </tr>
 * <tr>
 * <td>05-09</td>
 * <td>5</td>
 * <td>Cinco primeiras posições do campo livre (posições 20 a 24 do código de
 * barras)</td>
 * </tr>
 * <tr>
 * <td>10-10</td>
 * <td>1</td>
 * <td>Dígito verificador do primeiro campo</td>
 * </tr>
 * <tr>
 * <td>11-20</td>
 * <td>10</td>
 * <td>6ª a 15ª posições do campo livre (posições 25 a 34 do código de barras)
 * </td>
 * </tr>
 * <tr>
 * <td>21-21</td>
 * <td>1</td>
 * <td>Dígito verificador do segundo campo</td>
 * </tr>
 * <tr>
 * <td>22-31</td>
 * <td>10</td>
 * <td>16ª a 25ª posições do campo livre (posições 35 a 44 do código de barras)
 * </td>
 * </tr>
 * <tr>
 * <td>32-32</td>
 * <td>1</td>
 * <td>Dígito verificador do terceiro campo</td>
 * </tr>
 * <tr>
 * <td>33-33</td>
 * <td>1</td>
 * <td>Dígito verificador geral (posição 5 do código de barras)</td>
 * </tr>
 * <tr>
 * <td>34-37</td>
 * <td>4</td>
 * <td>Posições 34 a 37 – fator de vencimento (posições 6 a 9 do código
 * debarras)</td>
 * </tr>
 * <tr>
 * <td>37-47</td>
 * <td>10</td>
 * <td>Posições 38 a 47 – valor nominal do título(posições 10 a 19 do código de
 * barras)</td>
 * </tr>
 * </table>
 * 
 * <br />
 * <br />
 * 
 * Observações:
 * 
 * <br />
 * <ul>
 * 
 * <li>Em cada um dos três primeiros campos, após a 5a posição, deve ser
 * inserido um ponto “.”, a fim de facilitar a visualização, para a digitação,
 * quando necessário;</li>
 * <li>Quinto campo:
 * <ul>
 * <br />
 * <li>preenchimento com zeros entre o fator de vencimento e o valor até
 * completar 14 posições;
 * <li>a existência de “0000” no campo “fator de vencimento” da linha digitável
 * do bloqueto de cobrança é indicativo de que o código de barras não contém
 * fator de vencimento. Nesse caso, o banco acolhedor/recebedor estará isento
 * das responsabilidades pelo recebimento após o vencimento, que impede de
 * identificar automaticamente se o bloqueto está ou não vencido;</li>
 * <li>quando se tratar de bloquetos sem discriminação do valor no código de
 * barras, a representação deverá ser com zeros;</li>
 * <li>não deverá conter separação por pontos, vírgulas ou espaços;</li>
 * </ul>
 * <br />
 * </li>
 * <li>Os dígitos verificadores referentes aos 1º, 2º e 3º campos não são
 * representados no código de barras;</li>
 * <li>Os dados da linha digitável não se apresentam na mesma ordem do código de
 * barras.</li>
 * 
 * </ul>
 * 
 * 
 * @see org.jrimum.bopepo.CodigoDeBarras
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
public class LinhaDigitavel implements Serializable {

	private static final long serialVersionUID = -6089634012523938802L;

	private static Logger log = LoggerFactory.getLogger(LinhaDigitavel.class);

	private static final Pattern LINHA_DIGITAVEL_UNFORMATTER_PATTERN = Pattern.compile("(\\d{5})(\\d{5})(\\d{5})(\\d{6})(\\d{5})(\\d{6})(\\d{1})(\\d{14})");
	private static final String LINHA_DIGITAVEL_FORMATTER_REPLACEMENT = "$1.$2 $3.$4 $5.$6 $7 $8";

	private final String formattedValue;

	/**
	 * <p>
	 * Cria uma linha digitável a partir do código de barras passado.
	 * </p>
	 * 
	 * @param codigoDeBarras
	 * 
	 * @see CodigoDeBarras
	 * 
	 * @since 0.2
	 */
	LinhaDigitavel(final CodigoDeBarras codigoDeBarras) {
		log.trace("Instanciando Linha Digitável");
		log.debug("codigoDeBarra instance : " + codigoDeBarras);

		final String codigoDeBarrasValue = codigoDeBarras.write();
		// final BoletoLinhaDigitavelDV calculadorDV = new BoletoLinhaDigitavelDV();
		final LuhnCheckDigit checkDigit = new LuhnCheckDigit();

		final StringBuilder linhaDigitavel = new StringBuilder();

		final StringBuilder toCalculateDV1 = new StringBuilder();
		toCalculateDV1.append(StringUtils.substring(codigoDeBarrasValue, 0, 3));
		toCalculateDV1.append(StringUtils.substring(codigoDeBarrasValue, 3, 4));
		toCalculateDV1.append(StringUtils.substring(codigoDeBarrasValue, 19, 24));
		linhaDigitavel.append(toCalculateDV1);

		// final int boletoDV1 = calculadorDV.calcule(toCalculateDV1.toString());
		try {
			final String boletoDV1 = checkDigit.calculate(toCalculateDV1.toString());
			linhaDigitavel.append(boletoDV1);
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		log.debug("Linha Digitável com o campo 1: " + linhaDigitavel);

		final String toCalculateDV2 = StringUtils.substring(codigoDeBarrasValue, 24, 34);

		//final int boletoDV2 = calculadorDV.calcule(toCalculateDV2);

		linhaDigitavel.append(toCalculateDV2);
		try {
			final String boletoDV2 = checkDigit.calculate(toCalculateDV2.toString());
			linhaDigitavel.append(boletoDV2);
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		log.debug("Linha Digitável até o campo 2: " + linhaDigitavel);

		final String toCalculateDV3 = StringUtils.substring(codigoDeBarrasValue, 34, 44);
		//final int boletoDV3 = calculadorDV.calcule(toCalculateDV3);
		linhaDigitavel.append(toCalculateDV3);
		try {
			final String boletoDV3 = checkDigit.calculate(toCalculateDV3.toString());
			linhaDigitavel.append(boletoDV3);
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		log.debug("Linha Digitável até o campo 3: " + linhaDigitavel);

		linhaDigitavel.append(codigoDeBarras.getDigitoVerificadorGeral());
		log.debug("Linha Digitável até campo 4: " + linhaDigitavel);

		linhaDigitavel.append(StringUtils.substring(codigoDeBarrasValue, 5, 9));
		linhaDigitavel.append(StringUtils.substring(codigoDeBarrasValue, 9, 19));
		log.debug("Linha Digitável até o campo 5: " + linhaDigitavel);

		this.formattedValue = LINHA_DIGITAVEL_UNFORMATTER_PATTERN.matcher(linhaDigitavel).replaceAll(LINHA_DIGITAVEL_FORMATTER_REPLACEMENT);
		log.debug("linhaDigitavel instanciada : " + this.formattedValue);
	}

	/**
	 * Escreve a linha digitável formatada (com espaço entre os campos).
	 */
	public String write() {
		return this.formattedValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
