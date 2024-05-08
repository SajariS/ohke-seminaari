package RW.JuomaPeli.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "card")
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	//private String title;
	
	private String desc;
	
	private Boolean goodTrait;
	
	private Boolean userMade;
	
	/* Korjattu
	@JsonIgnore
	@ManyToMany
	private List<Character> character;
	*/ 
	/* Korjattu
	@JsonIgnore
	@ManyToMany
	private List<Game> game;
	*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getUserMade() {
		return userMade;
	}

	public void setUserMade(Boolean userMade) {
		this.userMade = userMade;
	}

	public Boolean getGoodTrait() {
		return goodTrait;
	}

	public void setGoodTrait(Boolean goodTrait) {
		this.goodTrait = goodTrait;
	}

	public Card(String desc, Boolean userMade, Boolean goodTrait) {
		super();
		//this.title = title;
		this.desc = desc;
		this.userMade = userMade;
		this.goodTrait = goodTrait;
	}

	public Card() {
		super();
	}
	
	
}
