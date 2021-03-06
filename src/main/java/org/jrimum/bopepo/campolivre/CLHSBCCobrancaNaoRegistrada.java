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
 * Created at: 30/03/2008 - 18:09:27
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
 * Criado em: 30/03/2008 - 18:09:27
 * 
 */

package org.jrimum.bopepo.campolivre;

import java.util.Calendar;
import java.util.Date;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.bopepo.parametro.ParametroHSBC;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do HSBC, para cobrança não registrada(CNR), deve seguir esta
 * forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr>
 * <thead>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th> </thead>
 * </tr>
 * 
 * <tr>
 * <td>20-26</td>
 * <td>7</td>
 * <td>9(7)</td>
 * <td>Conta do cedente (sem dígito)</td>
 * <td>Código do cedente</td>
 * </tr>
 * 
 * <tr>
 * <td>27-39</td>
 * <td>13</td>
 * <td>9(13)</td>
 * <td>Nosso número (sem dígito)</td>
 * <td>Número bancário - Código do documento, sem os dígitos verificadores e
 * tipo identificador.</td>
 * </tr>
 * 
 * <tr>
 * <td>40-43</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td>Fator de vencimento</td>
 * <td>ou Data do vencimento no formato juliano</td>
 * </tr>
 * 
 * <tr>
 * <td>44-44</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td>2 FIXO</td>
 * <td>Código do Aplicativo CNR = 2</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
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
public class CLHSBCCobrancaNaoRegistrada {

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkParametroBancarioNotNull(titulo, ParametroHSBC.IDENTIFICADOR_CNR);

		final CampoLivre campoLivre = new CampoLivre(4);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 7);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 13);
		campoLivre.addZeroLeft(getDataVencimento(titulo), 4);
		// 2 FIXO (Código do Aplicativo CNR - Cob. Não Registrada)
		campoLivre.add(2, 1);

		return campoLivre;
	}

	private static String getDataVencimento(final Titulo titulo) {
		final Integer tipoIdentificadorCNR = titulo.getParametrosBancarios().getValor(ParametroHSBC.IDENTIFICADOR_CNR);
		final Date vencimento = titulo.getDataDoVencimento();

		final int SEM_VENCIMENTO = 5;
		final int COM_VENCIMENTO = 4;

		switch (tipoIdentificadorCNR) {
		case SEM_VENCIMENTO:
			return "0000";
		case COM_VENCIMENTO:
			final Calendar c = Calendar.getInstance();
			c.setTime(vencimento);
			return new StringBuilder(String.valueOf(c.get(Calendar.DAY_OF_YEAR)))
					.append(String.valueOf(c.get(Calendar.YEAR) % 10)).toString();
		default:
			throw new IllegalStateException("Tipo de identificador CNR desconhecido!");
		}
	}

}
