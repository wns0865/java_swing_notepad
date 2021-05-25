package project1;

import java.awt.Font;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class test extends JFrame{
	JRadioButton wordButton = new JRadioButton("단어");
	JRadioButton meanButton = new JRadioButton("뜻");
	ButtonGroup group = new ButtonGroup();
	JButton startButton1 = new JButton("start");
	JButton submitButton = new JButton("submit");
	JTextField testField = new JTextField();
	JLabel wordLabel = new JLabel("단어 설명 ~~");
	test() throws IOException{
		System.out.println("h");
		group.add(wordButton);
		group.add(meanButton);
		wordButton.setBounds(150, 150, 100, 100);
		meanButton.setBounds(250, 150, 100, 100);
		startButton1.setBounds(150, 300, 200, 50);
		testField.setBounds(150, 200, 200, 50);
		submitButton.setBounds(150, 300, 200, 50);
		wordLabel.setBounds(150, 100, 200, 50);
		
		wordButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		meanButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		startButton1.setFont(new Font("Comic Sans", Font.BOLD, 30));
		testField.setFont(new Font("Comic Sans", Font.BOLD, 30));
		submitButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		wordLabel.setFont(new Font("Comic Sans", Font.BOLD, 30));
		
		testField.setVisible(false);
		submitButton.setVisible(false);
		wordLabel.setVisible(false);
		
		wordButton.addActionListener(e->{
			startButton1.addActionListener(f -> {
				wordButton.setVisible(false);
				meanButton.setVisible(false);
				startButton1.setVisible(false);
				testField.setVisible(true);
				submitButton.setVisible(true);
				wordLabel.setVisible(true);
				group.clearSelection();
				for(int i=0;i<MyFrame.SubjectWordCnt[MyFrame.currentSubject];i++) {
					if(MyFrame.SubjectWord[MyFrame.currentSubject][i][3].equals(true)) {
						wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][i][1]);
					}
				}
			});
		});
		
		meanButton.addActionListener(e->{
			startButton1.addActionListener(f -> {
				wordButton.setVisible(false);
				meanButton.setVisible(false);
				startButton1.setVisible(false);
				testField.setVisible(true);
				submitButton.setVisible(true);
				wordLabel.setVisible(true);
				group.clearSelection();
				for(int i=0;i<MyFrame.SubjectWordCnt[MyFrame.currentSubject];i++) {
					if(MyFrame.SubjectWord[MyFrame.currentSubject][i][3].equals(true)) {
						wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][i][0]);
					}
				}
			});
		});

		this.setResizable(false);
		this.setSize(500,500);
		this.setLayout(null);
		this.setVisible(true);
		this.add(wordButton);
		this.add(meanButton);
		this.add(startButton1);
		this.add(testField);
		this.add(submitButton);
		this.add(wordLabel);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
	        	wordButton.setVisible(true);
				meanButton.setVisible(true);
				startButton1.setVisible(true);
				testField.setVisible(false);
				submitButton.setVisible(false);
				wordLabel.setVisible(false);
				wordLabel.setText("");
			}
	    });
	}
}
