package it.objectmethod.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "articolo")
public class Articolo {
	@GeneratedValue
	@Id
	@Column(name = "id_articolo")
	private Integer idArticolo;

	@Column(name = "codice_articolo")
	private String codiceArticolo;

	@Column(name = "nome_articolo")
	private String nomeArticolo;

	@Column(name = "disponibilita")
	private int disponibilita;

	@Column(name = "prezzo_unitario")
	private double prezzoUnitario;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Ordine> ordiniArticolo;

	public int getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(int idArticolo) {
		this.idArticolo = idArticolo;
	}

	public String getCodiceArticolo() {
		return codiceArticolo;
	}

	public void setCodiceArticolo(String codiceArticolo) {
		this.codiceArticolo = codiceArticolo;
	}

	public String getNomeArticolo() {
		return nomeArticolo;
	}

	public void setNomeArticolo(String nomeArticolo) {
		this.nomeArticolo = nomeArticolo;
	}

	public int getDisponibilità() {
		return disponibilita;
	}

	public void setDisponibilità(int disponibilità) {
		this.disponibilita = disponibilità;
	}

	public double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public List<Ordine> getOrdiniArticolo() {
		return ordiniArticolo;
	}

	public void setOrdiniArticolo(List<Ordine> ordiniArticolo) {
		this.ordiniArticolo = ordiniArticolo;
	}

}
