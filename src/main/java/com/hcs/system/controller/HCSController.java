package com.hcs.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcs.system.model.Cliente;
import com.hcs.system.repository.clientes;
import com.hcs.system.service.CadastroClienteService;

@Controller
@RequestMapping("/clientes")
public class HCSController {
	
private static final String CADASTRO_VIEW = "CadastroCliente";
	
	@Autowired
	private clientes clientes;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Cliente());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Cliente cliente, Errors errors, RedirectAttributes attributes) {
		
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try {
			cadastroClienteService.salvar(cliente);
			attributes.addFlashAttribute("mensagem","Cliente salvo com sucesso!");
			return "redirect:/clientes/novo";
		}catch(DataIntegrityViolationException e) {
			errors.rejectValue("dataNascimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Cliente> todosClientes = clientes.findAll();
		ModelAndView mv = new ModelAndView("PesquisaClientes");
		mv.addObject("clientes",todosClientes);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Cliente cliente) { 
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(cliente);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroClienteService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem","Titulo excluído com sucesso!");
		return "redirect:/clientes";
	}
	
}
