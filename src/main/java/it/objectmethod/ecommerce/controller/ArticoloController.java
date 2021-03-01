package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Articolo;
import it.objectmethod.ecommerce.repository.ArticoloRepository;

@RestController
@RequestMapping("/articolo")
public class ArticoloController {
	@Autowired
	private ArticoloRepository repArticolo;

	@GetMapping("/trova-articoli")
	public List<Articolo> stampaArticoli(@RequestParam("name") String name,
			@RequestParam("idArticolo") String idArticolo) {
		ArrayList<Articolo> articoloTrovato = repArticolo.trovaArticoli(name, idArticolo);
		return articoloTrovato;
	}

}
