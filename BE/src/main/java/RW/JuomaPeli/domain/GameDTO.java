package RW.JuomaPeli.domain;

import java.util.List;

public class GameDTO {

	private String code;
	private Long id;
	private List<Player> players;
	private List<Card> cards;
	private boolean started;
	
	public GameDTO(String code, boolean started) {
		super();
		this.code = code;
		this.started = started;
	}
	public GameDTO(String code, Long id, List<Player> players, List<Card> cards) {
		super();
		this.code = code;
		this.id = id;
		this.players = players;
		this.cards = cards;
	}
	public GameDTO(String code) {
		super();
		this.code = code;
	}
	public GameDTO() {
		super();
	}
	
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	
}
