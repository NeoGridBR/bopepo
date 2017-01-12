package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBanrisulCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CLBanrisulCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class Banrisul extends AbstractBanco {

	public Banrisul() {
		super("041", "92702067000196", "BANCO DO ESTADO DO RIO GRANDE DO SUL S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkCarteiraTipoCobrancaNotNull(titulo);
		TituloValidator.checkContaBancariaCodigoMenorOuIgualQue(titulo, 9999999);

		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkNossoNumeroTamanhoMenorOuIgualQue(titulo, 8);

		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case COM_REGISTRO:
			return CLBanrisulCobrancaRegistrada.newCampoLivre(titulo);
		case SEM_REGISTRO:
			return CLBanrisulCobrancaNaoRegistrada.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException("Campo livre diponível apenas para carteiras com ou sem cobrança.");
	}

}
