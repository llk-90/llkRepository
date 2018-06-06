package com.fh.service.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.NoFixedFacet;


@Service("scoresImportService")
public class ScoresImportService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScoresImportMapper.datalistPage", page);
	}
	
	/*
	*考试批次
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> picilist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ScoresImportMapper.picilist", page);
	}
	
	/*
	 * 导入
	 */
	@SuppressWarnings("unchecked")
	public String readExcel(MultipartFile file)throws Exception{
		//从session中得到USER_ID,再将其赋给HOUSEHOLD_ID
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//用户名
		String user_id = user.getUSER_ID();
		String errorMsg = null;
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "Import_score");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readGradeExcel(filePath, fileName, 0, 2, 0);	//执行读EXCEL操作,读出的数据导入List 0:从第1行开始；2:从第C列开始；0:第0个sheet
			List<PageData> excelPdList =new ArrayList<PageData>();
			List<String> stunNumLIst = new ArrayList<String>();
			List<PageData> piciList = new ArrayList<PageData>();
			List<PageData> snoList = new ArrayList<PageData>();
			/**
			 *{var2=手机号, var3=姓名, var4=语文, var5=数学, var6=英语, var7=政治, var8=物理
			 * var9=化学, var10=地理, var11=历史, var12=生物, var13=美术, var14=音乐, var15=体育,
			 * var16=德育考核, var17=劳动技术, var18=计算机, var19=物理实验, var20=化学实验, var21=生物实验}, 
			 */		
			String rc_batch="";
			int a = 0;			
			String kong = null;
			for(PageData excelData : listPd){
				PageData pd = new PageData();			
				a++;
				if (a == 1) {
					 rc_batch = excelData.getString("var9"); 
				}else if (a == 2) {
					continue;
				}else {
										
					if (Tools.isEmpty(excelData.getString("var2"))){
						errorMsg = "Excel表格中手机号不能为空,请核查后重新导入！";
						break;
					}
					
			        String rc_phone = excelData.getString("var2");//手机号	  
					stunNumLIst.add(rc_phone);				            //添加手机号
					pd.put("RC_PHONE",rc_phone );						//手机号					
					 //验证考试批次
					if (Tools.isEmpty(rc_batch)) {	 
						errorMsg = "考试批次为空或者Excel表格批次位置[应该在J列]录入错误！";
						break;						
					}				
					pd.put("RC_BATCH", rc_batch);						//考试批次
					//验证姓名
					String var3 = excelData.getString("var3");
					if (Tools.checkName(var3)) {	  
						pd.put("RC_NAME", var3);
					}else if(Tools.isEmpty(excelData.getString("var3"))) {
						errorMsg = "姓名不能为空";
						break;
					}
					else {
						errorMsg = "姓名录入错误";
						break;
					}
					double total = 0;
					double totalS = 0;
					//验证语文成绩
					String RC_CHINESE = excelData.getString("var4");
					if (Tools.isEmpty(RC_CHINESE)) {	  
						total+=totalS;
						pd.put("RC_CHINESE", kong);	//语文
					}else if (Tools.checkGrade(RC_CHINESE)) {
						Double chinese = Double.parseDouble(RC_CHINESE.toString());
						total+=chinese;
						pd.put("RC_CHINESE", RC_CHINESE); //语文
					} else{
						errorMsg = "语文成绩录入错误";
						break;	
					}
					//验证数学成绩
					String RC_MATH = excelData.getString("var5");
					if (Tools.isEmpty(RC_MATH)) {	  
						total+=totalS;
						pd.put("RC_MATH", kong);	//数学
					}else if (Tools.checkGrade(RC_MATH)) {
						Double math = Double.parseDouble(RC_MATH.toString());
						total+=math;
						pd.put("RC_MATH", RC_MATH); //数学
					} else{
						errorMsg = "数学成绩录入错误";
						break;	
					}					
					//验证英语成绩
					String RC_ENGLISH = excelData.getString("var6");
					if (Tools.isEmpty(RC_ENGLISH)) {	  
						total+=totalS;
						pd.put("RC_ENGLISH", kong);	//英语
					}else if (Tools.checkGrade(RC_ENGLISH)) {
						Double english = Double.parseDouble(RC_ENGLISH.toString());
						total+=english;
						pd.put("RC_ENGLISH", RC_ENGLISH); //英语
					} else{
						errorMsg = "英语成绩录入错误";
						break;	
					}
					//验证政治成绩
					String RC_POLITICS = excelData.getString("var7");
					if (Tools.isEmpty(RC_POLITICS)) {	  
						total+=totalS;
						pd.put("RC_POLITICS", kong);	//政治
					}else if (Tools.checkGrade(RC_POLITICS)) {
						Double politics = Double.parseDouble(RC_POLITICS.toString());
						total+=politics;
						pd.put("RC_POLITICS", RC_POLITICS); //政治
					} else{
						errorMsg = "政治成绩录入错误";
						break;	
					}
					//验证物理成绩
					String RC_HYSICS = excelData.getString("var8");
					if (Tools.isEmpty(RC_HYSICS)) {	  
						total+=totalS;
						pd.put("RC_HYSICS", kong);	//物理
					}else if (Tools.checkGrade(RC_HYSICS)) {
						Double hysics = Double.parseDouble(RC_HYSICS.toString());
						total+=hysics;
						pd.put("RC_HYSICS", RC_HYSICS); //物理
					} else{
						errorMsg = "物理成绩录入错误";
						break;	
					}
					//验证化学成绩
					String RC_CHEMISTRY = excelData.getString("var9");
					if (Tools.isEmpty(RC_CHEMISTRY)) {	  
						total+=totalS;
						pd.put("RC_CHEMISTRY", kong);	//化学
					}else if (Tools.checkGrade(RC_CHEMISTRY)) {
						Double chemistry = Double.parseDouble(RC_CHEMISTRY.toString());
						total+=chemistry;
						pd.put("RC_CHEMISTRY", RC_CHEMISTRY); //化学
					} else{
						errorMsg = "化学成绩录入错误";
						break;	
					}
					//验证地理成绩
					String RC_GEOGRAPHY = excelData.getString("var10");
					if (Tools.isEmpty(RC_GEOGRAPHY)) {	  
						total+=totalS;
						pd.put("RC_GEOGRAPHY", kong);	//地理
					}else if (Tools.checkGrade(RC_GEOGRAPHY)) {
						Double geography = Double.parseDouble(RC_GEOGRAPHY.toString());
						total+=geography;
						pd.put("RC_GEOGRAPHY", RC_GEOGRAPHY); //地理
					} else{
						errorMsg = "地理成绩录入错误";
						break;	
					}
					//验证历史成绩
					String RC_HISTORY = excelData.getString("var11");
					if (Tools.isEmpty(RC_HISTORY)) {	  
						total+=totalS;
						pd.put("RC_HISTORY", kong);	//历史
					}else if (Tools.checkGrade(RC_HISTORY)) {
						Double history = Double.parseDouble(RC_HISTORY.toString());
						total+=history;
						pd.put("RC_HISTORY", RC_HISTORY); //历史
					} else{
						errorMsg = "历史成绩录入错误";
						break;	
					}					
					//验证生物成绩
					String RC_BIOLOGY = excelData.getString("var12");
					if (Tools.isEmpty(RC_BIOLOGY)) {	  
						total+=totalS;
						pd.put("RC_BIOLOGY", kong);	//生物
					}else if (Tools.checkGrade(RC_BIOLOGY)) {
						Double biology = Double.parseDouble(RC_BIOLOGY.toString());
						total+=biology;
						pd.put("RC_BIOLOGY", RC_BIOLOGY); //生物
					} else{
						errorMsg = "生物成绩录入错误";
						break;	
					}	
					//验证美术成绩
					String RC_ART = excelData.getString("var13");
					if (Tools.isEmpty(RC_ART)) {	  
						total+=totalS;
						pd.put("RC_ART", kong);	//美术
					}else if (Tools.checkGrade(RC_ART)) {
						Double art = Double.parseDouble(RC_ART.toString());
						total+=art;
						pd.put("RC_ART", RC_ART); //美术
					} else{
						errorMsg = "美术成绩录入错误";
						break;	
					}	
					//验证音乐成绩
					String RC_MUSIC = excelData.getString("var14");
					if (Tools.isEmpty(RC_MUSIC)) {	  
						total+=totalS;
						pd.put("RC_MUSIC", kong);	//音乐
					}else if (Tools.checkGrade(RC_MUSIC)) {
						Double music = Double.parseDouble(RC_MUSIC.toString());
						total+=music;
						pd.put("RC_MUSIC", RC_MUSIC); //音乐
					} else{
						errorMsg = "音乐成绩录入错误";
						break;	
					}	
					//验证体育成绩
					String RC_SPORT = excelData.getString("var15");
					if (Tools.isEmpty(RC_SPORT)) {	  
						total+=totalS;
						pd.put("RC_SPORT", kong);	//体育
					}else if (Tools.checkGrade(RC_SPORT)) {
						Double sport = Double.parseDouble(RC_SPORT.toString());
						total+=sport;
						pd.put("RC_SPORT", RC_SPORT); //体育
					} else{
						errorMsg = "体育成绩录入错误";
						break;	
					}	
					//验证德育考核成绩
					String RC_MORAL = excelData.getString("var16");
					if (Tools.isEmpty(RC_MORAL)) {	  
						total+=totalS;
						pd.put("RC_MORAL", kong);	//德育考核
					}else if (Tools.checkGrade(RC_MORAL)) {
						Double moral = Double.parseDouble(RC_MORAL.toString());
						total+=moral;
						pd.put("RC_MORAL", RC_MORAL); //德育考核
					} else{
						errorMsg = "德育考核成绩录入错误";
						break;	
					}	
					//验证劳动技术成绩
					String RC_LABOR = excelData.getString("var17");
					if (Tools.isEmpty(RC_LABOR)) {	  
						total+=totalS;
						pd.put("RC_LABOR", kong);	//劳动技术
					}else if (Tools.checkGrade(RC_LABOR)) {
						Double labor = Double.parseDouble(RC_LABOR.toString());
						total+=labor;
						pd.put("RC_LABOR", RC_LABOR); //劳动技术
					} else{
						errorMsg = "劳动技术成绩录入错误";
						break;	
					}	
					//验证计算机成绩
					String RC_COMPUTER = excelData.getString("var18");
					if (Tools.isEmpty(RC_COMPUTER)) {	  
						total+=totalS;
						pd.put("RC_COMPUTER", kong);	//计算机
					}else if (Tools.checkGrade(RC_COMPUTER)) {
						Double computer = Double.parseDouble(RC_COMPUTER.toString());
						total+=computer;
						pd.put("RC_COMPUTER", RC_COMPUTER); //计算机
					} else{
						errorMsg = "计算机成绩录入错误";
						break;	
					}	
					//验证物理实验成绩
					String RC_PHYTEST = excelData.getString("var19");
					if (Tools.isEmpty(RC_PHYTEST)) {	  
						total+=totalS;
						pd.put("RC_PHYTEST", kong);	//物理实验
					}else if (Tools.checkGrade(RC_PHYTEST)) {
						Double phy = Double.parseDouble(RC_PHYTEST.toString());
						total+=phy;
						pd.put("RC_PHYTEST", RC_PHYTEST); //物理实验
					} else{
						errorMsg = "物理实验成绩录入错误";
						break;	
					}	
					//验证化学实验成绩
					String RC_CHETEST = excelData.getString("var20");
					if (Tools.isEmpty(RC_CHETEST)) {	  
						total+=totalS;
						pd.put("RC_CHETEST", kong);	//化学实验
					}else if (Tools.checkGrade(RC_CHETEST)) {
						Double che = Double.parseDouble(RC_CHETEST.toString());
						total+=che;
						pd.put("RC_CHETEST", RC_CHETEST); //化学实验
					} else{
						errorMsg = "化学实验成绩录入错误";
						break;	
					}	
					//验证生物实验成绩
					String RC_BIOTEST = excelData.getString("var21");
					if (Tools.isEmpty(RC_BIOTEST)) {	  
						total+=totalS;
						pd.put("RC_BIOTEST", kong);	//生物实验
					}else if (Tools.checkGrade(RC_BIOTEST)) {
						Double bio = Double.parseDouble(RC_BIOTEST.toString());
						total+=bio;
						pd.put("RC_BIOTEST", RC_BIOTEST); //生物实验
					} else{
						errorMsg = "生物实验成绩录入错误";
						break;	
					}	
					pd.put("total", total);						
					excelPdList.add(pd);
					pd.put("userId", user_id);
					piciList.add(pd);
					snoList.add(pd);
				}
				
					    
			}
			// 判断批次在成绩表中是否存在
			if(Tools.isEmpty(errorMsg)){
				for(PageData piList : piciList){
				List<PageData> listp =	(List<PageData>) dao.findForList("ScoresImportMapper.findPmCount", piList);
				for(PageData pList : listp){
					String piciCount = pList.get("pici_num").toString();
					Integer piciCount1 = Integer.parseInt(piciCount.toString());
					if( piciCount1 > 0 ){
						errorMsg = "此次成绩已经成功导入,请核查批次是否正确，重新导入！";
						break;
					}
				}
				}		
			}
			// 查询此学号的学生是否该老师的学生 
			/*if(Tools.isEmpty(errorMsg)){
				for(PageData noList : snoList){
				String num = noList.getString("RC_STU_NO");
				List<PageData> listn =	(List<PageData>) dao.findForList("ScoresImportMapper.findNmCount", noList);
				for(PageData nList : listn){					
					String piciCount = nList.get("stu_num").toString();
					Integer piciCount1 = Integer.parseInt(piciCount.toString());
					if( piciCount1 <= 0 ){
						errorMsg = "学号【"+num + "】的学生不是该老师的学生，请核查学生信息是否录入正确,重新导入！";
						break;
					}
				}
				}		
			}*/
						
			if(Tools.isEmpty(errorMsg)){
				for (String stu_phone : stunNumLIst){
					// 判断学生手机号在学生信息表中是否存在
					int scoreCount = (int) dao.findForObject("ScoresImportMapper.findBmCount", stu_phone);
					if( scoreCount <= 0 ){
						errorMsg = "手机号【"+stu_phone +  "】的学生不存在，请核查手机号是否录入正确,重新导入！";
						break;
					}			
				}
			}
			if(Tools.isEmpty(errorMsg)){
				for(PageData excelData : excelPdList){
					dao.save("ScoresImportMapper.saveS", excelData);
				}
				errorMsg = Const.SUCCESS;
			}
		}
		return errorMsg;
	}
	
	/*
	* 批量删除
	*/
	public Map<String, Object> deleteAll(PageData pd)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("array_ID",ArrayDATA_IDS);
			dao.delete("ScoresImportMapper.deleteAll" ,ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return map;
	}
}