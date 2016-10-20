package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public abstract class AbstractBanco {

	protected final Titulo titulo;

	public AbstractBanco(final Titulo titulo) {
		this.titulo = titulo;
		TituloValidator.checkBancoNotNull(titulo);
		TituloValidator.checkTituloValor(titulo);
	}

	public abstract CampoLivre getCampoLivre() throws NotSupportedCampoLivreException;

}
