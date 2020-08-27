package br.com.bilotta.bluefood.domain.pagamento;

import javax.validation.constraints.Pattern;

public class DadosCartao {
	
	@Pattern(regexp = "\\d{16}", message = "O n�mero do cartao � inv�lido!")
	private String numCartao;
	
	public String getNumCartao() {
		return numCartao;
	}
	
	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}
}
