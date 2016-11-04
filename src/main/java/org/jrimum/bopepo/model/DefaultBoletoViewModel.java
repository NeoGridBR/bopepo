package org.jrimum.bopepo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

/**
 * DataSource do Bopepo para ser utilizado na camada view
 *
 * @author cgrings
 */
public class DefaultBoletoViewModel implements BoletoViewModel {

	private static final Pattern CEP_UNFORMATTER_PATTERN = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})");
	private static final String CEP_FORMATTER_REPLACEMENT = "$1.$2-$3";

	final Boleto boleto;
	final Cedente beneficiario;
	final Sacado pagador;
	final SacadorAvalista sacadorAvalista;

	/**
	 * Cria uma lista BopepoDataAdapter a partir de uma lista de boleto do
	 * bopepo
	 *
	 * @param boletos
	 * @return
	 */
	public static List<BoletoViewModel> create(List<Boleto> boletos) {
		List<BoletoViewModel> bopepoDataAdapters = new ArrayList<BoletoViewModel>();
		for (Boleto boleto : boletos) {
			bopepoDataAdapters.add(create(boleto));
		}
		return bopepoDataAdapters;
	}

	/**
	 * Cria um BopepoDataAdapter a partir de um boleto do bopepo
	 *
	 * @param boleto
	 * @return
	 */
	public static BoletoViewModel create(Boleto boleto) {
		return new DefaultBoletoViewModel(boleto);
	}

	private DefaultBoletoViewModel(final Boleto boleto) {
		this.boleto = boleto;
		this.beneficiario = boleto.getTitulo().getCedente();
		this.pagador = boleto.getTitulo().getSacado();
		this.sacadorAvalista = boleto.getTitulo().getSacadorAvalista();
	}

	@Override
	public String getAceite() {
		return (boleto.getTitulo().getAceite().equals(Aceite.A) ? "SIM" : "NÃO");
	}

	@Override
	public String getAgenciaECodigoBeneficiario() {
		final ContaBancaria contaBancaria = boleto.getTitulo().getContaBancaria();
		final StringBuilder result = new StringBuilder();
		result.append(String.format("%04d / %09d", contaBancaria.getAgencia().getCodigo(),
				contaBancaria.getNumeroDaConta().getCodigoDaConta()));
		if (StringUtils.isNotBlank(contaBancaria.getNumeroDaConta().getDigitoDaConta())) {
			result.append("-").append(contaBancaria.getNumeroDaConta().getDigitoDaConta());
		}
		return result.toString();
	}

	@Override
	public String getBancoNumeroFormatado() {
		final CodigoDeCompensacaoBACEN codigoBacen = boleto.getTitulo().getContaBancaria().getBanco()
				.getCodigoDeCompensacaoBACEN();
		return codigoBacen.getCodigoFormatado();
	}

	@Override
	public String getBancoNumeroFormatadoComDigito() {
		final CodigoDeCompensacaoBACEN codigoBacen = boleto.getTitulo().getContaBancaria().getBanco()
				.getCodigoDeCompensacaoBACEN();
		return codigoBacen.getCodigoFormatado() + "-" + codigoBacen.getDigito();
	}

	@Override
	public String getBeneficiarioNumeroDeInscricaoFormatado() {
		return beneficiario.getCPRF().getCodigoFormatado();
	}

	@Override
	public String getBeneficiarioNome() {
		return beneficiario.getNome();
	}

	@Override
	public String getBeneficiarioEnderecoLinha1() {
		return getEnderecoLinha1(beneficiario.getEnderecos().iterator().next());
	}

	@Override
	public String getBeneficiarioEnderecoLinha2() {
		return getEnderecoLinha2(beneficiario.getEnderecos().iterator().next());
	}

	@Override
	public List<String> getInstrucoes() {
		return boleto.getInstrucoesAoCaixa();
	}

	@Override
	public String getBeneficiarioCarteira() {
		final Carteira carteira = boleto.getTitulo().getContaBancaria().getCarteira();
		if (carteira.getCodigo() != null) {
			return carteira.getCodigo().toString();
		} else {
			return carteira.getNome();
		}
	}

	@Override
	public String getCodigoDeBarras() {
		return boleto.getCodigoDeBarras().write();
	}

	@Override
	public Date getDataDoDocumento() {
		return boleto.getTitulo().getDataDoDocumento();
	}

	@Override
	public Date getDataDoProcessamento() {
		return boleto.getDataDeProcessamento();
	}

	@Override
	public Date getDataDeVencimento() {
		return boleto.getTitulo().getDataDoVencimento();
	}

	@Override
	public String getEspecieDocumento() {
		return boleto.getTitulo().getTipoDeDocumento().getSigla();
	}

	@Override
	public String getLinhaDigitavel() {
		return boleto.getLinhaDigitavel().write();
	}

	@Override
	public String getLocalDePagamento() {
		return boleto.getLocalPagamento();
	}

	@Override
	public String getEspecieMoeda() {
		return boleto.getTitulo().getTipoDeMoeda().write();
	}

	@Override
	public BigDecimal getQuantidadeDeMoeda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNossoNumeroFormatado() {
		return String.format("%s-%s", boleto.getTitulo().getNossoNumero(), boleto.getTitulo().getDigitoDoNossoNumero());
	}

	@Override
	public String getNumeroDoDocumentoFormatado() {
		return StringUtils.leftPad(boleto.getTitulo().getNumeroDoDocumento(), 10, '0');
	}

	@Override
	public String getPagadorNomeENumeroDeInscricaoFormatado() {
		return pagador.getNome() + " - " + pagador.getCPRF().getCodigoFormatado();
	}

	@Override
	public String getPagadorEnderecoLinha1() {
		return getEnderecoLinha1(pagador.getEnderecos().iterator().next());
	}

	@Override
	public String getPagadorEnderecoLinha2() {
		return getEnderecoLinha2(pagador.getEnderecos().iterator().next());
	}

	@Override
	public String getPagadorInstrucao() {
		return boleto.getInstrucaoAoSacado();
	}

	@Override
	public String getSacadorAvalistaNomeENumeroDeInscricaoFormatado() {
		String result = null;
		if (sacadorAvalista != null) {
			final StringBuilder builder = new StringBuilder();
			builder.append(sacadorAvalista.getNome()).append(" - ").append(sacadorAvalista.getCPRF().getCodigoFormatado());
			result = builder.toString();
		}
		return result;
	}

	@Override
	public String getSacadorAvalistaEnderecoLinha1() {
		String result = null;
		if (sacadorAvalista != null) {
			final StringBuilder builder = new StringBuilder();
			final Endereco endereco = sacadorAvalista.getEnderecos().iterator().next();
			builder.append(endereco.getLogradouro()).append(", ").append(endereco.getNumero());
			if (StringUtils.isNotBlank(endereco.getComplemento())) {
				builder.append(" - ").append(endereco.getComplemento());
			}
			result = builder.toString();
		}
		return result;
	}

	@Override
	public String getSacadorAvalistaEnderecoLinha2() {
		String result = null;
		if (sacadorAvalista != null) {
			final StringBuilder builder = new StringBuilder();
			final Endereco endereco = sacadorAvalista.getEnderecos().iterator().next();
			builder.append(formatCEP(endereco.getCEP().getCep())).append(", ").append(endereco.getBairro()).append(" - ")
					.append(endereco.getLocalidade()).append(" - ").append(endereco.getUF());
			result = builder.toString();
		}
		return result;
	}

	@Override
	public BigDecimal getValorDoDocumento() {
		return boleto.getTitulo().getValor();
	}

	private String formatCEP(final String value) {
		String cepSemMascara = value.replaceAll("[^0-9]+", "");
		final Matcher unformatterMatcher = CEP_UNFORMATTER_PATTERN.matcher(cepSemMascara);
		return matchAndReplace(unformatterMatcher, CEP_FORMATTER_REPLACEMENT);
	}

	private String matchAndReplace(final Matcher matcher, final String replacement) {
		final String result;
		if (matcher.matches()) {
			result = matcher.replaceAll(replacement);
		} else {
			throw new IllegalArgumentException();
		}
		return result;
	}

	private String getEnderecoLinha1(final Endereco endereco) {
		final StringBuilder result = new StringBuilder();
		result.append(endereco.getLogradouro());
		final String numero = endereco.getNumero();
		if (StringUtils.isNotBlank(numero)) {
			result.append(", ").append(numero);
		}
		final String complemento = endereco.getComplemento();
		if (StringUtils.isNotBlank(complemento)) {
			result.append(" - ").append(complemento);
		}
		return result.toString();
	}

	private String getEnderecoLinha2(final Endereco endereco) {
		final StringBuilder result = new StringBuilder();
		result.append(formatCEP(endereco.getCEP().getCep())).append(", ").append(endereco.getBairro()).append(" - ")
				.append(endereco.getLocalidade()).append(" - ").append(endereco.getUF());
		return result.toString();
	}

}
