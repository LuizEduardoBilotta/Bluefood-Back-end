package br.com.bilotta.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bilotta.bluefood.application.service.ClienteService;
import br.com.bilotta.bluefood.application.service.ValidationException;
import br.com.bilotta.bluefood.domain.cliente.Cliente;
import br.com.bilotta.bluefood.domain.cliente.ClienteRepository;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.bilotta.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bilotta.bluefood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository ClienteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		
		return "cliente-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = ClienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
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
	
	ControllerHelper.setEditMode(model, true);
	return "cliente-cadastro";
	}
	
}
