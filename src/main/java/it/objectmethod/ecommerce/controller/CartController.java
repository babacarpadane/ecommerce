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

		/*
		 * if (qta < 1) { resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
		 * return resp; }
		 */
		// Ora permettiamo l'inserimento di un numero negativo (solamente se il prodotto
		// è gia
		// in carrello e voglio rimuoverne una quantità

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
			if (t) { // entra in questo if solamente se non è un carrello nuovo

				for (int i = 0; i < carrello.getListaSpesa().size(); i++) { // ciclo tutta la lista della spesa per
																			// vedere
					// se l'articolo che l'utente sta provando a inserire è già presente o meno

					if (art == carrello.getListaSpesa().get(i).getArticolo()) {
						if (qta + carrello.getListaSpesa().get(i).getQuantita() < 0) {
							resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
							return resp; // in questo caso qta è per forza negativo, cioè l'utente sta provando a
											// rimuovere
							// articoli. Se il suo modulo è > della quantità che
							// c'è nel carrello, allora restituisce errore
						} else {
							if (qta + carrello.getListaSpesa().get(i).getQuantita() == 0) {
								// in questo caso l'utente vuole rimuovere tutti gli elementi
								// di quel determinato articolo, perciò rimuoviamo l'intera riga ordine
								carrello.getListaSpesa().remove(i);
								art.setDisponibilità(art.getDisponibilità() - qta);
								art = artRep.save(art);
								resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
								return resp;
							} else { // cioè se vuole rimuovere prodotti ma non tutti
								carrello.getListaSpesa().get(i)
										.setQuantita(qta + carrello.getListaSpesa().get(i).getQuantita());
								carrello = carRep.save(carrello);
								art.setDisponibilità(art.getDisponibilità() - qta); // aggiorno la disponibilità nel db
								art = artRep.save(art);
								resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
								return resp;
							}
						}
					} else { // se l'articolo non è già presente
						dettaglio.setQuantita(qta);
						art.setDisponibilità(art.getDisponibilità() - qta);
						art = artRep.save(art);
					}
				}
			} else { // se non è un carrello nuovo
				dettaglio.setQuantita(qta);
				art.setDisponibilità(art.getDisponibilità() - qta);
				art = artRep.save(art);
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