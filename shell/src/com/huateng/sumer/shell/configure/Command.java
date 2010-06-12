package com.huateng.sumer.shell.configure;

import java.util.ArrayList;
import java.util.List;

public class Command {
	private String name;
	private String handler;
	private boolean quit = false;
	private String description = "";
	private String ant;
	private String target;

	private List<Dependency> dependencies = new ArrayList<Dependency>();
	
	public void addDepenedency(Dependency dependency)
	{
		dependencies.add(dependency);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public boolean isQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnt() {
		return ant;
	}

	public void setAnt(String ant) {
		this.ant = ant;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}
}
