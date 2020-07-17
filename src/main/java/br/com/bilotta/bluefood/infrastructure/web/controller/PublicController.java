package br.com.bilotta.bluefood.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bilotta.bluefood.domain.cliente.Cliente;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

	@GetMapping("cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente-cadastro";
	}
}
