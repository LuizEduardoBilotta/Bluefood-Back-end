package br.com.bilotta.bluefood.domain.cliente;

import javax.persistence.Entity;
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

	private String cpf;
	
	private String cep;
}
