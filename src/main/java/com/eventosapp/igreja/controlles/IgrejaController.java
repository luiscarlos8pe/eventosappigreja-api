package com.eventosapp.igreja.controlles;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.igreja.models.Evento;
import com.eventosapp.igreja.models.Igreja;
import com.eventosapp.igreja.repository.EventoRepository;
import com.eventosapp.igreja.repository.IgrejaRepository;


@Controller
public class IgrejaController {
	
	
	@Autowired
	private IgrejaRepository igrejaRepository;
	
	@Autowired
	private EventoRepository  eventoRepository;
	
	@RequestMapping(value = "/cadastrarIgreja", method = RequestMethod.GET)
	public String formIgreja() {
		return "igreja/formIgreja";
	}

	@RequestMapping(value = "/cadastrarIgreja", method = RequestMethod.POST)
	public String formIgreja(@Valid Igreja igreja, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarIgreja";
		}
		igrejaRepository.save(igreja);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarIgreja";
	}
	
	@RequestMapping("/igrejas")
	public ModelAndView listaEventos() {
		ModelAndView modelAndView = new ModelAndView("index");
		Iterable<Igreja> igrejas = igrejaRepository.findAll(); // busca lista de igrejas	
		modelAndView.addObject("igreja", igrejas);//a primeira igreja e o que esta na index ,a segunda igrejas e a lista	
		return modelAndView;
		
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalheIgreja(@PathVariable("codigo") long codigo){
		Igreja igreja = igrejaRepository.findByCodigo(codigo);
		ModelAndView modelAndView = new ModelAndView("igreja/detalhesIgreja");
		modelAndView.addObject("igreja", igreja);
		
		Iterable<Evento> eventos = eventoRepository.findByIgreja(igreja);
		modelAndView.addObject("eventos", eventos);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalheIgrejaPost(@PathVariable("codigo") long codigo, @Valid Evento evento, BindingResult result, RedirectAttributes atributes) {
		if(result.hasErrors()) {
			atributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		Igreja igreja = igrejaRepository.findByCodigo(codigo);
		evento.setIgreja(igreja);
		eventoRepository.save(evento);
		atributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
		
	
	
	@RequestMapping("/deletarIgreja")
	public String deletarIgreja(long codigo) {
		Igreja igreja = igrejaRepository.findByCodigo(codigo);
		igrejaRepository.delete(igreja);
		return "redirect:/igrejas";
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		eventoRepository.delete(evento);
		
		Igreja igreja = evento.getIgreja();
		long codigoLong = igreja.getCodigo();
		String codigo1 = "" + codigoLong;
		return "redirect:/" + codigo1;
		
	}
	

	
}
