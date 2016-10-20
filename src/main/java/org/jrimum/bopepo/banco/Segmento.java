package org.jrimum.bopepo.banco;

public enum Segmento {

	BANCO_DO_BRASIL_BANCO_MULTIPLO("Banco do Brasil - Banco Múltiplo"),
	BANCO_MULTIPLO("Banco Múltiplo"),
	BANCO_COMERCIAL("Banco Comercial"),
	BANCO_MULTIPLO_COOPERATIVO("Banco Múltiplo Cooperativo"),
	BANCO_DE_CAMBIO("Banco de Câmbio"),
	BANCO_COMERCIAL_ESTRANGEIRO("Banco Comercial Estrangeiro"),
	CAIXA_ECONOMICA_FEDERAL("Caixa Econômica Federal"),
	COOPERATIVA_DE_CREDITO("Cooperativa de Crédito");

	private final String descricao;

	private Segmento(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
