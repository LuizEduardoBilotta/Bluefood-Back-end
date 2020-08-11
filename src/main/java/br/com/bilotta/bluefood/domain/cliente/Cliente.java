package br.com.bilotta.bluefood.domain.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.bilotta.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Cliente extends Usuario {

	@NotBlank(message = "O CPF n�o pode ser vazio!")
	@Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inv�lido!")
	@Column(length = 11, nullable = false)
	private String cpf;
	
	@NotBlank(message = "O CEP n�o pode ser vazio!")
	@Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato inv�lido!")
	@Column(length = 8)
	private String cep;
}
