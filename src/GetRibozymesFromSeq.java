import java.util.Arrays;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class GetRibozymesFromSeq {

	public String[] ResultRibozyme;
		public GetRibozymesFromSeq (String input, String Transcriptome, int maxGC, int minGC) throws java.io.IOException, InterruptedException  {


	





		PrintWriter primers;
		primers = new PrintWriter(new FileWriter("extracted_Primers.txt"));
		primers.close();
		
		PrintWriter FASTA;
		FASTA = new PrintWriter(new FileWriter("pRZ-blast.fa"));
		FASTA.close();
		
		int posA = 0;
		int posB = 0;
		if (input.length() < 18){
				System.out.println(" input sequence to short");
		} else {
		
			for (int i = 12; i < (input.length() -6); i++){
		
				if (input.charAt(i) == 'A' || input.charAt(i) == 'a'){
				if (input.charAt(i+1) == 'T' || input.charAt(i+1) == 'U' || input.charAt(i+1) == 't') {	
					final String nt4 = input.substring(i+2,i+6);
					final String nt12 = input.substring(i-12,i);
					posA = posA + 1;
					Print_EbPrimers(nt4 , nt12, posA, i-12, i+6, maxGC, minGC);
				}
				
				
			}
				else if (input.charAt(i) == 'G' || input.charAt(i) == 'g'){
				if (input.charAt(i+1) == 'T' || input.charAt(i+1) == 'U' || input.charAt(i+1) == 't') {	
					final String nt4 = input.substring(i+2,i+6);
					final String nt12 = input.substring(i-12,i);
					posB = posB + 1;
					Print_PbPrimers(nt4 , nt12, posB, i-12, i+6, maxGC, minGC);
				}
				
				
			}
		
		}
		}

		final String Organism = Transcriptome;
		final String human_execution = "blastn -task blastn-short -db refseq_rna -query pRZ-blast.fa -remote -entrez_query \"Homo sapiens [organism]\" -outfmt 6 -out blast_result.txt";
		final String mouse_execution = "blastn -task blastn-short -db refseq_rna -query pRZ-blast.fa -remote -entrez_query \"Mus musculus [organism]\" -outfmt 6 -out blast_result.txt";
//		System.out.println(human_execution);

		if (Organism.equals("human")){
		System.out.println(human_execution);
		String[] command =
	    {
	        "cmd",
	    };
	    Process p;
		try {
			p = Runtime.getRuntime().exec(command);
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
	                stdin.println(human_execution);
	                stdin.close();
	                p.waitFor();
	    	} catch (Exception e) {
	 		e.printStackTrace();
		}
		}	
		
		if (Organism.equals("mouse")){
		System.out.println(mouse_execution);
   		String[] command =
	    {
	        "cmd",
	    };
	    Process p;
		try {
			p = Runtime.getRuntime().exec(command);
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
	                stdin.println(mouse_execution);
	                stdin.close();
	                p.waitFor();
	    	} catch (Exception e) {
	 		e.printStackTrace();
		}
		}	
	
	final String [][] Primers = ReadTXT2("extracted_Primers.txt");
//	System.out.println(Primers.length);
	final String [][] BlastResult = ReadTXT2("blast_result.txt");
//	System.out.println(BlastResult.length);	
	
	int primerListlength = Primers.length;
	int BlastListlength = BlastResult.length;
	String [] Result = new String [1];
	Result[0] = "Primer" + "\t" + "sequence" + "\t" + "start" + "\t" + "end" + "\t" + "target site" + "\t" + "GC_content[%]"+ "\t" + "Tmelt[°C]" + "\t" + "targets";
	double score = 0;
	String rowResult = "";
	String FWOI = "";
	String BlOI= "";
	int posPrimer = 0;
	int posBlast = 0;
	int lastPos = 0;
	int firstPos = 0;
	String Gene = "";

	String primerResultA = "";
	String primerResultB = "";
	
	
	while( posPrimer < primerListlength && posBlast < BlastListlength){
		FWOI = Primers[posPrimer][0];
		BlOI = BlastResult[posBlast][0];
//	System.out.println(posPrimer);
//	System.out.println(posBlast);
		primerResultA = convertArrayToString(Primers[posPrimer]);
		primerResultB = convertArrayToString(Primers[posPrimer+1]);
			if (FWOI.equals(BlOI)){
				lastPos = Integer.parseInt(BlastResult[posBlast][7]);
				firstPos = Integer.parseInt(BlastResult[posBlast][6]);
				Gene = BlastResult[posBlast][1];
				if (lastPos > 12){
					score = (lastPos -firstPos)/0.14;
					rowResult = rowResult + Gene + ", " +  Double.toString(score) + "; ";	
				}
				posBlast = posBlast +1;
			} else {
				if (rowResult.length() == 0){
					rowResult = "-";
				}
				primerResultA = primerResultA + "\t" + rowResult;
				Result = addElement(Result, primerResultA);
				primerResultB = primerResultB + "\t" + "-";
				Result = addElement(Result, primerResultB);
				posPrimer = posPrimer +2;
				rowResult = "";
			}
		}
	while( posPrimer < primerListlength && posBlast +1 == BlastListlength){
		
			primerResultA = convertArrayToString(Primers[posPrimer]);
			primerResultB = convertArrayToString(Primers[posPrimer+1]);

	
			primerResultA = primerResultA + "\t" + rowResult;
			Result = addElement(Result, primerResultA);
			primerResultB = primerResultB + "\t" + "-";
			Result = addElement(Result, primerResultB);
			posPrimer = posPrimer +2;
			rowResult = "";
	
	}
	
//	System.out.println(posPrimer);
//	System.out.println(posBlast);
	ResultRibozyme = Result;
	
	}


    
    public static String ReverseComplement(final String B) {
        String ReverseComplement = "";
        String C = "";
        for (int i = B.length() - 1; i >= 0; --i) {
            if (B.charAt(i) == 'A' || B.charAt(i) == 'a') {
                C = String.valueOf(C) + 'T';
            }
            else if (B.charAt(i) == 'T' || B.charAt(i) == 'U' || B.charAt(i) == 't') {
                C = String.valueOf(C) + 'A';
            }
            else if (B.charAt(i) == 'g' || B.charAt(i) == 'G') {
                C = String.valueOf(C) + 'C';
            }
            else if (B.charAt(i) == 'c' || B.charAt(i) == 'C') {
                C = String.valueOf(C) + 'G';
            }
            else {
                C = String.valueOf(C) + B.charAt(i);
            }
        }
        C = (ReverseComplement = C.replace(" ", ""));
        return ReverseComplement;
    }
    
	public static double GC_content (final String annealing_strand){
		double content = 0;
		double GC = 0;
		for (int i = 0; i < annealing_strand.length() ; i ++){
			if (annealing_strand.charAt(i) == 'g' || annealing_strand.charAt(i) == 'G') {
                GC = GC +1;
            }
            else if (annealing_strand.charAt(i) == 'c' || annealing_strand.charAt(i) == 'C') {
                GC = GC +1;
            }
		}
		content = GC / annealing_strand.length() *100;
		return content;
	}

	public static double Tmelt (final String annealing_strand){
		double Tmelt = 0;
		
		int nA = 0;
		int nT = 0;
		int nG = 0;
		int nC = 0;
		for (int i = 0; i < annealing_strand.length() ; i ++){
			
            if (annealing_strand.charAt(i) == 'A' || annealing_strand.charAt(i) == 'a') {
                nA = nA +1;
            }
            else if (annealing_strand.charAt(i) == 'T' || annealing_strand.charAt(i) == 'U' || annealing_strand.charAt(i) == 't') {
                nT = nT +1;
            }
            else if (annealing_strand.charAt(i) == 'g' || annealing_strand.charAt(i) == 'G') {
                nG = nG +1;
            }
            else if (annealing_strand.charAt(i) == 'c' || annealing_strand.charAt(i) == 'C') {
                nC = nC +1;
            }
           
		}
		int GC = nG +nC;
		double LENGTH = annealing_strand.length();
		double GCfrac = GC / LENGTH;
		double GCFrac2 = GCfrac * GCfrac;
		Tmelt = 64.6 +(58.4 * GCfrac) + (11.8* GCFrac2) - (820 / LENGTH);
		return Tmelt;
	}
	
	public static void Print_EbPrimers(final String Arm4, final String Arm12, final int nopr, final int start, final int stop, final int maXGC, final int miNGC) throws IOException{

		final String annealing_strand = Arm12 + Arm4;
		final String annealing= Arm12 + "at" + Arm4;
		final String FWappen = "taatacgactcactatagcgcgtggactaggccacgttaaatag";
		final String RVappen = "ctatttaacgtggcctag";
		final String loop = "cgctctaggcttaa";
		final String Arm4rv = ReverseComplement(Arm4);
		final String Arm12rv = ReverseComplement(Arm12);
		
		
		final double GCcont = GC_content(annealing_strand);
		
		if (GCcont >= miNGC && GCcont <= maXGC) {
			final String header = ">FW_eBac_" + nopr;
			final String sequence = annealing.substring(4,18);
		
		
		
			final String a = "FW_eBac_" + nopr + "\t" + FWappen + Arm4rv.substring(0,3) + "\t" + start + "\t" + stop + "\t" + annealing+ "\t" + GC_content(annealing_strand) + "\t" + Tmelt(annealing_strand);
			final String b = "RV_Bac_" + nopr + "\t" + Arm12 + loop + Arm4 +RVappen + "\t" + start + "\t" + stop + "\t" + annealing + "\t" + GC_content(annealing_strand) + "\t" + Tmelt(annealing_strand);

			writeToFile(a, "extracted_Primers.txt");
			writeToFile(b, "extracted_Primers.txt");
			writeToFile(header, "pRZ-blast.fa");
			writeToFile(sequence, "pRZ-blast.fa");
			

		}

	}
		public static void Print_PbPrimers(final String Arm4, final String Arm12, final int nopr, final int start, final int stop, final int maXGC, final int miNGC) throws IOException{

		final String annealing_strand = Arm12 + Arm4;
		final String annealing= Arm12 + "gt" + Arm4;
		final String FWappen = "taatacgactcactatagcgcgtggttagggccacgttaaatag";
		final String RVappen = "ctatttaacgtggcccta";
		final String loop = "cgcttagggcttaa";
		final String Arm4rv = ReverseComplement(Arm4);
		final String Arm12rv = ReverseComplement(Arm12);
			
		
		final double GCcont = GC_content(annealing_strand);
		
		if (GCcont >= miNGC && GCcont <= maXGC) {
			final String header = ">FW_pPoly_" + nopr;
			final String sequence = annealing.substring(4,18);
		
		
			
			final String a = "FW_pPoly_" + nopr + "\t" + FWappen + Arm4rv.substring(0,3) + "\t" + start + "\t" + stop + "\t" + annealing + "\t" + GC_content(annealing_strand) + "\t" + Tmelt(annealing_strand);
			final String b = "RV_pPoly_" + nopr + "\t" + Arm12 + loop + Arm4 +RVappen + "\t" + start + "\t" + stop  + "\t" + annealing + "\t" + GC_content(annealing_strand) + "\t" + Tmelt(annealing_strand);

			writeToFile(a, "extracted_Primers.txt");
			writeToFile(b, "extracted_Primers.txt");
			writeToFile(header, "pRZ-blast.fa");
			writeToFile(sequence, "pRZ-blast.fa");
		}	

	}
	private static void writeToFile(String text, String filePath) {
		File file = new File(filePath);
		FileWriter fr = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try {
			// to append to file, you need to initialize FileWriter using below constructor
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static String [][] ReadTXT2(String path) throws IOException{
		FileReader data = null;
		String thisline = "";
		String all = "";
		String myArray[];
		BufferedReader text;
		
		data = new FileReader(path);  // create the stream
		

		text = new BufferedReader(data);
		while( (thisline = text.readLine()) != null) {
			all += thisline + "\n";
			}
		text.close();
		
		myArray = all.split("\n");
		
		 String[][] matrix = new String[myArray.length][]; 
		    int r = 0;
		    for (String row : myArray) {
		        matrix[r++] = row.split("\t");
		    }	
		return matrix;

		
	}
	
	static void PrintTXT (String[] PrintArray, String filename) throws IOException{
		PrintWriter result;
		result = new PrintWriter(new FileWriter(filename));
		for (int i = 0; i < PrintArray.length; i ++ ){
			result.println(PrintArray[i]);
		}
		result.close();
	}

	public static String[] addElement(String[] a, String e) {
	    a  = Arrays.copyOf(a, a.length + 1);
	    a[a.length - 1] = e;
	    return a;
	}
	public static String convertArrayToString(String[] strArray) {
        String ArrayString = strArray[0];
        for (int i = 1; i < strArray.length; i++) {
            ArrayString = ArrayString + "\t" + (strArray[i]);
        }
        return ArrayString;
    }
}
