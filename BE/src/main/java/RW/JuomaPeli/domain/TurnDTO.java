package RW.JuomaPeli.domain;

public class TurnDTO {
	
	private Long playersTurn;	
	private String code;
	//Voi olla lista jos halutan antaa montavaihehtoa
	//sitten vaatii vielä lisää parametreja siitä mikä kortti valitaan
	private Card pulledCard;
	private boolean choice;
	public TurnDTO(Long playersTurn, String code, Card pulledCard, boolean choice) {
		super();
		this.playersTurn = playersTurn;
		this.code = code;
		this.pulledCard = pulledCard;
		this.choice = choice;
	}
	public TurnDTO() {
		super();
	}
	public Long getPlayersTurn() {
		return playersTurn;
	}
	public void setPlayersTurn(Long playersTurn) {
		this.playersTurn = playersTurn;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Card getPulledCard() {
		return pulledCard;
	}
	public void setPulledCard(Card pulledCard) {
		this.pulledCard = pulledCard;
	}
	public boolean isChoice() {
		return choice;
	}
	public void setChoice(boolean choice) {
		this.choice = choice;
	}
	
	
}
