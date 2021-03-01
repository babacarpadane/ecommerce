package it.objectmethod.ecommerce.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Ordine;
import it.objectmethod.ecommerce.entity.RigaOrdine;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.OrdineRepository;
import it.objectmethod.ecommerce.repository.RigaOrdineRepository;

@RestController
@RequestMapping("/api")
public class OrdineController {
	@Autowired
	private CartRepository carRep;
	
	@Autowired
	private OrdineRepository ordRep;
	
	@Autowired
	private RigaOrdineRepository rigRep;

	@PostMapping("/crea-ordine")
	public ResponseEntity<Ordine> stampaOrdine(@RequestParam("id_utente") Long idUtente) {
		
		ResponseEntity<Ordine> resp = null;
		
		// Creazone codice alfanumerico di 5 caratteri
		String code = null;
		List<String> codeList = new ArrayList<String>();
		do {
			StringBuilder codice = new StringBuilder();
			Random rand = new Random();
			codice.append((char) (rand.nextInt(25) + 65));
			codice.append((char) (rand.nextInt(9) + 48));
			codice.append((char) (rand.nextInt(9) + 48));
			codice.append((char) (rand.nextInt(9) + 48));
			codice.append((char) (rand.nextInt(9) + 48));
			codice.append((char) (rand.nextInt(9) + 48));
			code = codice.toString();
		} while (codeList.contains(code)); // Se è già presente nella lista, ricicla e ne crea uno nuovo
		
		Cart carrello = carRep.findByUserIdUtente(idUtente);
		Ordine ordine = new Ordine();
		ordine.setNumeroOrdine(code);
		ordine.setUtenteOrdine(carrello.getUser());
		Date data = Date.valueOf(getLocalDate());
		ordine.setDataOrdine(data);
			
		List<CartDetail> listaSpesa = carrello.getListaSpesa();
		List<RigaOrdine> listaRighe = new ArrayList<RigaOrdine>();
		RigaOrdine riga = null;
		ordine = ordRep.save(ordine);
		for (int i = 0; i < listaSpesa.size(); i++) {
			riga = new RigaOrdine();
			riga.setArticolo(listaSpesa.get(i).getArticolo());
			riga.setQuantita(listaSpesa.get(i).getQuantita());
			riga.setOrdine(ordine);
			listaRighe.add(riga);
			riga = rigRep.save(riga);
		}
		ordine.setArticoliOrdine(listaRighe);
		carRep.delete(carrello);
		resp = new ResponseEntity<Ordine>(ordine, HttpStatus.OK);
		return resp;
	}
	
	public static LocalDate getLocalDate() {
	    return LocalDate.now();
	}
}
