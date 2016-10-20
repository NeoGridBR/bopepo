package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoSantander;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoSantander extends AbstractBanco {

	public BancoSantander(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		return CLBancoSantander.newCampoLivre(titulo);
	}

}
