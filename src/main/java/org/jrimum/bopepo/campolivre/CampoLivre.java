package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CampoLivre implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final char ZERO_CHAR = '0';

	/**
	 * Tamanho do Campo Livre, igual para qualquer que seja o banco.
	 */
	public static final int STRING_LENGTH = 25;

	private static final int DEFAULT_FRACTION_DIGITS = 2;
	private static final String DEFAULT_DATE_PATTERN = "yyMMdd";

	private final int length;
	private final List<String> blocks;
	private int currentSize = 0;

	public CampoLivre(final int length, final int size) {
		this.length = length;
		this.blocks = new ArrayList<>(size);
	}

	public CampoLivre(final int size) {
		this(STRING_LENGTH, size);
	}

	private void add(final String value) {
		blocks.add(value);
		currentSize++;
	}

	public void add(final String value, final int length) {
		this.add(value);
	}

	public void add(final Integer value, final int length) {
		this.add(value.toString(), length);
	}

	public void add(final Date value, final int length) {
		this.add(value, DEFAULT_DATE_PATTERN, length);
	}

	public void add(final Date value, final String pattern, final int length) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		this.add(dateFormat.format(value), length);
	}

	public void addZeroLeft(final String value, final int length) {
		this.add(StringUtils.leftPad(value, length, ZERO_CHAR));
	}

	public void addZeroLeft(final Integer value, final int length) {
		this.addZeroLeft(value.toString(), length);
	}

	public void addZeroLeft(final BigDecimal value, final int length) {
		this.addZeroLeft(value, DEFAULT_FRACTION_DIGITS, length);
	}

	public void addZeroLeft(final BigDecimal value, final int fractionDigits, final int length) {
		this.addZeroLeft(value.movePointRight(fractionDigits).toString(), length);
	}

	public void addZero(final int length) {
		this.add(StringUtils.repeat("0", length));
	}

	/**
	 * <p>
	 * Retorna o valor do campo livre a partir das informações armazenadas sem
	 * verificar se está compatível com número de fields declarado pelo campo
	 * livre. Isso implica que a string retornada poderá ser menor do que 25
	 * caracteres.
	 * </p>
	 * 
	 * @return string a partir dos campos contidos até o momento.
	 * 
	 * @since 0.2
	 */
	public final String getValue() {
		final StringBuilder result = new StringBuilder();
		for (final String part : this.blocks) {
			result.append(part);
		}
		return result.toString();
	}

	public final String write() {
		final String result = this.getValue();
		if(currentSize != this.blocks.size()) {
			throw new IllegalStateException(format("O número de elementos do Campo Livre '[%s]' é diferente do especificado [%s]!", currentSize, this.blocks.size()));
		}
		final int fieldLength = StringUtils.length(result);
		if(fieldLength != this.length) {
			throw new IllegalStateException(format("O comprimento do Campo Livre '[%s]' é diferente do especificado [%s]!", fieldLength, this.length));
		}
		return result;
	}
}
