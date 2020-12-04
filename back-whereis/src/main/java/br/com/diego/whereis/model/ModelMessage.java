package br.com.diego.whereis.model;

public class ModelMessage {
	private String message;

	public ModelMessage() {

	}

	public ModelMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("Model [message=%s]", message);
	}
}
