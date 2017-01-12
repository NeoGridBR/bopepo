package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre da Caixa Econômica Federal para cobrança simples (CS), rápida(CR) e sem registro (SR)
 * - SICOB, deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="campolivre">
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
 * <td>20-29</td>
 * <td>10</td>
 * <td>9(10)</td>
 * <td style="text-align:left;padding-left:10px">Nosso Número</td>
 * <td style="text-align:left;padding-left:10">
 * Nosso Número no padrão de uma das
 * três cobranças (simples, rápida ou sem registro)
 * 
 * <p>Exemplos:<br/>
 * Cobrança Simples - Nosso número inicia com 3. Ex: Carteira 11<br/>
 * Cobrança Rápida - Nosso número inicia com 9. Ex: Carteira 12 <br/>
 * Cobrança Sem Registro - Nosso número inicia com 80, 81 ou 82. Ex: Carteira 14.
 * </p>
 * 
 * </td>
 * </tr>
 * <tr>
 * <td>30-33</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência</td>
 * <td style="text-align:left;padding-left:10">Código da Agência Cedente</td>
 * </tr>
 * <tr>
 * <td>34-36</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Código da Operação</td>
 * <td style="text-align:left;padding-left:10">Operação Código</td>
 * </tr>
 * <tr>
 * <td>37-44</td>
 * <td>8</td>
 * <td>9(8)</td>
 * <td style="text-align:left;padding-left:10">Código do número da conta</td>
 * <td style="text-align:left;padding-left:10">Código fornecido pela Agência</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>

 * @see <a href="http://www1.caixa.gov.br/download/asp/download.asp?subCategId=198&CategId=65&subCateglayout=Manuais&Categlayout=Cobran%C3%A7a%20Caixa%20%E2%80%93%20SICOB#aba_voce">Manuais SICOB - Caixa</a>
 * @see <a href="http://downloads.caixa.gov.br/_arquivos/cobrcaixasicob/manuaissicob/ESPCODBARR_SICOB.pdf">Especificação código barras com nosso número de 11 posições</a>
 * @see <a href="http://downloads.caixa.gov.br/_arquivos/cobrcaixasicob/manuaissicob/ESPCODBARBLOQCOBRANREGIST_16POSICOES.pdf">Leiaute de Arquivo Eletrônico Padrão CNAB 240 - Cobrança Bancária CAIXA - SICOB</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLCaixaEconomicaFederalSICOBNN10 {

	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Caixa Econômica
	 *   Federal que tenha o serviço SINCO.
	 * </p>
	 * 
	 * @param titulo - Título com as informações para geração do campo livre
	 */

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkParametroBancarioNotNull(titulo, ParametroCaixaEconomicaFederal.CODIGO_OPERACAO);

		final String nossoNumero = titulo.getNossoNumero();
		checkNossoNumeroSICOB(nossoNumero);

		final CampoLivre campoLivre = new CampoLivre(4);
		campoLivre.add(nossoNumero, 10);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getAgencia().getCodigo(), 4);
		final Integer codigoOperacao = titulo.getParametrosBancarios()
				.getValor(ParametroCaixaEconomicaFederal.CODIGO_OPERACAO);
		campoLivre.addZeroLeft(codigoOperacao, 3);
		campoLivre.addZeroLeft(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 8);
		return campoLivre;
	}

	/**
	 * <p>
	 * Verifica se o nosso número do título começa com 3 (identificador da
	 * Carteira Simples), 9 (identificador da Carteira Rápida) ou 80, 81 ou 82
	 * (que são identificadores da Carteira Sem Registro); Caso contrário gera
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * <p>
	 * Padrões aceitos de 10 dígitos:
	 * <ul>
	 * <li>3NNNNNNNNN (a ser validado com os colegas da Neogrid)</li>
	 * <li>9NNNNNNNNN</li>
	 * <li>80NNNNNNNN</li>
	 * <li>81NNNNNNNN</li>
	 * <li>82NNNNNNNN</li>
	 * </ul>
	 * </p>
	 * 
	 * @param nn
	 *            Nosso Número
	 */
	private static void checkNossoNumeroSICOB(final String nossoNumero) {
		if (!nossoNumero.startsWith("3") && !nossoNumero.startsWith("9") && !nossoNumero.startsWith("80")
				&& !nossoNumero.startsWith("81") && !nossoNumero.startsWith("82")) {
			throw new IllegalArgumentException(
					format("Para a cobrança SICOB o nosso número [%s] deve começar com 3 que é o identificador da \"carteira siples\" [3NNNNNNNNN] ou 9 que é o identificador da \"carteira rápida\" [9NNNNNNNNN] ou 80, 81 e 82 para \"carteira sem registro\" [82NNNNNNNN]!",
							nossoNumero));
		}
	}

}
