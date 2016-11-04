package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoDeBrasilia;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoDeBrasilia extends AbstractBanco {

	public BancoDeBrasilia() {
		super("070", "00000208000100", "BRB- Banco de Brasília S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoDeBrasilia.newCampoLivre(titulo);
	}

}
