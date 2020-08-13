package br.com.bilotta.bluefood.application.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.bilotta.bluefood.domain.cliente.Cliente;
import br.com.bilotta.bluefood.domain.cliente.ClienteRepository;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bilotta.bluefood.domain.restaurante.Restaurante;
import br.com.bilotta.bluefood.domain.restaurante.RestauranteRepository;
import br.com.bilotta.bluefood.util.StringUtils;

@Component
public class InsertDataForTesting {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		clientes();
		Restaurante[] restaurantes = restaurantes();
	}
	
	private Restaurante[] restaurantes() {
		
		List<Restaurante> restaurantes = new ArrayList<>();
		
		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow();
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow();
		CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow();
		CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow();
		
		Restaurante r = new Restaurante();
		r.setNome("Bubger King");
		r.setEmail("atendimento@bubgerking.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("44444444444444");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("4444444444");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Mc Naldo's");
		r.setEmail("atendimento@mcnaldos.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("44444444444444");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("4444444444");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Sbubby");
		r.setEmail("atendimento@sbubby.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("44444444444444");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("4444444444");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		r.setTempoEntregaBase(38);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Pizza Brut");
		r.setEmail("atendimento@pizzabrut.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("44444444444444");
		r.setTaxaEntrega(BigDecimal.valueOf(9.8));
		r.setTelefone("4444444444");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0004-logo.png");
		r.setTempoEntregaBase(22);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Wiki Japa");
		r.setEmail("atendimento@wikijapa.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("44444444444444");
		r.setTaxaEntrega(BigDecimal.valueOf(14.9));
		r.setTelefone("4444444444");
		r.getCategorias().add(categoriaJapones);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0005-logo.png");
		r.setTempoEntregaBase(19);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()];
		return restaurantes.toArray(array);
	}
	
	private Cliente[] clientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joaosilva@gmail.com");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("00000000");
		c.setCpf("33333333333");
		c.setTelefone("77777777777");
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("mariatorres@yahoo.com");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("88888888");
		c.setCpf("66666666666");
		c.setTelefone("99999999999");
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
	}
}
