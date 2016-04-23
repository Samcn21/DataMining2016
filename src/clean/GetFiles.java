package clean;

import java.io.File;
import java.io.FilenameFilter;

public class GetFiles {
	
	public static File[] getFileList(String dirPath){
		File dir = new File(dirPath);
		File[] fileList = dir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith(".json");
			}
		});
		return fileList;
	}

}