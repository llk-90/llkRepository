package com.fh.util;

import org.springframework.context.ApplicationContext;

/**
 * 项目名称：
 * 
 * @author:fh
 * 
 */
public class Const {
	public static final String WEIXIN_APPID = "wx1550529915a2e7a0";
	public static final String WEIXIN_SECRET = "0f146cef22942ffd711058178e4f2ead";
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList"; // 当前菜单
	public static final String SESSION_allmenuList = "allmenuList"; // 全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";
	public static final String SESSION_USERROL = "USERROL"; // 用户对象
	public static final String SESSION_USERNAME = "USERNAME"; // 用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String SUCCESS = "success";
	public static final String LOGIN = "/login_toLogin.do"; // 登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt"; // 系统名称路径
	public static final String PAGE = "admin/config/PAGE.txt"; // 分页条数配置路径
	public static final String EMAILs = "admin/config/EMAIL.txt"; // 邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt"; // 短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt"; // 短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt"; // 文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt"; // 图片水印配置路径
	public static final String WEIXIN = "admin/config/WEIXIN.txt"; // 微信配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";// WEBSOCKET配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/"; // 图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/"; // 文件上传路径
	public static final String SCOREFILENAME = "Import_score.xls";// 成绩列表模板名字
	public static final String TEACHERFILENAME = "Import_teacher.xls";// 教师导入模板名字
	public static final String StudentFileName = "Import_student.xls";// 和教育学生信息导入模板名字
	public static final String StudentFileName2 = "Import_student_zhangzonbao.xls";// 掌踪宝学生信息导入模板名字
	public static final String StuOpenCardFileName = "Import_StuOpenCard.xls";// 学生开卡信息导入模板名字
	public static final String FILENAME = "admin/config/FILENAME.txt";// 课件文件上传路径
	public static final String KEYWORD = "admin/config/KEYWORD.txt";
	public static final String IMAGEURL = "admin/config/IMAGE.txt";//图片存储路径
	public static final String TARBAR = "\\admin\\config\\TARBAR.txt";
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; // 二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((gradesList)|(uploadFiles)|(isLogin)|(WxTest)|(WxUtil)|(CallBackServlet)|(pengApp)|(userInfoApp)|(appSettings)|(appHelp)|(appInfo)|(wx)|(login)|(logout)|(code)|(app)|(weixin)|(static)|(websocket)|(webapp)|(xft)|(weixiplusCommon)|(weixiplusBing)|(weixiplusEducationInfo)|(weixiplusEducationInfoForThird)|(weixiplusHomepage)|(weixinplusmicrocampus)|(attInterface)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static final String HTML_TEACHER = ".*/((login.html)|(leave_list.html)|(send_notice.html)|(messageList.html)|(personal_center_teacher.html)|(contacts.html)|(BingPage.html)).*";
	// 20170616 START
	public static final String HTML_PARENT = ".*/((bind.html)|(login.html)|(pay.html)|(courseware_type.html)|(attendanceCheck.html)|(toAttendance.webapp)|(urls.html)|(leaveApplication.html)|(all_grades.html)|(notice_list.html)|(messageContent.html)|(messageContentTeacher.html)|(personal_center_parent.html)|(iBaby.index.html)|(BingPage.html)|(schoolChoose.html)).*"; // 不对匹配该值的访问路径拦截（正则）
	// 20170616 END  
	public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化
	
	// 20180131 START by zp
	public static final String XFT_SCHOOL = "admin/xft/"; // WEBSOCKET配置路径
	// 20180131 END by zp
	/**
	 * 角色
	 */
	public static final String ADMINS = "0";// 管理员
	public static final String QUYU = "1";// 区域
	public static final String JIAOSHI = "2";// 教师
	public static final String JIAZHANG = "3";// 家长
	public static final String ZUZHNAG = "4";// 组长
	public static final String KEHU = "5";// 客户经理
	public static final String SCHOOLADMIN = "6";// 学校管理员

	/**
	 * 爱倍多平台协议地址
	 */
	public static final String IBABYURL = "http://www.abdjy.com:12346/routon";
	/**
	 * 创蓝短信发送
	 */
	// public static final String SMURL = "http://222.73.117.158/msg/";// 应用地址
	public static final String SMURL = "http://222.73.117.158/msg/";// 应用地址

	public static final String SMACCOUNT = "Zhangs888";// 账号
	public static final String SMPSWD = "Zhangs888888";// 密码
	/**
	 * 信息推送
	 */
	protected static final String APPID = "Ok4OzsGUCD7j52F0ffsUh2";
	protected static final String APPKEY = "YZ4TqPiUBPACMHgcmd4qqA";
	protected static final String AppSecret = "8qnRzUmoRj6fAxx6Hy1xm2";
	protected static final String MASTERSECRET = "CqSsO1j8Gf9fstLozelEz4";
	protected static final String HOST = "http://sdk.open.api.igexin.com/apiex.htm"; // OpenService接口地址
	/**
	 * 邮件发送
	 */
	public static final String SMTP = "smtp.qq.com";
	public static final String PORT = "25";
	public static final String EMAIL = "787907000@qq.com";
	public static final String PAW = "ugutziberrlmbdif";

	/**
	 * 课件分析类别
	 */
	public static final String DAY = "day";
	public static final String WEEK = "week";
	public static final String MONTH = "month";
	public static final Integer TYPE_PIC_CODE = 1;
	public static final Integer TYPE_VIDEO_CODE = 2;
	public static final Integer TYPE_TEXT_CODE = 3;
	public static final String TYPE_PIC_NAME = "图文类";
	public static final String TYPE_VIDEO_NAME = "视频类";
	public static final String TYPE_TEXT_NAME = "文档类";
	/**
	 * 课件分析导出时使用
	 */
	public static final String TYPE_DAY_CODE = "1";
	public static final String TYPE_WEEK_CODE = "2";
	public static final String TYPE_MONTH_CODE = "3";

	/* 角色组 */
	public static final String ROLEADMIN = "1";// 系统管理员
	public static final String ROLEFARM = "2";// 用户组

	/* 用户初次登陆时提示信息 */
	public static final String TISHI = "无上次登录信息！";
	/**
	 * APP Constants
	 */
	// app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[] { "countries", "uname", "passwd", "title",
			"full_name", "company_name", "countries_code", "area_code", "telephone", "mobile" };
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[] { "国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称",
			"国家编号", "区号", "电话", "手机号" };

	// app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };
	public static final String YanzhmFileName = "Import_Yanzhm.xls";// 验证码信息导入模板名字

	/**
	 * 校付通
	 */
	// 支付订单返回前端code：200 （成功）
	public static final String RESULTSCUCODE = "200";
	// 支付订单返回前端msg
	public static final String RESULTSUCMSG = "订单创建成功，请前往支付";
	// 支付订单返回前端code：404（失败）
	public static final String RESULTFAILCODE = "404";
	// 支付订单返回前端msg：
	public static final String RESULTFALIMSG = "订单创建失败，请返回重新创建";
	public static final String RESULTERRMSG = "订单创建失败，未能查询到充值账号";
	public static final String SIGNATURE_VALID_FAIL = "signature_valide_fail";
	public static final String SIGNATURE_VALID_SUC = "success";

}
