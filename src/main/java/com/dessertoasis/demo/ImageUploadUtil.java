package com.dessertoasis.demo;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ImageUploadUtil {
	//請將上傳圖檔路徑請改為自己裝置上的路徑
	private static final String userRoot = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images";
	
	public String savePicture(MultipartFile file, Integer memberId, String folderName,String projectName) {
		if(!file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String uniqueFileName = generateUniqueFileName(originalFilename);
			String memberFolderPath  = userRoot+"/"+folderName+"/"+memberId+"/"+projectName;
			File folder = new File(memberFolderPath);
			
			//檢查資料夾是否存在  不存在則建立資料夾
			if (!folder.exists()) {
                folder.mkdirs();
            }
			
			
			String targetFilePath = memberFolderPath + "/" + uniqueFileName;
            File targetFile = new File(targetFilePath);
            
            //將檔案存於指定路徑
            try {
				file.transferTo(targetFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
            
            return folderName + "/"+ memberId+"/"+projectName+"/"+uniqueFileName;
		}
		return "N";
		
	}
	
	public static String getPath() {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("ImgSavePath");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String property = properties.getProperty("savePath");
		return property;
	}
	 private String generateUniqueFileName(String originalFilename) {
		 String fileName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	        return fileName + "_" + timestamp + extension;
	    }
	
}


