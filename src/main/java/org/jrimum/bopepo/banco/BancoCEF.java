package org.jrimum.bopepo.banco;

import static java.lang.String.format;

import org.jrimum.bopepo.campolivre.CLCaixaEconomicaFederalSICOBNN10;
import org.jrimum.bopepo.campolivre.CLCaixaEconomicaFederalSICOBNN14;
import org.jrimum.bopepo.campolivre.CLCaixaEconomicaFederalSIGCB;
import org.jrimum.bopepo.campolivre.CLCaixaEconomicaFederalSINCO;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoCEF extends AbstractBanco {

	public BancoCEF() {
		super("104", "00360305000104", "CAIXA ECONOMICA FEDERAL", Segmento.CAIXA_ECONOMICA_FEDERAL);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkNossoNumero(titulo);

		switch (titulo.getNossoNumero().length()) {
		case 10:
			return CLCaixaEconomicaFederalSICOBNN10.newCampoLivre(titulo);
		case 14:
			return CLCaixaEconomicaFederalSICOBNN14.newCampoLivre(titulo);
		case 15:
			return CLCaixaEconomicaFederalSIGCB.newCampoLivre(titulo);
		case 17:
			return CLCaixaEconomicaFederalSINCO.newCampoLivre(titulo);
		}
		throw new NotSupportedCampoLivreException(format(
				"Campo Livre não suportado para o Nosso Número [%s] de tamanho [%s]."
						+ " Apenas títulos com Nosso Número de tamanho [%s] são suportados (SICOB, SIGCB e SINCO respectivamente).",
				titulo.getNossoNumero(), titulo.getNossoNumero().length(), 10 + "," + 14 + "," + 15 + "," + 17));
	}

}
