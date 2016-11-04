package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoDoNordesteDoBrasil;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoDoNordesteDoBrasil extends AbstractBanco {

	public BancoDoNordesteDoBrasil() {
		super("004","07237373000120", "BANCO DO NORDESTE DO BRASIL S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		return CLBancoDoNordesteDoBrasil.newCampoLivre(titulo);
	}

}
