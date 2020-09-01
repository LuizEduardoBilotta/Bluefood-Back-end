package br.com.bilotta.bluefood.domain.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query("SELECT p from Pedido p where p.cliente.id = ?1 ORDER BY p.data DESC")
	public List<Pedido> listPedidosByCliente(Integer clienteId);
	
	public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);
}
