package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras
 * (<em>cobrança</em>) sem registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Especial (sem registro)</font>
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
 * <td align="center"><font face="Arial">20</font></td>
 * 
 * <td><font face="Arial">Código da transação = 5</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">21 a 26</font></td>
 * <td><font face="Arial">Número do Cliente (Espécie de conta)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">27</font></td>
 * <td><font face="Arial">Dígito Verificador do Número do Cliente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 29</font></td>
 * <td><font face="Arial">zeros</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">30 a 43</font></td>
 * <td><font face="Arial">Referência do Cliente (Nosso Número Gerado Pelo
 * Cliente)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">44</font></td>
 * <td><font face="Arial">Dígito Verificador da Referência do
 * Cliente</font></td>
 * </tr>
 * </table>
 * </div>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class CLUnibancoCobrancaNaoRegistrada extends AbstractCLUnibanco {

	private static final Integer CODIGO_TRANSACAO = 5;

	private static final Integer RESERVADO = 0;

	/**
	 * <p>
	 * Dado um título, cria um campo livre para o padrão do Banco Unibanco que
	 * tenha o tipo de cobrança não registrada.
	 * </p>
	 * 
	 * @param titulo
	 *            título com as informações para geração do campo livre
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final ContaBancaria conta = titulo.getContaBancaria();

		TituloValidator.checkContaBancariaCodigo(titulo);
		TituloValidator.checkContaBancariaDigito(titulo);
		TituloValidator.checkNossoNumero(titulo);

		final CampoLivre campoLivre = new CampoLivre(6);
		campoLivre.addInteger(CODIGO_TRANSACAO, 1);
		campoLivre.addIntegerZeroLeft(conta.getNumeroDaConta().getCodigoDaConta(), 6);
		Integer digitoDaConta = Integer.valueOf(conta.getNumeroDaConta().getDigitoDaConta());
		campoLivre.addInteger(Integer.valueOf(digitoDaConta), 1);
		campoLivre.addIntegerZeroLeft(RESERVADO, 2);
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 14);
		campoLivre.addString(calculeDigitoEmModulo11(titulo.getNossoNumero()), 1);
		return campoLivre;
	}
}
