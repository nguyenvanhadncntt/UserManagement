package user.management.vn.websocket.model;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sender sender;
	private Object message;
	private Date dateSend;
	private Date dateSeen;
	
	public ChatMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public ChatMessage(Sender sender, Object message, Date dateSend, Date dateSeen) {
		super();
		this.sender = sender;
		this.message = message;
		this.dateSend = dateSend;
		this.dateSeen = dateSeen;
	}

	public Sender getSender() {
		return sender;
	}
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public Date getDateSend() {
		return dateSend;
	}
	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}
	public Date getDateSeen() {
		return dateSeen;
	}
	public void setDateSeen(Date dateSeen) {
		this.dateSeen = dateSeen;
	}
	
}
