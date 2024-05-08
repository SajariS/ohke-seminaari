package RW.JuomaPeli.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Character {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	private String name; 
	
	private int age;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "character_card", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Set<Card> characterCard = new HashSet<>();
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="playerId")
	private Player player;

	public Character(String name, int age, Player player) {
		super();
		this.name = name;
		this.age = age;
		this.player = player;
	}
	
	public Character () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	

	public Set<Card> getCharacterCard() {
		return characterCard;
	}

	public void setCharacterCard(Set<Card> characterCard) {
		this.characterCard = characterCard;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
