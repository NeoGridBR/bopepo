package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoRural.CODIGO_REDUZIDO;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do Banco Rural, para cobrança não registrada, deve seguir esta
 * forma:
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
 * <td>20-20</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td style="text-align:left;padding-left:10">Tipo de Cobrança - 9</td>
 * <td style="text-align:left;padding-left:10">Tipo de Cobrança - 9</td>
 * </tr>
 * <tr>
 * <td>21-23</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente</td>
 * </tr>
 * <tr>
 * <td>24-26</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Código Reduzido do Cliente</td>
 * <td style="text-align:left;padding-left:10">O código reduzido deverá ser
 * solicitado ao gerente da agência.</td>
 * </tr>
 * <tr>
 * <td>27-41</td>
 * <td>15</td>
 * <td>9(15)</td>
 * <td style="text-align:left;padding-left:10">Seu número</td>
 * <td style="text-align:left;padding-left:10">Nosso número</td>
 * </tr>
 * <tr>
 * <td>42-44</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Constante "000"</td>
 * <td style="text-align:left;padding-left:10">Zeros</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class CLBancoRuralCobrancaNaoRegistrada {

	/**
	 * Tipo de Cobrança = 9.
	 */
	private static final Integer TIPO_COBRANCA = Integer.valueOf(9);

	/**
	 * Constante
	 */
	private static final String ZEROS = "000";

	/**
	 * <p>
	 * Dado um título, cria um campo livre para cobrança não registrada do Banco
	 * Rural.
	 * </p>
	 * 
	 * @param titulo
	 *            - Título com as informações para geração do campo livre
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final CampoLivre campoLivre = new CampoLivre(5);
		campoLivre.addInteger(TIPO_COBRANCA, 1);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 3);
		campoLivre.addIntegerZeroLeft(titulo.getParametrosBancarios().<Integer>getValor(CODIGO_REDUZIDO), 3);
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 15);
		campoLivre.addString(ZEROS, 3);
		return campoLivre;
	}

}
