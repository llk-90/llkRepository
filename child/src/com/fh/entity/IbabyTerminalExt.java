package com.fh.entity;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType; 

/** 
 * @create      2017-6-7 
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "TerminalExt")
@XmlType(propOrder = {"terminalId","sos1","sos2","sos3","sos4",
		"guardian1","guardian2","gpsQueryFormat","gpsKey",
		"gameKey","gameTimeRange","restTimeSet","schoolHomeTimeRange",
		"schoolCheckInNotice","schoolTimeRange","studyForbidden",
		"comunicationFireWall","lostRobNotice","lostRobNoticeMsg",
		"pwdManage","autoTurnOffOn","allowFamilyListen","turnOffNeedPwd",
		"timePerCall","noManGetCallSet","lowPowerNoticeFamily",
		"getCallMode","syncTimes","age","grade","sex","clockSetting",
		"autoLockScreen","autoOffScreen","turnOnRing","turnOffRing",
		"canLockKey","blueToothOpen","mute","contextualModelSetting",
		"facePic","appDefaultAllowTime","appNewAppRunSet","appFreeUse"})
public class IbabyTerminalExt {
	
	// 紧急求助电话1-4
	@XmlElement(name = "Sos1")
    private String sos1;

	@XmlElement(name = "Sos2")
    private String sos2;

	@XmlElement(name = "Sos3")
    private String sos3;

	@XmlElement(name = "Sos4")
    private String sos4;

	//监护人电话1-2	
	@XmlElement(name = "Guardian1")
    private String guardian1;
	
	@XmlElement(name = "Guardian2")
    private String guardian2;

	//儿童机id
	@XmlElement(name = "TerminalId")
    private String terminalId;

	@XmlElement(name = "GpsQueryFormat")
	private String	gpsQueryFormat;
	@XmlElement(name = "GpsKey")
	private String	gpsKey;
	@XmlElement(name = "GameKey")
	private String	gameKey;
	@XmlElement(name = "GameTimeRange")
	private String	gameTimeRange;
	@XmlElement(name = "RestTimeSet")
	private String	restTimeSet;
	@XmlElement(name = "SchoolHomeTimeRange")
	private String	schoolHomeTimeRange;
	@XmlElement(name = "SchoolCheckInNotice")
	private String	schoolCheckInNotice;
	@XmlElement(name = "SchoolTimeRange")
	private String	schoolTimeRange;
	@XmlElement(name = "StudyForbidden")
	private String	studyForbidden;
	@XmlElement(name = "ComunicationFireWall")
	private String	comunicationFireWall;
	@XmlElement(name = "LostRobNotice")
	private String	lostRobNotice;
	@XmlElement(name = "LostRobNoticeMsg")
	private String	lostRobNoticeMsg;
	@XmlElement(name = "PwdManage")
	private String	pwdManage;
	@XmlElement(name = "AutoTurnOffOn")
	private String	autoTurnOffOn;
	@XmlElement(name = "AllowFamilyListen")
	private String	allowFamilyListen;
	@XmlElement(name = "TurnOffNeedPwd")
	private String	turnOffNeedPwd;
	@XmlElement(name = "TimePerCall")
	private String	timePerCall;
	@XmlElement(name = "NoManGetCallSet")
	private String	noManGetCallSet;
	@XmlElement(name = "LowPowerNoticeFamily")
	private String	lowPowerNoticeFamily;
	@XmlElement(name = "GetCallMode")
	private String	getCallMode;
	@XmlElement(name = "SyncTimes")
	private String	syncTimes;
	@XmlElement(name = "Age")
	private String	age;
	@XmlElement(name = "Grade")
	private String	grade;
	@XmlElement(name = "Sex")
	private String	sex;
	@XmlElement(name = "ClockSetting")
	private String	clockSetting;
	@XmlElement(name = "AutoLockScreen")
	private String	autoLockScreen;
	@XmlElement(name = "AutoOffScreen")
	private String	autoOffScreen;
	@XmlElement(name = "TurnOnRing")
	private String	turnOnRing;
	@XmlElement(name = "turnOffRing")
	private String	turnOffRing;
	@XmlElement(name = "CanLockKey")
	private String	canLockKey;
	@XmlElement(name = "BlueToothOpen")
	private String	blueToothOpen;
	@XmlElement(name = "Mute")
	private String	mute;
	@XmlElement(name = "ContextualModelSetting")
	private String	contextualModelSetting;
	@XmlElement(name = "FacePic")
	private String	facePic;
	@XmlElement(name = "AppDefaultAllowTime")
	private String	appDefaultAllowTime;
	@XmlElement(name = "AppNewAppRunSet")
	private String	appNewAppRunSet;
	@XmlElement(name = "AppFreeUse")
	private String	appFreeUse;

	public String getSos1() {
		return sos1;
	}

	public void setSos1(String sos1) {
		this.sos1 = sos1;
	}

	public String getSos2() {
		return sos2;
	}

	public void setSos2(String sos2) {
		this.sos2 = sos2;
	}

	public String getSos3() {
		return sos3;
	}

	public void setSos3(String sos3) {
		this.sos3 = sos3;
	}

	public String getSos4() {
		return sos4;
	}

	public void setSos4(String sos4) {
		this.sos4 = sos4;
	}

	public String getGuardian1() {
		return guardian1;
	}

	public void setGuardian1(String guardian1) {
		this.guardian1 = guardian1;
	}

	public String getGuardian2() {
		return guardian2;
	}

	public void setGuardian2(String guardian2) {
		this.guardian2 = guardian2;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getGpsQueryFormat() {
		return gpsQueryFormat;
	}

	public void setGpsQueryFormat(String gpsQueryFormat) {
		this.gpsQueryFormat = gpsQueryFormat;
	}

	public String getGpsKey() {
		return gpsKey;
	}

	public void setGpsKey(String gpsKey) {
		this.gpsKey = gpsKey;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public String getGameTimeRange() {
		return gameTimeRange;
	}

	public void setGameTimeRange(String gameTimeRange) {
		this.gameTimeRange = gameTimeRange;
	}

	public String getRestTimeSet() {
		return restTimeSet;
	}

	public void setRestTimeSet(String restTimeSet) {
		this.restTimeSet = restTimeSet;
	}

	public String getSchoolHomeTimeRange() {
		return schoolHomeTimeRange;
	}

	public void setSchoolHomeTimeRange(String schoolHomeTimeRange) {
		this.schoolHomeTimeRange = schoolHomeTimeRange;
	}

	public String getSchoolCheckInNotice() {
		return schoolCheckInNotice;
	}

	public void setSchoolCheckInNotice(String schoolCheckInNotice) {
		this.schoolCheckInNotice = schoolCheckInNotice;
	}

	public String getSchoolTimeRange() {
		return schoolTimeRange;
	}

	public void setSchoolTimeRange(String schoolTimeRange) {
		this.schoolTimeRange = schoolTimeRange;
	}

	public String getStudyForbidden() {
		return studyForbidden;
	}

	public void setStudyForbidden(String studyForbidden) {
		this.studyForbidden = studyForbidden;
	}

	public String getComunicationFireWall() {
		return comunicationFireWall;
	}

	public void setComunicationFireWall(String comunicationFireWall) {
		this.comunicationFireWall = comunicationFireWall;
	}

	public String getLostRobNotice() {
		return lostRobNotice;
	}

	public void setLostRobNotice(String lostRobNotice) {
		this.lostRobNotice = lostRobNotice;
	}

	public String getLostRobNoticeMsg() {
		return lostRobNoticeMsg;
	}

	public void setLostRobNoticeMsg(String lostRobNoticeMsg) {
		this.lostRobNoticeMsg = lostRobNoticeMsg;
	}

	public String getPwdManage() {
		return pwdManage;
	}

	public void setPwdManage(String pwdManage) {
		this.pwdManage = pwdManage;
	}

	public String getAutoTurnOffOn() {
		return autoTurnOffOn;
	}

	public void setAutoTurnOffOn(String autoTurnOffOn) {
		this.autoTurnOffOn = autoTurnOffOn;
	}

	public String getAllowFamilyListen() {
		return allowFamilyListen;
	}

	public void setAllowFamilyListen(String allowFamilyListen) {
		this.allowFamilyListen = allowFamilyListen;
	}

	public String getTurnOffNeedPwd() {
		return turnOffNeedPwd;
	}

	public void setTurnOffNeedPwd(String turnOffNeedPwd) {
		this.turnOffNeedPwd = turnOffNeedPwd;
	}

	public String getTimePerCall() {
		return timePerCall;
	}

	public void setTimePerCall(String timePerCall) {
		this.timePerCall = timePerCall;
	}

	public String getNoManGetCallSet() {
		return noManGetCallSet;
	}

	public void setNoManGetCallSet(String noManGetCallSet) {
		this.noManGetCallSet = noManGetCallSet;
	}

	public String getLowPowerNoticeFamily() {
		return lowPowerNoticeFamily;
	}

	public void setLowPowerNoticeFamily(String lowPowerNoticeFamily) {
		this.lowPowerNoticeFamily = lowPowerNoticeFamily;
	}

	public String getGetCallMode() {
		return getCallMode;
	}

	public void setGetCallMode(String getCallMode) {
		this.getCallMode = getCallMode;
	}

	public String getSyncTimes() {
		return syncTimes;
	}

	public void setSyncTimes(String syncTimes) {
		this.syncTimes = syncTimes;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClockSetting() {
		return clockSetting;
	}

	public void setClockSetting(String clockSetting) {
		this.clockSetting = clockSetting;
	}

	public String getAutoLockScreen() {
		return autoLockScreen;
	}

	public void setAutoLockScreen(String autoLockScreen) {
		this.autoLockScreen = autoLockScreen;
	}

	public String getAutoOffScreen() {
		return autoOffScreen;
	}

	public void setAutoOffScreen(String autoOffScreen) {
		this.autoOffScreen = autoOffScreen;
	}

	public String getTurnOnRing() {
		return turnOnRing;
	}

	public void setTurnOnRing(String turnOnRing) {
		this.turnOnRing = turnOnRing;
	}

	public String getTurnOffRing() {
		return turnOffRing;
	}

	public void setTurnOffRing(String turnOffRing) {
		this.turnOffRing = turnOffRing;
	}

	public String getCanLockKey() {
		return canLockKey;
	}

	public void setCanLockKey(String canLockKey) {
		this.canLockKey = canLockKey;
	}

	public String getBlueToothOpen() {
		return blueToothOpen;
	}

	public void setBlueToothOpen(String blueToothOpen) {
		this.blueToothOpen = blueToothOpen;
	}

	public String getMute() {
		return mute;
	}

	public void setMute(String mute) {
		this.mute = mute;
	}

	public String getContextualModelSetting() {
		return contextualModelSetting;
	}

	public void setContextualModelSetting(String contextualModelSetting) {
		this.contextualModelSetting = contextualModelSetting;
	}

	public String getFacePic() {
		return facePic;
	}

	public void setFacePic(String facePic) {
		this.facePic = facePic;
	}

	public String getAppDefaultAllowTime() {
		return appDefaultAllowTime;
	}

	public void setAppDefaultAllowTime(String appDefaultAllowTime) {
		this.appDefaultAllowTime = appDefaultAllowTime;
	}

	public String getAppNewAppRunSet() {
		return appNewAppRunSet;
	}

	public void setAppNewAppRunSet(String appNewAppRunSet) {
		this.appNewAppRunSet = appNewAppRunSet;
	}

	public String getAppFreeUse() {
		return appFreeUse;
	}

	public void setAppFreeUse(String appFreeUse) {
		this.appFreeUse = appFreeUse;
	}
	
}
