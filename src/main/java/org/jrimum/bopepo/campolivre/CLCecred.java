package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.bopepo.parametro.ParametroCECRED;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * O campo livre do CECRED - Cooperativa Central de Crédito Urbano deve seguir
 * esta forma:
 * </p>
 * 
 * TODO tabela com campos
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLCecred {

	/**
	 * <p>
	 * Cria um campo livre instanciando o número de fields
	 * ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public static CampoLivre newCampoLivre(final Titulo titulo) {
		TituloValidator.checkParametroBancarioMenorOuIgualQue(titulo, ParametroCECRED.CODIGO_DO_CONVENIO, 999999);
		TituloValidator.checkNossoNumeroTamanho(titulo, 17);
		TituloValidator.checkCarteiraCodigo(titulo, 1, 99);

		final CampoLivre campoLivre = new CampoLivre(3);
		campoLivre.addIntegerZeroLeft(titulo.getParametrosBancarios().<Integer>getValor(ParametroCECRED.CODIGO_DO_CONVENIO), 6);
		campoLivre.addStringZeroLeft(titulo.getNossoNumero(), 17);
		campoLivre.addIntegerZeroLeft(titulo.getContaBancaria().getCarteira().getCodigo(), 2);
		return campoLivre;
	}

}
