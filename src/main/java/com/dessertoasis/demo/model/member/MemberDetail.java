package com.dessertoasis.demo.model.member;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity @Table(name="memberDetail")
public class MemberDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "int")
	private Integer id;
	
	@Column(name="idNumber", columnDefinition = "varchar(10)")
	private String idNumber;
	
	@Column(name="birthday", columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(name="deliveryAddress", columnDefinition = "nvarchar(100)")
	private String deliveryAddress;
	
	@Column(name="pic", columnDefinition = "varchar(max)")
	private String pic;
	
	@Column(name="folderURL", columnDefinition = "varchar(max)")
	private	String folderURL;
	
	public MemberDetail(){
		
	}
	
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "memberId")
	 private Member member;
}
