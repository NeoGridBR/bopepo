package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLMercantilDoBrasil;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoMercantilDoBrasil extends AbstractBanco {

	public BancoMercantilDoBrasil() {
		super("389", "17184037000110", "BANCO MERCANTIL DO BRASIL S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLMercantilDoBrasil.newCampoLivre(titulo);
	}

}
