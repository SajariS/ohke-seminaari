package RW.JuomaPeli.domain;

public class LobbyEvent {
	
	private Long kickedPlayerId;

	public Long getKickedPlayerId() {
		return kickedPlayerId;
	}

	public void setKickedPlayerId(Long kickedPlayerId) {
		this.kickedPlayerId = kickedPlayerId;
	}

	public LobbyEvent(Long kickedPlayerId) {
		super();
		this.kickedPlayerId = kickedPlayerId;
	}
	
	

}
