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
 * Created at: 30/03/2008 - 18:09:58
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
 * Criado em: 30/03/2008 - 18:09:58
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.containsAny;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static org.apache.commons.lang.StringUtils.strip;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Esta classe tem como finalidade encapsular toda a lógica de criação de um
 * campo livre e de fornecer para o pacote
 * <code>org.jrimum.bopepo.campolivre</code> um único ponto de acesso ao mesmo.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class CampoLivreFactory {

	/**
	 * Looger.
	 */
	private static Logger log = LoggerFactory.getLogger(CampoLivreFactory.class);

	/**
	 * Cria um campo livre a partir dos dados contidos no título fornecido.
	 * 
	 * @param titulo
	 *            com todos os dados para a geração do campo livre
	 * @return instância de campo livre ou nulo.
	 * @throws NotSupportedBancoException
	 *             Caso o banco informado na conta bancária não tenha nenhuma
	 *             implementação de campo livre.
	 * @throws NotSupportedCampoLivreException
	 *             Caso exista implementações de campo livre para o banco
	 *             informa na conta bancária, mas nenhuma dessas implementações
	 *             foram adequadas para os dados do título.
	 * @throws CampoLivreException
	 *             Caso ocorra algum problema na geração do campo livre.
	 */
	public static CampoLivre create(final Titulo titulo)
			throws NotSupportedBancoException, NotSupportedCampoLivreException {
		if (log.isTraceEnabled()) {
			log.trace("Instanciando Campo livre");
		}
		if (log.isDebugEnabled()) {
			log.debug("titulo instance : " + titulo);
		}

		try {
			TituloValidator.checkBancoNotNull(titulo);
			if (log.isDebugEnabled()) {
				log.debug(format("Campo Livre do Banco: %s", titulo.getContaBancaria().getBanco().getNome()));
			}

			final String codigoBACENFormatado = titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN()
					.getCodigoFormatado();
			if (BancosSuportados.isSuportado(codigoBACENFormatado)) {
				final BancosSuportados banco = BancosSuportados.BANCOS_SUPORTADOS.get(codigoBACENFormatado);
				return banco.getBanco().getCampoLivre(titulo);
			} else {
				/*
				 * Se chegar até este ponto, é sinal de que para o banco em
				 * questão, apesar de estar definido no EnumBancos, não há
				 * implementações de campo livre, logo considera-se o banco com
				 * não suportado.
				 */
				throw new NotSupportedBancoException();
			}
		} catch (final CampoLivreException e) {
			/*
			 * Caso seja uma exceção esperada.
			 */
			throw e;
		} catch (final Exception e) {
			/*
			 * Encapsula-se qualquer outra exceção.
			 */
			throw new CampoLivreException(e);
		}
	}

	/**
	 * Devolve um CampoLivre a partir de uma String.
	 * 
	 * @param strCampoLivre
	 * 
	 * @return Referência para uma instância anônima de CampoLivre.
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public static CampoLivre create(final String value) {
		final String strCampoLivre = strip(value);

		Validate.notEmpty(strCampoLivre, "O Campo Livre não deve ser vazio!");

		Validate.isTrue(strCampoLivre.length() == CampoLivre.STRING_LENGTH, "O tamanho do Campo Livre [ "
				+ strCampoLivre + " ] deve ser igual a 25 e não [" + strCampoLivre.length() + "]!");
		Validate.isTrue(!containsAny(strCampoLivre, " "), "O Campo Livre [ " + strCampoLivre + " ] não deve conter espaços em branco!");
		Validate.isTrue(isNumeric(strCampoLivre), "O Campo Livre [ " + strCampoLivre + " ] deve ser uma String numérica!");

		final CampoLivre campoLivre = new CampoLivre(1);
		campoLivre.addString(strCampoLivre, StringUtils.length(strCampoLivre));
		return campoLivre;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
