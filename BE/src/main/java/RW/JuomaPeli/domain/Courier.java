package RW.JuomaPeli.domain;

public class Courier {

	private String text;
	private String name;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Courier(String text, String name) {
		super();
		this.text = text;
		this.name = name;
	}
	public Courier() {
		super();
	}
	
	
}
