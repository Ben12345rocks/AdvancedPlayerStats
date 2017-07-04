package com.Ben12345rocks.AdvancedPlayerStats.Rewards;

public enum OntimeAchivement {
	HOURS("Hours"),
	DAYS("Days");

	public static OntimeAchivement getTimeType(String str) {
		for (OntimeAchivement a : values()) {
			if (a.toString().equalsIgnoreCase(str)) {
				return a;
			}
		}
		return HOURS;
	}

	private OntimeAchivement(String text) {
		this.text = text;
	}

	private String text;

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
