package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLHSBCCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CLHSBCCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoHSBC extends AbstractBanco {

	public BancoHSBC(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		TituloValidator.checkCarteiraRegistroNotNull(titulo);

		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case COM_REGISTRO:
			return CLHSBCCobrancaRegistrada.newCampoLivre(titulo);
		case SEM_REGISTRO:
			return CLHSBCCobrancaNaoRegistrada.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException(
				"Campo livre diponível somente para títulos com carteiras com tipo de cobrança COM_REGISTRO ou SEM_REGISTRO.");
	}

}
