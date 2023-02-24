package shop.api.rest.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;

/**
 * @author user : KMH
 * 22.02.05
 * 파일 관리
 * **/
public class FileUtil {
	
	public static String removeFiles(List<Map<String, Object>> realPath) throws IOException {
		realPath.forEach(path -> {
			File file = new File(String.valueOf(path.get("fullPath")));
			if(file.exists()) {
			    file.delete();
			} else {
				new NoSuchFileException(String.valueOf(path.get("fullPath")), "404", "Not Found");
			}
		});
		return "remove files";
	}

    public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {

        // 1. 확장자
        int pos     = originalName.lastIndexOf(".");
        String ext  = originalName.substring(pos + 1);

        // 2. 파일저장명 변경
        String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;

        // 3. 파일 저장경로
        String uploadedFullPathName = uploadPath + "/" + savedName;
        uploadedFullPathName = uploadedFullPathName.substring(uploadPath.length());

        // 4. 파일 저장
        File target = new File(uploadPath, savedName);
        FileCopyUtils.copy(fileData, target);

        return uploadedFullPathName;
    }

    public static String uploadFile(String uploadPath, byte[] fileData) throws IOException {

        //저장파일명 생성 (UUID)
        String savedName = UUID.randomUUID().toString().replaceAll("-", "");

        //파일 저장
        File target = new File(uploadPath, savedName);
        FileCopyUtils.copy(fileData, target);

        return savedName;
    }
	
    public static String calcPath(String uploadPath) {

        //기본경로 패스가 없으면 만들기.
        File dirPath = new File(uploadPath);

        if (!dirPath.exists()) {
            dirPath.mkdir();
        }

        String replaceUploadPath = uploadPath.replace(File.separatorChar, '/');
        return makeDir(replaceUploadPath, getFolderDate());
    }
	
	private static String makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists()) {
			return null;
		}

		for (String path : paths) {
			uploadPath += path;
			File dirPath = new File(uploadPath);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
		return uploadPath;
	}
	
   public static String getFolderDate() {
        Calendar cal = Calendar.getInstance();
        String yearPath     = "/" + cal.get(Calendar.YEAR);
        String monthPath    = new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String datePath     = new DecimalFormat("00").format(cal.get(Calendar.DATE));
        return yearPath + monthPath + datePath;
    }
	   
	// 파일명 (UTF-8 전환)
    public static String transUtf8FileName(String fileName) {
        byte[] transFileName = fileName.getBytes(StandardCharsets.UTF_8);
        return URLEncoder.encode(new String(transFileName, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}