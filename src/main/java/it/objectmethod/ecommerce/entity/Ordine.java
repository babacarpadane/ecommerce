package it.objectmethod.ecommerce.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ordine")
public class Ordine {
	@GeneratedValue
	@Id
	@Column(name = "id_ordine")
	private Integer idOrdine;

	@Column(name = "numero_ordine")
	private String numeroOrdine;

	@Column(name = "data_ordine")
	private Date dataOrdine;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_utente")
	private Utente utenteOrdine;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "riga_ordine", joinColumns = @JoinColumn(name = "id_ordine", referencedColumnName = "id_ordine"), inverseJoinColumns = @JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo"))
	private List<RigaOrdine> righeOrdine;

	public Integer getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Integer idOrdine) {
		this.idOrdine = idOrdine;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Utente getUtenteOrdine() {
		return utenteOrdine;
	}

	public void setUtenteOrdine(Utente utenteOrdine) {
		this.utenteOrdine = utenteOrdine;
	}

	public List<RigaOrdine> getArticoliOrdine() {
		return righeOrdine;
	}

	public void setArticoliOrdine(List<RigaOrdine> righeOrdine) {
		this.righeOrdine = righeOrdine;
	}

}
