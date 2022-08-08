import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI_Frame2 extends JFrame {

		JTextField seq1;
		JLabel backGround_transcriptome;
		JLabel GC_content_text;
		JLabel inputA;

		JButton seqA;

		JButton save;
		JCheckBox mouse;
		JCheckBox human;
		JCheckBox fly;
		JButton button;

		JFileChooser fc;
		public String[] output;
		public String a;
		public String b;
		public String c;
		public String refTranscriptome = "human";
		public String headerInput1;
		public String sequenceInput1 =null;
		public String sequenceInputA =null;
		JTextField Threshold_min;
		JLabel thr_min;
		JTextField Threshold_max;
		JLabel thr_max;
		JLabel thr_2ndArm;
		JTextField Threshold_2ndArm;
		public boolean MOUSE;
		public boolean HUMAN;
		public boolean FLY;

		public String titel;
		public String titelA;
		public String titelB;
		public String titelC;


	
		public GUI_Frame2(){
			setVisible(true);
			setSize(650, 650);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("pistol Ribozyme Design Tool");
			setResizable(true);
			fc = new JFileChooser();

			
			
			
			this.getContentPane().setLayout(null);

			this.initWindow();

			this.addWindowListener(new WindowListener() {

				public void windowClosed(WindowEvent arg0) {


				}

				public void windowActivated(WindowEvent e) {


				}

				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}

				public void windowDeactivated(WindowEvent e) {


				}

				public void windowDeiconified(WindowEvent e) {


				}

				public void windowIconified(WindowEvent e) {


				}

				public void windowOpened(WindowEvent e) {


				}



			});

		}
		protected void initWindow() 
		{
			// Initiate:
			inputA = new JLabel ("paste sequence or choose FASTA");
			backGround_transcriptome = new JLabel(" choose backround transcriptome");
			GC_content_text = new JLabel ("GC content");
			
			seq1 = new JTextField();

			seqA = new JButton("open FASTA");
			

			save = new JButton("save");
			human = new JCheckBox("human");
			mouse = new JCheckBox("mouse");
			fly = new JCheckBox("drosophila");
			button = new JButton("Get Ribozymes");
			thr_min = new JLabel("min GC content");
			Threshold_min = new JTextField();
			thr_2ndArm = new JLabel("length of 3' annealing strand");
			Threshold_2ndArm = new JTextField();
			thr_max = new JLabel("max GC content");
			Threshold_max = new JTextField();
			
			mouse.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED){	
					MOUSE = true;
				} else {
					MOUSE = false;
				}
				}
				
			});
			
			human.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED){	
					HUMAN = true;
				} else {
					HUMAN = false;
				}
				}
				
			});
			fly.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED){	
					FLY = true;
				} else {
					FLY = false;
				}
				}
				
			});			

			
			seqA.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == seqA) {
				        int returnVal = fc.showOpenDialog(GUI_Frame2.this);

				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            File file = fc.getSelectedFile();
				            a = file.getAbsolutePath();
				            ReadFasta OpenFile1 = null;
							try {
								OpenFile1 = new ReadFasta(a);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				            String seqInputOpen1 = OpenFile1.sequence;
				            if (seqInputOpen1 != null){
				            	sequenceInputA = seqInputOpen1;
				            	seq1.setText(sequenceInputA);
				            	titelB = OpenFile1.header;
				            } else {
				            	titelB = "sequence A";
				            }
				        } 
				
				        }
				}
			});
			

			
			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == save) {
				        int returnVal = fc.showSaveDialog(GUI_Frame2.this);

				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            File file3 = fc.getSelectedFile();
				            c = file3.getAbsolutePath();
				            try {
								PrintTXT SAVE = new PrintTXT(output, c);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				        }
				
				        }
				}
			});

			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					try {
						buttonBerechneClicked();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}		

			});
			

			// add Elements

			inputA.setBounds(5,10, 590, 25);
			seq1.setBounds(5, 40, 590, 300);
			seqA.setBounds(5, 350, 590, 25);
			backGround_transcriptome.setBounds(5,375, 590, 25);
			mouse.setBounds(5, 400, 100, 25);
			human.setBounds(155, 400, 100,25);
			fly.setBounds(305, 400, 100,25);
			GC_content_text.setBounds(5,425, 590, 25);
			thr_min.setBounds(5,430, 200, 25);
			Threshold_min.setBounds(315, 430, 200, 25);
			thr_max.setBounds(5,455, 200, 25);
			Threshold_max.setBounds(315, 455, 200, 25);
			thr_2ndArm.setBounds(5,480, 200, 25);
			Threshold_2ndArm.setBounds(315, 480, 200, 25);
			button.setBounds(5, 525, 280, 25);
			save.setBounds(315, 525, 280, 25);
		
			
			this.getContentPane().add(seq1);
			this.getContentPane().add(seqA);

			this.getContentPane().add(mouse);
			this.getContentPane().add(human);
			this.getContentPane().add(fly);
			this.getContentPane().add(Threshold_min);
			this.getContentPane().add(Threshold_max);
			this.getContentPane().add(Threshold_2ndArm);
			this.getContentPane().add(save);
			this.getContentPane().add(button);
			
		
		
			add(inputA);
			add(backGround_transcriptome);
			add(thr_max);
			add(thr_min);
			add(thr_2ndArm);
			this.pack();
		}
	

		protected void buttonBerechneClicked() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			// decide between FileChooser and pasted sequence and convert all letters to capital letter
		String thrMin = Threshold_min.getText();
		String thrMax = Threshold_max.getText();
		String thr2nd = Threshold_2ndArm.getText();
		int thresholdFieldMin = 45;
		if (!thr_max.equals("")){
			thresholdFieldMin = Integer.parseInt(thrMin);
		} else {
			thresholdFieldMin = 45;
		}
		int thresholdFieldMax = 60;
		if (!thr_min.equals("")){
			thresholdFieldMax = Integer.parseInt(thrMax);
		} else {
			thresholdFieldMax = 60;
		}
		int thresholdField2nd = 12;
		if (!thr_2ndArm.equals("")){
			thresholdField2nd = Integer.parseInt(thr2nd);
		} else {
			thresholdField2nd = 12;
		}
		if (MOUSE == true){
			refTranscriptome = "mouse";
		}
		if (HUMAN == true){
			refTranscriptome = "human";
		}
		if (FLY == true){
			refTranscriptome = "drosophila";
		}
		sequenceInput1 = seq1.getText();
		
		GetRibozymesFromSeq2b con = new GetRibozymesFromSeq2b(sequenceInput1, refTranscriptome, thresholdField2nd, thresholdFieldMax, thresholdFieldMin);
		output = con.ResultRibozyme;
		OutputFrame ApplicationOut = new OutputFrame(output, "pistolRibozymes");
		}
		

	
	}


