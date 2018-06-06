package com.fh.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fh.entity.Comment;
import com.fh.entity.Moment;
/**
 * 
 * @author phf
 *
 */
public class Utils {
	/**
	 * 保存文件
	 * @param file
	 * @return 返回文件路径
	 */
	public static String saveFile(MultipartFile file){
		String path = null;
		// 路径 文件名
		String filepaths = "";
		// 附件接收
		if (!file.isEmpty()) {
			// 取文件格式后缀名
			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			// 实际存储文件名
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
			String fileName = sdf.format(new Date())+fileType;
			//System.out.println(new SimpleDateFormat("GGGG-yyyy-MM-dd HH:mm:ss:SS").format(new Date()));
			//String filename = UuidUtil.get32UUID() + fileType;// 取32位UUID作为文件名
			filepaths = "/"+ Const.FILEPATHIMG + fileName;
			//pd.put("pictureURL", "/files"+filepaths);
			path = Tools.readTxtFile(Const.FILENAME) + filepaths;
			System.out.println(path);
			// 存放位置
			File destFile = new File(path);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	
	
	
	public static PageData changeMomentToPd(Moment moment) {
		PageData pd = new PageData();
		if(moment!=null) {
			//pd.put("commentList", moment.getList());
			pd.put("key", moment.getKey());
			pd.put("id", moment.getId());
			pd.put("uid", moment.getUid());
			pd.put("title", moment.getTitle());
			pd.put("content", moment.getContent());
			pd.put("picture", moment.getPicture());
			if(moment.getPicture()==null) {
				pd.put("picture", "");
			}
			pd.put("upvote_id", moment.getUpvote_id());
			pd.put("create_time", moment.getCreate_time());
			pd.put("is_delete", moment.getIs_delete());
			pd.put("ClassId", moment.getClassId());
			pd.put("usertype", moment.getUsertype());
		}
		return pd;
	}
	
	
	public static PageData changeCommentToPd(Comment comment){
		PageData pd = new PageData();
		if(comment!=null) {
			pd.put("id", comment.getId());
			pd.put("mid", comment.getMid());
			pd.put("uid", comment.getUid());
			pd.put("reply_id", comment.getReply_id());
			/*if(comment.getReply_id()==null) {
				pd.put("reply_id", "");
			}*/
			pd.put("content", comment.getContent());
			pd.put("create_time", comment.getCreate_time());
			if("".equals(comment.getCreate_time().toString().trim())) {
				pd.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			pd.put("is_delete", comment.getIs_delete());
			pd.put("ClassId", comment.getClassId());
			if(comment.getClassId()==null) {
				pd.put("ClassId", "");
			}
			pd.put("usertype", comment.getUsertype());
		}
		return pd;
	}
}
