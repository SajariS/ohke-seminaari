package RW.JuomaPeli.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	private String userName;
	
	@Enumerated(EnumType.STRING)
    private Decision decision;
	
	@OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
	private Character character;
	
	@ManyToOne
	@JoinColumn(name="gameId")
	private Game game;
	
	// Demoa varten helpotus
	private String code;
	
	private boolean host;
	
	
	
	public boolean getHost() {
		return host;
	}

	public void setHost(boolean isHost) {
		this.host = isHost;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Player(String userName, String code) {
		super();
		this.userName = userName;
		this.code = code;
	}
	
	

	public Player(String userName, String code, boolean isHost) {
		super();
		this.userName = userName;
		this.code = code;
		this.host = isHost;
	}

	public Player(String userName, Game game) {
		super();
		this.userName = userName;
		this.game = game;
	}
	
	public Player() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
    

}
