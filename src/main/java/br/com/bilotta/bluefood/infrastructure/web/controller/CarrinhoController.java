package br.com.bilotta.bluefood.infrastructure.web.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.bilotta.bluefood.domain.pedido.Carrinho;
import br.com.bilotta.bluefood.domain.pedido.ItemPedido;
import br.com.bilotta.bluefood.domain.pedido.Pedido;
import br.com.bilotta.bluefood.domain.pedido.PedidoRepository;
import br.com.bilotta.bluefood.domain.pedido.RestauranteDiferenteException;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapio;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@ModelAttribute("carrinho")
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	@GetMapping(path="/visualizar")
	public String viewCarrinho() {
		return "cliente-carrinho";
	}
	
	
	@GetMapping(path="/adicionar")
	public String adicionarItem(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("quantidade") Integer quantidade,
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
		
		try {
			carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
		} catch (RestauranteDiferenteException e) {
			
			model.addAttribute("msg", "Não é possível itens de restaurantes diferentes no mesmo pedido!"
							+ "Recomendamos realizar em pedidos separados.");
		}
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path="/remover")
	public String removerItem(
			@RequestParam("itemId") Integer itemId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus, 
			Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow(NoSuchElementException::new);
		
		carrinho.removerItem(itemCardapio);
		
		if (carrinho.vazio()) {
			sessionStatus.setComplete();
		}
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/refazerCarrinho") 
	public String refazerCarrinho(
			@RequestParam("pedidoId") Integer pedidoId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
		
		carrinho.limpar();
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			carrinho.adicionarItem(itemPedido);
		}
		
		return "cliente-carrinho";
	}
}

