package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLSicredi;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoSicredi extends AbstractBanco {

	public BancoSicredi() {
		super("748", "01181521000155", "Banco Cooperativo Sicredi S.A.", Segmento.BANCO_MULTIPLO_COOPERATIVO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLSicredi.newCampoLivre(titulo);
	}

}
