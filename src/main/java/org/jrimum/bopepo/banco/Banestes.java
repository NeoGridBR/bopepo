package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBanestes;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class Banestes extends AbstractBanco {

	public Banestes() {
		super("021", "28127603000178", "BANCO DO ESTADO DO ESPIRITO SANTO S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBanestes.newCampoLivre(titulo);
	}

}
