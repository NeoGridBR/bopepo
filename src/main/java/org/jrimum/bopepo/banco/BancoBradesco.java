package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBradesco;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoBradesco extends AbstractBanco {

	public BancoBradesco() {
		super("237", "60746948000112", "BANCO BRADESCO S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBradesco.newCampoLivre(titulo);
	}

}
