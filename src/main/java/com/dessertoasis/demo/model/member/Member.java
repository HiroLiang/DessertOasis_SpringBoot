package com.dessertoasis.demo.model.member;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity @Table(name="member")
public class Member { //與會員帳號相關
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="memberId" , columnDefinition = "int")
	private Integer memberId;
	
	@Column(name="fullName", columnDefinition ="NVARCHAR(100)")
	private String fullName;
	
	@Column(name="memberName", columnDefinition ="NVARCHAR(100)")
	private String memberName;
	
	@Column(name="account", columnDefinition ="VARCHAR(50)")
	private String account;
	
	@Column(name="email", columnDefinition ="VARCHAR(50)")
	private String email;
	
	@Column(name="passwords", columnDefinition ="VARCHAR(max)")
	private String passwords;
	
	@Column(name="emailForCode", columnDefinition ="VARCHAR(50)")
	private String emailForCode;
	
	@Column(name="code", columnDefinition ="VARCHAR(100)")
	private String code;
	
	@Column(name="access", columnDefinition ="NVARCHAR(30)")
	private String access;
	
	@Enumerated(EnumType.STRING)
	@Column(name="memberStatus" , columnDefinition = "nvarchar(30)")
	private MemberState memberStatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="signDate" , columnDefinition ="datetime2(6)")
	private Date signDate;

	@OneToMany(mappedBy = "recipeAuthor",cascade = CascadeType.ALL)
	private List<Recipes> recipes;
	
	public Member() {
	}
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    private MemberDetail memberDetail;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    private Company company;
	
	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Order> orderList;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getEmailForCode() {
		return emailForCode;
	}

	public void setEmailForCode(String emailForCode) {
		this.emailForCode = emailForCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public MemberState getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberState memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public List<Recipes> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipes> recipes) {
		this.recipes = recipes;
	}

	public MemberDetail getMemberDetail() {
		return memberDetail;
	}

	public void setMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	
}
