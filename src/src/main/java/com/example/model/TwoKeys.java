package com.example.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

public class TwoKeys implements Serializable{
	private long idUser;
	private long idProd;
}