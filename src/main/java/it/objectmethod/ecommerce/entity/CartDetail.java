package it.objectmethod.ecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "carrello_dettaglio")
public class CartDetail {
	@GeneratedValue
	@Id
	@Column(name = "id_carrello_dettaglio")
	private Long idCarrelloDettaglio;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name="id_carrello")
	private Cart carrello;

	@Column(name = "quantita")
	private Integer quantita;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_articolo")
	private Articolo articolo;

	public Cart getCarrello() {
		return carrello;
	}

	public void setCarrello(Cart carrello) {
		if (carrello == null) {
			carrello = new Cart();
		}
		this.carrello = carrello;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo id_articolo) {
		this.articolo = id_articolo;
	}

	public Long getIdCarrelloDettaglio() {
		return idCarrelloDettaglio;
	}

	public void setIdCarrelloDettaglio(Long idCarrelloDettaglio) {
		this.idCarrelloDettaglio = idCarrelloDettaglio;
	}

}
