import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PrintTXT {
	PrintTXT(String[] PrintArray, String path) throws IOException{
		PrintWriter result;
		Date DATE = new Date();
		 SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	     String date = (DATE_FORMAT.format(DATE)).replace("-", "");
		String filename = path +  date +  ".txt";
		result = new PrintWriter(new FileWriter(filename));
		for (int i = 0; i < PrintArray.length; i ++ ){
			result.println(PrintArray[i]);
		}
		result.close();
	}
}
