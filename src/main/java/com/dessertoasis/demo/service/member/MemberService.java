package com.dessertoasis.demo.service.member;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.EmailUtil;
import com.dessertoasis.demo.OtpUtil;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberCmsTable;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.member.MemberState;
import com.dessertoasis.demo.model.member.RegisterDto;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PageSortService pService;

	
	@Autowired
	private MemberRepository mRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private OtpUtil otpUtil;

	// 新增 更改成加密後的版本
//	public void insert(Member member) {
//		mRepo.save(member);
//	}

	public Member findByAccount(String account) {
		return mRepo.findByAccount(account);
	}

	// 查詢單筆
	public Member findByMemberId(Integer id) {
		Optional<Member> optional = mRepo.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	// 查詢全部
	public List<Member> findAllMember() {
		List<Member> result = mRepo.findAll();
		return result;
	}

	// 刪除
	public void deleteByMemberId(Integer id) {
		mRepo.deleteById(id);
	}

	// 修改
	public Member updateMember(Member member) {
		return mRepo.save(member);
	}

	// 密碼加密並新增
	public Member addMember(Member member) {
		String rawPassword = member.getPasswords();
		member.setPasswords(passwordEncoder.encode(rawPassword));
		return mRepo.save(member);
	}

	// 驗證是否重複帳號
	public boolean checkIfAccountExist(String account) {
		Member dbmem = mRepo.findByAccount(account);
		if (dbmem != null) {
			return true;
		} else {
			return false;
		}
	}

	// 透過加密密碼，驗證登入使用者
	public Member memberLogin(String account, String password, HttpSession session) {
		Member loginAccount = mRepo.findByAccount(account);
		if (loginAccount != null) {
			if (passwordEncoder.matches(password, loginAccount.getPasswords())) {
				// 登入成功，將會員物件存入 Session
				session.setAttribute("loggedInMember", loginAccount);
				return loginAccount;
			}
		}
		return null;
	}

	// 更新密碼
	public void updateMemberPassword(Integer memberId, String oldPassword, String newPassword) {
		try {

			Optional<Member> optional = mRepo.findById(memberId);

			if (optional.isPresent()) {
				// member不為空，進行舊密碼驗證
				Member member = optional.get();
				String oldEncodedPassword = member.getPasswords();

				// 驗證舊密碼
				if (passwordEncoder.matches(oldPassword, oldEncodedPassword)) {
					// 舊密碼驗證通過，加密新密碼並更新
					String newEncodedPassword = passwordEncoder.encode(newPassword);
					member.setPasswords(newEncodedPassword);
					mRepo.save(member);

				} else {
					// 密碼驗證失敗
					throw new IllegalArgumentException("舊密碼不正確，請重新輸入。");
				}
			} else {
				// 如果傳入的 Member 對象為空
				throw new IllegalArgumentException("會員不存在。");
			}
		} catch (Exception e) {
			// 處理潛在的異常，如數據庫連接問題
			throw new RuntimeException("密碼更新失敗，請稍後重試。", e);
		}
	}

	// 註冊帳號
	public String register(RegisterDto registerDto) {
		// 檢查是否重複帳號
		Member existingMember = mRepo.findByAccount(registerDto.getAccount());
		if (existingMember != null) {
			return "帳號已存在";
		}
		// 檢查是否重複Email
//		    Optional<Member> existingMemberByEmail = mRepo.findByEmail(registerDto.getEmail());
//		    if (existingMemberByEmail.isPresent()) {
//		        return "電子郵件地址已存在";
//		    }

		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
		} catch (MessagingException e) {
			return "驗證碼傳送失敗";
		}

		Member member = new Member();
		member.setAccount(registerDto.getAccount());
		member.setEmail(registerDto.getEmail());
		// ↓↓↓加密密碼↓↓↓
		member.setPasswords(passwordEncoder.encode(registerDto.getPasswords()));
		member.setOtp(otp);
		member.setOtpGeneratedTime(LocalDateTime.now());
		// ↓↓↓創立新帳號為一般會員↓↓↓
		member.setAccess(MemberAccess.USER);
		// ↓↓↓創立新帳號為不活耀狀態↓↓↓
		member.setMemberStatus(MemberState.INACTIVE);
		// ↓↓↓創立帳號日期是系統當下時間↓↓↓
		member.setSignDate(new Date());
		mRepo.save(member);
		return "註冊成功";
	}

	// 驗證帳號
	public String verifyAccount(String email, String otp) {
		Member member = mRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("找不到此email: " + email));
		if (member.getOtp().equals(otp)
				&& Duration.between(member.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (10 * 60)) { // 驗證信時效十分鐘
			member.setMemberStatus(MemberState.ACTIVE); // 改變會員狀態
			mRepo.save(member);
			return "驗證成功";
		}
		return "請重新驗證";
	}

	// 產生otp，並寄出email
	public String regenerateOtp(String email) {
		Member member = mRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("找不到此email: " + email));
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(email, otp);
		} catch (MessagingException e) {
			throw new RuntimeException("驗證碼傳送失敗");
		}
		member.setOtp(otp);
		member.setOtpGeneratedTime(LocalDateTime.now());
		mRepo.save(member);
		return "請確認信箱，驗證需再三分鐘內完成";
	}

//	// 寄驗證信
//	public void sendVerificationEmail(String toEmail, String token) {
//		String verificationLink =token;
//		System.out.println("我是verificationLink:"+verificationLink);
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setFrom("Dessert0asis@outlook.com");
//		mailMessage.setTo(toEmail);
//		mailMessage.setSubject("驗證信");
//		mailMessage.setText("點擊連結進行驗證" + verificationLink);
//		javaMailSender.send(mailMessage);
//	}

	public List<Member> getAllAdmin(Integer id) {
		List<Member> list = mRepo.findByAccessExcept(id);
		if (list != null)
			return list;
		return null;
	}

	// 檢查account和email是否符合
	public boolean isValidAccount(String account) {
		// 根據帳戶名稱和電子郵件地址查詢數據庫中的用戶
		Member member = mRepo.findByAccount(account);

		// 如果找到匹配的用戶，返回true；否則返回false
		return member != null;
	}

	// 忘記密碼
	public String forgotPassword(String email) {
		Member member = mRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("找不到 email: " + email));
		String newPassword = generateRandomPassword(16); // 長度16

		try {

			emailUtil.sendNewPasswordEmail(email, newPassword);
		} catch (MessagingException e) {
			throw new RuntimeException("傳送email失敗");
		}

		String encodedPassword = passwordEncoder.encode(newPassword);
		member.setPasswords(encodedPassword);

		mRepo.save(member);
		return "檢查信箱，重製密碼";

	}

	// 重設密碼
	public String setPassword(String email, String newpassword) {
		Member member = mRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("找不到 email: " + email));
		member.setPasswords(passwordEncoder.encode(newpassword));
		mRepo.save(member);
		return "密碼設定成功";
	}

	// 產生隨機密碼
	public String generateRandomPassword(int length) {
		// 在这里生成随机密码的逻辑，可以包含字母、数字、符号等
		// 以下示例生成包含字母和数字的随机密码
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder newPassword = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			newPassword.append(characters.charAt(index));
		}

		return newPassword.toString();
	}

	// 更新會員狀態
	public void updateMemberStatus(String account, String newStatus) {
		// 根據帳戶名稱查找用戶
		Member member = mRepo.findByAccount(account);

		// 如果找到用戶，更新其狀態
		if (member != null) {
			member.setMemberStatus(MemberState.ACTIVE);
			mRepo.save(member);
		}
	}
	
	
	public List<MemberCmsTable> getMemberPagenation(SortCondition sortCon){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定輸出表格型態
		CriteriaQuery<MemberCmsTable> cq = cb.createQuery(MemberCmsTable.class);
		// 決定select表格
		Root<Member> root = cq.from(Member.class);
		// 決定查詢 column
		cq.multiselect(root.get("id"), root.get("account"), root.get("fullName"),
				root.get("memberName"), root.get("email"),root.get("access"),root.get("memberStatus"),root.get("signDate"));
		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Member member = new Member();
		Predicate pre = pService.checkMemberCondition(root, predicate, sortCon, cb, member);
		
		// 填入 where 條件
		cq.where(pre);
		
		 if (sortCon.getSortBy() != null) {
		        System.out.println("sort");
		        if (pService.hasProperty(member, sortCon.getSortBy())) {
		            if (sortCon.getSortWay() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
		                cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
		            } else if (sortCon.getSortWay() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
		                cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
		            }
		        }
		    }
		// 分頁
		TypedQuery<MemberCmsTable> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());
		// 送出請求
		List<MemberCmsTable> list = query.getResultList();
		if (list != null) 
			return list;
		return null;
	}
	
	
	
	
	public Integer getMemberPages(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定輸出表格型態
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		// 決定select.join表格
		Root<Member> root = cq.from(Member.class);
		// 決定查詢 column
		cq.select(cb.count(root));
		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Member member = new Member();
		Predicate pre = pService.checkMemberCondition(root, predicate, sortCon, cb, member);
		cq.where(pre);

		//查詢傯頁數
		Long totalRecords = em.createQuery(cq).getSingleResult();
		Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());
		
		return totalPages;
	}
	
	
	
	
}
