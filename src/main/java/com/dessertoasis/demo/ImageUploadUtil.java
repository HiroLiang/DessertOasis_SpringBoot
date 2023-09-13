package com.dessertoasis.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

	private static final String userRoot = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images";

	public String savePicture(MultipartFile file, Integer memberId, String folderName, String projectName) {
		if (!file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String uniqueFileName = generateUniqueFileName(originalFilename);
			String memberFolderPath = userRoot + "/" + folderName + "/" + memberId + "/" + projectName;
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

			return folderName + "/" + memberId + "/" + projectName + "/" + uniqueFileName;
		}
		return "N";

	}

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

}
