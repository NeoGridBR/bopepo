package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBradesco;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoBradesco extends AbstractBanco {

	public BancoBradesco(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		return CLBradesco.newCampoLivre(titulo);
	}

}
