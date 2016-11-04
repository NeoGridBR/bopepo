package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoobCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class Bancoob extends AbstractBanco {

	public Bancoob() {
		super("756", "02038232000164", "Banco Cooperativo do Brasil S.A. - Bancoob", Segmento.BANCO_MULTIPLO_COOPERATIVO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoobCobrancaNaoRegistrada.newCampoLivre(titulo);
	}

}
