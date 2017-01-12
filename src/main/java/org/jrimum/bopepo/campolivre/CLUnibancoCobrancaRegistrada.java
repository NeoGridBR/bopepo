package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras
 * (<em>cobrança</em>) com registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Direta (com registro)</font>
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
 * <td align="center"><font face="Arial">20 a 21</font></td>
 * <td><font face="Arial">Código da transação = 04</font></td>
 * 
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">22 a 27</font></td>
 * <td><font face="Arial">Data do Vencimento do Título (AAMMDD)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 31</font></td>
 * 
 * <td><font face="Arial">Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">32</font></td>
 * 
 * <td><font face="Arial">Dígito Verificador da Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">33 a 43</font></td>
 * <td><font face="Arial">Nosso Número</font></td>
 * </tr>
 * <tr>
 * 
 * <td align="center"><font face="Arial">44</font></td>
 * <td><font face="Arial">Super Digito do Nosso Número (*)</font></td>
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

public class CLUnibancoCobrancaRegistrada extends AbstractCLUnibanco {

	private static final String CODIGO_TRANSACAO = "04";

	/**
	 * <p>
	 * Dado um título, cria um campo livre para o padrão do Banco Unibanco que
	 * tenha o tipo de cobrança registrada.
	 * </p>
	 * 
	 * @param titulo
	 *            título com as informações para geração do campo livre
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkTituloDataDoVencimentoNotNull(titulo);
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkAgenciaDigito(titulo);
		TituloValidator.checkNossoNumero(titulo);

		final ContaBancaria conta = titulo.getContaBancaria();

		final CampoLivre campoLivre = new CampoLivre(6);
		campoLivre.add(CODIGO_TRANSACAO, 2);
		campoLivre.add(titulo.getDataDoVencimento(), 6);
		campoLivre.addZeroLeft(conta.getAgencia().getCodigo(), 4);
		Integer digitoDaAgencia = Integer.valueOf(conta.getAgencia().getDigitoVerificador());
		campoLivre.add(Integer.valueOf(digitoDaAgencia), 1);
		campoLivre.addZeroLeft(titulo.getNossoNumero(), 11);
		campoLivre.add(calculeSuperDigito(titulo.getNossoNumero()), 1);
		return campoLivre;
	}

	/**
	 * <p>
	 * Calcula o Super Dígito do Nosso Número.
	 * </p>
	 * 
	 * <p>
	 * Super dígito do “Nosso Número” [calculado com o MÓDULO 11 (de 2 a 9)]
	 * obtido utilizando-se os algarismos do “Nosso Número” acrescido do número
	 * 1 à esquerda = [1/NNNNNNNNNNN] e multiplicando-se a sequência obetem-se a
	 * soma dos produtos. Em seguida multiplicando-se novamente a soma por 10 e
	 * depois realizando-se o módulo 11.
	 * </p>
	 * 
	 * 
	 * @param nossoNumero
	 * 
	 * @return Dígito verficador calculado
	 * 
	 * @see #calculeDigitoEmModulo11(String)
	 * @see org.jrimum.vallia.digitoverificador.Modulo
	 * 
	 * @since 0.2
	 */
	private static String calculeSuperDigito(String nossoNumero) {
		return calculeDigitoEmModulo11("1" + nossoNumero);
	}

}
