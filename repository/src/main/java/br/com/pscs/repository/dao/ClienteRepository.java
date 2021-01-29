package br.com.pscs.repository.dao;

import java.util.List;
import java.util.Optional;

import br.com.pscs.domain.model.Cliente;

public class ClienteRepository implements Repository<Cliente> {

	public Optional<List> findAll() {
		return Repository.super.findAll(Cliente.class);
	}

	public Optional<Cliente> findById(Integer id) {
		return Repository.super.findById(id, Cliente.class);
	}

	public Optional<Cliente> delete(Integer id) {
		return Repository.super.delete(Cliente.class, id);
	}

	public  Optional<List> findAllByNome(String search) {
		return findAllBy("nome", search);
	}

	public Optional<List> findAllBy(String campo, String search) {
		return Repository.super.findAllBy(campo, search, Cliente.class);
	}
}
