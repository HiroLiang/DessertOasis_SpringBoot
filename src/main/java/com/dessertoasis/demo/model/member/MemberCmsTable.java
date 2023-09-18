package com.dessertoasis.demo.model.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberCmsTable {
	
	private Integer id;
	private String account;
	private String fullName;
	private String memberName;
	private String email;
	private MemberAccess access;
	private MemberState memberStatus;
	private Date signDate;
	
	
	
	public MemberCmsTable() {
		super();
	}



	public MemberCmsTable(Integer id, String account, String fullName, String memberName, String email,
			MemberAccess access, MemberState memberStatus, Date signDate) {
		super();
		this.id = id;
		this.account = account;
		this.fullName = fullName;
		this.memberName = memberName;
		this.email = email;
		this.access = access;
		this.memberStatus = memberStatus;
		this.signDate = signDate;
	}



	public MemberCmsTable(Integer id, String account, String fullName, String memberName, String email,
			Date signDate) {
		super();
		this.id = id;
		this.account = account;
		this.fullName = fullName;
		this.memberName = memberName;
		this.email = email;
		this.signDate = signDate;
	}
	
	
	
	
}
