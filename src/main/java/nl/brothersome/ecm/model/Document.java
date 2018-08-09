package nl.brothersome.ecm.model;

import java.util.List;

public class Document {
	private int number; // 0-64
	// The attributes
	private String name;
	private int groupRights;
	private int author;
	private List<Integer> changers;

	private Document() {
		// Prevent to make a document without something
	}

	private List<Integer> physicalFolders;
	private List<String> contentFolders;

	public String giveName() {
		// List all folders etc.
		return "";
	}



}
