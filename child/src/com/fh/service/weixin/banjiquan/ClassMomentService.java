package com.fh.service.weixin.banjiquan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fh.dao.DaoSupport;
import com.fh.dao.KafkaMessageSend;
import com.fh.dao.LogicalDaoSupport;
import com.fh.dao.RedisDaoImpl;
import com.fh.entity.Comment;
import com.fh.entity.Moment;
import com.fh.util.Const;
import com.fh.util.DFAWordFilter;
import com.fh.util.ErrorMsg;
import com.fh.util.FormatData;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Utils;
import com.fh.util.UuidUtil;
import com.fh.util.WebHTMLDecodeSpecialSymbol;

import net.sf.json.JSONObject;

@Service("classMomentService")
public class ClassMomentService {
	@Resource(name = "redisDaoImpl")
	private RedisDaoImpl redisTemplate;
	@Resource(name = "KafkaMessageSend")
	private KafkaMessageSend kafkaMessageSend;
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "logicalDaoSupport")
	private LogicalDaoSupport logicalDao;
	DFAWordFilter filter = DFAWordFilter.getInstance();
	@Resource(name = "errorMsg")
	private ErrorMsg errorMsg;

	/**
	 * get all classMoment from redis , if information's size < 5 , then mysql
	 * append left data if size > 5 , pull all of information to html.
	 * 
	 * @param classId
	 * @param pageSize
	 * @param pageCount
	 * @param usertype
	 *            0.teacher 1.parent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public List<PageData> getMomentMsg(Integer uid, String classId, Integer pageSize, Integer pageCount, String key,
			Integer usertype) throws Exception {
		SqlSession sqlSession = logicalDao.getSqlSession();
		SqlSession daosqlSession = dao.getSqlSession();
		List<PageData> list = new ArrayList<>();
		if (key == null || key.trim() == "") {
			List<String> list1 = null;
			if (null != classId) {
				list1 = redisTemplate.getTypicalData(classId, "__*");
			} else {
				list1 = Collections.EMPTY_LIST;
			}
			if (null != list1 && list1.size() != 0) {
				Collections.sort(list1, Collections.reverseOrder());
				List<PageData> listDataComeplete = new ArrayList<>();
				for (String s : list1) {
					JSONObject jsonObject1 = JSONObject.fromObject(s);
					Map<String, Class<Comment>> commentMap = new HashMap<String, Class<Comment>>();
					commentMap.put("list", Comment.class);
					Moment moment = (Moment) JSONObject.toBean(jsonObject1, Moment.class, commentMap);
					if (moment.getIs_delete() != 1) {
						PageData pageDataComment = Utils.changeMomentToPd(moment);
						List<Comment> commentlist = moment.getList();
						List<PageData> commentPageList = new ArrayList<>();
						for (Comment c : commentlist) {
							PageData commentPageData = Utils.changeCommentToPd(c);
							if (1 != Integer.parseInt(commentPageData.get("is_delete").toString())) {
								PageData pageDataFromMySQL = sqlSession.selectOne("ClassMomentMapper.getParInfo",
										c.getUid());
								if (null == pageDataFromMySQL) {
									pageDataFromMySQL = daosqlSession.selectOne("ClassMomentMapper.getTeaInfo",
											c.getUid());
									if (null == pageDataFromMySQL) {
										pageDataFromMySQL = new PageData();
										pageDataFromMySQL.put("", "");
									}
								}
								PageData userInfoPageDate = pageDataFromMySQL;
								commentPageData.put("uid", userInfoPageDate.get("ParentId"));
								commentPageData.put("postUsername", userInfoPageDate.get("ParUserName"));
								if (c.getReply_id() != null && !("".equals(c.getReply_id().toString()))
										&& c.getReply_id() != 0) {

									PageData pageDataFromMySQL1 = sqlSession.selectOne("ClassMomentMapper.getParInfo",
											c.getReply_id());
									if (null == pageDataFromMySQL) {
										pageDataFromMySQL = daosqlSession.selectOne("ClassMomentMapper.getTeaInfo",
												c.getReply_id());
										if (null == pageDataFromMySQL) {
											pageDataFromMySQL = new PageData();
											pageDataFromMySQL.put("", "");
										}
									}
									userInfoPageDate = pageDataFromMySQL1;
									commentPageData.put("reply_id", userInfoPageDate.get("ParentId"));
									commentPageData.put("replyUsername", userInfoPageDate.get("ParUserName"));
								} else {
									commentPageData.put("reply_id", "");
								}
								commentPageList.add(commentPageData);
							}
						}
						pageDataComment.put("commentList", commentPageList);
						listDataComeplete.add(pageDataComment);
					}
				}
				list = listDataComeplete;
			}
			if (list.size() < 5) {
				PageData pd = new PageData();
				pd.put("classId", Integer.parseInt(classId));
				pd.put("offset", (pageCount - 1) * pageSize);
				pd.put("pageSize", pageSize - list.size());

				List<PageData> momentListFromMySQL = sqlSession.selectList("ClassMomentMapper.getClassMomentInfo", pd);
				if (null != momentListFromMySQL && momentListFromMySQL.size() != 0) {
					for (PageData pd1 : momentListFromMySQL) {
						List<PageData> commentList = new ArrayList<>();
						commentList = daosqlSession.selectList("ClassMomentMapper.getAllCommentInfo",
								(Integer) pd1.get("id"));
						pd1.put("commentList", commentList);
					}
				}
				list.addAll(momentListFromMySQL);
			}
		} else {
			PageData pd = new PageData();
			pd.put("classId", Integer.parseInt(classId));
			pd.put("pageSize", pageSize);
			Moment moment = new Moment();
			moment.setKey(key);
			Integer offset = sqlSession.selectOne("ClassMomentMapper.SearchMoment", moment);
			if (null != offset) {
				pd.put("offset", offset);
			} else {
				pd.put("offset", 0);
			}
			list = sqlSession.selectList("ClassMomentMapper.getClassMomentInfo", pd);
			if (null != list && list.size() != 0) {
				for (PageData pd1 : list) {
					List<PageData> commentList = new ArrayList<>();
					commentList = sqlSession.selectList("ClassMomentMapper.getAllCommentInfo", (Integer) pd1.get("id"));
					pd1.put("commentList", commentList);
				}
			}
		}
		if (list.size() != 0 && list != null) {
			for (PageData pd : list) {
				Integer postId = (Integer) pd.get("uid");
				String images = pd.getString("picture");
				String upvoteids = pd.getString("upvote_id");
				List<PageData> upvoteList = new ArrayList<>();
				PageData pageDataFromMySQL = sqlSession.selectOne("ClassMomentMapper.getParInfo", postId);
				if (null == pageDataFromMySQL) {
					pageDataFromMySQL = daosqlSession.selectOne("ClassMomentMapper.getTeaInfo", postId);
					if (null == pageDataFromMySQL) {
						pageDataFromMySQL = new PageData();
						pageDataFromMySQL.put("", "");
					}
				}
				pd.putAll(pageDataFromMySQL);
				if (images.trim().length() != 0) {
					String[] imageList = images.split(";");
					pd.put("picture", imageList);
				} else {
					pd.put("picture", "");
				}
				if (upvoteids != null && upvoteids.length() != 0) {
					String[] upvoteArr = upvoteids.split(";");
					for (String s : upvoteArr) {
						if (s.indexOf("p") != -1) {
							PageData pageDataFromMySQL1 = sqlSession.selectOne("ClassMomentMapper.getParInfo",
									Integer.parseInt(s.substring(1)));
							if (null == pageDataFromMySQL) {
								pageDataFromMySQL = daosqlSession.selectOne("ClassMomentMapper.getTeaInfo",
										s.substring(1));
								if (null == pageDataFromMySQL) {
									pageDataFromMySQL = new PageData();
									pageDataFromMySQL.put("", "");
								}
							}
							PageData upvotePd = pageDataFromMySQL1;
							upvoteList.add(upvotePd);
						} else if (s.indexOf("t") != -1) {
							PageData pageDataFromMySQL1 = sqlSession.selectOne("ClassMomentMapper.getTeaInfo",
									Integer.parseInt(s.substring(1)));
							if (null == pageDataFromMySQL) {
								pageDataFromMySQL = daosqlSession.selectOne("ClassMomentMapper.getTeaInfo",
										s.substring(1));
								if (null == pageDataFromMySQL) {
									pageDataFromMySQL = new PageData();
									pageDataFromMySQL.put("", "");
								}
							}
							PageData upvotePd = pageDataFromMySQL1;
							upvoteList.add(upvotePd);
						}
					}
				}
				if (upvoteids.indexOf(uid + "") != -1) {
					pd.put("is_like", 1);
				} else {
					pd.put("is_like", 0);
				}
				pd.put("upvote_id", upvoteList);
			}
		}
		dao.killSqlSession(daosqlSession);
		logicalDao.killSqlSession(sqlSession);
		return list;
	}

	/**
	 * add one new Momment
	 * 
	 * @param openid
	 *            WeChat openid
	 * @param title
	 * @param content
	 * @param classId
	 * @param request
	 *            get form file
	 */
	public void addNewMoment(Integer uid, String title, String content, Integer classId, Integer usertype,
			MultipartHttpServletRequest request) {
		MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
		List<MultipartFile> list = map.get("pictureList");
		String pictureUrl = "";
		for (MultipartFile c : list) {
			if (c.getSize() != 0) {
				if (!c.isEmpty()) {
					// 取文件格式后缀名
					String fileType = c.getOriginalFilename().substring(c.getOriginalFilename().indexOf("."));
					// 实际存储文件名
					String fileName = System.currentTimeMillis() + "__" + UuidUtil.get32UUID() + fileType;
					String filepaths = "/" + Const.FILEPATHIMG + fileName;
					String path = Tools.readTxtFile(Const.FILENAME) + filepaths;
					// 存放位置
					try {
						FileOutputStream fos = new FileOutputStream(new File(path)); // 写出到文件
						fos.write(c.getBytes());
						fos.flush();
						fos.close();
						String location = path;
						location = location.replaceAll("\\\\", "/");
						location = location.replaceAll("D:/weixin/uploadfile/", "http://www.guanai100.cn/files/");
						pictureUrl = pictureUrl + location + ";";
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Moment moment = new Moment();
		moment.setClassId(classId);
		moment.setUid(uid);
		moment.setIs_delete(0);
		title = WebHTMLDecodeSpecialSymbol.delHTMLTag(title);
		title = filter.replaceSensitiveWord(title, DFAWordFilter.MAX_MATCH_TYPE, "*");
		content = WebHTMLDecodeSpecialSymbol.delHTMLTag(content);
		content = filter.replaceSensitiveWord(content, DFAWordFilter.MAX_MATCH_TYPE, "*");
		moment.setTitle(title);
		moment.setContent(content);
		moment.setPicture(pictureUrl);
		moment.setUsertype(usertype);
		moment.setCreate_time(FormatData.getFormatNowTime());
		moment.GenerateNewKey();
		kafkaMessageSend.sendMessage(moment.getKey(), JSONObject.fromObject(moment).toString());
	}

	/**
	 * add one new Momment
	 * 
	 * @param openid
	 *            WeChat openid
	 * @param title
	 * @param content
	 * @param classId
	 * @param request
	 *            get form file
	 * @return
	 * @throws Exception
	 */
	public PageData addNewMoment2(Integer id, MultipartHttpServletRequest request) throws Exception {
		PageData pageData = new PageData();
		MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
		List<MultipartFile> list = map.get("pictureList");
		String pictureUrl = "";
		for (MultipartFile c : list) {
			if (c.getSize() != 0) {
				pictureUrl = pictureUrl + Utils.saveFile(c) + ";";
			}
		}
		PageData pd = new PageData();
		pd.put("url", pictureUrl);
		pd.put("id", id);
		try {
			dao.findForObject("CardReplacement.insertUrl", pd);
			pageData.put("errorcode", errorMsg.Success(0));
		} catch (Exception e) {
			e.printStackTrace();
			pageData.put("errorcode", errorMsg.Success(1001));
		}
		return pageData;
	}

	@SuppressWarnings("null")
	public PageData getParInfoUseInController(Integer parentId) throws Exception {
		SqlSession sqlSession = dao.getSqlSession();
		PageData pd = new PageData();
		pd = sqlSession.selectOne("ClassMomentMapper.getParInfo", parentId);
		if (null == pd) {
			pd = sqlSession.selectOne("ClassMomentMapper.getTeaInfo", parentId);
			if (null == pd) {
				pd.put("", "");
			}
		}
		dao.killSqlSession(sqlSession);
		return pd;
	}

	/**
	 * judge the redis have this moment or not?keep it
	 * 
	 * @param list
	 * @param key
	 * @return
	 */
	public Boolean isContainKey(List<PageData> list, String key) {
		if (list.size() != 0 && list != null) {
			for (PageData pd : list) {
				if (pd.getString("key").equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String updateStatus(String json) throws Exception {
		Map<String, Class<Comment>> map = new HashMap<String, Class<Comment>>();
		map.put("list", Comment.class);
		JSONObject jsonObject = JSONObject.fromObject(json);

		jsonObject.accumulate("list", jsonObject.remove("commentList"));
		String picUrls = "";
		String upvote_ids = "";
		List<JSONObject> upvote_idList = (List<JSONObject>) jsonObject.remove("upvote_id");
		for (JSONObject jsonObject2 : upvote_idList) {
			if (jsonObject2.getString("ParentId") != null) {
				upvote_ids = "p" + jsonObject2.getString("ParentId") + ";" + upvote_ids;
			} else if (jsonObject2.getString("TeacherId") != null) {
				// keep it,for teacher //
				upvote_ids = "t" + jsonObject2.getString("TeacherId") + ";" + upvote_ids;
			}
		}
		if (upvote_ids.length() != 0) {
			upvote_ids = upvote_ids.substring(0, upvote_ids.length() - 1);
		}
		if (!"".equals(jsonObject.get("picture").toString())) {
			for (String str : (List<String>) jsonObject.remove("picture")) {
				picUrls = str + ";" + picUrls;
			}
		}

		Moment moment = (Moment) JSONObject.toBean(jsonObject, Moment.class, map);

		moment.setContent(WebHTMLDecodeSpecialSymbol.delHTMLTag(moment.getContent()));
		moment.setTitle(WebHTMLDecodeSpecialSymbol.delHTMLTag(moment.getTitle()));
		moment.setPicture(picUrls);
		moment.setUpvote_id(upvote_ids);
		moment.setClassId(Integer.parseInt(jsonObject.getString("ClassId")));
		moment.setId(Integer.parseInt(jsonObject.getString("id")));

		if (moment.getId() == null || moment.getId() == 0) {
			List<Comment> listComment = new ArrayList<>();
			for (Comment comment : moment.getList()) {
				comment.setContent(WebHTMLDecodeSpecialSymbol.delHTMLTag(comment.getContent()));
				listComment.add(comment);
			}
			moment.setList(listComment);
			kafkaMessageSend.sendMessage(moment.getKey(), JSONObject.fromObject(moment).toString());
		} else {
			logicalDao.update("ClassMomentMapper.ChangeMoment", moment);
			for (Comment comment : moment.getList()) {
				comment.setContent(WebHTMLDecodeSpecialSymbol.delHTMLTag(comment.getContent()));
				comment.setMid(moment.getId());
				comment.setClassId(moment.getClassId());

				if (null == comment.getId() || 0 == comment.getId()) {
					comment.setCreate_time(FormatData.getFormatNowTime());
					logicalDao.save("ClassMomentMapper.AddComment", comment);
				} else {
					logicalDao.update("ClassMomentMapper.ChangeComment", comment);
				}
			}
		}
		return JSONObject.fromObject(moment).toString();
	}

	/**
	 * This method is using to get parent class-id
	 * 
	 * @param uid
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String getParentClass(Integer uid) throws NumberFormatException, Exception {
		return logicalDao.findForObject("ClassMomentMapper.getParentClass", uid).toString();
	}

	public PageData getInfoByOpenId(String openid) throws Exception {
		return (PageData) dao.findForObject("ClassMomentMapper.getInfoByOpenId", openid);
	}

	@SuppressWarnings("null")
	public PageData getTeaInfoUseInController(Integer teacherId) {
		SqlSession sqlSession = dao.getSqlSession();
		PageData pd = new PageData();
		pd = sqlSession.selectOne("ClassMomentMapper.getTeaInfo", teacherId);
		if (null == pd) {
			pd.put("", "");
		}
		dao.killSqlSession(sqlSession);
		return pd;
	}

	public PageData getInfoByTeaOpenId(String openid) throws Exception {
		return (PageData) dao.findForObject("ClassMomentMapper.getInfoByTeaOpenId", openid);
	}
}
