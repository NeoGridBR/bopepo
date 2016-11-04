package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLCecred;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoCecred extends AbstractBanco {

	public BancoCecred() {
		super("085", "05.463.212/0001-29", "COOPERATIVA CENTRAL DE CRÉDITO URBANO - CECRED", Segmento.COOPERATIVA_DE_CREDITO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLCecred.newCampoLivre(titulo);
	}

}
