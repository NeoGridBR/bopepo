package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoIntermedium;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoIntermedium extends AbstractBanco {

	public BancoIntermedium() {
		super("077", "00416968000101", "BANCO INTERMEDIUM S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoIntermedium.newCampoLivre(titulo);
	}

}
