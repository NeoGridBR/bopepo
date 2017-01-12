/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:04:06
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:04:06
 * 
 */

package org.jrimum.bopepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.CampoLivreFactory;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * É a representação do documento Boleto que por sua vez, representa títulos em
 * cobrança.
 * </p>
 * 
 * <p>
 * A classe encapsula os atributos integrantes e as funcionalidades inerentes à
 * construção de tal documento.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Boleto {

	private static Logger log = LoggerFactory.getLogger(Boleto.class);

	private static final int MAX_INSTRUCOES_CAIXA = 8;

	/**
	 * @see Titulo
	 */
	private Titulo titulo;

	/**
	 * @see #setDataDeProcessamento(Date)
	 */
	private Date dataDeProcessamento;

	/**
	 * @see CodigoDeBarras
	 */
	private CodigoDeBarras codigoDeBarras;

	/**
	 * @see LinhaDigitavel
	 */
	private LinhaDigitavel linhaDigitavel;

	/**
	 * @see CampoLivre
	 */
	private CampoLivre campoLivre;

	/**
	 * @see #setLocalPagamento(String)
	 */
	private String localPagamento;

	/**
	 * @see #setInstrucaoAoSacado(String)
	 */
	private String instrucaoAoSacado;

	private List<String> instrucoesAoCaixa = new ArrayList<String>(MAX_INSTRUCOES_CAIXA);

	/**
	 * Apenas cria um instâcia do boleto com os dados nulos.
	 */
	public Boleto() {
		super();
	}

	/**
	 * Cria um boleto pronto para ser gerado.
	 * 
	 * @param titulo
	 * @throws NotSupportedBancoException
	 * @throws NotSupportedCampoLivreException
	 */
	public Boleto(final Titulo titulo)
			throws IllegalArgumentException, NotSupportedBancoException, NotSupportedCampoLivreException {
		this(titulo, CampoLivreFactory.create(titulo));
	}

	/**
	 * @param titulo
	 * @param campoLivre
	 */
	public Boleto(Titulo titulo, CampoLivre campoLivre) {
		super();

		if (log.isTraceEnabled())
			log.trace("Instanciando boleto");

		if (log.isDebugEnabled())
			log.debug("titulo instance : " + titulo);

		if (log.isDebugEnabled())
			log.debug("campoLivre instance : " + campoLivre);

		Validate.notNull(titulo, "Título Nulo - Valor Não Permitido!");

			this.setTitulo(titulo);
			this.setCampoLivre(campoLivre);
			this.load();

			if (log.isDebugEnabled())
				log.debug("boleto instance : " + this);

		if (log.isDebugEnabled() || log.isTraceEnabled()) {

			log.trace("Boleto Instanciado : " + this);
		}

	}

	private void load() {
		codigoDeBarras = new CodigoDeBarras(titulo, campoLivre);
		linhaDigitavel = new LinhaDigitavel(codigoDeBarras);
		dataDeProcessamento = new Date();
		if (log.isInfoEnabled()) {
			log.info("Data de Processamento do Boleto : " + DateFormatUtils.ISO_DATE_FORMAT.format(dataDeProcessamento));
		}
	}

	/**
	 * @return O campoLivre da isntância.
	 */
	public CampoLivre getCampoLivre() {

		return campoLivre;
	}

	/**
	 * @param campoLivre
	 *            the campoLivre to set
	 */
	private void setCampoLivre(final CampoLivre campoLivre) {
		this.campoLivre = campoLivre;
	}

	/**
	 * @return the titulo
	 */
	public Titulo getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	/**
	 * @see #getDataDeProcessamento()
	 * 
	 * @return the dataDeProcessamento
	 */
	public Date getDataDeProcessamento() {
		return dataDeProcessamento;
	}

	/**
	 * <p>
	 * Data de emissão do boleto de cobrança.
	 * </p>
	 * 
	 * @param dataDeProcessamento
	 *            the dataDeProcessamento to set
	 */
	public void setDataDeProcessamento(Date dataDeProcessamento) {
		this.dataDeProcessamento = dataDeProcessamento;
	}

	/**
	 * @return the codigoDeBarras
	 */
	public CodigoDeBarras getCodigoDeBarras() {
		return codigoDeBarras;
	}

	/**
	 * @param codigoDeBarras
	 *            the codigoDeBarras to set
	 */
	public void setCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	/**
	 * @return the linhaDigitavel
	 */
	public LinhaDigitavel getLinhaDigitavel() {
		return linhaDigitavel;
	}

	/**
	 * @param linhaDigitavel
	 *            the linhaDigitavel to set
	 */
	public void setLinhaDigitavel(LinhaDigitavel linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	/**
	 * @see #setLocalPagamento(String)
	 * 
	 * @return String local de pagamento
	 */
	public String getLocalPagamento() {
		return localPagamento;
	}

	/**
	 * <p>
	 * Possíveis locais para pagamento.
	 * </p>
	 * <p>
	 * Exemplo: <em>Pagável preferencialmente na Rede X ou em qualquer Banco até
	 * o Vencimento.</em>
	 * </p>
	 * 
	 * @param localPagamento1
	 *            the localPagamento1 to set
	 */
	public void setLocalPagamento(String localPagamento1) {
		this.localPagamento = localPagamento1;
	}

	/**
	 * @see #setInstrucaoAoSacado(String)
	 * 
	 * @return the instrucaoAoSacado
	 */
	public String getInstrucaoAoSacado() {
		return instrucaoAoSacado;
	}

	/**
	 * <p>
	 * Instrução adicional ao sacado, para visualizar o conceito de negócio de
	 * sacado consultar o <a href="http://www.jrimum.org/bopepo">glossário</a>.
	 * </p>
	 * 
	 * @param insturcaoAoSacado
	 *            the insturcaoAoSacado to set
	 */
	public void setInstrucaoAoSacado(String insturcaoAoSacado) {
		this.instrucaoAoSacado = insturcaoAoSacado;
	}

	/**
	 * @return the Instruções ao Caixa
	 */
	public List<String> getInstrucoesAoCaixa() {
		return instrucoesAoCaixa;
	}

	/**
	 * @param instrucoes
	 *            the instrucoes to set
	 */
	public void setInstrucoesAoCaixa(List<String> instrucoes) {
		if ((this.instrucoesAoCaixa.size() + instrucoes.size()) > MAX_INSTRUCOES_CAIXA) {
			throw new IllegalArgumentException(String.format(
					"O quantidade de instuções ao caixa está limitada a [%s] instruções!", MAX_INSTRUCOES_CAIXA));
		}
		this.instrucoesAoCaixa.addAll(instrucoes);
	}

	public void addInstrucaoAoCaixa(final String instrucao) {
		if (this.instrucoesAoCaixa.size() >= MAX_INSTRUCOES_CAIXA) {
			throw new IllegalArgumentException(String.format(
					"O quantidade de instuções ao caixa está limitada a [%s] instruções!", MAX_INSTRUCOES_CAIXA));
		}
		this.instrucoesAoCaixa.add(instrucao);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
