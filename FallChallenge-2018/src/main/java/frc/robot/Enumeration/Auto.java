package frc.robot.Enumeration;

public enum Auto {
    NOTHING("Do nothing"),
    SEARCH_AND_DESTROY("Search and destroy");

    private final String name;
	Auto(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}