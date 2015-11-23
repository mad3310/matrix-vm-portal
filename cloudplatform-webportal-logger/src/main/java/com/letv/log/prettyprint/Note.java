package com.letv.log.prettyprint;

import org.apache.commons.lang3.ObjectUtils;

public class Note {
	public static enum Type {
		NULL("null"), REPEAT("repeat"), UNAVAILABLE("unavailable"), HIDDEN("hidden"), PRIVATE("private"), INSTANCE("instance of");
		
		private final String label;
		
		private Type(String label) {
			this.label = label;
		}
		
		public String toString() {
			return this.label;
		}
	}
	
	private final Type type;
	private final String text;
	
	public Note(Type type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public String toString() {
		return "[" + type + ": " + text + "]";
	}
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}
		
		Note other = (Note)o;
		
		return this.type == other.type && ObjectUtils.equals(this.text, other.text);
	}
}