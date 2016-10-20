package org.jrimum.bopepo.banco;

import org.jrimum.texgit.type.component.BlockOfFields;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;

public class CampoLivre extends BlockOfFields implements org.jrimum.bopepo.campolivre.CampoLivre {
	private static final long serialVersionUID = 1L;

	public CampoLivre(final Integer size) {
		super();
		setLength(CampoLivre.STRING_LENGTH);
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

}
