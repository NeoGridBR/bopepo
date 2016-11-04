package org.jrimum.bopepo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Interface que contém os métodos que serão utilizados pelo Jasper
 *
 * @author cgrings
 */
public interface BoletoViewModel {

	String getAceite();

	String getAgenciaECodigoBeneficiario();

	String getBancoNumeroFormatado();

	String getBancoNumeroFormatadoComDigito();

	String getBeneficiarioNumeroDeInscricaoFormatado();

	String getBeneficiarioNome();

	String getBeneficiarioEnderecoLinha1();

	String getBeneficiarioEnderecoLinha2();

	List<String> getInstrucoes();

	String getBeneficiarioCarteira();

	String getCodigoDeBarras();

	Date getDataDoDocumento();

	Date getDataDoProcessamento();

	Date getDataDeVencimento();

	String getEspecieDocumento();

	String getLinhaDigitavel();

	String getLocalDePagamento();

	String getEspecieMoeda();

	BigDecimal getQuantidadeDeMoeda();

	String getNossoNumeroFormatado();

	String getNumeroDoDocumentoFormatado();

	String getPagadorNomeENumeroDeInscricaoFormatado();

	String getPagadorEnderecoLinha1();

	String getPagadorEnderecoLinha2();

	String getSacadorAvalistaNomeENumeroDeInscricaoFormatado();

	String getSacadorAvalistaEnderecoLinha1();

	String getSacadorAvalistaEnderecoLinha2();

	BigDecimal getValorDoDocumento();

	String getPagadorInstrucao();
}
