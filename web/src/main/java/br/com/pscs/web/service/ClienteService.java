package br.com.pscs.web.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.pscs.domain.model.Cliente;
import br.com.pscs.repository.dao.ClienteRepository;
import lombok.NoArgsConstructor;

@Named
@ViewScoped
public class ClienteService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
	}

	private Optional<Cliente> isPresent(Optional<Cliente> clienteOptional, String sumary, String detailOk,
			String detailError) {
		if (clienteOptional.isPresent()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, sumary, detailOk));
			return Optional.of(clienteOptional.get());
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, sumary, detailError));
			return Optional.empty();
		}
	}

	public void save(Cliente cliente, ClienteRepository repository, List lista) {
		Optional<Cliente> clienteOptional = repository.save(cliente);
		isPresent(clienteOptional, "Cadastro", "Cliente Cadastrado com sucesso", "Erro ao cadastrar!!")
				.map(clienteAux -> {
					lista.add(clienteAux);
					return Void.TYPE;
				});

	}

	public void update(Integer oldClienteId, Cliente newCliente, ClienteRepository repository, List lista) {
		Optional<Cliente> clienteOptional = repository.update(newCliente, oldClienteId);
		isPresent(clienteOptional, "Cadastro", "Cliente Atualizado com sucesso", "Erro ao Atualizar!!")
				.map(clienteAux -> {
					if (lista.contains(clienteAux)) {
						lista.remove(clienteAux);
						newCliente.setId(clienteAux.getId());
						lista.add(newCliente);

					}

					return Void.TYPE;
				});

	}

	public void consultar(String search, ClienteRepository repository, List lista) {
		Optional<List> clienteOptional = repository.findAllByNome(search);

		if (clienteOptional.isPresent()) {
			lista = clienteOptional.get();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Search", "Foram encontrados " ));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Search", "Impossivel localizar o Cliente"));
		}

	}

	public void delete(Cliente cliente, ClienteRepository repository, List lista) {
		Optional<Cliente> clienteOptional = repository.delete(cliente);
		isPresent(clienteOptional, "Deletado", "Cliente Removido com sucesso", "Erro ao remover Cliente !!")
				.map(clienteAux -> {
					if (lista.contains(clienteAux)) {
						lista.remove(clienteAux);
					}

					return Void.TYPE;
				});
	}

}
