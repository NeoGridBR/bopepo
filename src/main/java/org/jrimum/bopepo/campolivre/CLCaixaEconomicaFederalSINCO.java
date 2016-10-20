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
 * Created at: 30/03/2008 - 18:09:45
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
 * Criado em: 30/03/2008 - 18:09:45
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.FixedField;

/**
 * <p>
 * O campo livre para o modelo SINCO segue esta forma:
 * </p>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <thead>
 * <tr>
 * <td>Posição</td>
 * <td>Tamanho</td>
 * <td>Conteúdo</td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>01</td>
 * <td>1</td>
 * <td>Número "1" (valor fixo)</td>
 * </tr>
 * <tr>
 * <td>02-07</td>
 * <td>6</td>
 * <td>Código do cliente CEDENTE (fornecido pela CAIXA)</td>
 * </tr>
 * <tr>
 * <td>08</td>
 * <td>1</td>
 * <td>Número "9" (valor fixo)</td>
 * </tr>
 * <tr>
 * <td>09-25</td>
 * <td>17</td>
 * <td>Posições livres do "nosso número"</td>
 * </tr>
 * </tbody>
 * </table>
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
public class CLCaixaEconomicaFederalSINCO {

	/**
	 * Constante "1".
	 */
	private static final FixedField<Integer> FIELD_1 = new FixedField<Integer>(1, 1);

	/**
	 * Constante "9".
	 */
	private static final FixedField<Integer> FIELD_9 = new FixedField<Integer>(9, 1);

	/**
	 * <p>
	 * Dado um título, cria um campo livre para o padrão do Banco Caixa
	 * Econômica Federal que tenha o serviço SINCO.
	 * </p>
	 * 
	 * @param titulo
	 *            Título com as informações para geração do campo livre.
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final CampoLivre campoLivre = new CampoLivre(4);
		campoLivre.add(FIELD_1);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 6);
		campoLivre.add(FIELD_9);
		campoLivre.addString(titulo.getNossoNumero(), 17);
		return campoLivre;
	}

}
