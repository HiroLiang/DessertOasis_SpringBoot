package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;

import lombok.Data;

/*
 * 定攤後台回傳表格格式
 * */

@Data
public class OrderCmsTable {
	
	private Integer id;
	private String fullName;
	private LocalDateTime ordDate;
	private LocalDateTime updateDate;
	private String ordStatus;

	public OrderCmsTable() {
		super();
	}

	public OrderCmsTable(Integer id, String fullName, LocalDateTime ordDate, LocalDateTime updateDate,
			String ordStatus) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.ordDate = ordDate;
		this.updateDate = updateDate;
		this.ordStatus = ordStatus;
	}


}
