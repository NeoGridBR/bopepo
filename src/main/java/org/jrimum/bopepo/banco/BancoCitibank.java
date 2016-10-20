package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoCitibankNN11;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoCitibank extends AbstractBanco {

	public BancoCitibank(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		TituloValidator.checkNossoNumero(titulo);

		switch(titulo.getNossoNumero().length()) {
		case 11:
			return CLBancoCitibankNN11.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException("Campo livre disponível somente para títulos com nosso número composto por 10 posições(convênio com 7), 11 posições ou 17 posições(convênio com 6).");
	}

}
