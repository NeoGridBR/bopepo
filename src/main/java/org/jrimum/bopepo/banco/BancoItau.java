package org.jrimum.bopepo.banco;

import java.util.HashSet;
import java.util.Set;

import org.jrimum.bopepo.campolivre.CLItauComCarteirasEspeciais;
import org.jrimum.bopepo.campolivre.CLItauPadrao;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class BancoItau extends AbstractBanco {

	/**
	 * Carteiras "exceção".
	 */
	private static final Set<Integer> CARTEIRAS_ESCRITURAIS = new HashSet<Integer>(8);
	private static final Set<Integer> CARTEIRAS_MODALIDADE_DIRETA = new HashSet<Integer>(5);

	/**
	 * <p>
	 * Carteiras especiais sem registro na qual são utilizadas 15 posições
	 * numéricas para identificação do título liquidado (8 do Nosso Número e 7
	 * do Seu Número).
	 * </p>
	 */
	private static final Set<Integer> CARTEIRAS_ESPECIAIS = new HashSet<Integer>(8);

	static {
		CARTEIRAS_ESCRITURAIS.add(104);
		CARTEIRAS_ESCRITURAIS.add(105);
		CARTEIRAS_ESCRITURAIS.add(112);
		CARTEIRAS_ESCRITURAIS.add(113);
		CARTEIRAS_ESCRITURAIS.add(114);
		CARTEIRAS_ESCRITURAIS.add(147);
		CARTEIRAS_ESCRITURAIS.add(166);
		CARTEIRAS_ESCRITURAIS.add(212);

		CARTEIRAS_MODALIDADE_DIRETA.add(126);
		CARTEIRAS_MODALIDADE_DIRETA.add(131);
		CARTEIRAS_MODALIDADE_DIRETA.add(146);
		CARTEIRAS_MODALIDADE_DIRETA.add(150);
		CARTEIRAS_MODALIDADE_DIRETA.add(168);

		CARTEIRAS_ESPECIAIS.add(106);
		CARTEIRAS_ESPECIAIS.add(107);
		CARTEIRAS_ESPECIAIS.add(122);
		CARTEIRAS_ESPECIAIS.add(142);
		CARTEIRAS_ESPECIAIS.add(143);
		CARTEIRAS_ESPECIAIS.add(195);
		CARTEIRAS_ESPECIAIS.add(196);
		CARTEIRAS_ESPECIAIS.add(198);
	}

	public BancoItau() {
		super("341", "60701190000104", "BANCO ITAÚ S.A.", Segmento.BANCO_MULTIPLO);
	}

	@Override
	public CampoLivre createCampoLivre(final Titulo titulo) throws NotSupportedCampoLivreException {
		TituloValidator.checkCarteiraCodigo(titulo);
		/*
		 * Se a carteira for especial, a forma de construir o campo livre será
		 * diferente.
		 */
		if (CARTEIRAS_ESPECIAIS.contains(titulo.getContaBancaria().getCarteira().getCodigo())) {
			return CLItauComCarteirasEspeciais.newCampoLivre(titulo);
		} else {
			return CLItauPadrao.newCampoLivre(titulo);
		}

	}

}
