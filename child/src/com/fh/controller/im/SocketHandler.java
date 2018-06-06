package com.fh.controller.im;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * @author oujin
 * 消息处理类
 */
public class SocketHandler extends ChannelInboundHandlerAdapter {
	
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private WebSocketServerHandshaker handshaker;
	private final String wsUri = "/ws";
	//websocket握手升级绑定页面 
     String wsFactoryUri = ""; 
	/*
	 * 握手建立
	 */
	@Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
    }

	/*
	 * 握手取消
	 */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  
        Channel incoming = ctx.channel();
        channels.remove(incoming);
    }
	
	/*
	 * channelAction
	 *
	 * channel 通道 action 活跃的
	 *
	 * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。 
	 *
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
	}
	/*
	 * channelInactive
	 *
	 * channel 通道 Inactive 不活跃的
	 *
	 * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。 
	 *
	 */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
	}

	/*
	 * 功能：读取 h5页面发送过来的信息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {// 如果是HTTP请求，进行HTTP操作
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {// 如果是Websocket请求，则进行websocket操作
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
	/*
	 * 功能：读空闲时移除Channel
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent evnet = (IdleStateEvent) evt;
			// 判断Channel是否读空闲, 读空闲时移除Channel
			if (evnet.state().equals(IdleState.READER_IDLE)) {                
				 
			}
		}
		ctx.fireUserEventTriggered(evt);
	}

	/*
	 * 功能：处理HTTP的代码
	 */
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws UnsupportedEncodingException {
		// 如果HTTP解码失败，返回HHTP异常
		if (req instanceof HttpRequest) {
			HttpMethod method = req.getMethod();
			// 如果是websocket请求就握手升级
			if (wsUri.equalsIgnoreCase(req.getUri())) {
				WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
						wsFactoryUri, null, false);
				handshaker = wsFactory.newHandshaker(req);
				if (handshaker == null) {
					WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
				} else {
					handshaker.handshake(ctx.channel(), req);
				}
			}
			 
		}
	}
	
	/*
	 * 处理Websocket的代码
	 */
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
			// 返回应答消息
			String requestmsg = ((TextWebSocketFrame) frame).text();
		    //System.out.println("websocket消息======"+requestmsg);
		    ctx.channel().writeAndFlush(requestmsg);
			String[] array= requestmsg.split(",");
            // 将通道加入通道管理器
			if(array.length==1){
				   //添加channel
			UserInfoManager.addChannel(array[0],ctx.channel());
			}
	        if (array.length== 2) {
	        	if(array[0].equals("remove")){
	        		//删除channel
	        	 UserInfoManager.removeChannel(array[1]);
	        	} 
	        }
	        if (array.length== 3) {
	        	if(array[0].equals("check")){
	        		//channel是否在线
	        		UserInfoManager.cheakChannel(array[1],array[2]);
	        	}else{
	                //聊天
	        	 String sendid=array[0];String friendid=array[1];String messageid=array[2];
	             UserInfoManager.broadcastMess(sendid,friendid,messageid);
	        	}
	        }
	}
	/**
	 * 功能：服务端发生异常的操作
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
