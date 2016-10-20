package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoSantander.IOF_SEGURADORA;

import org.jrimum.bopepo.banco.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Exceptions;

/**
 * <p>
 * O campo livre do Banco Santander deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: *
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr>
 * <thead>
 * <th >Posição</th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th>Conteúdo</th> </thead>
 * </tr>
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >Fixo "9"</td>
 * </tr>
 * <tr>
 * <td >21-27</td>
 * <td >7</td>
 * <td >9(7)</td>
 * <td >Conta do cedente padrão Santander</td>
 * </tr>
 * <tr>
 * <td >28-40</td>
 * <td >13</td>
 * <td >9(13)</td>
 * <td >
 * <p>
 * Nosso Número com zeros a esquerda. <strong>OBS:</strong> Caso o arquivo de
 * registro para os títulos seja de 400 bytes (CNAB)
 * </p>
 * <ul>
 * <li>Banco 353 (Banco Santander) - Utilizar somente 08 posições do Nosso
 * Numero (07 posições + DV), zerando os 05 primeiros dígitos</li>
 * <li>Banco 008 (Meridional do Brasil S/A) - Utilizar somente 09 posições do
 * Nosso Numero (08 posições + DV), zerando os 04 primeiros dígitos</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td >41-41</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td >
 * <p>
 * IOF – Seguradoras (Se 7% informar 7. Limitado a 9%)
 * </p>
 * <p>
 * <strong>Demais clientes usar 0 (zero)</strong>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td >42-44</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td >
 * <ul>
 * <li>101-Cobrança Simples Rápida COM Registro</li>
 * <li>102- Cobrança simples – SEM Registro</li>
 * <li>201- Penhor Rápida com Registro</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class CLBancoSantander {

	/**
	 * 101- Cobrança Simples Rápida COM Registro
	 */
	private static final int CARTEIRA_RAPIDA_COM_REGISTRO = 101;

	/**
	 * 201- Penhor Rápida com Registro
	 */
	private static final int CARTEIRA_RAPIDA_SEM_REGISTRO = 201;

	/**
	 * 102- Cobrança simples – SEM Registro
	 */
	private static final int CARTEIRA_SIMPLES_SEM_REGISTRO = 102;

	public static CampoLivre newCampoLivre(final Titulo titulo) {
		final ContaBancaria contaBancaria = titulo.getContaBancaria();
		final String nossoNumeroComDigito = titulo.getNossoNumero() + titulo.getDigitoDoNossoNumero();

		final CampoLivre campoLivre = new CampoLivre(6);
		campoLivre.add(new FixedField<Integer>(9, 1));
		campoLivre.addIntegerZeroLeft(contaBancaria.getNumeroDaConta().getCodigoDaConta(), 6);
		campoLivre.add(new FixedField<String>(contaBancaria.getNumeroDaConta().getDigitoDaConta(), 1));
		campoLivre.addStringZeroLeft(nossoNumeroComDigito, 13);

		// IOF – Seguradoras
		if (titulo.hasParametrosBancarios() && (titulo.getParametrosBancarios().getValor(IOF_SEGURADORA) != null)) {
			campoLivre
					.add(new FixedField<Integer>(titulo.getParametrosBancarios().<Integer>getValor(IOF_SEGURADORA), 1));
		} else {
			campoLivre.add(new FixedField<Integer>(0, 1));
		}

		final int codigoCarteira = contaBancaria.getCarteira().getCodigo();
		switch (codigoCarteira) {
		case CARTEIRA_RAPIDA_COM_REGISTRO:
		case CARTEIRA_RAPIDA_SEM_REGISTRO:
		case CARTEIRA_SIMPLES_SEM_REGISTRO:
			campoLivre.addIntegerZeroLeft(codigoCarteira, 3);
			break;
		default:
			Exceptions.throwIllegalArgumentException(String.format("CARTEIRA [%s] NÃO SUPORTADA!", codigoCarteira));
		}
		return campoLivre;
	}

}
