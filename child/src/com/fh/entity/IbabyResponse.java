package com.fh.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType; 

/** 
 * @create      2017-6-7 
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "Response")
@XmlType(propOrder = { "status", "pagination", "geoReverse", "positionList", "terminalExt", "telBookList", "fencingList"})
public class IbabyResponse {
	
	//返回值:状态
    @XmlElement(name = "Status")  
    private IbabyStatus status;

	//返回值:轨迹信息
    @XmlElement(name = "Pagination")    
    private IbabyPagination pagination;  
    
	//返回值:地址信息
    @XmlElement(name = "GeoReverse")    
    private IbabyGeoReverse geoReverse;  
    
    //返回值:定位信息
    @XmlElementWrapper(name = "PositionList")  
    @XmlElement(name = "Position")  
    private List<IbabyPosition> positionList;
    
	//返回值:儿童机设置信息
    @XmlElement(name = "TerminalExt")    
    private IbabyTerminalExt terminalExt;  
    
	//返回值:电话本设置信息
    @XmlElementWrapper(name = "TelBookList")  
    @XmlElement(name = "TelBook")    
    private List<IbabyTelBook> telBookList;  
    
	//返回值:电子围栏设置信息
    @XmlElementWrapper(name = "FencingList")  
    @XmlElement(name = "Fencing")    
    private List<IbabyFencing> fencingList;
    
	public IbabyStatus getStatus() {
		return status;
	}

	public void setStatus(IbabyStatus status) {
		this.status = status;
	}

	public IbabyPagination getPagination() {
		return pagination;
	}

	public void setPagination(IbabyPagination pagination) {
		this.pagination = pagination;
	}

	public List<IbabyPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<IbabyPosition> positionList) {
		this.positionList = positionList;
	}

	public IbabyGeoReverse getGeoReverse() {
		return geoReverse;
	}

	public void setGeoReverse(IbabyGeoReverse geoReverse) {
		this.geoReverse = geoReverse;
	}

	public IbabyTerminalExt getTerminalExt() {
		return terminalExt;
	}

	public void setTerminalExt(IbabyTerminalExt terminalExt) {
		this.terminalExt = terminalExt;
	}

	public List<IbabyTelBook> getTelBookList() {
		return telBookList;
	}

	public void setTelBookList(List<IbabyTelBook> telBookList) {
		this.telBookList = telBookList;
	}

	public List<IbabyFencing> getFencingList() {
		return fencingList;
	}

	public void setFencingList(List<IbabyFencing> fencingList) {
		this.fencingList = fencingList;
	}

	
}
