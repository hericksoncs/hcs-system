package com.hcs.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcs.system.model.Cliente;

public interface clientes extends JpaRepository<Cliente, Long>{

}
