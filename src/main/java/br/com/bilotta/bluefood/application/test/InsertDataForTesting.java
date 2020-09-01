package br.com.bilotta.bluefood.application.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.bilotta.bluefood.domain.cliente.Cliente;
import br.com.bilotta.bluefood.domain.cliente.ClienteRepository;
import br.com.bilotta.bluefood.domain.pagamento.StatusPagamento;
import br.com.bilotta.bluefood.domain.pedido.Pedido;
import br.com.bilotta.bluefood.domain.pedido.Pedido.Status;
import br.com.bilotta.bluefood.domain.pedido.PedidoRepository;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapio;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapioRepository;
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
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Cliente[] clientes = clientes();
		Restaurante[] restaurantes = restaurantes();
		itensCardapio(restaurantes);
		
		Pedido p = new Pedido();
		p.setData(LocalDateTime.now());
		p.setCliente(clientes[0]);
		p.setRestaurante(restaurantes[0]);
		p.setStatus(Status.Producao);
		p.setSubtotal(BigDecimal.valueOf(10));
		p.setTaxaEntrega(BigDecimal.valueOf(2));
		p.setTotal(BigDecimal.valueOf(12.0));
		pedidoRepository.save(p);
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
		clientes.add(c);
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("mariatorres@yahoo.com");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("88888888");
		c.setCpf("66666666666");
		c.setTelefone("99999999999");
		clientes.add(c);
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
	}
	
	private void itensCardapio(Restaurante[] restaurantes) {
		ItemCardapio ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Delicioso sanduíche com molho");
		ic.setNome("Double Cheese Burger Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche padrão que mata a fome");
		ic.setNome("Cheese Burger Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche natural com peito de peru");
		ic.setNome("Sanduíche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gás");
		ic.setNome("Refrigerante tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural e docinho");
		ic.setNome("Suco de laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(39.8));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
				
	}
}
