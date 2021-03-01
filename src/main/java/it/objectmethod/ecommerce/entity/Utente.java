package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "utente")
public class Utente {
	@GeneratedValue
	@Id
	@Column(name = "id_utente")
	private Long idUtente;

	@Column(name = "nome_utente")
	private String nomeUtente;

	@Column(name = "password")
	private String password;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_utente")
	private List<Ordine> ordiniUtente;
	// Un singolo utente può aver fatto più ordini

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Ordine> getOrdiniUtente() {
		return ordiniUtente;
	}

	public void setOrdiniUtente(List<Ordine> ordiniUtente) {
		this.ordiniUtente = ordiniUtente;
	}

}
