import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFasta {
	public String header;
	public String comment;
	public String sequence;
	
	public ReadFasta(String path) throws IOException{
		header = "";
		comment = "";
		sequence = "";
		FileReader data = null;
		data = new FileReader(path); 
		BufferedReader br = new BufferedReader(data);
		String thisline = "";
		String SequenceWithSpace = "";
		while( (thisline = br.readLine()) != null) {
			if (thisline.contains(">")){
				header += thisline;
			} else if(thisline.contains(";")){
				comment += thisline;
			} else {
				SequenceWithSpace += thisline;
			}
				
			}
		sequence = SequenceWithSpace.replace("\\s", "");
		br.close();
		
	}

}
