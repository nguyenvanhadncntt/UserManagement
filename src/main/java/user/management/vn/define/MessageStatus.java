package user.management.vn.define;

public enum MessageStatus {
	CHAT("CHAT"), 
	LEAVE("LEAVE"), 
	JOIN("JOIN");
	private String value;

	private MessageStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
