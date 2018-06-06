package com.fh.controller.weixin.attInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.weixin.attInterface.AttInterfaceService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/attInterface")
public class AttInterfaceController extends BaseController {
	
	@Resource(name="attInterfaceService")
	private AttInterfaceService attInterfaceService;

	private static Properties prop = null; 
	//区域名称和缩写
	private static Map<Object, Object> city = new HashMap<Object, Object>(); 

	@RequestMapping(value="/attendance")
	@ResponseBody
	public String attendance( HttpServletRequest request) throws Exception{
		String flag = "";
		PageData pd = this.getPageData();
		logBefore(logger, "开始执行解密");
		pd = attInterfaceService.decrypt(pd);
		logBefore(logger, "解密成功，开始发送模板并保存至数据库");
		InputStream in = null;  
		prop = new Properties();
		//发送模板消息并插入数据库
		if(pd!=null){
			//flag = attInterfaceService.sendTemp(pd,  request);

			    try {  
			    	in = this.getClass().getClassLoader().getResourceAsStream("zone.properties");  
	//		    	in = new BufferedInputStream(new FileInputStream("resources/zone.properties"));
			        prop.load(new InputStreamReader(in,"UTF-8"));
			        city = prop;
					//考勤信息接口调用
			        flag = attInterfaceService.sendData(pd,city);
				} catch (Exception e) { 
					if(in == null){
						try {
							in = this.getClass().getClassLoader().getResourceAsStream("resources/zone.properties");
					        prop.load(new InputStreamReader(in,"UTF-8"));
					        city = prop;
					        flag = attInterfaceService.sendData(pd,city);
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
					}
			    } finally {  
			        try {  
			            in.close();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
			    }
			}     
		return flag;
	}
}
