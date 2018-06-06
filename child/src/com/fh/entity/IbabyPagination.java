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
@XmlRootElement(name = "Pagination")
@XmlType(propOrder = { "totalSize", "beginIndex", "fetchSize", "trackList", "alarmInfoList" })
public class IbabyPagination {
	
	// 查询出来的轨迹总数/查询出来的监护记录总数
    @XmlElement(name = "TotalSize")  
    private String totalSize;

    // 开始位置
    @XmlElement(name = "BeginIndex")  
    private String beginIndex;    

    // 轨迹条数/监护记录数 固定给1000
    @XmlElement(name = "FetchSize")  
    private String fetchSize;   

    // 围栏监护信息
    @XmlElementWrapper(name = "AlarmInfoList")  
    @XmlElement(name = "AlarmInfo")  
    private List<IbabyAlarmInfo> alarmInfoList;

    // 轨迹信息
    @XmlElementWrapper(name = "TrackList")  
    @XmlElement(name = "Track")  
    private List<IbabyTrack> trackList;
    
    
	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(String beginIndex) {
		this.beginIndex = beginIndex;
	}

	public String getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
	}

	public List<IbabyTrack> getTrackList() {
		return trackList;
	}

	public void setTrackList(List<IbabyTrack> trackList) {
		this.trackList = trackList;
	}
	
    public List<IbabyAlarmInfo> getAlarmInfoList() {
		return alarmInfoList;
	}

	public void setAlarmInfoList(List<IbabyAlarmInfo> alarmInfoList) {
		this.alarmInfoList = alarmInfoList;
	}
}
