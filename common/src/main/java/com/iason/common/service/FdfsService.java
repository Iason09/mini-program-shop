package com.iason.common.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iason.common.config.FdfsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Iason on 2018/9/11.
 */
@Slf4j
@Service
public class FdfsService {

	@Autowired
	private FastFileStorageClient storageClient;
	@Autowired
	private FdfsConfig fdfsConfig;


	public String uploadFile(MultipartFile file) {
		try {
			StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
			return getResAbsolutePath(storePath);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("+++ deleteFile：上传文件异常");
			return null;
		}
	}

	public Boolean deleteFile(String path) {
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		try {
			StorePath storePath = StorePath.praseFromUrl(path);
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			log.info("删除" + path + "成功");
			return true;
		} catch (FdfsServerException e) {
			log.error("+++ deleteFile：删除" + path + "异常: " + e.getMessage());
			return false;
		}
	}

	/**
	 * 封装文件绝对路径
	 * @param storePath
	 * @return
	 */
	private String getResAbsolutePath(StorePath storePath) {
		return "http://" + fdfsConfig.getResHost() + "/" + storePath.getFullPath();
	}

}
