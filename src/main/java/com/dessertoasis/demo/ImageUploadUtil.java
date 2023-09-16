package com.dessertoasis.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadUtil {
	// 請將上傳圖檔路徑請改為自己裝置上的路徑
//	private static final String userRoot = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images";
//	private static final String userRoot = "C:\\dessertoasis-vue\\public\\images";
//	private static final String userRoot ="D:/dessertoasis-vue/public/images";
//	private static final String userRoot = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images";

	// 請將上傳圖檔路徑請改為自己裝置上的路徑, 由於用方法封裝起來tomcat會塞入預設字串導致抓不到正確路徑,別人的路徑先註解掉複製改為自己的

//	private static final String userRoot = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images";

	public String savePicture(MultipartFile file, String userPath, Integer memberId, String folderName,
			String projectName) {
		if (!file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String uniqueFileName = generateUniqueFileName(originalFilename);
			String memberFolderPath = userPath + "images/" + folderName + "/" + memberId + "/" + projectName;
			File folder = new File(memberFolderPath);

			// 檢查資料夾是否存在 不存在則建立資料夾
			if (!folder.exists()) {
				folder.mkdirs();
			}

			String targetFilePath = memberFolderPath + "/" + uniqueFileName;
			File targetFile = new File(targetFilePath);

			// 將檔案存於指定路徑
			try {
				file.transferTo(targetFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			return "images/" + folderName + "/" + memberId + "/" + projectName + "/" + uniqueFileName;
		}
		return "N";

	}

	// 回傳base64字串 以及 header字串(透過headers.setContentType傳給前端判斷)
	public List<String> getPicture(String userPath, String pictureUrl) {

		List<String> base64Content = new ArrayList<>();
		try {
			BufferedImage read = ImageIO.read(new File(userPath + pictureUrl));
			if (read != null) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int lastDotIndex = pictureUrl.lastIndexOf(".");
				if (lastDotIndex > 0) {
					String formatName = pictureUrl.substring(lastDotIndex + 1);
					ImageIO.write(read, formatName, byteArrayOutputStream);
					byte[] img = byteArrayOutputStream.toByteArray();
					String base64Img = Base64.getEncoder().encodeToString(img);
					String header = "image/" + formatName;

					base64Content.add(header);
					base64Content.add(base64Img);
				}
			}
			return base64Content;

		} catch (IOException e) {
			e.printStackTrace();
			base64Content.add("file not found");
			return base64Content;
		}
	}

	private String generateUniqueFileName(String originalFilename) {
		String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		return fileName + "_" + timestamp + extension;
	}

	//儲存圖片by base64
	public String saveImageToFolder(String path, String base64, String fileName) {
		try {
			byte[] imgByte = Base64.getDecoder().decode(base64);
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream(path + "/" + fileName);
			outputStream.write(imgByte);
			outputStream.close();
			return path + "/" + fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String writeImageToString(String pictureUrl) {
		try {
			//讀出圖片
			System.out.println("loading picture : "+pictureUrl);
			BufferedImage read = ImageIO.read(new File(pictureUrl));
			if (read != null) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int lastDotIndex = pictureUrl.lastIndexOf(".");
				if (lastDotIndex > 0) {
					String formatName = pictureUrl.substring(lastDotIndex + 1);
					ImageIO.write(read, formatName, byteArrayOutputStream);
					byte[] img = byteArrayOutputStream.toByteArray();
					String base64Img = Base64.getEncoder().encodeToString(img);
					String picUrl = "data:image/" + formatName+";base64,"+base64Img;
					System.out.println("success");
					return picUrl;
				}
			}
		} catch (IOException e) {
			System.out.println("faild");
			e.printStackTrace();
		}
		return null;
	}

}
