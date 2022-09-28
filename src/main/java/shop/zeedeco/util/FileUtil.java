package shop.zeedeco.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;

/**
 * @author user : KMH
 * 22.02.05
 * 파일 관리
 * **/
public class FileUtil {
	
	public static String removeFiles(List<String> realPath) throws IOException {
		realPath.forEach(path -> {
			File file = new File(path);
			if(file.exists()) {
				file.deleteOnExit();
			} else {
				new NoSuchFileException(path, "404", "해당경로의 파일을 찾을 수 없습니다.");
			}
		});
		return "remove files";
	}

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
		
		//확장자 추출
		int pos = originalName.lastIndexOf(".");
		String ext = originalName.substring(pos + 1);
		//저장파일명 생성 (UUID)
		String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
		//savefullpathName설정
		String uploadedFullPathName = uploadPath + File.separator + savedName;
		uploadedFullPathName = uploadedFullPathName.substring(uploadPath.length()).replace(File.separatorChar, '/');
		//파일 저장
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);

		return uploadedFullPathName;
	}
	
	public static String calcPath(String uploadPath) {
		
		File dirPath = new File(uploadPath); 

		// 기본경로 패스가 없으면 만들어준다.
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		
		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String replaceUploadPath = uploadPath.replace(File.separatorChar, '/');

		return makeDir(replaceUploadPath, yearPath, monthPath, datePath);
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
	
	
}

