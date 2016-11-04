package org.jrimum.bopepo.campolivre;

import org.jrimum.vallia.digitoverificador.Modulo;

abstract class AbstractCLUnibanco {

	/**
	 * <p>
	 * Calcula o dígito verificador para <em>referência do cliente (cobrança sem
	 * registro)</em> e base para cálculo do <em>super dígito do nosso numero
	 * (cobrança com registro)</em>.
	 * </p>
	 * 
	 * @param numero
	 * @return String dígito
	 * 
	 * @since 0.2
	 */
	protected static String calculeDigitoEmModulo11(String numero) {
		String dv = "";
		int soma = Modulo.calculeSomaSequencialMod11(numero, 2, 9);
		soma *= 10;
		final int resto = soma % 11;
		if (resto == 10 || resto == 0)
			dv = "0";
		else
			dv = "" + resto;
		return dv;
	}
}
