package org.jrimum.bopepo.campolivre;

import org.jrimum.texgit.type.component.BlockOfFields;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;

public class CampoLivre extends BlockOfFields {
	private static final long serialVersionUID = 1L;

	/**
	 * Tamanho do Campo Livre, igual para qualquer que seja o banco.
	 */
	public static final Integer STRING_LENGTH = 25;

	public CampoLivre(final Integer size) {
		super();
		setLength(STRING_LENGTH);
		setSize(size);
	}

	public void addString(final String value, final int length) {
		super.add(new FixedField<String>(value, length));
	}

	public void addStringZeroLeft(final String value, final int length) {
		super.add(new FixedField<String>(value, length, Fillers.ZERO_LEFT));
	}

	public void addInteger(final Integer value, final int length) {
		super.add(new FixedField<Integer>(value, length));
	}

	public void addIntegerZeroLeft(final Integer value, final int length) {
		super.add(new FixedField<Integer>(value, length, Fillers.ZERO_LEFT));
	}

	/**
	 * <p>
	 * Retorna o valor do campo livre a partir das informações armazenadas sem verificar se está
	 * compatível com número de fields declarado pelo campo livre. Isso implica
	 * que a string retornada poderá ser menor do que 25 caracteres.
	 * </p>
	 * 
	 * @return string a partir dos campos contidos até o momento.
	 * 
	 * @since 0.2
	 */
	public final String getValue() {
		final StringBuilder value = new StringBuilder();
		for(FixedField<?> f : this){
			value.append(f.write());
		}
		return value.toString();
	}

}
