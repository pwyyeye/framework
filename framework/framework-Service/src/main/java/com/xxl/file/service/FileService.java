package com.xxl.file.service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xxl.baseService.dao.IFrameworkDao;
import com.xxl.facade.FileRemote;
import com.xxl.facade.HelperRemote;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.service.BaseService;
import common.utils.SemAppUtils;

@Service("fileRemote")
public class FileService extends BaseService implements FileRemote,InitializingBean{

	public Log logger = LogFactory.getLog(this.getClass());

	private final static String FILESERVER_ACCESS_KEY = "FILESERVER_ACCESS_KEY";

	private final static String FILESERVER_SECRET_KEY = "FILESERVER_SECRET_KEY";

	private final static String FILESERVER_BUCKET = "FILESERVER_BUCKET";
	private final static String MADIA_BUCKET = "MADIA_BUCKET";
	private final static String FILESERVER_EXPIRES = "FILESERVER_EXPIRES";
	private final static String FILESERVER_CALLBACK_URL = "FILESERVER_CALLBACK_URL";
	private final static String FILESERVER_CALLBACK_HOST = "FILESERVER_CALLBACK_HOST";
	private final static String FILESERVER_URL = "FILESERVER_URL";

	private final static int WEBSERVICES_ERROR_MESSAGE_ID = 1200;

	@Autowired
	private HelperRemote helperRemote;

	private String fileServerAccessKey = "";

	private String fileServerSecretKey = "";
	private String fileServerBucket = "";
	private String mediaBucket = "";
	private int fileServerExpires = 24 * 3600; // default one hour,
	private String callbackUrl = "";
	private String callbackHost = "";
	private String fileServerUrl = "";

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		try {
			fileServerAccessKey = helperRemote
					.getProperty(FILESERVER_ACCESS_KEY);
			fileServerSecretKey = helperRemote
					.getProperty(FILESERVER_SECRET_KEY);
			fileServerBucket = helperRemote.getProperty(FILESERVER_BUCKET);
			mediaBucket = helperRemote.getProperty(MADIA_BUCKET);
			callbackUrl = helperRemote.getProperty(FILESERVER_CALLBACK_URL);
			callbackHost = helperRemote.getProperty(FILESERVER_CALLBACK_HOST);
			fileServerUrl = helperRemote.getProperty(FILESERVER_URL);
		} catch (Exception ee) {

		}
	}
	
	/**
	 * 7牛服务器接口
	 */
	public String getUploadFileToken() {
		String token = null;
		try {
			token = (String) helperRemote.getCustomValue(fileServerBucket
					+ SemAppUtils.getStdDateInt(Calendar.getInstance()));
		} catch (Exception ee) {
			token = null;
		}
		if (token == null) {
			Auth auth = Auth.create(this.fileServerAccessKey,
					this.fileServerSecretKey);
			StringMap sm = new StringMap();
			// sm.putNotEmpty("callbackUrl", callbackUrl);// config server
			// sm.putNotEmpty("callbackHost", callbackHost);// config server
			// sm
			// .putNotEmpty(
			// "callbackBody",
			// "key=$(key)&hash=$(etag)&endUser=$(endUser)&actionId=$(x:actionId)&args=$(x:args)");//
			// config
			// server
			token = auth.uploadToken(fileServerBucket, null,
					fileServerExpires, sm, true);
			try {

				helperRemote.setCustomValue(fileServerBucket
						+ SemAppUtils.getStdDateInt(Calendar.getInstance()),
						token);
			} catch (RemoteException e) {
				logger.error("set custom value failer", e);
			}
		}
		return token;
	}

	public String getUploadMediaToken() {
		String token = null;
		try {
			token = (String) helperRemote.getCustomValue(mediaBucket
					+ SemAppUtils.getStdDateInt(Calendar.getInstance()));
		} catch (Exception ee) {
			token = null;
		}
		if (token == null) {
			Auth auth = Auth.create(this.fileServerAccessKey,
					this.fileServerSecretKey);
			StringMap sm = new StringMap();
			sm.putNotEmpty("scope", mediaBucket);
			sm.putNotEmpty("deadline", "1390528576");
			sm.putNotEmpty("saveKey", "$(fsize)$(fname)");
			sm
					.putNotEmpty(
							"persistentOps",
							"avthumb/mp4|saveas/bWVkaWFzOiQoZm5hbWUpLm1wNA==;vframe/jpg/offset/0.1/rotate/90|saveas/bWVkaWFzOiQoZm5hbWUpLmpwZw==;avthumb/mp4/s/640x480/autoscale/1|saveas/bWVkaWFzOiQoZm5hbWUpX3MubXA0"); // server
			token = auth.uploadToken(mediaBucket, null, fileServerExpires, sm,
					true);
			try {
				helperRemote.setCustomValue(mediaBucket
						+ SemAppUtils.getStdDateInt(Calendar.getInstance()),
						token);
			} catch (RemoteException e) {
				logger.error("set custom value failer", e);
			}
		}
		return token;
	}

	public String getUploadFileTokenForWeb() {
		Auth auth = Auth.create(this.fileServerAccessKey,
				this.fileServerSecretKey);
		StringMap sm = new StringMap();
		sm.putNotEmpty("saveKey", "$(fsize)$(fname)");// config server
		// sm.putNotEmpty("callbackHost", callbackHost);// config server
		// sm
		// .putNotEmpty(
		// "callbackBody",
		// "key=$(key)&hash=$(etag)&endUser=$(endUser)&actionId=$(x:actionId)&args=$(x:args)");//
		// config
		// server
		return auth.uploadToken(fileServerBucket, null, fileServerExpires, sm,
				true);
	}

	public void saveUploadFile(Integer actionId, String filename, String form)
			throws BaseBusinessException, BaseException {
		logger.debug("save actionId:[" + actionId + "],filename[" + filename
				+ "],form:[" + form + "]");
		// handle url
		String url = fileServerUrl + filename;

//		invokeEjbServices(actionId, new Serializable[] { form,
//				new String[] { url } });
	}

	public String addQiNiuFile(List<MultipartFile> Fileitems) throws BaseException, BaseBusinessException {
		try {   ///////////////////////指定上传的Zone的信息//////////////////
		    //第一种方式: 指定具体的要上传的zone
		    //注：该具体指定的方式和以下自动识别的方式选择其一即可
		    //要上传的空间(bucket)的存储区域为华东时
		    // Zone z = Zone.zone0();
		    //要上传的空间(bucket)的存储区域为华北时
		    // Zone z = Zone.zone1();
		    //要上传的空间(bucket)的存储区域为华南时
		    // Zone z = Zone.zone2();

		    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
			System.out.println("1111111111111111111111111111");
		    Zone z = Zone.autoZone();
		    Configuration c = new Configuration(z);
			Iterator<MultipartFile> itr = Fileitems.iterator();
			UploadManager uploadManager = new UploadManager(c);
			while (itr.hasNext()) {
				MultipartFile item = itr.next();
				String fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isEmpty()) {
					// 检查文件大小
					if (item.getSize() > 10 * 1024 * 1024) {
						logger.info("上传文件大小超过限制");
						throw new BaseBusinessException("上传文件大小超过限制");
					}
					// 检查扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					// if
					// (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" +
					// extMap.get(dirName) + "格式。"));
					// return;
					// }

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = SemAppUtils.getFilename(fileName)
							+ df.format(new Date()) + "." + fileExt;
					Response res = uploadManager.put(item.getBytes(), newFileName,
							getUploadFileTokenForWeb());
					// log.info(res);
					// log.info(res.bodyString());
					// Ret ret = res.jsonToObject(Ret.class);
					if (res.isOK()) {
						logger
								.info("upload file success,[" + newFileName
										+ "]");
						return  newFileName;
					} else {
						logger.info("upload file fail,[" + newFileName
								+ "],return [" + res.bodyString() + "]");//
						return null;
					}
				
				}
			}
		} catch (Exception ee) {
			logger.error(ee);
			this.handleException(ee);
		}
		return null;
		
	}
}
