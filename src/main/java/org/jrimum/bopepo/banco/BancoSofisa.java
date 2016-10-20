package org.jrimum.bopepo.banco;

import org.apache.commons.lang.StringUtils;
import org.jrimum.bopepo.campolivre.CLBancoSofisa;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;

public class BancoSofisa extends AbstractBanco {

	public BancoSofisa(final Titulo titulo) {
		super(titulo);
	}

	@Override
	public CampoLivre getCampoLivre() throws NotSupportedCampoLivreException {
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkAgenciaDigito(titulo);
		TituloValidator.checkCarteiraRegistroNotNull(titulo);
		TituloValidator.checkNossoNumeroTamanho(titulo, 10);

		calculaDigitoDoNossoNumero();

		return CLBancoSofisa.newCampoLivre(titulo);
	}

	private void calculaDigitoDoNossoNumero() {
		final StringBuilder value = new StringBuilder(17);
		value.append(StringUtils.leftPad(titulo.getContaBancaria().getAgencia().getCodigo().toString(), 4, '0'))
				.append(titulo.getContaBancaria().getCarteira().getCodigo().toString()).append(titulo.getNossoNumero());
		int soma = Modulo.calculeSomaSequencialMod10(value.toString(), 1, 2);
		int digito = 10 - (soma % 10);
		titulo.setDigitoDoNossoNumero(digito + "");
	}

}
