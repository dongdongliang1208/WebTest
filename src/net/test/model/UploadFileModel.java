package net.test.model;


import org.springframework.web.multipart.MultipartFile;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2018/5/24 19:08
 * @version 1.0
 * @description
 */
public class UploadFileModel {

	private MultipartFile file;
	private MultipartFile photo;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
}
