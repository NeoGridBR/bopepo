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

import static org.jrimum.bopepo.parametro.ParametroCitibank.CODIGO_PRODUTO;

import org.apache.commons.lang.StringUtils;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;

/**
 * Interface comum para todos os campos livres do CITIBANK
 * 
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo Justino</a>
 * @since 0.2
 * @version 0.2
 */

class CLBancoCitiBankNN11 extends AbstractCLBancoDoBrasil {
	private static final long serialVersionUID = -7675528811239346517L;
	private static final Integer FIELDS_LENGTH = Integer.valueOf(7);

	CLBancoCitiBankNN11(final Titulo titulo) {
		super(FIELDS_LENGTH);
		final ContaBancaria conta = titulo.getContaBancaria();
		// Produto
		final Integer codigoProduto = titulo.getParametrosBancarios().getValor(CODIGO_PRODUTO);
		add(new FixedField<Integer>(codigoProduto, 1));
		// Portifólio
		final Integer portfolio = conta.getCarteira().getCodigo();
		add(new FixedField<Integer>(portfolio, 3, Fillers.ZERO_LEFT));

		final String numeroConta = StringUtils.leftPad(conta.getNumeroDaConta().getCodigoDaConta().toString(), 9, '0');
		// Base
		final String base = StringUtils.substring(numeroConta, 1, 7);
		add(new FixedField<String>(base, 6));
		// Sequencia
		final String sequencia = StringUtils.right(numeroConta, 2);
		add(new FixedField<String>(sequencia, 2));
		// Dígito
		final String digitoConta = conta.getNumeroDaConta().getDigitoDaConta();
		add(new FixedField<String>(digitoConta, 1));
		// Nosso Número
		final String nossoNumero = titulo.getNossoNumero();
		add(new FixedField<String>(nossoNumero, 11, Fillers.ZERO_LEFT));
		// Nosso Número
		final String digitoNossoNumero = titulo.getDigitoDoNossoNumero();
		add(new FixedField<String>(digitoNossoNumero, 1));
	}

	protected void addFields(Titulo titulo) {
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	protected void checkValues(Titulo titulo) {
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

}
