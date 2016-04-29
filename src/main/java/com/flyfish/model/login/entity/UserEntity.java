package com.flyfish.model.login.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.flyfish.common.BasisEntity;

/**
 * description: 
 *<p>
 * @author  hongpingliu@creditease.cn
 *<p>
 * create date：2016年4月28日 上午9:31:11 
 *</p>
  * @version 1.0  
*/
@Table(name="user")
public class UserEntity extends BasisEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 5023322322673390432L;
    
    @Column(name="id")
    private long id;
    @Column(name="tutorial_title")
    private String title;
    @Column(name="tutorial_author")
    private String author;
    @Column(name = "submission_date")
    protected Date submissionDate;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Date getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
    
}
