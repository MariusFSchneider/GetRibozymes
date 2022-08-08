import java.awt.Font;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputFrame extends JFrame  {
	JScrollPane scroll;
	JTextArea 	result;
	public OutputFrame(String[] input, String titel){
		setVisible(true);
		setSize(600, 800);
		
		setTitle(titel);
		setResizable(true);
	
		

		result = new JTextArea();
		scroll = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		scroll.setBounds(5,5,590,790);
		result.setLineWrap(true);
		result .setWrapStyleWord(true);
		add(scroll);
		Font font = new Font("Courier", Font.PLAIN, 12);
		result.setFont(font);
		for (int i = 0; i < input.length; i++){
			result.append(input[i] + "\n");
		}
	}
}
