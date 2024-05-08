package RW.JuomaPeli.dto;

public class PlayerDTO {
	
	private String userName;
    private String code;
    private boolean host;
    
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean getHost() {
		return host;
	}
	public void setHost(boolean isHost) {
		this.host = isHost;
	}
	public PlayerDTO(String userName, String code, boolean isHost) {
		super();
		this.userName = userName;
		this.code = code;
		this.host = isHost;
	}
    
}
