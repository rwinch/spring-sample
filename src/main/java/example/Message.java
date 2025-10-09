package example;

import java.util.Objects;

public class Message {
	public static final String DEFAULT_MESSAGE = "Hello Jackson!";

	private String text;

	public Message() {
		this(DEFAULT_MESSAGE);
	}

	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Message message))
			return false;
		return Objects.equals(text, message.text);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(text);
	}

	@Override
	public String toString() {
		return "Message [text=" + text + "]";
	}
}
