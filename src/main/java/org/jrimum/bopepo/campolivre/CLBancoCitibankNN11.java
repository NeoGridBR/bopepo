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
 * Created at: 30/03/2008 - 18:08:37
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a LicenÃ§a Apache, VersÃ£o 2.0 ("LICENÃ‡A"); vocÃª nÃ£o pode usar
 * esse arquivo exceto em conformidade com a esta LICENÃ‡A. VocÃª pode obter uma
 * cÃ³pia desta LICENÃ‡A em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigÃªncia legal ou acordo por escrito, a distribuiÃ§Ã£o de software sob
 * esta LICENÃ‡A se darÃ¡ â€œCOMO ESTÃ?â€?, SEM GARANTIAS OU CONDIÃ‡Ã•ES DE QUALQUER
 * TIPO, sejam expressas ou tÃ¡citas. Veja a LICENÃ‡A para a redaÃ§Ã£o especÃ­fica a
 * reger permissÃµes e limitaÃ§Ãµes sob esta LICENÃ‡A.
 * 
 * Criado em: 30/03/2008 - 18:08:37
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.apache.commons.lang.StringUtils;
import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.bopepo.parametro.ParametroCitibank;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Interface comum para todos os campos livres do CITIBANK
 * 
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo Justino</a>
 * @since 0.2
 * @version 0.2
 */

public class CLBancoCitibankNN11 {

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkContaBancariaCodigo(titulo);
		TituloValidator.checkNossoNumero(titulo);

		final ContaBancaria contaBancaria = titulo.getContaBancaria();
		final CampoLivre campoLivre = new CampoLivre(7);

		// Produto
		final Integer codigoProduto = titulo.getParametrosBancarios().getValor(ParametroCitibank.CODIGO_PRODUTO);
		campoLivre.addInteger(codigoProduto, 1);
		// Portifólio
		campoLivre.addIntegerZeroLeft(contaBancaria.getCarteira().getCodigo(), 3);

		final String numeroConta = StringUtils.leftPad(contaBancaria.getNumeroDaConta().getCodigoDaConta().toString(), 9, '0');
		// Base
		campoLivre.addString(StringUtils.substring(numeroConta, 1, 7), 6);
		// Sequencia
		campoLivre.addString(StringUtils.right(numeroConta, 2), 2);
		// Dígito
		campoLivre.addString(contaBancaria.getNumeroDaConta().getDigitoDaConta(), 1);
		// Nosso Número
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 11);
		// Dígito Nosso Número
		campoLivre.addString(titulo.getDigitoDoNossoNumero(), 1);

		return campoLivre;
	}

}
