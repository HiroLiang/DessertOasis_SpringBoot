package com.dessertoasis.demo.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.model.member.MemberDetailRepository;

@Service
public class MemberDetailService {
	@Autowired
	private MemberDetailRepository memberDetailRepository;
	
	//查詢
	public MemberDetail getMemberDetailByMemberId(Integer memberId) {
		
		return memberDetailRepository.findByMemberId(memberId);
	}
	//更新
	 public MemberDetail updateMemberDetail(Integer id,MemberDetail newMemberDetail) {
	        // 根據需要添加驗證邏輯，例如檢查新數據是否有效

	        // 從資料庫中查找現有的 MemberDetail
	        Optional<MemberDetail> optional = memberDetailRepository.findById(newMemberDetail.getId());
	                
	        if(optional.isPresent()) {
	        // 更新 MemberDetail 的相關字段
	        	MemberDetail memberDetail = optional.get();
	        	memberDetail.setIdNumber(newMemberDetail.getIdNumber());
	        	memberDetail.setBirthday(newMemberDetail.getBirthday());
	        	memberDetail.setDeliveryAddress(newMemberDetail.getDeliveryAddress());
//	        	memberDetail.setFolderURL(newMemberDetail.getFolderURL());
//	        	memberDetail.setPic(newMemberDetail.getPic());

	        // 保存更新後的 MemberDetail
	        return memberDetailRepository.save(memberDetail);
	        }
	        throw new ResourceNotFoundException("MemberDetail not found");
	    }
	 
	 	//新增
		public void saveMemberDetail(MemberDetail memberDetail) {
			memberDetailRepository.save(memberDetail);
			
		}
	}
