package org.jrimum.bopepo.campolivre;

import org.junit.Test;

/**
 * Classe base para os testes de campos livres do Banco Sofisa, contendo métodos
 * básicos de testes comuns
 * .
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public abstract class AbstractCLBancoSofisaBaseTest extends AbstractCampoLivreBaseTest {

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraNull() {
		testeSeNaoPermiteCarteiraNula();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraSemTipoDeCobranca() {
		testeSeNaoPermiteCarteiraSemTipoDeCobranca();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {
		testeSeNaoPermiteCarteiraComCodigoNegativo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoAcimaDe2Digitos() {
		testeSeNaoPermiteCarteiraComCodigoAcimaDoLimite(111);
	}
}
