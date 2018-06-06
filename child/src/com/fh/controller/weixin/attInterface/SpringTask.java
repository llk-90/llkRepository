package com.fh.controller.weixin.attInterface;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.service.weixin.attInterface.AttInterfaceService;

@Component("SpringTask")
public class SpringTask {

	@Resource(name="attInterfaceService") 
	private AttInterfaceService attInterfaceService;  
	
	@Scheduled()
    public void getUpdate(){  
		
		try {
//			attInterfaceService.updateTown();
//			attInterfaceService.sendSchoolAppData();
//			attInterfaceService.sendClassAppData();
//			attInterfaceService.updateStudent();
//			attInterfaceService.updateParent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
}
