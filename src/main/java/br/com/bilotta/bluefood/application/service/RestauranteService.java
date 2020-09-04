package br.com.bilotta.bluefood.application.service;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bilotta.bluefood.domain.cliente.Cliente;
import br.com.bilotta.bluefood.domain.cliente.ClienteRepository;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapio;
import br.com.bilotta.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.bilotta.bluefood.domain.restaurante.Restaurante;
import br.com.bilotta.bluefood.domain.restaurante.RestauranteComparator;
import br.com.bilotta.bluefood.domain.restaurante.RestauranteRepository;
import br.com.bilotta.bluefood.domain.restaurante.SearchFilter;
import br.com.bilotta.bluefood.domain.restaurante.SearchFilter.SearchType;
import br.com.bilotta.bluefood.util.SecurityUtils;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado!");
		}
		
		if (restaurante.getId() != null) {
			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow(NoSuchElementException::new);
			restaurante.setSenha(restauranteDB.getSenha());
			restaurante.setLogotipo(restauranteDB.getLogotipo());
			restauranteRepository.save(restaurante);
		
		} else {
			restaurante.encriptPassword();
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente != null) {
			return false;
		}
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		
		if (restaurante != null) {
			if (id == null) {
				return false;
			}
			
			if (!restaurante.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Restaurante> search(SearchFilter filter) {
		List<Restaurante> restaurantes;
		
		if (filter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
		
		} else if (filter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
		
		} else {
			throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + "�n�o � suportado!");
		}
		
		Iterator<Restaurante> it = restaurantes.iterator();
		
		while (it.hasNext()) {
			Restaurante restaurante = it.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();
			
			if (filter.isEntregaGratis() && taxaEntrega > 0 || !filter.isEntregaGratis() && taxaEntrega == 0) {
				it.remove();
			}
		}
		
		
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);
		
		return restaurantes;
	}
	
	@Transactional
	public void saveItemCardapio(ItemCardapio itemCardapio) {
		itemCardapio = itemCardapioRepository.save(itemCardapio);
		itemCardapio.setImagemFileName();
		imageService.uploadComida(itemCardapio.getImageFile(), itemCardapio.getImagem());
	}
}
