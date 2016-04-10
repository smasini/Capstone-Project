package smasini.it.thetvdb.support;

public class Language {

   // private static String preferredLanguage = "";
	private String name;
	private String prefix;
	private String id;

	public Language(){}

	public Language(String name, String prefix, String id) {
		super();
		this.name = name;
		this.prefix = prefix;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
