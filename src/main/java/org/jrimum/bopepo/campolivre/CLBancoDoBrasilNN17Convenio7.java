package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;

/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 17 dígitos e convênio
 * de 7 posições deve seguir esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: *
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr>
 * <thead>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th> </thead>
 * </tr>
 * <tr>
 * <td>20-25</td>
 * <td>6</td>
 * <td>9(6)</td>
 * <td>Constante zeros = "000000"</td>
 * <td>Constante zeros = "000000"</td>
 * </tr>
 * <tr>
 * <td>26-42</td>
 * <td>17</td>
 * <td>9(17)</td>
 * <td>Nosso Número (sem dígito) composto pelo número do convênio fornecido pelo
 * Banco (CCCCCCC) e complemento do Nosso-Número, sem DV (NNNNNNNNNN)</td>
 * <td>Nosso Número (sem dígito) CCCCCCCNNNNNNNNNN</td>
 * </tr>
 * <tr>
 * <td>43-44</td>
 * <td>2</td>
 * <td>9(2)</td>
 * <td>Código da carteira</td>
 * <td>Tipo de Carteira/Modalidade de Cobrança</td>
 * </tr>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLBancoDoBrasilNN17Convenio7 {

	/**
	 * Constante em forma de campo {@link #CONSTANT_VALUE} e
	 * {@link #CONSTANT_LENGTH}, valor escrito: "000000".
	 */
	private static final FixedField<Integer> CONSTANT_FIELD = new FixedField<Integer>(0, 6, Fillers.ZERO_LEFT);

	/**
	 * <p>
	 * Cria um campo livre instanciando o número de fields ({@code
	 * FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkNossoNumeroTamanho(titulo, 17);
		TituloValidator.checkCarteiraCodigo(titulo, 1, 99);

		final CampoLivre campoLivre = new CampoLivre(3);
		campoLivre.add(CONSTANT_FIELD);
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 17);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getCarteira().getCodigo(), 2);
		return campoLivre;
	}

}
