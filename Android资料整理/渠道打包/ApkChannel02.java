import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;

import jxl.Sheet;
import jxl.Workbook;

public class ApkChannel02 {

	private static final String versionName = "3.4.0";

	public static void main(String[] args) {
		String apkPath = "F:/渠道打包/ckjr_jiami.apk";
		List<String> list = new ArrayList<String>();
		try {
			Workbook book = Workbook.getWorkbook(new FileInputStream(new File("F:/app包对应名称.xls")));
			Sheet sheet = book.getSheet(0);
			for(int i=1;i<sheet.getRows();i++) {
				String contents = sheet.getCell(2, i).getContents();
				if(contents!=null && !"".equals(contents.trim()))
					list.add(contents);
			}
			book.close();
			
			File dfile = new File("F:/渠道打包/ckjr");
			if(!dfile.exists())
				dfile.mkdirs();
			
			for(String channel:list) {
				File file = new File(apkPath);
				FileOutputStream fos = new FileOutputStream(dfile.getPath()+"/ckjr_" + channel + "_"+versionName+".apk");
				ZipFile zipFile = new ZipFile(file);
				ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos);
				Enumeration<ZipArchiveEntry> entries = (Enumeration<ZipArchiveEntry>) zipFile.getEntries();
				while(entries.hasMoreElements()) {
					ZipArchiveEntry entry = entries.nextElement();
					zos.putArchiveEntry(entry);
//					zos.putArchiveEntry(new ZipArchiveEntry(entry.getName()));
					int length;
					byte[] buffer = new byte[1024];
					InputStream is = zipFile.getInputStream(entry);
					while((length=is.read(buffer))!=-1) {
						zos.write(buffer, 0, length);
					}
					is.close();
					buffer = null;
				}
				zos.putArchiveEntry(new ZipArchiveEntry("META-INF/channel_" + channel));
				zos.closeArchiveEntry();
				zos.close();
				System.out.println("剩余" + (list.size()-list.indexOf(channel)-1));
//				break;
			}
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
