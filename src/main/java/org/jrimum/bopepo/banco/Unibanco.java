package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLUnibancoCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CLUnibancoCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class Unibanco extends AbstractBanco {

	public Unibanco() {
		super("409", "33700394000140", "UNIBANCO-UNIAO DE BANCOS BRASILEIROS S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkCarteiraTipoCobrancaNotNull(titulo);
		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case SEM_REGISTRO:
			return CLUnibancoCobrancaNaoRegistrada.newCampoLivre(titulo);
		case COM_REGISTRO:
			return CLUnibancoCobrancaRegistrada.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException(
				"Campo livre diponível somente para títulos com carteiras com tipo de cobrança COM_REGISTRO ou SEM_REGISTRO.");
	}

}
