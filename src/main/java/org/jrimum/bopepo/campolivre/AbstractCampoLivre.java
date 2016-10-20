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
 * Created at: 30/03/2008 - 18:07:11
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
 * Criado em: 30/03/2008 - 18:07:11
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.banco.BancoBradesco;
import org.jrimum.bopepo.banco.BancoCEF;
import org.jrimum.bopepo.banco.BancoCitibank;
import org.jrimum.bopepo.banco.BancoDoBrasil;
import org.jrimum.bopepo.banco.BancoHSBC;
import org.jrimum.bopepo.banco.BancoItau;
import org.jrimum.bopepo.banco.BancoSafra;
import org.jrimum.bopepo.banco.BancoSantander;
import org.jrimum.bopepo.banco.BancoSofisa;
import org.jrimum.bopepo.banco.TituloValidator;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.FixedField;
import org.jrimum.texgit.type.component.BlockOfFields;
import org.jrimum.utilix.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Esta classe é responsável por determinar a interface campo livre e também
 * determinar qual implementação de campo livre se aplica a um determinado
 * título.
 * </p>
 * 
 * <p>
 * Uma outra forma de analisar esta classe é sob o prisma de uma Abstract
 * Factory.
 * </p>
 * 
 * <p>
 * <dl>
 * <dt><strong>Field Livre:</strong>
 * <dd>É um espaço reservado no código de barras e a sua implementação varia de
 * banco para banco.</dd></dt>
 * </dl>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:lukas.antunes@virtualsistemas.com.br">Lukas
 *         Antunes</a> - Colaborador com o banco Intermedium (077)
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a> -
 *         Colaborador com o banco Rural (453)
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a> - 
 * 		   Colaborador com o Banco do Nordeste do Brasil (004).
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo</a> - 
 * 		   Colaborador com o Banco Citibank (756).
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a> - 
 * 		   Colaborador com o Banco de Brasília (070).
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCampoLivre extends BlockOfFields implements CampoLivre {

	/**
	 * {@code serialVersionUID = 4605730904122445595L}
	 */
	private static final long serialVersionUID = 4605730904122445595L;
	
	/**
	 * Looger.
	 */
	private static Logger log = LoggerFactory.getLogger(Objects.class);
	
	/**
	 * Nosso número com 7 posições.
	 */
	static final int NN7 = 7;

	/**
	 * Nosso número com 8 posições.
	 */
	static final int NN8 = 8;
	
	/**
	 * Nosso número com 9 posições.
	 */
	static final int NN9 = 9;
	
	/**
	 * Nosso número com 10 posições.
	 */
	static final int NN10 = 10;
	
	/**
	 * Nosso número com 11 posições.
	 */
	static final int NN11 = 11;
	
	/**
	 * Nosso número com 14 posições.
	 */
	static final int NN14 = 14;

	/**
	 * Nosso número com 15 posições.
	 */
	static final int NN15 = 15;
	
	/**
	 * Nosso número com 17 posições.
	 */
	static final int NN17 = 17;

	/**
	 * Subclasses não precisam definir o tamanho.
	 */
	@SuppressWarnings("unused")
	private AbstractCampoLivre(Integer fieldsLength, Integer stringLength) {
		super(null,null);
	}

	/**
	 * Cria um campo livre com um determinado número de campos
	 * 
	 * @param fieldsLength
	 *            - Número de campos
	 */
	protected AbstractCampoLivre(Integer fieldsLength) {
		super();
		setLength(CampoLivre.STRING_LENGTH);
		setSize(fieldsLength);
	}

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
	protected static CampoLivre create(Titulo titulo) throws NotSupportedBancoException,
			NotSupportedCampoLivreException, CampoLivreException {

		if (log.isTraceEnabled()){
			
			log.trace("Instanciando Campo livre");
		}
		if (log.isDebugEnabled()){
			
			log.debug("titulo instance : " + titulo);
		}

		try {
			TituloValidator.checkBancoNotNull(titulo);
			
			if (log.isDebugEnabled()){
				
				log.debug(format("Campo Livre do Banco: %s", titulo.getContaBancaria().getBanco().getNome()));
			}
			
			if (BancosSuportados.isSuportado(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				final BancosSuportados banco = BancosSuportados.suportados.get( titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());

				switch (banco) {

					case BANCO_BRADESCO: return (new BancoBradesco(titulo)).getCampoLivre();
					case BANCO_DO_BRASIL: return (new BancoDoBrasil(titulo)).getCampoLivre();
					case BANCO_DO_NORDESTE_DO_BRASIL: return AbstractCLBancoDoNordesteDoBrasil.create(titulo);
					case BANCO_ABN_AMRO_REAL: return AbstractCLBancoReal.create(titulo);
					case CAIXA_ECONOMICA_FEDERAL: return (new BancoCEF(titulo)).getCampoLivre();
					case HSBC: return (new BancoHSBC(titulo)).getCampoLivre();
					case UNIBANCO: return AbstractCLUnibanco.create(titulo);
					case BANCO_ITAU: return (new BancoItau(titulo)).getCampoLivre();
					case BANCO_SAFRA: return (new BancoSafra(titulo)).getCampoLivre();
					case BANCO_SOFISA: return (new BancoSofisa(titulo)).getCampoLivre();
					case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL: return AbstractCLBanrisul.create(titulo);
					case MERCANTIL_DO_BRASIL: return AbstractCLMercantilDoBrasil.create(titulo);
					case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO: return AbstractCLBanestes.create(titulo);
					case BANCO_RURAL: return AbstractCLBancoRural.create(titulo);
					case BANCO_SANTANDER: return (new BancoSantander(titulo)).getCampoLivre();
					case BANCO_INTEMEDIUM: return AbstractCLBancoIntermedium.create(titulo);
					case BANCO_SICREDI: return AbstractCLSicredi.create(titulo);
					case BANCOOB: return AbstractCLBancoob.create(titulo);
					case CITIBANK: return (new BancoCitibank(titulo)).getCampoLivre(); 
					case BANCO_DE_BRASILIA: return AbstractCLBancoDeBrasilia.create(titulo);
					case CECRED: return AbstractCLCecred.create(titulo);

					default:
						/*
						 * Se chegar neste ponto e nenhum campo livre foi definido, então é
						 * sinal de que existe implementações de campo livre para o banco em
						 * questão, só que nenhuma destas implementações serviu e a classe
						 * abstrata responsável por fornecer o campo livre não gerou a
						 * exceção NotSupportedCampoLivreException. Trata-se de uma mensagem
						 * genérica que será utilizada somente em último caso.
						 */
						throw new NotSupportedCampoLivreException(
								"Não há implementações de campo livre para o banco "
										+ titulo.getContaBancaria().getBanco()
												.getCodigoDeCompensacaoBACEN().getCodigoFormatado()
										+ " compatíveis com as "
										+ "caracteríticas do título informado.");
				}
			} else {
				
				/*
				 * Se chegar até este ponto, é sinal de que para o banco em
				 * questão, apesar de estar definido no EnumBancos, não há
				 * implementações de campo livre, logo considera-se o banco com
				 * não suportado.
				 */
				throw new NotSupportedBancoException();
			}
		} catch(CampoLivreException e) {
			/*
			 * Caso seja uma exceção esperada.
			 */
			throw e;
			
		} catch(Exception e) {
			/*
			 * Encapsula-se qualquer outra exceção. 
			 */
			throw new CampoLivreException(e);
		}
	}
	
	/**
	 * <p>
	 * Constrói um campo livre após executar os métodos
	 * {@link AbstractCampoLivre#checkValues(Titulo)} e {@link AbstractCampoLivre#addFields(Titulo)}, retornando em
	 * seguida esta instância pronta para escrita.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @return a instância pronta para escrita
	 * 
	 * @since 0.2
	 */
	protected final CampoLivre build(Titulo titulo) {

		checkValues(titulo);

		addFields(titulo);

		return this;
	}
	
	/**
	 * <p>
	 * Usado pelo método {@link AbstractCampoLivre#build(Titulo)} para verificar a consistência do
	 * campo livre. Se algum inconsistência for verificada, este método deverá
	 * lança-la.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected abstract void checkValues(Titulo titulo);
	
	/**
	 * <p>
	 * Usado pelo método {@link AbstractCampoLivre#build(Titulo)}, adiciona os campos do campo
	 * livre deixando-o pronto para escrita.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected abstract void addFields(Titulo titulo);
	
	/**
	 * <p>
	 * Gera o campo livre a parir dos campos armazenados sem verificar se está
	 * compatível com número de fields declarado pelo campo livre. Isso implica
	 * que a string retornada poderá ser menor do que 25 caracteres.
	 * </p>
	 * 
	 * @return string a partir dos campos contidos até o momento.
	 * 
	 * @since 0.2
	 */
	protected final String writeFields() {

		StringBuilder campoLivreAtual = new StringBuilder();
		
		for(FixedField<?> f : this){
			campoLivreAtual.append(f.write());
		}
		
		return campoLivreAtual.toString();
	}

	/*
	 * Validações para subclasses.
	 */

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return Objects.toString(this);
	}
	
}
