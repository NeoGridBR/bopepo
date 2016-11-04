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
 * Created at: 14/04/2011 - 20:16:07
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
 * Criado em: 14/04/2011 - 20:16:07
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Objects;

/**
 * <p>
 * O campo livre da Caixa para Cobrança Sem Registro SICOB - Nosso Número 16
 * posições, deve seguir esta forma:
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
 * <td >20-24</td>
 * <td >5</td>
 * <td >9(5)</td>
 * <td style="text-align:left;padding-left:10">Código do Cliente(sem dígito
 * verificador)</td>
 * <td style="text-align:left;padding-left:10">Código do Cliente Cedente
 * fornecido pela CAIXA</td>
 * </tr>
 * <tr>
 * <td >25-28</td>
 * <td >4</td>
 * <td >9(4)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência(sem dígito
 * verificador)</td>
 * <td style="text-align:left;padding-left:10">CNPJ da Agência da Conta do
 * Cliente Cedente</td>
 * </tr>
 * <tr>
 * <td >29-29</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td style="text-align:left;padding-left:10">Código da Carteira = 8</td>
 * <td style="text-align:left;padding-left:10">Código da Carteira = 8</td>
 * </tr>
 * <tr>
 * <td >30-30</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td style="text-align:left;padding-left:10">Constante = 7</td>
 * <td style="text-align:left;padding-left:10">Constante = 7</td>
 * </tr>
 * <tr>
 * <td >31-44</td>
 * <td >14</td>
 * <td >&nbsp;9(14)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número(sem dígito
 * verificador)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número do Cliente com 14
 * posições</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2.3
 */
public class CLCaixaEconomicaFederalSICOBNN14 {

	/**
	 * Constante "7".
	 */
	private static final FixedField<Integer> FIELD_7 = new FixedField<Integer>(7, 1);

	/**
	 * Valor constante do campo "Carteira" = 8 - Carteira Sem Registro
	 * Eletrônica.
	 */
	private static final Integer CARTEIRA_SEM_REGISTRO = Integer.valueOf(8);

	/**
	 * Código da carteira: sempre 8.
	 */
	private static final FixedField<Integer> CARTEIRA_FIELD = new FixedField<Integer>(CARTEIRA_SEM_REGISTRO, 1);

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 99999);
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkCarteiraCodigo(titulo, 1, 9);
		checkCarteiraSemRegistro(titulo);
		TituloValidator.checkNossoNumeroTamanho(titulo, 14);

		final CampoLivre campoLivre = new CampoLivre(5);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 5);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 4);
		campoLivre.add(CARTEIRA_FIELD);
		campoLivre.add(FIELD_7);
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 14);
		return campoLivre;
	}

	/**
	 * Verifica se o código da carteira da conta bancária do título é igual
	 * (carteira simples), caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * 
	 * <p>
	 * Motivo extraído do manual do banco: <br />
	 * <br />
	 * <i>Carteira Sem Registro Eletrônica com Nosso Número de 16 posições,
	 * sendo que 14 destas posições são para livre uso pelo Cedente. Apenas
	 * Clientes Cedentes com Código do Cedente na operação 870 podem operar com
	 * a Cobrança Sem Registro com 16 posições de Nosso Número.</i>
	 * </p>
	 * 
	 * @param titulo
	 */
	private static void checkCarteiraSemRegistro(Titulo titulo) {
		Objects.checkArgument(titulo.getContaBancaria().getCarteira().getCodigo().equals(CARTEIRA_SEM_REGISTRO),
				format("Apenas a carteira de código [8] \"Carteira Sem Registro Eletrônica\" é permitida e não o código [%s]!",
						titulo.getContaBancaria().getCarteira().getCodigo()));
	}

}
