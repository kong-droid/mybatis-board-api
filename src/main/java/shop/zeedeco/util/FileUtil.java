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
 * ���� ����
 * **/
public class FileUtil {
	
	public static String removeFiles(List<String> realPath) throws IOException {
		realPath.forEach(path -> {
			File file = new File(path);
			if(file.exists()) {
				file.deleteOnExit();
			} else {
				new NoSuchFileException(path, "404", "�ش����� ������ ã�� �� �����ϴ�.");
			}
		});
		return "remove files";
	}

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
		
		//Ȯ���� ����
		int pos = originalName.lastIndexOf(".");
		String ext = originalName.substring(pos + 1);
		//�������ϸ� ���� (UUID)
		String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
		//savefullpathName����
		String uploadedFullPathName = uploadPath + File.separator + savedName;
		uploadedFullPathName = uploadedFullPathName.substring(uploadPath.length()).replace(File.separatorChar, '/');
		//���� ����
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);

		return uploadedFullPathName;
	}
	
	public static String calcPath(String uploadPath) {
		
		File dirPath = new File(uploadPath); 

		// �⺻��� �н��� ������ ������ش�.
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

