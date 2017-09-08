package com.hcs.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hcs.system.model.Cliente;
import com.hcs.system.repository.clientes;

@Service
public class CadastroClienteService {

	@Autowired
	private clientes clientes;
	
	public void salvar(Cliente cliente) {
		try {
			clientes.save(cliente);
		} catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		clientes.delete(codigo);
	}
}
