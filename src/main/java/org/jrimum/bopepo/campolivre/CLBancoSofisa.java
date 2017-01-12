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
import org.jrimum.bopepo.parametro.ParametroBancoSofisa;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class CLBancoSofisa {

	/**
	 * /**
	 * <p>
	 * O campo livre do Banco Sofisa deve seguir esta forma:
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
	 * </thead>
	 * <tbody>
	 * <tr>
	 * <td>20-23</td>
	 * <td>4</td>
	 * <td>9(4)</td>
	 * <td>Agência Cedente (Sem o digito verificador, completar com zeros a esquerda quando necessário)</td>
	 * <td>Código da Agência (sem dígito)</td>
	 * </tr>
	 * <tr>
	 * <td>24-26</td>
	 * <td>3</td>
	 * <td>9(3)</td>
	 * <td>Código da Carteira</td>
	 * <td>Código da Carteira</td>
	 * </tr>
	 * <tr>
	 * <td>27-33</td>
	 * <td>7</td>
	 * <td>9(7)</td>
	 * <td>Operação (Número da Operação do Cliente, na agência, completar com zeros a esquerda quando necessário)</td>
	 * <td>Número da Operação do Cliente</td>
	 * </tr>
	 * <tr>
	 * <td>34-43</td>
	 * <td>10</td>
	 * <td>9(10)</td>
	 * <td>Número do Nosso Número (sem o digito verificador)</td>
	 * <td>Nosso Número (sem dígito)</td>
	 * </tr>
	 * <tr>
	 * <td>44-44</td>
	 * <td>1</td>
	 * <td>9</td>
	 * <td>Dígito verificador do Nosso Número</td>
	 * <td>Dígito Nosso Número</td>
	 * </tr>
	 * </table>
	 * 
	 * @param titulo
	 * @return
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkParametroBancarioNotNull(titulo, ParametroBancoSofisa.CODIGO_DO_CONVENIO);

		final CampoLivre campoLivre = new CampoLivre(5);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 4);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getCarteira().getCodigo(), 3);
		final Integer codigoOperacao = titulo.getParametrosBancarios().<Integer>getValor(ParametroBancoSofisa.CODIGO_DO_CONVENIO);
		campoLivre.addZeroLeft(codigoOperacao, 7);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 10);
		campoLivre.add(titulo.getDigitoDoNossoNumero(), 1);
		return campoLivre;
	}

}
