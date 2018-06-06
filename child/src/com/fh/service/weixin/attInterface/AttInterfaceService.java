package com.fh.service.weixin.attInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fh.controller.system.configure.ConfigureController.Configure;
import com.fh.controller.weixin.TemplateData;
import com.fh.controller.weixin.WxTemplate;
import com.fh.controller.weixin.WxTest;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.util.cmcc.OpenService;
import com.fh.util.cmcc.Response;

@Service("attInterfaceService")
public class AttInterfaceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public PageData decrypt(PageData pd) {
		PageData retPd = new PageData();
		String ret = "";
		String t = "";
		String data = "";
		if (pd != null) {
			t = pd.getString("t");
			retPd.put("t", t);
			data = pd.getString("data");
			// 将t相加得到key √
			int key = 0;

			boolean isNum = t.matches("[0-9]+");

			if (!isNum) {
				return null;
			}

			for (int i = 0; i < t.length(); i++) {
				key += Integer.parseInt(String.valueOf(t.charAt(i)));
			}

			// 将数据分为数组并交换高低位
			char[] dataArr = data.toCharArray();
			if (dataArr.length == 0 || dataArr.length % 2 != 0) {
				return null;
			}
			List<String> dataList = new ArrayList<String>();
			for (int i = 0; i < dataArr.length; i = i + 2) {
				dataList.add(new StringBuilder(String.valueOf(dataArr[i]) + String.valueOf(dataArr[i + 1])).reverse()
						.toString());
			}

			int k;
			StringBuffer str = new StringBuffer();
			List<Integer> jzList = new ArrayList<Integer>();
			for (int i = 0; i < dataList.size(); i++) {
				jzList.add(Integer.parseInt(dataList.get(i), 16));
				k = jzList.get(i) ^ key;
				str.append((char) k);
			}
			ret = new StringBuilder(str).reverse().toString();
			String[] list = ret.split("&");
			for (int i = 0; i < list.length; i++) {
				String[] temp = list[i].split("=");
				if (temp.length == 2) {
					retPd.put(temp[0], temp[1]);
				}
			}
		}
		return retPd;
	}

	public String sendTemp(PageData pd, HttpServletRequest request) throws Exception {
		String iid = "";
		String beginDate = "";
		String type = "";
		String flag = "";
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time = new SimpleDateFormat("HH-mm");
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (pd != null) {
			if (pd.getString("iid") != null) {
				iid = pd.getString("iid");
				if(iid.length() < 10){
					int length = iid.length();
					int addNum = 10-length;
					for (int i = 0; i < addNum; i++) {
						iid = "0"+iid;
					}
				}
			} else {
				flag = "0:40002";
				return flag;
			}
			if (pd.getString("t") != null) {
				beginDate = pd.getString("t");
				pd.put("date", date.format(new Date(Long.valueOf(beginDate + "000"))));
				pd.put("time", time.format(new Date(Long.valueOf(beginDate + "000"))));
				pd.put("datetime", datetime.format(new Date(Long.valueOf(beginDate + "000"))));
			} else {
				flag = "0:40003";
				return flag;
			}
			if (pd.getString("type") != null) {
				type = pd.getString("type");
				if (type.equals("0")) {
					type = "进入学校";
				} else if (type.equals("1")) {
					type = "离开学校";
				}else {
					flag = "0:40004";
					return flag;
				}
			} else {
				flag = "0:40004";
				return flag;
			}
//			flag = "0:OK";
			String sd = datetime.format(new Date(Long.valueOf(beginDate + "000")));
			List<PageData> parentsList = (List<PageData>) dao.findForList("AttInterfaceMapper.findParentList", iid);
			List<String> school = (List<String>) dao.findForList("AttInterfaceMapper.findSchool", iid);
			Configure configure = new Configure();
			String openid = "";
			WxTest wxTest = new WxTest();
			if (parentsList == null || parentsList.size() == 0) {
				//System.out.println("t_user_student表不存在卡号:"+iid);
			}
			for (PageData p : parentsList) {
				p.put("userId", p.getString("us_user_id"));
				List<PageData> list = (List<PageData>) dao.findForList("CoursewarePublishMapper.findStuByUserId", p);
				if(list != null && list.size() > 0){
					openid = p.getString("uw_open_id");
					try {
						WxTemplate temp = new WxTemplate();
						// 点击消息转到的url
						/* temp.setUrl("http://www.baidu.com"); */
						// 家长的openid
						temp.setTouser(openid);
						// 标题颜色
						temp.setTopcolor("#000000");
						// 模板id
						temp.setTemplate_id("60dcfZK0w3ogYooe3FYigWNRwXK-pOZNlaimUZBijn8");

						// 参数设置
						Map<String, TemplateData> m = new HashMap<String, TemplateData>();
						TemplateData first = new TemplateData();
						first.setColor("#000000");
						
						//UserName学生姓名
						PageData userNameList = (PageData) dao.findForObject("AttInterfaceMapper.findUserNameList", iid);
						
						first.setValue("您好，亲爱的" + userNameList.get("UserName") + "家长，您的孩子有一条考勤记录");
						m.put("first", first);
						TemplateData keyword1 = new TemplateData();
						keyword1.setColor("#000000");
						keyword1.setValue(sd);
						m.put("keyword1", keyword1);
						TemplateData keyword2 = new TemplateData();
						keyword2.setColor("#000000");
						if (school != null && school.size() != 0) {
							keyword2.setValue(school.get(0));
						} else {
							keyword2.setValue("数据错误");
						}
						m.put("keyword2", keyword2);
						TemplateData keyword3 = new TemplateData();
						keyword3.setColor("#000000");
						keyword3.setValue(type);
						m.put("keyword3", keyword3);

						temp.setData(m);
						// 开始发送
						wxTest.sendTemp(configure.getAppId(), configure.getAppSecret(), temp,  request);
					} catch (Exception e) {
					}
				}
			}
			List<String> list = (List<String>) dao.findForList("AttInterfaceMapper.findStuId", iid);
			if (list.size() != 0 && list != null) {
				pd.put("stu_id", list.get(0));
				dao.save("AttInterfaceMapper.save", pd);
				flag = "0:OK";
			} else {
				flag = "0:40002";  //卡号不存在
				//System.out.println("qry_student_info表不存在卡号:"+iid);
				return flag;
			}
		}
		return flag;
	}
	/**
	 * 考勤信息推送 
	 */
	public String sendData(PageData pd,Map<Object, Object> city) throws Exception{
		System.out.println("考勤记录");
		// 访问日志
		Log mlog = LogFactory.getLog("messagelogger");
		
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		//打卡时间
		Date time = new Date(Long.valueOf(pd.getString("t") + "000"));
//		Date time = new Date();
		//设备id
		String iid = "";
		//进出学校
		String type = "";
		if (pd.getString("iid") != null) {
			iid = pd.getString("iid");
			if(iid.length() < 10){
				int length = iid.length();
				int addNum = 10-length;
				for (int i = 0; i < addNum; i++) {
					iid = "0"+iid;
				}
			}
			//高 追加 添加处理后的IC卡信息
			pd.put("icId", iid);
		}
		if (pd.getString("type") != null) {
			type = pd.getString("type");
		}
		if (pd.getString("t") != null) {
			pd.put("datetime", datetime.format(time));
			pd.put("date", date.format(time));
		}
		

		//查询区域,学校等信息
		PageData zoneInfo = (PageData) dao.findForObject("AttInterfaceMapper.findIdInfomations", pd);
		if (zoneInfo==null) {
			return "Please check: "+pd.toString();
		}
		//请求报文体
		StringBuilder requestXML = new StringBuilder("<MSG_BODY>");

		//第三方的记录唯一标 识
//		StringBuilder strFlag = new StringBuilder();//定义变长字符串
//		Random random=new Random();
//		int chiticNo = (int) dao.findForObject("QryInterfaceMapper.findChticNo", null);
//		if (chiticNo == 99999999) {
//			pd.put("chiticNo", 1);
//		}
//		else{
//			pd.put("chiticNo", chiticNo);
//		}
//
//		dao.update("QryInterfaceMapper.updateChticNo", pd);
		int chiticNo =  Integer.valueOf(Pattern.compile("\\D").matcher(String.valueOf(UUID.randomUUID())).replaceAll("").trim().substring(0, 8));

		// gao 修改
		if (zoneInfo!=null && !zoneInfo.isEmpty()){
			requestXML.append("<CityId>").append(zoneInfo.get("CityId")).append("</CityId>")
			.append("<PunchRecords>")
					.append("<PunchRecord>")
					 
					.append("<RecordSn>").append(chiticNo).append("</RecordSn>") //考勤序号
					 
					.append("<DeviceId>").append(pd.get("mid")).append("</DeviceId>");
		
			requestXML.append("<CardId>").append(iid).append("</CardId>")
					.append("<UserType>2</UserType>")
					.append("<PunchInOut>").append(pd.get("type")).append("</PunchInOut>")
					.append("<TempleNum>1</TempleNum>")
					.append("<PunchTime>").append(pd.getString("datetime")).append("</PunchTime>");
		}
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),0, 0, 0);  
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		//当天起始和结束时间
		Date start = calendar.getTime();
		Date end = calendar2.getTime();
		//入校时间
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		Date getIn = calendar.getTime();
		//出校时间
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		calendar.add(Calendar.MINUTE, 30);
		Date getOut = calendar.getTime();
		
		//插入数据时先查询当天数据是否存在
		//当天的考勤记录
		List<PageData> attelist = new ArrayList<>();		
		attelist =  (List<PageData>)dao.findForList("AttInterfaceMapper.selectatte", pd);
		if("41670".equals(pd.get("schoolid"))) {
			//进行数据库存储操作
			if (attelist.size()==0) {//当今天没有数据时
				if(time.after(getIn) && time.before(getOut)){
					if(type.equals("0")){//迟到
						//插入迟到打卡的数据
						dao.save("AttInterfaceMapper.saveattemorning", pd);
					}else if(type.equals("1")){//早退
						//插入早退打卡的数据
						dao.save("AttInterfaceMapper.saveatteafternoon", pd);
					}
				}else if((time.before(getIn) || time.equals(getIn)) && time.after(start)){//正常进入				
					//插入正常入校打卡的数据
					pd.put("type", "");
					dao.save("AttInterfaceMapper.saveattemorning", pd);
					//上午无数据，下午正常离校
				}else if(time.before(end)){				
					//插入正常离校打卡的数据
					pd.put("type", "");
					dao.save("AttInterfaceMapper.saveatteafternoon", pd);
				}
			} else {//当今天有数据时,因为有数据，只关心出打
				if(time.after(getIn) && time.before(getOut)){
					if(type.equals("1") && attelist.get(0).getString("type") == "0"){//早退且迟到
						//插入早退打卡的数据
						pd.put("type", "2");
						dao.update("AttInterfaceMapper.updateatteafternoon", pd);
					}else if(type.equals("1") && attelist.get(0).getString("type") == ""){//早退，上午没迟到
						//插入早退打卡的数据
						dao.update("AttInterfaceMapper.updateatteafternoon", pd);
					}
				}else if(time.before(end) && time.after(getOut)){				
					//插入正常离校打卡的数据
					pd.put("type", attelist.get(0).getString("type"));
					dao.update("AttInterfaceMapper.updateatteafternoon", pd);
				}
				
			}
		}
		
		
		//******-----*******以下为短信发送代码
		if(time.after(getIn) && time.before(getOut)){
			if(type.equals("0")){
				requestXML.append("<PunchName>迟到</PunchName>")
				.append("<PunchInOut>").append(type).append("</PunchInOut>")
				.append("<SiteType>1</SiteType>")
				.append("<Remark>迟到打卡</Remark>");
				
			}else if(type.equals("1")){
				requestXML.append("<PunchName>早退</PunchName>").append("<PunchInOut>").append(type)
						.append("</PunchInOut>").append("<SiteType>1</SiteType>")
						.append("<Remark>早退打卡</Remark>");
			}
		}else if((time.before(getIn) || time.equals(getIn)) && time.after(start)){
			requestXML.append("<PunchName>正常</PunchName>")
			.append("<PunchInOut>").append(type).append("</PunchInOut>")
			.append("<SiteType>1</SiteType>")
			.append("<Remark>正常进入</Remark>");
		}else if(time.before(end) && time.after(getOut)){
			requestXML.append("<PunchName>正常</PunchName>")
			.append("<PunchInOut>").append(type).append("</PunchInOut>")
			.append("<SiteType>1</SiteType>")
			.append("<Remark>正常离开</Remark>");
		}
		
						
		requestXML.append("</PunchRecord></PunchRecords></MSG_BODY>");
		
		//消息类型
		String msgType = "SEND_REAL_KQ";
		String logContent = OpenService.openService(requestXML.toString(),msgType).getBody();
		logContent = "snCode:" + logContent.substring(logContent.indexOf("<Sn>")+4, logContent.indexOf("</Sn>"));
		logContent = logContent + "  IcNo:" + iid + "  mid:" + pd.get("mid") + "  schoolid:" + pd.get("schoolid");
		
		//调用考勤信息接口
		mlog.error(logContent);
		System.err.println("考勤信息接口推送成功的卡号:"+iid);
		return "0:OK";
	}
	
	
	/**
	 * 获取学校增量信息
	 */
	@SuppressWarnings("unchecked")
	public void sendSchoolAppData() throws Exception {
		PageData pd = new PageData();
		List<PageData> list = (List<PageData>) dao.findForList("QryInterfaceMapper.findSchoolAddInfo", null);

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = datetime.format(cal.getTime());
		// 获取昨天时间
		cal.add(Calendar.DATE, -1);
		String yesterday = datetime.format(cal.getTime());

		if (!list.isEmpty()) {
			String msgType = "QRY_SCHOOL_UPDATE";
			for (PageData data : list) {
				String requestXML = "<MSG_BODY><CityId>" + data.getString("CityId") + "</CityId><TownId>"
						+ data.get("TownId").toString() + "</TownId>";
				requestXML = requestXML + "<StartTime>" + yesterday + "</StartTime>" + "<EndTime>" + today
						+ "</EndTime></MSG_BODY>";
				Response response = OpenService.openService(requestXML, msgType);
				String protocolXML = response.getBody();
				// 解析XML
				Document doc = (Document) DocumentHelper.parseText(protocolXML);
				Element root = doc.getRootElement();
				String count = root.element("PageCount").getText();
				if ("1".equals(count)) {
					Element Schools = root.element("SchoolLogList");
					if (Schools.hasContent()) {
						Iterator Elements = Schools.elementIterator();
						while (Elements.hasNext()) {
							Element oneClass = (Element) Elements.next();
							int SchoolID = Integer.valueOf(oneClass.element("SchoolId").getText());
							int AreaID = Integer.valueOf(oneClass.element("AreaId").getText());
							String AreaName = oneClass.element("AreaName").getText();
							String TownName = oneClass.element("TownName").getText();
							String SchoolName = oneClass.element("SchoolName").getText();
							String Section = oneClass.element("Section").getText();
							int TownId = Integer.valueOf(oneClass.element("TownId").getText());
							String CityId = oneClass.element("CityId").getText();
							String opCode = oneClass.element("OperateCode").getText();
							pd.put("SchoolID", SchoolID);
							pd.put("AreaID", AreaID);
							pd.put("AreaName", AreaName);
							pd.put("TownName", TownName);
							pd.put("SchoolName", SchoolName);
							pd.put("Section", Section);
							pd.put("TownId", TownId);
							pd.put("CityId", CityId);
							pd.put("ModifyTime", today);
							if ("A".equals(opCode)) {
								// 将所有结果保存到数据库
								dao.save("QryInterfaceMapper.insertSchoolInfo", pd);
							} else if ("M".equals(opCode)) {
								// 更新数据
								dao.update("QryInterfaceMapper.updateSchoolInfo", pd);
							} else if ("D".equals(opCode)) {
								// 删除数据
								dao.delete("QryInterfaceMapper.deleteSchoolInfo", pd);
							}

						}
					}
				}
			}
		}

	}

	/**
	 * 获取班级增量信息
	 */
	@SuppressWarnings("unchecked")
	public void sendClassAppData() throws Exception {
		PageData pd = new PageData();
		List<PageData> list = (List<PageData>) dao.findForList("QryInterfaceMapper.findClassAddInfo", pd);
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = datetime.format(cal.getTime());
		// 获取昨天时间
		cal.add(Calendar.DATE, -1);
		String yesterday = datetime.format(cal.getTime());

		if (!list.isEmpty()) {
			String msgType = "QRY_CLASS_UPDATE";
			for (PageData data : list) {
				String requestXML = "<MSG_BODY><CityId>" + data.getString("CityId") + "</CityId><SchoolId>"
						+ data.get("SchoolId").toString() + "</SchoolId>";
				requestXML = requestXML + "<StartTime>" + yesterday + "</StartTime>" + "<EndTime>" + today
						+ "</EndTime></MSG_BODY>";
				Response response = OpenService.openService(requestXML, msgType);
				String protocolXML = response.getBody();

				// if (protocolXML.length()>12){
				// 解析XML
				Document doc = (Document) DocumentHelper.parseText(protocolXML);
				Element root = doc.getRootElement();
				String count = root.element("PageCount").getText();
				if ("1".equals(count)) {
					Element classList = root.element("ClassLogList");
					if (classList.hasContent()) {
						Iterator Elements = classList.elementIterator();
						while (Elements.hasNext()) {
							Element oneClass = (Element) Elements.next();
							int ClassID = Integer.valueOf(oneClass.element("ClassId").getText());
							int SchoolID = Integer.valueOf(oneClass.element("SchoolId").getText());
							int GradeID = Integer.valueOf(oneClass.element("GradeId").getText());
							String GradeName = oneClass.element("GradeName").getText();
							int Bank = Integer.valueOf(oneClass.element("Bank").getText());
							String ClassName = oneClass.element("ClassName").getText();
							int ClassType = Integer.valueOf(oneClass.element("ClassType").getText());
							String opCode = oneClass.element("OperateCode").getText();

							pd.put("ClassID", ClassID);
							pd.put("SchoolID", SchoolID);
							pd.put("GradeID", GradeID);
							pd.put("GradeName", GradeName);
							pd.put("Bank", Bank);
							pd.put("ClassName", ClassName);
							pd.put("ClassType", ClassType);
							pd.put("ModifyTime", today);
							if ("A".equals(opCode)) {
								// 将所有结果保存到数据库
								dao.save("QryInterfaceMapper.insertClassInfo", pd);
							} else if ("M".equals(opCode)) {
								// 更新数据
								dao.update("QryInterfaceMapper.updateClassInfo", pd);
							} else if ("D".equals(opCode))  {
								// 删除数据
								dao.delete("QryInterfaceMapper.deleteClassInfo", pd);
							}

						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 获取镇区增量数据接口
	 */
	public void updateTown() {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前一天
		String defaultEndDate = sdf.format(dNow); // 格式化当前时间
		// 获取城镇信息
		try {
			
			// 消息类型
			String msgType = "QRY_TOWN_UPDATE";
			List<PageData> listCity = (List<PageData>) dao.findForList("QryInterfaceMapper.findCityIds", null);
			
			if (!listCity.isEmpty()) {
				for(PageData pd : listCity){
					// 请求报文体
					String requestXML = "<MSG_BODY><CityId>" + pd.getString("CityId") + "</CityId><StartTime>"
							+ defaultStartDate + "</StartTime><EndTime>" + defaultEndDate + "</EndTime></MSG_BODY>";

					// 调用考勤信息接口
					Response response = OpenService.openService(requestXML, msgType);
					Document doc = (Document) DocumentHelper.parseText(response.getBody());
					Element element = doc.getRootElement();
					int PageCount = Integer.valueOf(element.element("PageCount").getText());
					//判断查询出来的个数
					if (PageCount > 0) {
						Element etown = element.element("TownList");
						if (etown.hasContent()) {
							Iterator cityList = etown.elementIterator();

							while (cityList.hasNext()) {
								Element ctElement = (Element) cityList.next();
								String OperateCode = ctElement.element("OperateCode").getText();
								int TownId = Integer.valueOf(ctElement.element("TownId").getText());
								String CityId = ctElement.element("CityId").getText();
								String Code = ctElement.element("Code").getText();
								int Sequence = Integer.valueOf(ctElement.element("Sequence").getText());
								
								PageData pageData = new PageData();
								pageData.put("TownId", TownId);
								pageData.put("CityId", CityId);
								pageData.put("Code", Code);
								pageData.put("Sequence", Sequence);
								pageData.put("ModifyTime", defaultEndDate);
								//判断OperateCode的值为A
								if ("A".equals(OperateCode)) {
									dao.save("QryInterfaceMapper.InsertTownInfo", pageData);
									//判断OperateCode的值为M
								} else if ("M".equals(OperateCode)) {
									dao.update("QryInterfaceMapper.updateTown", pageData);
									//判断OperateCode的值为D
								} else if ("D".equals(OperateCode)) {
									dao.delete("QryInterfaceMapper.deleteTown", pageData);
								}

							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取学生信息增量数据接口
	 */
	public void updateStudent() {

		// 访问日志
		Log mlog = LogFactory.getLog("messagelogger");
		
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -3); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前一天
		String defaultEndDate = sdf.format(dNow); // 格式化当前时间
		// 查询学生信息
		try {
			PageData pd = new PageData();

			@SuppressWarnings("unchecked")
			List<PageData> list = (List<PageData>) dao.findForList("QryInterfaceMapper.findClassAddInfo", pd);
			if (!list.isEmpty()) {
				for(PageData data : list){
					String[] str=new String[]{"fs","qy","zq"};
					for(int i=0; i<str.length; i++) {
						String uCityid=str[i];					
					
					if(uCityid.equals(data.get("CityId"))){
						String requestXML = "<MSG_BODY><CityId>"+uCityid+"</CityId><SchoolId>"
								+ data.get("SchoolId").toString() + "</SchoolId><StartTime>" + defaultStartDate + "</StartTime><EndTime>"
								+ defaultEndDate + "</EndTime></MSG_BODY>";
						//System.out.println(requestXML);
						// 消息类型
						String msgType = "QRY_STUDENT_UPDATE";
						// 调用考勤信息接口
						Response response = OpenService.openService(requestXML, msgType);
						Document doc = (Document) DocumentHelper.parseText(response.getBody());
						Element element = doc.getRootElement();
						int PageCount = Integer.valueOf(element.element("PageCount").getText());
						if (PageCount > 0) {
							Element etown = element.element("StudentLogList");
							if (etown.hasContent()) {
								Iterator cityList = etown.elementIterator();
	
								while (cityList.hasNext()) {
									Element ctElement = (Element) cityList.next();
									String OperateCode = ctElement.element("OperateCode").getText();
									int classId = ctElement.element("ClassId") == null ? 0
											: Integer.valueOf(ctElement.element("ClassId").getText());
									String studentId = ctElement.element("UserId") == null ? ""
											: ctElement.element("UserId").getText();
									String studentSeq = ctElement.element("StudentSeq") == null ? ""
											: ctElement.element("StudentSeq").getText();
									String userName = ctElement.element("UserName") == null ? ""
											: ctElement.element("UserName").getText();
									int schoolId = ctElement.element("SchoolId") == null ? 0
											: Integer.valueOf(ctElement.element("SchoolId").getText());
									String loginName = ctElement.element("LoginName") == null ? ""
											: ctElement.element("LoginName").getText();
									String icNo = ctElement.element("IcNo") == null ? "" : ctElement.element("IcNo").getText();
									String sex = ctElement.element("Sex") == null ? "" : ctElement.element("Sex").getText();
	
									PageData pdInfo = new PageData();
									pdInfo.put("classId", classId);
									pdInfo.put("studentId", studentId);
									pdInfo.put("studentSeq", studentSeq);
									pdInfo.put("userName", userName);
									pdInfo.put("schoolId", schoolId);
									pdInfo.put("loginName", loginName);
									pdInfo.put("icNo", icNo);
									pdInfo.put("sex", sex);
									pdInfo.put("ModifyTime", defaultEndDate);
									mlog.error(OperateCode + "-" + classId + "-" + studentId + "-" + studentSeq + "-" + userName + "-" + schoolId + "-" + loginName + "-" + icNo + "-" + sex);
									System.out.println(OperateCode + "-" + classId + "-" + studentId + "-" + studentSeq + "-" + userName + "-" + schoolId + "-" + loginName + "-" + icNo + "-" + sex);
									if ("A".equals(OperateCode)) {
										dao.save("QryInterfaceMapper.insertStudentsInfo", pdInfo);
										//dao.save("QryInterfaceMapper.insertTstudent", pdInfo);
									} else if ("M".equals(OperateCode)) {
										dao.update("QryInterfaceMapper.updateStudent", pdInfo);
										//dao.update("QryInterfaceMapper.updateStudentPar", pdInfo);
									} else if ("D".equals(OperateCode)) {
										dao.delete("QryInterfaceMapper.deleteStudent", pdInfo);
									}
	
								}
							}
						}
					}
					mlog.error("家长导入完毕！");
				}
			}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		
		AttInterfaceService attInterfaceService = new AttInterfaceService();
		attInterfaceService.updateStudent();
	
	}
	
	/**
	 * 获取家长增量数据接口
	 */
	public void updateParent() {
		
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();

		// 访问日志
		Log mlog = LogFactory.getLog("messagelogger");
		
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前一天
		String defaultEndDate = sdf.format(dNow); // 格式化当前时间
		
		//获取学生与家长信息
		try {
			
			// 消息类型
			String msgType = "QRY_PARENT_UPDATE";
			List<PageData> listSchoolID = (List<PageData>) dao.findForList("QryInterfaceMapper.findPaSchoolID", null);
			
			if (!listSchoolID.isEmpty()) {
				for(PageData pd : listSchoolID) {
					
					// 请求报文体
					String requestXML = "<MSG_BODY><CityId>" + pd.getString("CityId")+"</CityId><SchoolID>" + pd.get("SchoolID") +"</SchoolID><StartTime>"
							+ defaultStartDate + "</StartTime><EndTime>" + defaultEndDate + "</EndTime></MSG_BODY>";
					
					// 调用考勤信息接口
					Response response = OpenService.openService(requestXML, msgType);
					Document doc = (Document) DocumentHelper.parseText(response.getBody());
					Element element = doc.getRootElement();

						Element etown = element.element("FamilyLogList");
						if (etown != null) {
							if (etown.hasContent()) {
								Iterator studentList = etown.elementIterator();		
								while (studentList.hasNext()) {
									
									PageData pageData = new PageData();
									//学生List取值
									Element parElement = (Element) studentList.next();
									
									//学生id
									int stuUserId = parElement.element("StuUserId") == null ? 0 : Integer.valueOf(parElement.element("StuUserId").getText());
									pageData.put("StuUserId", stuUserId);								
									
									//家长id
									int userId = parElement.element("UserId") == null ? 0 : Integer.valueOf(parElement.element("UserId").getText());		
									pageData.put("UserId", userId);
									
									//家长登录ID
									String loginName = parElement.element("LoginName") == null ? null :parElement.element("LoginName").getText();		
									pageData.put("LoginName",loginName);
									
									//家长真实姓名
									String userName = parElement.element("UserName") == null ? null :parElement.element("UserName").getText();		
									pageData.put("UserName", userName);
									
									//家长头像地址
									String headImage = parElement.element("HeadImage") == null ? null :parElement.element("HeadImage").getText();		
									pageData.put("HeadImage", headImage);
									
									//ParEMail
									String eMail = parElement.element("EMail") == null ? null : parElement.element("EMail").getText();		
									pageData.put("EMail", eMail);
									
									//ParIcNo
									String icNo = parElement.element("IcNo") == null ? null : parElement.element("IcNo").getText();		
									pageData.put("IcNo", icNo);

									//AccountId
									int accountId = parElement.element("AccountId") == null ? 0 : Integer.valueOf(parElement.element("AccountId").getText());		//家长账号编号	
									pageData.put("AccountId", accountId);
									
									//最后修改时间
									String modifyTime = parElement.element("ModifyTime") == null ? ""                 
											: parElement.element("ModifyTime").getText();
									pageData.put("ModifyTime", modifyTime);
										
									//变更类型
									String OperateCode = parElement.element("OperateCode").getText();
									
									if ("A".equals(OperateCode)) {
										dao.save("QryInterfaceMapper.insertParent", pageData);
										System.out.println("A" + pd + "---" + stuUserId + "---" + userId);
									} else if ("M".equals(OperateCode)) {
										dao.update("QryInterfaceMapper.updateParent", pageData);
										System.out.println("M" + pd + "---" + stuUserId + "---" + userId);
									} else if ("D".equals(OperateCode)) {
										dao.delete("QryInterfaceMapper.deleteParent", pageData);
										System.out.println("D" + pd + "---" + stuUserId + "---" + userId);
									}
									mlog.error(OperateCode + "---" + pd + "---" + stuUserId + "---" + userId);
								}
								
							} 
						} else {
							System.out.println(pd + "---该学校没有数据！");
							mlog.error(pd + "---该学校没有数据！");
						}				
				}	
				System.out.println("导入完毕！");
				mlog.error("家长导入完毕！");
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
	}		
	
	
	
}
