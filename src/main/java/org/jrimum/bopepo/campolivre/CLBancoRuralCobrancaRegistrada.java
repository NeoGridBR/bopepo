package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do Banco Rural, para cobrança registrada, deve seguir esta
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
 * <td style="text-align:left;padding-left:10px">Tipo de Cobrança - 0</td>
 * <td style="text-align:left;padding-left:10">Tipo de Cobrança - 0</td>
 * </tr>
 * <tr>
 * <td>21-23</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente</td>
 * </tr>
 * <tr>
 * <td>24-32</td>
 * <td>9</td>
 * <td>9(9)</td>
 * <td style="text-align:left;padding-left:10">Conta Corrente</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente</td>
 * </tr>
 * <tr>
 * <td>33-33</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td style="text-align:left;padding-left:10">Dígito da Conta Corrente</td>
 * <td style="text-align:left;padding-left:10">Dígito da Conta do Cedente</td>
 * </tr>
 * <tr>
 * <td>34-40</td>
 * <td>7</td>
 * <td>9(7)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número</td>
 * <td style="text-align:left;padding-left:10">Nosso Número</td>
 * </tr>
 * <tr>
 * <td>41-41</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td style="text-align:left;padding-left:10">Dígito do Nosso Número</td>
 * <td style="text-align:left;padding-left:10">Dígito do Nosso Número</td>
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
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class CLBancoRuralCobrancaRegistrada {

	/**
	 * Tipo de Cobrança = 0.
	 */
	private static final Integer TIPO_COBRANCA = Integer.valueOf(0);

	/**
	 * Constante
	 */
	private static final String ZEROS = "000";

	/**
	 * <p>
	 * Dado um título, cria um campo livre para cobrança registrada do Banco
	 * Rural.
	 * </p>
	 * 
	 * @param titulo
	 *            - título com as informações para geração do campo livre
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final CampoLivre campoLivre = new CampoLivre(7);
		campoLivre.add(TIPO_COBRANCA, 1);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 3);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 9);
		campoLivre.add(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), 1);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 7);
		campoLivre.add(titulo.getDigitoDoNossoNumero(), 1);
		campoLivre.add(ZEROS, 3);
		return campoLivre;
	}

}
