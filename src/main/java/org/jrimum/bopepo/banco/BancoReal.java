package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoReal;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoReal extends AbstractBanco {

	public BancoReal() {
		super("356", "33066408000115", "BANCO ABN AMRO REAL S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoReal.newCampoLivre(titulo);
	}

}
