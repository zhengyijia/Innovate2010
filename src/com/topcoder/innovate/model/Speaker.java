/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.innovate.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Speaker implements Serializable {
    /**
     * <p>
     * The speaker name.
     * </p>
     */
    private String name;
    
    /**
     * <p>
     * The speaker title.
     * </p>
     */
    private String title;
    /**
     * <p>
     * The speaker picture.
     * </p>
     */
    private String picture;
    /**
     * <p>
     * The speaker details.
     * </p>
     */
    private String details;
    /**
     * <p>
     * The speaker session identificators.
     * </p>
     */
    private List<String> sessionIds;
    
    /**
     * <p>
     * Simple getter for a namesake field.
     * </p>
     * 
     * @return value of a namesake field.
     */
    public String getName() {
    	return this.name;
    }
    /**
     * <p>
     * Simple setter for a namesake field.
     * </p>
     * 
     * @param name - new value for a namesake field.
     */
    public void setName(String name) {
    	this.name = name;
    }
    /**
     * <p>
     * Simple getter for a namesake field.
     * </p>
     * 
     * @return value of a namesake field.
     */
    public String getTitle() {
    	return this.title;
    }
    /**
     * <p>
     * Simple setter for a namesake field.
     * </p>
     * 
     * @param name - new value for a namesake field.
     */
    public void setTitle(String title) {
    	this.title = title;
    }

    
    /**
     * <p>
     * Simple getter for a namesake field.
     * </p>
     * 
     * @return value of a namesake field.
     */
    public String getPicture() {
    	return this.picture;
    }
    /**
     * <p>
     * Simple setter for a namesake field.
     * </p>
     * 
     * @param picture - new value for a namesake field.
     */
    public void setPicture(String picture) {
    	this.picture = picture;

    }
    /**
     * <p>
     * Simple getter for a namesake field.
     * </p>
     * 
     * @return value of a namesake field.
     */
    public String getDetails() {
    	return this.details;
    }
    /**
	 * <p>
	 * Simple getter for a namesake field.
	 * </p>
	 * 
	 * @return value of a namesake field.
	 */
	
    public void setDetails(String details) {
    	this.details = details;
    }
    /**
	 * <p>
	 * Simple setter for a namesake field.
	 * </p>
	 * 
	 * @param details - new value for a namesake field.
	 */
	
    public List<String> getSessionIds() {
    	return this.sessionIds;
    }
    /**
     * <p>
     * Simple setter for a namesake field.
     * </p>
     * 
     * @param sessionIds - new value for a namesake field.
     */
    public void setSessionIds(List<String> sessionIds) {
    	this.sessionIds = sessionIds;
    }
}