package br.com.bilotta.bluefood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bilotta.bluefood.application.ClienteService;
import br.com.bilotta.bluefood.application.RestauranteService;
import br.com.bilotta.bluefood.application.ValidationException;
import br.com.bilotta.bluefood.domain.cliente.Cliente;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bilotta.bluefood.domain.restaurante.Restaurante;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

	@Autowired
	private ClienteService clienteService;
	
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@GetMapping("cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@GetMapping("restaurante/new")
	public String newRestaurante(Model model) {
		model.addAttribute("restaurante", new Restaurante());
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping("cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors errors,
			Model model) {
		
		if(!errors.hasErrors()) {
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!");
			} catch (ValidationException e){
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@PostMapping("restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante, 
			Errors errors,
			Model model) {
		
		if(errors.hasErrors()) {
			System.out.println("CNPJ: " + restaurante.getCnpj());
			System.out.println("Email: " + restaurante.getEmail());
			System.out.println("Logotipo: " + restaurante.getLogotipo());
			System.out.println("Nome: " + restaurante.getNome());
			System.out.println("Senha: " + restaurante.getSenha());
			System.out.println("Telefone: " + restaurante.getTelefone());
			System.out.println("Categorias: " + restaurante.getCategorias());
			System.out.println("ID: " + restaurante.getId());
			System.out.println("LogotioFile: " + restaurante.getLogotipoFile());
			System.out.println("Taxa de Entrega: " + restaurante.getTaxaEntrega());
			System.out.println("Tempo de Entrega Base: " + restaurante.getTempoEntregaBase());
			System.out.println("Erro:" + errors);
		}
		
		if(!errors.hasErrors()) {
			
			System.out.println("CNPJ: " + restaurante.getCnpj());
			System.out.println("Email: " + restaurante.getEmail());
			System.out.println("Logotipo: " + restaurante.getLogotipo());
			System.out.println("Nome: " + restaurante.getNome());
			System.out.println("Senha: " + restaurante.getSenha());
			System.out.println("Telefone: " + restaurante.getTelefone());
			System.out.println("Categorias: " + restaurante.getCategorias());
			System.out.println("ID: " + restaurante.getId());
			System.out.println("LogotioFile: " + restaurante.getLogotipoFile());
			System.out.println("Taxa de Entrega: " + restaurante.getTaxaEntrega());
			System.out.println("Tempo de Entrega Base: " + restaurante.getTempoEntregaBase());
			System.out.println("Erro:" + errors);
			
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso!");
			} catch (ValidationException e){
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.setEditMode(model, false);
		return "restaurante-cadastro";
	}
}
