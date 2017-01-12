package org.jrimum.bopepo.banco;

import org.apache.commons.lang.StringUtils;
import org.jrimum.bopepo.campolivre.CLBancoSofisa;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;

public class BancoSofisa extends AbstractBanco {

	public BancoSofisa() {
		super("637", "60889128000180", "Banco Sofisa S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkContaBancariaCodigo(titulo);
		TituloValidator.checkContaBancariaDigito(titulo);
		TituloValidator.checkAgenciaCodigoMenorOuIgualQue(titulo, 9999);
		TituloValidator.checkAgenciaDigito(titulo);
		TituloValidator.checkCarteiraTipoCobrancaNotNull(titulo);
		TituloValidator.checkNossoNumeroTamanho(titulo, 10);

		calculaDigitoDoNossoNumero(titulo);

		return CLBancoSofisa.newCampoLivre(titulo);
	}

	private void calculaDigitoDoNossoNumero(final Titulo titulo) {
		final StringBuilder value = new StringBuilder(17);
		value.append(StringUtils.leftPad(titulo.getContaBancaria().getAgencia().getCodigo().toString(), 4, '0'))
				.append(titulo.getContaBancaria().getCarteira().getCodigo().toString()).append(titulo.getNossoNumero());
		int soma = Modulo.calculeSomaSequencialMod10(value.toString(), 1, 2);
		int restoMod10 = (soma % 10);
		int digito = (restoMod10 == 0) ? 0 : 10 - restoMod10;
		titulo.setDigitoDoNossoNumero(digito + "");
	}

}
