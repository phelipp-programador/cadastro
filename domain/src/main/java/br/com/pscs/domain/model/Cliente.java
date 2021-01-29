package br.com.pscs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * CRUD para cadastro básico de Cliente (Incluir, Alterar, Consultar e Listar):
          (Java 1.8, JSF 2 + Primefaces, JPA + Hibernate, Banco MySQL).


Campos: ID, NOME, CPF, DATA NASCIMENTO, TELEFONE, E-MAIL, NOME PAI, NOME MÃE.
 * @author phelipp
 *
 */

@Data
@Entity
public class Cliente implements Serializable ,ModelImpl{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
    @OneToOne( cascade = CascadeType.ALL)
	private Telefone telefone;
	private String email;
	private String nomePai;
	private String nomeMae;
	

}

