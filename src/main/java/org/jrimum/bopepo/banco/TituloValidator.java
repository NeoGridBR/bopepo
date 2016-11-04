package org.jrimum.bopepo.banco;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static org.jrimum.domkee.financeiro.banco.febraban.Banco.isCodigoDeCompensacaoOK;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jrimum.domkee.financeiro.banco.ParametroBancario;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class TituloValidator {

	/**
	 * <p>
	 * Verifica se o título não é nulo, senão lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void checkTituloNotNull(final Titulo titulo) {
		Validate.notNull(titulo, "Título não pode ser nulo!");
	}

	/**
	 * <p>
	 * Verifica se o valor do título não é nulo e é positivo, caso contrário
	 * lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkTituloValor(final Titulo titulo) {
		checkTituloNotNull(titulo);
		Validate.notNull(titulo.getValor(), "Valor do título não pode ser nulo!");
		Validate.isTrue(titulo.getValor().compareTo(ZERO) >= 0,
				format("O valor do título deve ser um número positivo ou zero e não [%s].", titulo.getValor()));
	}

	/**
	 * <p>
	 * Verifica se a data do vencimento do título não é nula, senão
	 * lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkTituloDataDoVencimentoNotNull(final Titulo titulo) {
		checkTituloNotNull(titulo);
		Validate.notNull(titulo.getDataDoVencimento(),
				"Data do Vendimento do título não pode ser nula!");
	}


	/**
	 * <p>
	 * Verifica se a conta bancária do título não é nula, senão lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void checkContaBancariaNotNull(final Titulo titulo) {
		checkTituloNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria(), "Conta bancária do título não pode ser nula!");
	}

	/**
	 * <p>
	 * Verifica se o número da conta da conta bancária do título não é nulo,
	 * senão lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private final static void checkContaBancariaNumeroNotNull(final Titulo titulo) {
		Validate.notNull(titulo.getContaBancaria().getNumeroDaConta(),
				"Número da conta bancária do título não pode ser nulo!");
	}

	/**
	 * <p>
	 * Verifica se o código do do número da conta bancária não é nulo e se é um
	 * número > 0, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkContaBancariaCodigo(final Titulo titulo) {
		checkContaBancariaNumeroNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(),
				"Código do número da conta bancária não pode ser nulo!");
		final boolean expression = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() > 0;
		Validate.isTrue(expression,
				format("Código do número da conta bancária deve ser um número inteiro natural positivo e não [%s].",
						titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta()));
	}

	/**
	 * <p>
	 * Verifica se o código do número da conta bancária do título é um número
	 * menor que ou igual ao limite informado, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limite
	 *            - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	public final static void checkContaBancariaCodigoMenorOuIgualQue(final Titulo titulo, final int limite) {
		checkContaBancariaCodigo(titulo);
		final boolean expression = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() <= limite;
		Validate.isTrue(expression,
				format("Código [%s] do número da conta deve ser um número menor que ou igual a [%s].",
						titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), limite));
	}

	/**
	 * <p>
	 * Verifica se o dígito verificador do número da conta bancária não é nulo,
	 * não é vazio e se é numérico, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkContaBancariaDigito(final Titulo titulo) {
		final String digitoVerificadorConta = titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta();
		Validate.notNull(digitoVerificadorConta, "Dígito verificador do número da conta bancária não pode ser nulo!");
		Validate.notEmpty(digitoVerificadorConta,
				format("Dígito verificador [\"%s\"] do número da conta bancária não pode ser vazio!",
						titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta()));
		if (!StringUtils.isNumeric(digitoVerificadorConta)) {
			throw new IllegalArgumentException(
					format("Nesse contexto o dígito verificador [\"%s\"] do número da conta deve ser numérico!",
							titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta()));
		}
	}

	/**
	 * <p>
	 * Verifica se o banco da conta bancária do título não é nulo, senão lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public static void checkBancoNotNull(final Titulo titulo) {
		checkContaBancariaNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getBanco(), "Banco da conta bancária do título não pode ser nulo!");
		final boolean expression = isCodigoDeCompensacaoOK(
				titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());
		Validate.isTrue(expression, format("Código de compensação [%s] inválido!",
				titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()));
	}

	/**
	 * <p>
	 * Verifica se a agência da conta bancária do título não é nula, senão lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private final static void checkAgenciaNotNull(final Titulo titulo) {
		Validate.notNull(titulo.getContaBancaria().getAgencia(), "Agência bancária do título não pode ser nula!");
	}

	/**
	 * <p>
	 * Verifica se o código do número da agência bancária não é nulo e se é um
	 * número > 0, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private final static void checkAgenciaCodigo(final Titulo titulo) {
		checkAgenciaNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getAgencia().getCodigo(),
				"Código da agência bancária não pode ser nulo!");
		final boolean expression = titulo.getContaBancaria().getAgencia().getCodigo() > 0;
		Validate.isTrue(expression,
				format("Código da agência bancária deve ser um número inteiro natural positivo e não [%s].",
						titulo.getContaBancaria().getAgencia().getCodigo()));
	}

	/**
	 * <p>
	 * Verifica se o código do número da agência da conta bancária do título é
	 * um número menor que ou igual ao limite informado, caso contrário lança
	 * uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limite
	 *            - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	public final static void checkAgenciaCodigoMenorOuIgualQue(final Titulo titulo, final int limite) {
		checkAgenciaCodigo(titulo);
		final int codigoAgencia = titulo.getContaBancaria().getAgencia().getCodigo();
		final boolean expression = codigoAgencia <= limite;
		Validate.isTrue(expression,
				format("Código [%s] da agência deve ser um número menor que ou igual a [%s].", codigoAgencia, limite));
	}

	/**
	 * <p>
	 * Verifica se o dígito verificador da agência da conta bancária não é nulo,
	 * não é vazio e se é numérico, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkAgenciaDigito(final Titulo titulo) {
		checkAgenciaNotNull(titulo);
		final String digitoVerificadorAgencia = titulo.getContaBancaria().getAgencia().getDigitoVerificador();
		Validate.notEmpty(digitoVerificadorAgencia, format(
				"Dígito verificador [\"%s\"] da agência bancária não pode ser vazio!", digitoVerificadorAgencia));
		if (!StringUtils.isNumeric(digitoVerificadorAgencia)) {
			throw new IllegalArgumentException(
					format("Nesse contexto o dígito verificador [\"%s\"] da agência bancária deve ser numérico!",
							digitoVerificadorAgencia));
		}
	}

	/**
	 * <p>
	 * Verifica se a carteira da conta bancária do título não é nula, senão
	 * lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private final static void checkCarteiraNotNull(final Titulo titulo) {
		checkContaBancariaNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getCarteira(),
				"Carteira da conta bancária do título não pode ser nula!");
	}

	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título não é nulo e
	 * se é um número > 0, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkCarteiraCodigo(final Titulo titulo) {
		checkCarteiraNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getCarteira().getCodigo(), "Código da carteira não pode ser nulo!");
		final boolean expression = titulo.getContaBancaria().getCarteira().getCodigo() > 0;
		Validate.isTrue(expression, format("Código da carteira deve ser um número inteiro natural positivo e não [%s].",
				titulo.getContaBancaria().getCarteira().getCodigo()));
	}

	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título é um número
	 * dentro dos limites informados, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limiteInferior
	 *            - Limite mínimo permitido
	 * @param limiteSuperior
	 *            - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	public final static void checkCarteiraCodigo(final Titulo titulo, final int limiteInferior,
			final int limiteSuperior) {
		checkCarteiraCodigo(titulo);
		final int codigoDaCarteira = titulo.getContaBancaria().getCarteira().getCodigo();
		final boolean expression = ((codigoDaCarteira >= limiteInferior) && (codigoDaCarteira <= limiteSuperior));
		Validate.isTrue(expression, format("Código [%s] da carteira deve ser um número entre [%s] e [%s].",
				codigoDaCarteira, limiteInferior, limiteSuperior));
	}

	/**
	 * <p>
	 * Verifica se o tipo da carteira da conta bancária do título não é nulo,
	 * senão lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkCarteiraRegistroNotNull(final Titulo titulo) {
		checkCarteiraNotNull(titulo);
		Validate.notNull(titulo.getContaBancaria().getCarteira().getTipoCobranca(),
				"Tipo de cobrança (COM ou SEM registro) da carteira não pode ser nulo!");
	}

	/**
	 * <p>
	 * Verifica se onosso número do título não é nulo, não é vazio e se é
	 * numérico, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkNossoNumero(final Titulo titulo) {
		final String nossoNumero = titulo.getNossoNumero();
		Validate.notNull(nossoNumero, "Nosso número do título não pode ser nulo!");
		Validate.notEmpty(nossoNumero, format("Nosso número [\"%s\"] do título não pode ser vazio!", nossoNumero));
		if (!StringUtils.isNumeric(nossoNumero)) {
			throw new IllegalArgumentException(
					format("Nosso número [\"%s\"] do título deve conter somente dígitos numéricos!", nossoNumero));
		}
	}

	/**
	 * <p>
	 * Verifica se o nosso número do título tem o tamanho determinado, caso
	 * contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * <tt>"Tamanho do nosso número [%s] diferente do esperado [%s]!"</tt>.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * 
	 * @since 0.2
	 */
	public final static void checkNossoNumeroTamanho(final Titulo titulo, final int length) {
		checkNossoNumero(titulo);
		final boolean expression = StringUtils.length(titulo.getNossoNumero()) == length;
		Validate.isTrue(expression, format("Tamanho [%s] do nosso número [\"%s\"] diferente do esperado [%s]!",
				StringUtils.length(titulo.getNossoNumero()), titulo.getNossoNumero(), length));
	}

	/**
	 * <p>
	 * Verifica se o dígito verificador do nosso número do título não é nulo,
	 * não é vazio e se é numérico (natural positivo), caso contrário lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	public final static void checkNossoNumeroDigito(final Titulo titulo) {
		final String digitoNossoNumero = titulo.getDigitoDoNossoNumero();
		Validate.notNull(digitoNossoNumero, "Dígito verificador do nosso número do título não pode ser nulo!");
		Validate.notEmpty(digitoNossoNumero,
				format("Dígito verificador [\"%s\"] do nosso número do título não pode ser vazio!", digitoNossoNumero));
		if (!StringUtils.isNumeric(digitoNossoNumero)) {
			throw new IllegalArgumentException(
					format("Nesse contexto o dígito verificador [\"%s\"] do nosso número deve ser um número inteiro positivo!",
							digitoNossoNumero));
		}

	}

	/**
	 * <p>
	 * Verifica se o dígito do nosso número do título tem o tamanho determinado,
	 * caso contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * <tt>"Tamanho [%s] do dígito do nosso número [\"%s\"] diferente do esperado [%s]!"</tt>.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * 
	 * @since 0.2
	 */
	public final static void checkNossoNumeroDigitoTamanho(final Titulo titulo, final int length) {
		final boolean expression = StringUtils.length(titulo.getDigitoDoNossoNumero()) == length;
		Validate.isTrue(expression,
				format("Tamanho [%s] do dígito do nosso número [\"%s\"] diferente do esperado [%s]!",
						StringUtils.length(titulo.getDigitoDoNossoNumero()), titulo.getDigitoDoNossoNumero(), length));
	}

	/**
	 * <p>
	 * Verifica se o título contém {@code ParametrosBancariosMap} e se este
	 * contém um valor não é nulo do parâmetro determinado, caso contrário lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param param
	 *            Parâmetro a ser validado
	 * 
	 * @since 0.2
	 */
	public final static void checkParametroBancarioNotNull(final Titulo titulo, final ParametroBancario<?> param) {
		Validate.notNull(titulo.getParametrosBancarios(),
				format("O parâmetro bancário [\"%s\"] é necessário! [titulo.getParametrosBancarios() == null]", param));
		Validate.isTrue(titulo.getParametrosBancarios().contemComNome(param),
				format("Parâmetro bancário [\"%s\"] não encontrado!", param));
		Validate.notNull(titulo.getParametrosBancarios().getValor(param),
				format("Parâmetro bancário [\"%s\"] não contém valor!", param));
	}

	/**
	 * <p>
	 * Verifica se o título com o parâmetro informado é um número inteiro menor
	 * que ou igual ao limite informado, caso contrário lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param param
	 *            Parâmetro a ser validado
	 * @param limite
	 *            Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	public final static void checkParametroBancarioMenorOuIgualQue(final Titulo titulo,
			final ParametroBancario<?> param, final int limite) {
		checkParametroBancarioNotNull(titulo, param);
		final int valor = titulo.getParametrosBancarios().getValor(param).intValue();
		final boolean expression = valor <= limite;
		Validate.isTrue(expression, format(
				"Parâmetro [%s] com valor [%s] deve ser um número menor que ou igual a [%s].", param, valor, limite));
	}

}
