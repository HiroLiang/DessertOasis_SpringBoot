package com.dessertoasis.demo.model.member;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name="bank")
public class Bank {
//	bankCodeName branchName bankAccount 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "int")
	private Integer id;
	
	@Column(name="bankCodeName", columnDefinition = "nvarchar(100)")
	private String bankCodeName;
	
	@Column(name="branchName", columnDefinition = "nvarchar(100)")
	private String branchName;
	
	@Column(name="bankAccount", columnDefinition = "nvarchar(100)")
	private String bankAccount;
	
	public Bank(){
			
		}
	
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "memberId")
	 @JsonIgnore
	 private Member member;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankCodeName() {
		return bankCodeName;
	}

	public void setBankCodeName(String bankCodeName) {
		this.bankCodeName = bankCodeName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	 
}

