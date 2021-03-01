package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrello")
public class Cart {
	@GeneratedValue
	@Id
	@Column(name = "id_carrello")
	private Long idCarrello;

	@OneToOne
	@JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
	private Utente user;

	@JoinColumn(name = "id_carrello")
	@OneToMany(cascade = CascadeType.ALL)
	private List<CartDetail> listaSpesa;

	public Long getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(Long idCarrello) {
		this.idCarrello = idCarrello;
	}

	public Utente getUser() {
		return user;
	}

	public void setUser(Utente user) {
		this.user = user;
	}
	
	public List<CartDetail> getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(List<CartDetail> listaSpesa) {
		this.listaSpesa = listaSpesa;
	}

}
