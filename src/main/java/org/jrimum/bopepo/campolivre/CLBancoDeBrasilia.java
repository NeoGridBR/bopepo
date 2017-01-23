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
 * Created at: 24/08/2013 - 19:54:00
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
 * Criado em: 24/08/2013 - 19:54:00
 * 
 */

package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO1;
import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO2;

import org.apache.commons.lang.StringUtils;
import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * <p>
 * O campo livre do BRB - Banco de Brasília deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="campolivre">
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
 * <td >20-22</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td style="text-align:left;padding-left:10">fixo "000" ZEROS</td>
 * <td style="text-align:left;padding-left:10">ZEROS</td>
 * </tr>
 * <tr>
 * <td >23-25</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (Sem o dígito verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência (sem dígito)</td>
 * </tr>
  * <tr>
 * <td >26-32</td>
 * <td >7</td>
 * <td >&nbsp;9(7)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (Sem o digito verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >33-33</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Carteira | Modalidade/ Categoria</td>
 * <td style="text-align:left;padding-left:10">Categoria - referente ao tipo de cobrança 1-sem registro impressão local, 2-com registro impressão local</td>
 * </tr>
 * <tr>
 * <td >34-39</td>
 * <td >6</td>
 * <td >&nbsp;9(6)</td>
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Número sequencial</td>
 * </tr>
 * <tr>
 * <td >40-42</td>
 * <td >3</td>
 * <td >&nbsp;9(3)</td>
 * <td style="text-align:left;padding-left:10">Código BACEN(Sem o digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Código do Banco- 070 - BRB</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador 1 da CHAVE ASBACE</td>
 * <td style="text-align:left;padding-left:10">DV1</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador 2 da CHAVE ASBACE</td>
 * <td style="text-align:left;padding-left:10">DV2</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
/**
 * @author gleitao
 *
 */
public class CLBancoDeBrasilia {

	/**
	 * <p>
	 * Cria um campo livre instanciando o número de fields
	 * ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 999);
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 9999999);
		TituloValidator.checkNossoNumeroTamanho(titulo, 6);
		TituloValidator.checkCarteiraCodigo(titulo, 1, 2);

		final CampoLivre campoLivre = new CampoLivre(8);

		campoLivre.addZero(3);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 3);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 7);
		campoLivre.add(titulo.getContaBancaria().getCarteira().getCodigo(), 1);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 6);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo(), 3);

		final String valorParcial = campoLivre.getValue();
		final Integer[] chaveASBACE = calculeChaveASBACE(valorParcial);
		campoLivre.add(chaveASBACE[0], 1);
		campoLivre.add(chaveASBACE[1], 1);

		disponibilizeDigitosDaChaveAsbaceNeste(titulo, chaveASBACE[0], chaveASBACE[1]);

		return campoLivre;
	}

	/**
	 * Calcula o primeiro dígito da CHAVE ASBECE independente do segundo, mas o
	 * segundo DV depende deste e quando o segundo é calculado este pode,
	 * talvez, ser alterado.
	 * 
	 * @since 0.2
	 */
	private static Integer[] calculeChaveASBACE(final String chaveASBACE) {
		int dv1ChaveASBACE;
		if(StringUtils.length(chaveASBACE) == 23) {
			dv1ChaveASBACE = Modulo.calculeMod10(chaveASBACE, 1, 2);
			if (dv1ChaveASBACE > 0) {
				dv1ChaveASBACE = 10 - dv1ChaveASBACE;
			}
		} else {
			dv1ChaveASBACE = Integer.parseInt(StringUtils.right(chaveASBACE, 1));
		}

		final String chaveASBACEComDv1 = chaveASBACE + dv1ChaveASBACE;
		int dv2ChaveASBACE = Modulo.calculeMod11(chaveASBACEComDv1, 2, 7);
		if (dv2ChaveASBACE > 0) {
			if (dv2ChaveASBACE != 1) {
				dv2ChaveASBACE = 11 - dv2ChaveASBACE;
			} else {
				int digito1Recalculado = dv1ChaveASBACE + 1;
				return calculeChaveASBACE(chaveASBACE + ((digito1Recalculado == 10) ? 0 : digito1Recalculado));
			}
		}

		return new Integer[]{dv1ChaveASBACE, dv2ChaveASBACE};
	}


	/**
	 * Disponibiliza no objeto titulo os dígitos da CHAVE ASBACE = mesmo que o
	 * campo livre menos os dois ultimos digitos.
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void disponibilizeDigitosDaChaveAsbaceNeste(final Titulo titulo,
			final Integer dv1ChaveASBACE, final Integer dv2ChaveASBACE) {
		ParametrosBancariosMap parametrosBancarios = titulo.getParametrosBancarios();
		if (parametrosBancarios == null) {
			parametrosBancarios = new ParametrosBancariosMap();
		}
		parametrosBancarios.adicione(CHAVE_ASBACE_DIGITO1, dv1ChaveASBACE);
		parametrosBancarios.adicione(CHAVE_ASBACE_DIGITO2, dv2ChaveASBACE);
		titulo.setParametrosBancarios(parametrosBancarios);
	}

}
