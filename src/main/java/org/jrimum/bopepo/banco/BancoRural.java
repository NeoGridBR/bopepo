package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CLBancoRuralCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.CLBancoRuralCobrancaNaoRegistradaSeguradora;
import org.jrimum.bopepo.campolivre.CLBancoRuralCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoRural extends AbstractBanco {

	public BancoRural() {
		super("453", "58160789000128", "Banco Rural S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		final TipoDeCobranca tipoCobranca = titulo.getContaBancaria().getCarteira().getTipoCobranca();
		if (tipoCobranca == TipoDeCobranca.SEM_REGISTRO) {
			final int tamanhoNossoNumero = titulo.getNossoNumero().length();
			if (tamanhoNossoNumero == 10) {
				return CLBancoRuralCobrancaNaoRegistradaSeguradora.newCampoLivre(titulo);
			}
			if (tamanhoNossoNumero == 15) {
				return CLBancoRuralCobrancaNaoRegistrada.newCampoLivre(titulo);
			}
			throw new NotSupportedCampoLivreException(
					"Combrança sem registro com campo livre diponível somente para títulos com nosso número"
							+ " composto por 10 posições(apólice de seguro com I.O.S.) e 15 posições(padrão).");
		}
		if (tipoCobranca == TipoDeCobranca.COM_REGISTRO) {
			return CLBancoRuralCobrancaRegistrada.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException(
				"Campo livre diponível somente para títulos com carteiras com tipo de cobrança COM_REGISTRO ou SEM_REGISTRO.");

	}

}
