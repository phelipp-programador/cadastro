package br.com.pscs.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Telefone implements Serializable,ModelImpl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
//	@Size(min = 2,max = 3,message = "Campo ddd deve conter no minimo 2 e no maximo 3 caracter")
	//@Pattern(regexp = "${[0-9]}*",message = "Campo ddd deve conter apenas numeral")
	private String ddd;
	//@Size(min = 2,max = 3,message = "Campo numero deve conter no minimo 8 e no maximo 9 caracter")
	//@Pattern(regexp = "${[0-9]}*",message = "Campo numero deve conter apenas numeral")
	private String numero;
}
