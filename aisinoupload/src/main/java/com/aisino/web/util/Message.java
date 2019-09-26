package com.aisino.web.util;

public class Message {
	private int level;
	private String message;

	private Message(int level, String message) {
		this.level = level;
		this.message = message;
	}

	public static Message SUCCESS(String message) {
		return new Message(1, message);
	}

	public static Message NOTICE(String message) {
		return new Message(2, message);
	}

	public static Message WARNING(String message) {
		return new Message(3, message);
	}

	public static Message ERROR(String message) {
		return new Message(4, message);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
