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
 * Created at: Dec 13, 2008 - 10:40:38 AM
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
 * Criado em: Dec 13, 2008 - 10:40:38 AM
 * 
 */
package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras
 * (<em>cobrança</em>) sem registro, caucionadas e com registro. O tipo de
 * cobrança de carteira caucionada se enquadra no conceito de cobrança
 * registrada, sendo diferenciada pelo código da carteira.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Normal - CAMPO LIVRE - Chave ASBACE</font>
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" >
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face=
 * "Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Campo Livre No Código De
 * Barras (20 a 44)</font></strong></td>
 * <tr>
 * <td align="center"><font face="Arial">20 a 27</font></td>
 * <td><font face="Arial">Nosso Número (sem os dois dígitos)</font></td>
 * 
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 38</font></td>
 * <td><font face="Arial">Conta Corrente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">39 a 39</font></td>
 * 
 * <td><font face="Arial">Produto = 2-Sem registro; 3-Caucionada; 4,5,6 e
 * 7-Cobrança com registro</font></td>
 * </tr>
 * 
 * <tr>
 * <td align="center"><font face="Arial">40 a 42</font></td>
 * <td><font face="Arial">Constante = "021" Código do BANESTES </font></td>
 * </tr>
 * 
 * <tr>
 * <td align="center"><font face="Arial">43 a 44</font></td>
 * <td><font face="Arial">Duplo Dígito referente às posições 20 a 42</font></td>
 * </tr>
 * 
 * </table>
 * </div>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a> Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLBanestes {

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkNossoNumeroTamanhoMenorOuIgualQue(titulo, 8);
		TituloValidator.checkCarteiraTipoCobrancaNotNull(titulo);

		final CampoLivre campoLivre = new CampoLivre(5);
		campoLivre.addZeroLeft(Integer.valueOf(titulo.getNossoNumero()), 8);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 11);

		final Integer codigoDaCarteiraDeCobranca = titulo.getContaBancaria().getCarteira().getCodigo();

		final TipoDeCobranca tipoDeCobranca = titulo.getContaBancaria().getCarteira().getTipoCobranca();
		switch (tipoDeCobranca) {
		case SEM_REGISTRO:
			campoLivre.add(2, 1);
			break;
		case COM_REGISTRO:
			if (codigoDaCarteiraDeCobranca >= 3 && codigoDaCarteiraDeCobranca <= 7) {
				campoLivre.add(codigoDaCarteiraDeCobranca, 1);
				break;
			} else {
				throw new CampoLivreException("Código da carteira de cobrança com registro deve ser"
						+ " especificado com 3,4,5,6 ou 7. Valor atual = [" + codigoDaCarteiraDeCobranca + "]");
			}
		default:
			throw new CampoLivreException("Tipo de cobrança [" + tipoDeCobranca + "] não é suportado!");
		}
		campoLivre.addZeroLeft(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo(), 3);
		campoLivre.addZeroLeft(calculaDuploDV(campoLivre.getValue()), 2);
		return campoLivre;
	}

	/**
	 * Calcula duplo dígito verificador da posição 0 à 23 do campo livre (Chave
	 * ASBACE)..
	 * 
	 * @return Duplo dígito verificador.
	 * 
	 */
	private static int calculaDuploDV(final String value) {
		final int duploDV;
		byte primeiroDV = calculaPrimeiroDV(value);
		final int segundoDV;

		// resto proveniente do módulo 11 com pesos de 2 a 7
		int restoDoModulo11 = new Modulo(TipoDeModulo.MODULO11, 7, 2).calcule(value + primeiroDV);
		if (restoDoModulo11 == 0) {
			segundoDV = 0;
		} else if (restoDoModulo11 == 1) {
			if (primeiroDV == 9) {
				primeiroDV = 0;
			} else {
				primeiroDV++;
			}
			segundoDV = new Modulo(TipoDeModulo.MODULO11, 7, 2).calcule(value + primeiroDV);
		} else {
			segundoDV = (11 - restoDoModulo11);
		}
		duploDV = Byte.parseByte(String.valueOf(primeiroDV) + String.valueOf(segundoDV));
		return duploDV;
	}

	/**
	 * Calcula o primeiro dígito verificador.
	 * 
	 * @param fields
	 * 
	 * @return O primeiro dígito verificador dos dois existentes na chave ASBACE
	 *         (Campo livre).
	 */
	private static byte calculaPrimeiroDV(String fields) {
		final byte primeiroDV;
		// resto proveniente do módulo 10
		byte restoDoModulo10 = (byte) new Modulo(TipoDeModulo.MODULO10).calcule(fields);
		// se não houver resto, primeiroDV = 0
		// caso contrário, primeiroDV = 10 - resto
		primeiroDV = (byte) ((restoDoModulo10 == 0) ? 0 : 10 - restoDoModulo10);
		return primeiroDV;
	}

}
