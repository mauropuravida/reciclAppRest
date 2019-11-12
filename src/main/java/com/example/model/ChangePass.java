package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ChangePass{

	  @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  @Column(name = "token")
	  private String idChange;
	  
	  @Column(name = "date")
	  private java.sql.Timestamp fecha;
	  
	  @Column(name = "user")
	  private long user;
	  
	  public ChangePass() {
		  fecha = new java.sql.Timestamp(System.currentTimeMillis());
	  }
	  
	  public void setId(String id) {
		  idChange=id;
	  }
	  
	  public String getIdChange() {
		  return idChange;
	  }
	  
	  public java.sql.Timestamp getFecha(){
		  return fecha;
	  }

	public long getUser() {
		return user;
	}
	
	public void setUser(long l) {
		this.user=l;
	}
	
}
