/* 
 * Copyright 2010 JRimum Project
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
 * Created at: 15/01/2010 - 20:40:59
 *
 * ================================================================================
 *
 * Direitos autorais 2010 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 15/01/2010 - 20:40:59
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static org.jrimum.bopepo.parametro.ParametroBancoSicredi.POSTO_DA_AGENCIA;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;
import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * <p>
 * O campo livre do banco Sicredi deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="100%" id="campolivre">
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </tr>
 * </thead> <tbody style="text-align:center">
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">tipo de cobrança: "1" ou "3"</td>
 * <td style="text-align:left;padding-left:10">Código numérico correspondente ao
 * tipo de cobrança: "1"- Com Registro ou "3"- Sem Registro</td>
 * </tr>
 * <tr>
 * <td >21-21</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Constante correspondente ao tipo
 * de carteira: "1"- carteira simples. Embora no código fonte do manual mencione
 * outras duas possibilidades "2"-Caucionada ou "3"-Descontada.</td>
 * <td style="text-align:left;padding-left:10">Código númerico correspondente ao
 * tipo de carteira: "1" - carteira simples</td>
 * </tr>
 * <tr>
 * <td >22-29</td>
 * <td >8</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (8)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número</td>
 * </tr>
 * <tr>
 * <td >30-30</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">DV do Nosso Número (1)</td>
 * <td style="text-align:left;padding-left:10">Dígito Verificador do Nosso
 * Número</td>
 * </tr>
 * <tr>
 * <td >31-34</td>
 * <td >4</td>
 * <td style="text-align:left;padding-left:10">Cooperativa de crédito / agência
 * cedente</td>
 * <td style="text-align:left;padding-left:10">Cooperativa de crédito / agência
 * cedente</td>
 * </tr>
 * <tr>
 * <td >35-36</td>
 * <td >2</td>
 * <td style="text-align:left;padding-left:10">Unidade de atendimento / posto da
 * agênica cedente</td>
 * <td style="text-align:left;padding-left:10">Unidade de atendimento / posto da
 * agênica cedente</td>
 * </tr>
 * <tr>
 * <td >37-41</td>
 * <td >5</td>
 * <td style="text-align:left;padding-left:10">Código do Cedente</td>
 * <td style="text-align:left;padding-left:10">Código do Cedente</td>
 * </tr>
 * <tr>
 * <td >42-42</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Filler: 1 (um) quando o campo
 * "valor do documento" diferente de 0 (zero), caso contrário zero.</td>
 * <td style="text-align:left;padding-left:10">1 (um) quando o campo "valor do
 * documento" diferente de 0 (zero), caso contrário zero.</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Constante "0"- Zero</td>
 * <td style="text-align:left;padding-left:10">Zero Fixo</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do campo livre
 * calculado por módulo 11 com aproveitamento total (resto igual a (0) zero ou
 * (1) um o Dígito será (0) zero)</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do campo livre
 * calculado por módulo 11 com aproveitamento total (resto igual a (0) zero ou
 * (1) um o Dígito será (0) zero)</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLSicredi {

	/**
	 * Código númerico correspondente ao tipo de cobrança: "1" - Com Registro.
	 */
	private static final String COBRANCA_COM_REGISTRO = "1";

	/**
	 * Código númerico correspondente ao tipo de cobrança: "3" - Sem Registro.
	 */
	private static final String COBRANCA_SEM_REGISTRO = "3";

	/**
	 * Valor constante do campo "Tipo da Carteira": "1" - carteira simples.
	 */
	private static final Integer CARTEIRA_SIMPLES_VALUE = NumberUtils.INTEGER_ONE;

	/**
	 * Instância de módulo 11 para cálculo do DV do campo livre.
	 */
	private static final Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);

	/**
	 * Cria um campo livre instanciando o número de fields
	 * ({@code FIELDS_LENGTH}) deste campo.
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkCarteiraCodigo(titulo);
		checkCarteiraSimples(titulo);
		TituloValidator.checkCarteiraTipoCobrancaNotNull(titulo);
		TituloValidator.checkNossoNumeroTamanho(titulo, 8);
		TituloValidator.checkNossoNumeroDigitoTamanho(titulo, 1);
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkParametroBancarioNotNull(titulo, POSTO_DA_AGENCIA);
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 99999);

		final CampoLivre campoLivre = new CampoLivre(10);
		if (titulo.getContaBancaria().getCarteira().isComRegistro()) {
			campoLivre.add(COBRANCA_COM_REGISTRO, 1);
		} else {
			campoLivre.add(COBRANCA_SEM_REGISTRO, 1);
		}
		campoLivre.add(CARTEIRA_SIMPLES_VALUE, 1);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 8);
		campoLivre.addZeroLeft(titulo.getDigitoDoNossoNumero(), 1);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 4);
		campoLivre.addZeroLeft(titulo.getParametrosBancarios().<Integer>getValor(POSTO_DA_AGENCIA), 2);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 5);
		if (titulo.getValor().compareTo(ZERO) == 1) {
			campoLivre.add("1", 1);
		} else {
			campoLivre.addZero(1);
		}
		campoLivre.addZero(1);
		campoLivre.add(calculeDigitoVerificador(campoLivre.getValue()), 1);
		return campoLivre;
	}

	/**
	 * Verifica se o código da carteira da conta bancária do título é igual 1
	 * (carteira simples), caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * 
	 * @param titulo
	 */
	private static void checkCarteiraSimples(final Titulo titulo) {
		Validate.isTrue(titulo.getContaBancaria().getCarteira().getCodigo().equals(CARTEIRA_SIMPLES_VALUE),
				format("Apenas a carteira de código [1] \"carteira simples\" é permitida e não o código [%s]!",
						titulo.getContaBancaria().getCarteira().getCodigo()));
	}

	/**
	 * Calcula o dígito verificador deste campo livre (posição 25 do campo livre
	 * 44 do código de barras) com módulo 11 a partir das 24 posições deste
	 * campo livre.
	 * 
	 * @return dígito verificador
	 * 
	 * @since 0.2
	 */
	private static Integer calculeDigitoVerificador(final String value) {
		final int resto = modulo11.calcule(value);
		if (resto == 0 || resto == 1) {
			return Integer.valueOf(0);
		} else {
			return Integer.valueOf(modulo11.valor() - resto);
		}
	}

}
