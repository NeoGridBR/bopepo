package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoSafraCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CLBancoSafraCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoSafra extends AbstractBanco {

	public BancoSafra() {
		super("422", "58160789000128", "BANCO SAFRA S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkCarteiraRegistroNotNull(titulo);

		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case COM_REGISTRO:
			return CLBancoSafraCobrancaRegistrada.newCampoLivre(titulo);
		case SEM_REGISTRO:
			return CLBancoSafraCobrancaNaoRegistrada.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException(
				"Campo livre diponível somente para títulos com carteiras com tipo de cobrança COM_REGISTRO ou SEM_REGISTRO.");
	}

}
