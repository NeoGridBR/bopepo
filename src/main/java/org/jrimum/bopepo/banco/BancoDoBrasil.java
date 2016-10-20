package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoDoBrasilNN10;
import org.jrimum.bopepo.campolivre.CLBancoDoBrasilNN11;
import org.jrimum.bopepo.campolivre.CLBancoDoBrasilNN17Convenio6;
import org.jrimum.bopepo.campolivre.CLBancoDoBrasilNN17Convenio7;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoDoBrasil extends AbstractBanco {

	public BancoDoBrasil(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		TituloValidator.checkNossoNumero(titulo);
		TituloValidator.checkContaBancariaCodigo(titulo);

		switch (titulo.getNossoNumero().length()) {
		case 10:
			return CLBancoDoBrasilNN10.newCampoLivre(titulo);
		case 11:
			return CLBancoDoBrasilNN11.newCampoLivre(titulo);
		case 17:
			if (titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() < 1000000) {
				return CLBancoDoBrasilNN17Convenio6.newCampoLivre(titulo);
			} else {
				return CLBancoDoBrasilNN17Convenio7.newCampoLivre(titulo);
			}
		}
		throw new NotSupportedCampoLivreException("Campo livre diponível somente para títulos com nosso número "
				+ "composto por 10 posições(convênio com 7), 11 posições ou 17 posições(convênio com 6).");
	}

}
