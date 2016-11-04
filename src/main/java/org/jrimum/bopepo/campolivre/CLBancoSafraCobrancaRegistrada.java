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
 * Created at: 21/04/2008 - 20:31:39
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
 * Criado em: 21/04/2008 - 20:31:39
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do Bradesco deve seguir esta forma:
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
 * <td style="text-align:left;padding-left:10">Sistema = constante 7</td>
 * <td style="text-align:left;padding-left:10">Valor fixo 7</td>
 * </tr>
 * <tr>
 * <td >21-34</td>
 * <td >14</td>
 * <td >9(14)</td>
 * <td style="text-align:left;padding-left:10">Código do cliente = Agência +
 * Conta</td>
 * <td style="text-align:left;padding-left:10">Código do cliente = (Ag[4] +
 * dv[1]) + (Cc[8] + dv[1])</td>
 * </tr>
 * <tr>
 * <td >35-43</td>
 * <td >9</td>
 * <td >&nbsp;9(9)</td>
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o
 * digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número do título exatamente
 * com 9 dígitos (sem dígito verificador)</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Tipo cobrança = constante 2 -
 * Eletrônica Emitido pelo Cliente</td>
 * <td style="text-align:left;padding-left:10">Valor fixo 2</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLBancoSafraCobrancaRegistrada {

	/**
	 * Cria um campo livre instanciando o número de fields
	 * ({@code FIELDS_LENGTH} ) deste campo.
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkAgenciaDigito(titulo);
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 999999);
		TituloValidator.checkContaBancariaDigito(titulo);
		TituloValidator.checkNossoNumeroTamanho(titulo, 9);

		final CampoLivre campoLivre = new CampoLivre(7);
		campoLivre.addInteger(7, 1);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 4);
		campoLivre.addString(titulo.getContaBancaria().getAgencia().getDigitoVerificador(), 1);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 8);
		campoLivre.addString(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), 1);
		campoLivre.addString(titulo.getNossoNumero(), 9);
		// 1 = Bloqueto Emitido pelo Banco. 2 = Eletrônica Emitido pelo Cliente.
		// 4 = Express Emitido pelo Cliente.
		campoLivre.addInteger(2, 1);
		return campoLivre;
	}

}
