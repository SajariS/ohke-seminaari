package RW.JuomaPeli.domain;

import java.util.List;

public class CharacterDTO {

	private Long id;
	private String name;
	private Player player;
	private int age;
	private List<Card> cards;

	public CharacterDTO(Long id, String name, Player player, int age, List<Card> cards) {
		super();
		this.id = id;
		this.name = name;
		this.player = player;
		this.age = age;
		this.cards = cards;
	}

	public CharacterDTO(String name, Player player, int age) {
		super();
		this.name = name;
		this.player = player;
		this.age = age;
	}

	public CharacterDTO() {
		super();
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
