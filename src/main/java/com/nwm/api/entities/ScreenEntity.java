/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ScreenEntity{
	
	private int id;
	private String screen_name;
	private int level;
	private int parent;
	private String path;
	private int has_child;
	private int status;
	private int is_delete;
	private String created_date;
	private String created_by;
	private String updated_date;
	private String updated_by;
	private String class_icon;
	private String link_demo;
	private String description;
	private int screen_mode;
	
	
	
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink_demo() {
		return link_demo;
	}
	public void setLink_demo(String link_demo) {
		this.link_demo = link_demo;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the screen_name
	 */
	public String getScreen_name() {
		return screen_name;
	}
	/**
	 * @param screen_name the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(int parent) {
		this.parent = parent;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the has_child
	 */
	public int getHas_child() {
		return has_child;
	}
	/**
	 * @param has_child the has_child to set
	 */
	public void setHas_child(int has_child) {
		this.has_child = has_child;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the is_delete
	 */
	public int getIs_delete() {
		return is_delete;
	}
	/**
	 * @param is_delete the is_delete to set
	 */
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	/**
	 * @return the created_date
	 */
	public String getCreated_date() {
		return created_date;
	}
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	/**
	 * @return the created_by
	 */
	public String getCreated_by() {
		return created_by;
	}
	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	/**
	 * @return the updated_date
	 */
	public String getUpdated_date() {
		return updated_date;
	}
	/**
	 * @param updated_date the updated_date to set
	 */
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	/**
	 * @return the updated_by
	 */
	public String getUpdated_by() {
		return updated_by;
	}
	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	/**
	 * @return the class_icon
	 */
	public String getClass_icon() {
		return class_icon;
	}
	/**
	 * @param class_icon the class_icon to set
	 */
	public void setClass_icon(String class_icon) {
		this.class_icon = class_icon;
	}
	
}
