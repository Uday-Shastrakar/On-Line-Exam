package com.exam.model.exam;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
	private Long qId;
    
    private String title;
    @Column(length = 5000)
    private String description;
    
	private String maxMarks;
	private String numberOfQuestions;
	private boolean active = false;
	//add
	
	//many to one (category)
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
	
	//code
	@OneToMany(mappedBy ="quiz",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Question> questions= new HashSet<>();
	
	
	
	
	public Quiz() {
		
	}
	
	
	public Long getQid() {
		return qId;
	}
	public void setQid(Long qid) {
		this.qId = qid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getNumberOfQuestions() {
		return numberOfQuestions;
	}
	public void setNumberOfQuestions(String numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Set<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	
	
}
