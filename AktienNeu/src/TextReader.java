	import java.io.File;
	import java.io.FileReader;
	import java.io.IOException;
	
public class TextReader {


		public static String reader(String FileName) {
			
			File file = new File(FileName);
			
			  if (!file.canRead() || !file.isFile()) {
		            System.exit(0);
			  }
			FileReader fr = null;
			int c;
			StringBuffer buff = new StringBuffer();
			try {
				fr=new FileReader(file);
				while((c = fr.read()) != -1){
					buff.append((char) c);
				}
				fr.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return buff.toString();
		}
		
	}
