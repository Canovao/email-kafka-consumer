package br.com.dbc.vemser.financeiro.emailkafkaconsumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO {
    private String nome;
    private String email;
    private String numeroConta;
    private String numeroCartao;
    private String codigoSeguranca;
    private String numeroAgencia;
    private LocalDate dataExpedicao;
    private TipoCartao tipoCartao;
    private LocalDate dataVencimento;
}
