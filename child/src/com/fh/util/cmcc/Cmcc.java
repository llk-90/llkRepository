package com.fh.util.cmcc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.5.4 2012-10-18T14:11:48.921+08:00
 * Generated source version: 2.5.4
 * 
 */
@WebService(targetNamespace = "http://www.cmcc.com/edu/", name = "cmcc")
// @XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Cmcc {

	@WebResult(name = "Response", targetNamespace = "http://www.cmcc.com/edu/", partName = "parameters")
	@WebMethod(operationName = "EDU", action = "http://www.cmcc.com/edu/EDU")
	public Response edu(
			@WebParam(partName = "parameters", name = "Request", targetNamespace = "http://www.cmcc.com/edu/") Request parameters);

	@WebResult(name = "Response", targetNamespace = "http://www.cmcc.com/xxt/", partName = "parameters")
	@WebMethod(operationName = "XXT", action = "http://www.cmcc.com/xxt/XXT")
	public Response xxt(
			@WebParam(partName = "parameters", name = "Request", targetNamespace = "http://www.cmcc.com/xxt/") Request parameters);
}