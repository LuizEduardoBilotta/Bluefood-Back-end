package br.com.bilotta.bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.bilotta.bluefood.domain.usuario.Usuario;
import br.com.bilotta.bluefood.infrastructure.web.validator.UploadConstraint;
import br.com.bilotta.bluefood.util.FileType;
import br.com.bilotta.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario{
	
	@NotBlank
	@Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inv�lido")
	@Column(length = 14, nullable = false)
	private String cnpj;
	
	@Size(max = 80)
	private String logotipo;
	
	@UploadConstraint(acceptedTypes = { FileType.PNG, FileType.JPG }, message = "O arquivo n�o n�o � um arquivo de imagem v�lido!")
	private transient MultipartFile logotipoFile;
	
	@NotNull(message = "A taxa de entrega n�o pode ser vazia!")
	@Min(0)
	@Max(99)
	private BigDecimal taxaEntrega;
	
	@NotNull(message = "O tempo de entrega n�o pode ser vazio!")
	@Min(0)
	@Max(120)
	private Integer tempoEntregaBase;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "restaurante_has_categoria",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
			  )
	@Size(min = 1, message = "O restaurante precisa ter no m�nimo uma categoria!")
	@ToString.Exclude
	private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
	
	@OneToMany(mappedBy = "restaurante")
	private Set<ItemCardapio> itemCardapio = new HashSet<>(0);
	
	public void setLogotipoFileName() {
		if (getId() == null) {
			throw new IllegalStateException("� necess�rio primeiramente gravar o registro!");
		}
		
		this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());
	}
	
	public String getCategoriaAsText() {
		Set <String> strings = new LinkedHashSet<>();
		
		for (CategoriaRestaurante categoria : categorias) {
			strings.add(categoria.getNome());
		}
		
		return StringUtils.concatenate(strings);
	}
}
