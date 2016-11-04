package org.jrimum.bopepo.banco;

import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.comum.pessoa.id.cprf.CNPJ;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public abstract class AbstractBanco {

	private final Banco instituicao;

	public AbstractBanco(final String codigoDeCompensacaoBACEN, final String cnpj, final String razaoSocial, final Segmento segmento) {
		this.instituicao = new Banco(new CodigoDeCompensacaoBACEN(codigoDeCompensacaoBACEN), razaoSocial, new CNPJ(cnpj), segmento.getDescricao());
	}

	public Banco getInstituicao() {
		return instituicao;
	}

	public CampoLivre getCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkBancoNotNull(titulo);
		TituloValidator.checkTituloValor(titulo);
		return createCampoLivre(titulo);
	}
	
	protected abstract CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException;

}
