package com.fh.util.model;



import com.fh.util.ErrorMsg;
import com.fh.util.PageData;

public class ResponseBody extends PageData {

     
    
     
     /**
 	 * 进行错误码封装
 	 * 
 	 * @param 错误码参数
 	 * @return 
 	 * @throws IOException 
 	 */
     public ResponseBody errorCode(int code) throws Exception {
		
    	 ErrorMsg errorMsg = new ErrorMsg();
    	 this.put("errorcode", errorMsg.Success(code));
    	 return this;
    	 
	}
    
	
}
