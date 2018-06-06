package com.fh.controller.im;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

 

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author oj
 * Channel的管理器
 */
@SuppressWarnings("deprecation")
public class UserInfoManager {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<String, List<Channel> > channelInfos = new ConcurrentHashMap<>();
    /**
     * 登录注册 channel
     *
     *  
     */
    public static void addChannel(String uid,Channel channel) {
    	 
    	String flag="0";
    	Set<String> keySet = channelInfos.keySet();
    	for(String id:keySet){
    		if(id.equals(uid)){
    			flag="1";
    			break;
    		} 
    	}
    	//id不存在，新建map
    	if(flag.equals("0")){
    		List<Channel> list=new ArrayList<Channel>();
        	list.add(channel);
        	channelInfos.put(uid, list);
    	}
    	//该id已存在，将channel加入到该id的map
        if(flag.equals("1")){
        	List list=channelInfos.get(uid);
        	list.add(channel);
        	channelInfos.put(uid, list);
        }
        for(int i=0;i<channelInfos.get(uid).size();i++){
        	channelInfos.get(uid).get(i).writeAndFlush(new TextWebSocketFrame(uid+"已连接"));
        }
     
    }

    /**
     * 普通消息
     *
     * @param message
     */
    public static void broadcastMess(String sender ,String  fid,String message) {
    	      for(int i=0;i<channelInfos.get(fid).size();i++){
    	        	channelInfos.get(fid).get(i).writeAndFlush(new TextWebSocketFrame("chatflag"+","+sender+","+message));
    	        }
    }
    public static void cheakChannel(String receiveid,String sendid){
    	Set<String> keySet = channelInfos.keySet();
    	String flag="0";
    	for(String id:keySet){
    		if(id.equals(receiveid)){
    			for(int i=0;i<channelInfos.get(sendid).size();i++){
    	        	channelInfos.get(sendid).get(i).writeAndFlush(new TextWebSocketFrame("onlineflag"+","+receiveid+","+"在线"));
    	        }
    			flag="1";
    			break;
    		} 
    	}
    	if(flag.equals("0")){
    		for(int i=0;i<channelInfos.get(sendid).size();i++){
	        	channelInfos.get(sendid).get(i).writeAndFlush(new TextWebSocketFrame("onlineflag"+","+receiveid+","+"离线"));
	        }
    	}
    }
    
    
   public static void removeChannel(String id){
	   List<Channel> clist= channelInfos.get(id);
	   if(clist.size()!=0){
	   for(Channel channel:clist){
		   channel.close();
	   }
	   channelInfos.remove(id);
   }
   }
    /**
     * response消息
     *
     * @param message
     */ 
    private static void responseToClient(Channel ch, Object msg) {  
    	
        String serverMsg = (String) msg;  
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(serverMsg  
                .getBytes()));  
        response.headers().set(CONTENT_TYPE, "text/plain");  
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());  
        response.headers().set(CONNECTION, Values.KEEP_ALIVE);  
        ch.writeAndFlush(response);  
    }  

}
