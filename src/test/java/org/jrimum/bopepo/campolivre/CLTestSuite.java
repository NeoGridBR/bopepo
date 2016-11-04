package org.jrimum.bopepo.campolivre;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestCLBancoDoBrasilNN10.class,
	TestCLBancoDoBrasilNN11.class,
	TestCLBancoDoBrasilNN17Convenio6.class,
	TestCLBancoDoBrasilNN17Convenio7.class,
	//BancoDoNordesteDoBrasil
	TestCLBanestes.class,
	TestCLBancoSantander.class,
	TestCLBanrisulCobrancaNaoRegistrada.class,
	TestCLBanrisulCobrancaRegistrada.class,
	TestCLBancoDeBrasilia.class,
	TestCLBancoIntermedium.class,
	TestCLCecred.class,
	TestCLCaixaEconomicaFederalSICOBNossoNumero14.class,
	TestCLCaixaEconomicaFederalSIGCB.class,
	TestCLCaixaEconomicaFederalSINCO.class,
	TestCLBradesco.class,
	TestCLItauComCarteirasEspeciais.class,
	TestCLItauPadrao.class,
	TestCLBancoReal.class,
	TestCLMercantilDoBrasil.class,
	TestCLHSBCCobrancaNaoRegistrada.class,
	TestCLHSBCCobrancaRegistrada.class,
	TestCLUnibancoCobrancaNaoRegistrada.class,
	TestCLUnibancoCobrancaRegistrada.class,
	TestCLBancoSafraCobrancaNaoRegistrada.class,
	TestCLBancoSafraCobrancaRegistrada.class,
	TestCLBancoSofisaCobrancaRegistrada.class,
	//Citibank
	TestCLSicredi.class,
	TestCLBancoRuralCobrancaNaoRegistrada.class,
	TestCLBancoRuralCobrancaNaoRegistradaSeguradora.class,
	TestCLBancoRuralCobrancaRegistrada.class,
	TestCLBancoobCobrancaNaoRegistrada.class
})
public class CLTestSuite {

}
