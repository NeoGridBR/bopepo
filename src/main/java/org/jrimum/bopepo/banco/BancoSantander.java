package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoSantander;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoSantander extends AbstractBanco {

	public BancoSantander() {
		super("033", "90400888000142", "BANCO SANTANDER (BRASIL) S. A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoSantander.newCampoLivre(titulo);
	}

}
