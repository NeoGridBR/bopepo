/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 17/02/2011 - 12:40:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 17/02/2011 - 12:40:00
 */

package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoob.MODALIDADE_DE_COBRANCA;
import static org.jrimum.bopepo.parametro.ParametroBancoob.NUMERO_DA_PARCELA;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do bradesco deve seguir esta forma:
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
 * <td >9(1)</td>
 * <td style="text-align:left;padding-left:10">Carteira</td>
 * <td style="text-align:left;padding-left:10">Carteira</td>
 * </tr>
 * <tr>
 * <td >21-24</td>
 * <td >4</td>
 * <td >9(4)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (Cooperativa)
 * (sem o dígito verificador, completar com zeros a esquerda quando
 * necessário)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência (sem
 * dígito)</td>
 * </tr>
 * <tr>
 * <td >25-26</td>
 * <td >2</td>
 * <td >9(2)</td>
 * <td style="text-align:left;padding-left:10">Modalidade</td>
 * <td style="text-align:left;padding-left:10">Modalidade</td>
 * </tr>
 * <tr>
 * <td >27-33</td>
 * <td >7</td>
 * <td >9(7)</td>
 * <td style="text-align:left;padding-left:10">Código do Cliente (sem o dígito
 * verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Conta do Beneficiário (sem
 * dígito)</td>
 * </tr>
 * <tr>
 * <td >34-40</td>
 * <td >7</td>
 * <td >9(7)</td>
 * <td style="text-align:left;padding-left:10">Número do título(sem o dígito
 * verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >41-41</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do Nosso
 * Número</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do Nosso
 * Número</td>
 * </tr>
 * <tr>
 * <td >42-44</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td style="text-align:left;padding-left:10">Número da Parcela (completar com
 * zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Número da Parcela</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLBancoobCobrancaNaoRegistrada {

	/**
	 * Tamanho do campo Carteira = 1.
	 */
	protected static final Integer CARTEIRA_LENGTH = Integer.valueOf(1);

	/**
	 * Tamanho do campo Agência = 4.
	 */
	protected static final Integer AGENCIA_LENGTH = Integer.valueOf(4);

	/**
	 * Tamanho do campo código da modalidade de cobrança = 2.
	 */
	protected static final Integer MODALIDADE_DE_COBRANCA_LENGTH = Integer.valueOf(2);

	/**
	 * Valor do código da modalidade de cobrança (01) = SIMPLES.
	 */
	protected static final Integer COBRANCA_SIMPLES = Integer.valueOf(1);

	/**
	 * Tamanho do campo Nosso Número = 7.
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(7);

	/**
	 * Tamanho do campo Dígito Verificador do Nosso Número = 1.
	 */
	private static final Integer DV_NOSSO_NUMERO_LENGTH = Integer.valueOf(1);

	/**
	 * Tamanho do campo Conta = 6.
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(6);

	/**
	 * Tamanho do campo Dígito da conta = 1
	 */
	private static final Integer DV_CONTA_LENGTH = 1;

	/**
	 * Valor do número de parcelas = 1.
	 */
	private static final Integer UMA_PARCELA = Integer.valueOf(1);

	/**
	 * Tamanho do campo Conta = 3.
	 */
	private static final Integer NUMERO_DA_PARCELA_LENGTH = 3;

	/**
	 * <p>
	 * Cria um campo livre instanciando o número de fields
	 * ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkCarteiraCodigo(titulo, 1, 9);
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkNossoNumeroTamanho(titulo, NOSSO_NUMERO_LENGTH);
		TituloValidator.checkNossoNumeroDigitoTamanho(titulo, DV_NOSSO_NUMERO_LENGTH);
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 999999);
		TituloValidator.checkContaBancariaDigito(titulo);

		final CampoLivre campoLivre = new CampoLivre(8);

		Integer codigoDaModalidadeDeCobranca = COBRANCA_SIMPLES;
		Integer numeroDaParcela = UMA_PARCELA;

		if (titulo.hasParametrosBancarios()) {
			if (titulo.getParametrosBancarios().contemComNome(MODALIDADE_DE_COBRANCA)) {
				TituloValidator.checkParametroBancarioNotNull(titulo, MODALIDADE_DE_COBRANCA);
				codigoDaModalidadeDeCobranca = titulo.getParametrosBancarios().getValor(MODALIDADE_DE_COBRANCA);
			}
			if (titulo.getParametrosBancarios().contemComNome(NUMERO_DA_PARCELA)) {
				TituloValidator.checkParametroBancarioNotNull(titulo, NUMERO_DA_PARCELA);
				numeroDaParcela = titulo.getParametrosBancarios().getValor(NUMERO_DA_PARCELA);
			}
		}

		campoLivre.addZeroLeft(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH);
		campoLivre.addZeroLeft(codigoDaModalidadeDeCobranca, MODALIDADE_DE_COBRANCA_LENGTH);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), DV_CONTA_LENGTH);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH);
		campoLivre.add(titulo.getDigitoDoNossoNumero(), DV_NOSSO_NUMERO_LENGTH);
		campoLivre.addZeroLeft(numeroDaParcela, NUMERO_DA_PARCELA_LENGTH);
		return campoLivre;
	}
}
