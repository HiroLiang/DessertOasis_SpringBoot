package com.dessertoasis.demo.model.membership;

import java.util.LinkedHashSet;
import java.util.Set;

import com.dessertoasis.demo.model.recipe.Recipe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data
@Table(name="membership")
public class Membership {
	
	@Id @Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//會員名稱
	@Column(name="MEMBERNAME")
	private String memberName;
	//暱稱
	@Column(name="NICKNAME")
	private String nickName;
	//帳號
	@Column(name="ACCOUNT")
	private String accoount;
	//密碼
	@Column(name="PASSWORD")
	private String password;
	//Email
	@Column(name="EMAIL")
	private String email;
	//第三方唯一識別碼
	@Column(name="GOOGLEAPICLIENTID")
	private String googleApiClientId;
	//身分證字號
	@Column(name="IDENTITYNUMBER")
	private String identityNumer;
	//電話號碼
	@Column(name="PHONENUMBER")
	private String phoneNumber;
	//手機號碼
	@Column(name="CELLPHONENUMBER")
	private String cellphoneNumer;
	//地址
	@Column(name="ADDRESS")
	private String address;
	//個人頭像
	@Column(name="PROFILEPIC")
	private byte[] profilePic;
	//帳號權限
	@Column(name="ACCESS")
	private String access;
	//帳號狀態
	@Column(name="ACCOUNTSTATE")
	private String accountState;
	//個人圖庫
	@Column(name="IMAGEGALLERY")
	private String imageGallery;
	
	//作為FK連結食譜RECIPEAUTHORID
	@OneToMany(mappedBy = "recipeAuthorID" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<Recipe> recipes = new LinkedHashSet<>();
	
	public Membership() {
	}

}
