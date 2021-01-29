package br.com.pscs.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.pscs.domain.model.Cliente;
import br.com.pscs.domain.model.Telefone;
import br.com.pscs.repository.dao.ClienteRepository;
import br.com.pscs.web.service.ClienteService;
import lombok.Data;

@Named
@SessionScoped
@Data
public class ClienteBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ClienteService service;
	private ClienteRepository repository;
	private Cliente cliente;
	private List<Cliente> clientes;
	private boolean isCreated = false;
	private String txtPerquisa;

	@PostConstruct
	public void init() {
		clientes = new ArrayList<Cliente>();
		repository = new ClienteRepository();
		cliente = new Cliente();
		cliente.setTelefone(new Telefone());
		// service = new ClienteService();
		 findAll();

	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public void showInfo(String infoMessage, String content) {
		addMessage(FacesMessage.SEVERITY_INFO, infoMessage, content);
	}

	private void resetCliente() {
		cliente = new Cliente();
		cliente.setTelefone(new Telefone());
	}

	public void save() {
		service.save(this.cliente, repository, clientes);
		//findAll();
		PrimeFaces.current().executeScript("PF('createClienteDialog').hide()");
		resetCliente();

	}

	public void update(Integer id, Cliente cliente) {
		service.update(id, cliente, repository, clientes);
		resetCliente();
	}

	public void delete(Cliente cliente) {
		service.delete(cliente, repository, clientes);
	}

	public void findAll() {
		Optional<List> clientesOptional = repository.findAll();
		clientesOptional.ifPresentOrElse(value -> {
			this.clientes = value;
			addMessage(FacesMessage.SEVERITY_INFO, "Pesquisa",
					"possuem" + this.clientes.size() + " clientes cadastrados");
		}, () -> {
			addMessage(FacesMessage.SEVERITY_WARN, "Pesquisa", "Não possue dados na lista");
		});

	}

	public void consultar() {
		if (!txtPerquisa.isBlank() || !txtPerquisa.isEmpty()) {
			Optional<List> clienteOptional = repository.findAllByNome(txtPerquisa);

			clienteOptional.ifPresentOrElse(value -> {
				this.clientes = value;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Search", "Foram encontrados "));

			}, () -> {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Search", "Não consta Dados"));

			});

		} else {
			findAll();
		}
	}

}
