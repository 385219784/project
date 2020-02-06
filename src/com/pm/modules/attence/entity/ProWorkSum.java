package com.pm.modules.attence.entity;

import java.util.Date;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import com.pm.modules.project.entity.Project;

public class ProWorkSum   extends DataEntity<ProWorkSum> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Project  pro;    //项目
	
	private Integer serial; //序号
	private String  proNum;
	private String proName;
	private String proShortened;
	private User user; //专业人员
	private String principal;		// 项目负责人
	private String supervisor;		// 项目主管
	
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getProNum() {
		return proNum;
	}
	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getProShortened() {
		return proShortened;
	}
	public void setProShortened(String proShortened) {
		this.proShortened = proShortened;
	}


	private  Date  start;
	private  Date end;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	private double sumWorkTime;   //总工日
	private double sumOverTime;   //总加班

	private double overTimeDay;  // 总加班工日
	
	private  double work1;
	private  double work2;
	private  double work3;
	private  double work4;
	private  double work5;
	private  double work6;
	private  double work7;
	private  double work8;
	private  double work9;
	private  double work10;
	private  double work11;
	private  double work12;
	private  double work13;
	private  double work14;
	private  double work15;
	private  double work16;
	private  double work17;
	private  double work18;
	private  double work19;
	private  double work20;
	private  double work21;
	private  double work22;
	private  double work23;
	private  double work24;
	private  double work25;
	private  double work26;
	private  double work27;
	private  double work28;
	private  double work29;
	private  double work30;
	private  double over1;
	private  double over2;
	private  double over3;
	private  double over4;

	public Project getPro() {
		return pro;
	}
	public void setPro(Project pro) {
		this.pro = pro;
	}
	public double getSumWorkTime() {
		return sumWorkTime;
	}
	public void setSumWorkTime(double sumWorkTime) {
		this.sumWorkTime = sumWorkTime;
	}
	public double getSumOverTime() {
		return sumOverTime;
	}
	public void setSumOverTime(double sumOverTime) {
		this.sumOverTime = sumOverTime;
	}
	public double getWork1() {
		return work1;
	}
	public void setWork1(double work1) {
		this.work1 = work1;
	}
	public double getWork2() {
		return work2;
	}
	public void setWork2(double work2) {
		this.work2 = work2;
	}
	public double getWork3() {
		return work3;
	}
	public void setWork3(double work3) {
		this.work3 = work3;
	}
	public double getWork4() {
		return work4;
	}
	public void setWork4(double work4) {
		this.work4 = work4;
	}
	public double getWork5() {
		return work5;
	}
	public void setWork5(double work5) {
		this.work5 = work5;
	}
	public double getWork6() {
		return work6;
	}
	public void setWork6(double work6) {
		this.work6 = work6;
	}
	public double getWork7() {
		return work7;
	}
	public void setWork7(double work7) {
		this.work7 = work7;
	}
	public double getWork8() {
		return work8;
	}
	public void setWork8(double work8) {
		this.work8 = work8;
	}
	public double getWork9() {
		return work9;
	}
	public void setWork9(double work9) {
		this.work9 = work9;
	}
	public double getWork10() {
		return work10;
	}
	public void setWork10(double work10) {
		this.work10 = work10;
	}
	public double getWork11() {
		return work11;
	}
	public void setWork11(double work11) {
		this.work11 = work11;
	}
	public double getWork12() {
		return work12;
	}
	public void setWork12(double work12) {
		this.work12 = work12;
	}
	public double getWork13() {
		return work13;
	}
	public void setWork13(double work13) {
		this.work13 = work13;
	}
	public double getWork14() {
		return work14;
	}
	public void setWork14(double work14) {
		this.work14 = work14;
	}
	public double getWork15() {
		return work15;
	}
	public void setWork15(double work15) {
		this.work15 = work15;
	}
	public double getWork16() {
		return work16;
	}
	public void setWork16(double work16) {
		this.work16 = work16;
	}
	public double getWork17() {
		return work17;
	}
	public void setWork17(double work17) {
		this.work17 = work17;
	}
	public double getWork18() {
		return work18;
	}
	public void setWork18(double work18) {
		this.work18 = work18;
	}
	public double getWork19() {
		return work19;
	}
	public void setWork19(double work19) {
		this.work19 = work19;
	}
	public double getWork20() {
		return work20;
	}
	public void setWork20(double work20) {
		this.work20 = work20;
	}
	public double getWork21() {
		return work21;
	}
	public void setWork21(double work21) {
		this.work21 = work21;
	}
	public double getWork22() {
		return work22;
	}
	public void setWork22(double work22) {
		this.work22 = work22;
	}
	public double getWork23() {
		return work23;
	}
	public void setWork23(double work23) {
		this.work23 = work23;
	}
	public double getWork24() {
		return work24;
	}
	public void setWork24(double work24) {
		this.work24 = work24;
	}
	public double getWork25() {
		return work25;
	}
	public void setWork25(double work25) {
		this.work25 = work25;
	}
	public double getWork26() {
		return work26;
	}
	public void setWork26(double work26) {
		this.work26 = work26;
	}
	public double getWork27() {
		return work27;
	}
	public void setWork27(double work27) {
		this.work27 = work27;
	}
	public double getWork28() {
		return work28;
	}
	public void setWork28(double work28) {
		this.work28 = work28;
	}
	public double getWork29() {
		return work29;
	}
	public void setWork29(double work29) {
		this.work29 = work29;
	}
	public double getWork30() {
		return work30;
	}
	public void setWork30(double work30) {
		this.work30 = work30;
	}
	public double getOver1() {
		return over1;
	}
	public void setOver1(double over1) {
		this.over1 = over1;
	}
	public double getOver2() {
		return over2;
	}
	public void setOver2(double over2) {
		this.over2 = over2;
	}
	public double getOver3() {
		return over3;
	}
	public void setOver3(double over3) {
		this.over3 = over3;
	}
	public double getOver4() {
		return over4;
	}
	public void setOver4(double over4) {
		this.over4 = over4;
	}
	public double getOver5() {
		return over5;
	}
	public void setOver5(double over5) {
		this.over5 = over5;
	}
	public double getOver6() {
		return over6;
	}
	public void setOver6(double over6) {
		this.over6 = over6;
	}
	public double getOver7() {
		return over7;
	}
	public void setOver7(double over7) {
		this.over7 = over7;
	}
	public double getOver8() {
		return over8;
	}
	public void setOver8(double over8) {
		this.over8 = over8;
	}
	public double getOver9() {
		return over9;
	}
	public void setOver9(double over9) {
		this.over9 = over9;
	}
	public double getOver10() {
		return over10;
	}
	public void setOver10(double over10) {
		this.over10 = over10;
	}
	public double getOver11() {
		return over11;
	}
	public void setOver11(double over11) {
		this.over11 = over11;
	}
	public double getOver12() {
		return over12;
	}
	public void setOver12(double over12) {
		this.over12 = over12;
	}
	public double getOver13() {
		return over13;
	}
	public void setOver13(double over13) {
		this.over13 = over13;
	}
	public double getOver14() {
		return over14;
	}
	public void setOver14(double over14) {
		this.over14 = over14;
	}
	public double getOver15() {
		return over15;
	}
	public void setOver15(double over15) {
		this.over15 = over15;
	}
	public double getOver16() {
		return over16;
	}
	public void setOver16(double over16) {
		this.over16 = over16;
	}
	public double getOver17() {
		return over17;
	}
	public void setOver17(double over17) {
		this.over17 = over17;
	}
	public double getOver18() {
		return over18;
	}
	public void setOver18(double over18) {
		this.over18 = over18;
	}
	public double getOver19() {
		return over19;
	}
	public void setOver19(double over19) {
		this.over19 = over19;
	}
	public double getOver20() {
		return over20;
	}
	public void setOver20(double over20) {
		this.over20 = over20;
	}
	public double getOver21() {
		return over21;
	}
	public void setOver21(double over21) {
		this.over21 = over21;
	}
	public double getOver22() {
		return over22;
	}
	public void setOver22(double over22) {
		this.over22 = over22;
	}
	public double getOver23() {
		return over23;
	}
	public void setOver23(double over23) {
		this.over23 = over23;
	}
	public double getOver24() {
		return over24;
	}
	public void setOver24(double over24) {
		this.over24 = over24;
	}
	public double getOver25() {
		return over25;
	}
	public void setOver25(double over25) {
		this.over25 = over25;
	}
	public double getOver26() {
		return over26;
	}
	public void setOver26(double over26) {
		this.over26 = over26;
	}
	public double getOver27() {
		return over27;
	}
	public void setOver27(double over27) {
		this.over27 = over27;
	}
	public double getOver28() {
		return over28;
	}
	public void setOver28(double over28) {
		this.over28 = over28;
	}
	public double getOver29() {
		return over29;
	}
	public void setOver29(double over29) {
		this.over29 = over29;
	}
	public double getOver30() {
		return over30;
	}
	public void setOver30(double over30) {
		this.over30 = over30;
	}
	private  double over5;
	private  double over6;
	private  double over7;
	private  double over8;
	private  double over9;
	private  double over10;
	private  double over11;
	private  double over12;
	private  double over13;
	private  double over14;
	private  double over15;
	private  double over16;
	private  double over17;
	private  double over18;
	private  double over19;
	private  double over20;
	private  double over21;
	private  double over22;
	private  double over23;
	private  double over24;
	private  double over25;
	private  double over26;
	private  double over27;
	private  double over28;
	private  double over29;

	public double getOverTimeDay() {
		return overTimeDay;
	}

	public void setOverTimeDay(double overTimeDay) {
		this.overTimeDay = overTimeDay;
	}

	public double getOvday1() {
		return ovday1;
	}

	public void setOvday1(double ovday1) {
		this.ovday1 = ovday1;
	}

	public double getOvday2() {
		return ovday2;
	}

	public void setOvday2(double ovday2) {
		this.ovday2 = ovday2;
	}

	public double getOvday3() {
		return ovday3;
	}

	public void setOvday3(double ovday3) {
		this.ovday3 = ovday3;
	}

	public double getOvday4() {
		return ovday4;
	}

	public void setOvday4(double ovday4) {
		this.ovday4 = ovday4;
	}

	public double getOvday5() {
		return ovday5;
	}

	public void setOvday5(double ovday5) {
		this.ovday5 = ovday5;
	}

	public double getOvday6() {
		return ovday6;
	}

	public void setOvday6(double ovday6) {
		this.ovday6 = ovday6;
	}

	public double getOvday7() {
		return ovday7;
	}

	public void setOvday7(double ovday7) {
		this.ovday7 = ovday7;
	}

	public double getOvday8() {
		return ovday8;
	}

	public void setOvday8(double ovday8) {
		this.ovday8 = ovday8;
	}

	public double getOvday9() {
		return ovday9;
	}

	public void setOvday9(double ovday9) {
		this.ovday9 = ovday9;
	}

	public double getOvday10() {
		return ovday10;
	}

	public void setOvday10(double ovday10) {
		this.ovday10 = ovday10;
	}

	public double getOvday11() {
		return ovday11;
	}

	public void setOvday11(double ovday11) {
		this.ovday11 = ovday11;
	}

	public double getOvday12() {
		return ovday12;
	}

	public void setOvday12(double ovday12) {
		this.ovday12 = ovday12;
	}

	public double getOvday13() {
		return ovday13;
	}

	public void setOvday13(double ovday13) {
		this.ovday13 = ovday13;
	}

	public double getOvday14() {
		return ovday14;
	}

	public void setOvday14(double ovday14) {
		this.ovday14 = ovday14;
	}

	public double getOvday15() {
		return ovday15;
	}

	public void setOvday15(double ovday15) {
		this.ovday15 = ovday15;
	}

	public double getOvday16() {
		return ovday16;
	}

	public void setOvday16(double ovday16) {
		this.ovday16 = ovday16;
	}

	public double getOvday17() {
		return ovday17;
	}

	public void setOvday17(double ovday17) {
		this.ovday17 = ovday17;
	}

	public double getOvday18() {
		return ovday18;
	}

	public void setOvday18(double ovday18) {
		this.ovday18 = ovday18;
	}

	public double getOvday19() {
		return ovday19;
	}

	public void setOvday19(double ovday19) {
		this.ovday19 = ovday19;
	}

	public double getOvday20() {
		return ovday20;
	}

	public void setOvday20(double ovday20) {
		this.ovday20 = ovday20;
	}

	public double getOvday21() {
		return ovday21;
	}

	public void setOvday21(double ovday21) {
		this.ovday21 = ovday21;
	}

	public double getOvday22() {
		return ovday22;
	}

	public void setOvday22(double ovday22) {
		this.ovday22 = ovday22;
	}

	public double getOvday23() {
		return ovday23;
	}

	public void setOvday23(double ovday23) {
		this.ovday23 = ovday23;
	}

	public double getOvday24() {
		return ovday24;
	}

	public void setOvday24(double ovday24) {
		this.ovday24 = ovday24;
	}

	public double getOvday25() {
		return ovday25;
	}

	public void setOvday25(double ovday25) {
		this.ovday25 = ovday25;
	}

	public double getOvday26() {
		return ovday26;
	}

	public void setOvday26(double ovday26) {
		this.ovday26 = ovday26;
	}

	public double getOvday27() {
		return ovday27;
	}

	public void setOvday27(double ovday27) {
		this.ovday27 = ovday27;
	}

	public double getOvday28() {
		return ovday28;
	}

	public void setOvday28(double ovday28) {
		this.ovday28 = ovday28;
	}

	public double getOvday29() {
		return ovday29;
	}

	public void setOvday29(double ovday29) {
		this.ovday29 = ovday29;
	}

	public double getOvday30() {
		return ovday30;
	}

	public void setOvday30(double ovday30) {
		this.ovday30 = ovday30;
	}

	private  double over30;


	private double ovday1;
	private double ovday2;
	private double ovday3;
	private double ovday4;
	private double ovday5;
	private double ovday6;
	private double ovday7;
	private double ovday8;
	private double ovday9;
	private double ovday10;
	private double ovday11;
	private double ovday12;
	private double ovday13;
	private double ovday14;
	private double ovday15;
	private double ovday16;
	private double ovday17;
	private double ovday18;
	private double ovday19;
	private double ovday20;
	private double ovday21;
	private double ovday22;
	private double ovday23;
	private double ovday24;
	private double ovday25;
	private double ovday26;
	private double ovday27;
	private double ovday28;
	private double ovday29;
	private double ovday30;




}
