package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Articolo;
import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Utente;
import it.objectmethod.ecommerce.repository.ArticoloRepository;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.UtenteRepository;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private ArticoloRepository artRep;
	@Autowired
	private UtenteRepository uteRep;
	@Autowired
	private CartRepository carRep;

	@GetMapping("/aggiungi")
	public ResponseEntity<Cart> aggiungiProdotto(@RequestParam("qta") Integer qta,
			@RequestParam("id_art") Integer idArticolo, @RequestParam("user") Long idUtente) {
		ResponseEntity<Cart> resp = null;
		Utente user = uteRep.findById(idUtente).get();
		Optional<Articolo> optArt = artRep.findById(idArticolo);
		boolean t = true;

		if (qta < 1) {
			resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
			return resp;
		}

		if (optArt.isPresent()) {

			Articolo art = optArt.get();
			// Controllo subito se la quantità richiesta è inferiore della disponibilità del
			// prodotto
			if (qta > art.getDisponibilità()) {
				resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
				return resp;
			}

			Cart carrello = null;
			carrello = carRep.findByUserIdUtente(idUtente);
			if (carrello == null) {
				carrello = new Cart();
				carrello.setUser(user);
				carrello.setListaSpesa(new ArrayList<CartDetail>());
				t = false;
			}

			CartDetail dettaglio = new CartDetail();
			dettaglio.setArticolo(art);
			if (t) {

				for (int i = 0; i < carrello.getListaSpesa().size(); i++) {

					if (art == carrello.getListaSpesa().get(i).getArticolo()) {
						carrello.getListaSpesa().get(i)
								.setQuantita(qta + carrello.getListaSpesa().get(i).getQuantita());
						carrello = carRep.save(carrello);
						resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
						return resp;
					} else {
						dettaglio.setQuantita(qta);
					}
				}
			} else {
				dettaglio.setQuantita(qta);
			}

			List<CartDetail> lista = carrello.getListaSpesa();
			lista.add(dettaglio);
			carrello.setListaSpesa(lista);
			carrello = carRep.save(carrello);

			// Modifichiamo la disponibilità dell'articolo
			art.setDisponibilità(art.getDisponibilità() - qta);
			art = artRep.save(art);

			resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}