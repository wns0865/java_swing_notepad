package projectTTS;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class test extends JFrame {
	JRadioButton wordButton = new JRadioButton("단어 시험");
	JRadioButton meanButton = new JRadioButton("뜻 시험");
	ButtonGroup group = new ButtonGroup();
	JButton startButton1 = new JButton("start");
	JButton submitButton = new JButton("submit");
	JTextField testField = new JTextField();
	JLabel wordLabel = new JLabel();
	Object [][]wrong = new Object[100][2];
	JScrollPane scrolltable = new JScrollPane();
	int score;
	int wrongcnt;
	int[] currenttestnum = new int[100];
	int testcount;
	int testcnt;
	int result;
	List<Integer> array = new ArrayList<>();
	test() throws IOException{
		JRootPane  rootPane  =  this.getRootPane();
	    rootPane.setDefaultButton(submitButton);  
		Object[] column = {"단어","뜻"};
		JTable wrongTable = new JTable(wrong,column) {
			@Override
			public Class getColumnClass(int column) {
	            switch (column) {
	                case 0:
	                    return String.class;
	                case 1:
	                    return String.class;
	                default:
	                    return Boolean.class;
	            }
	        }
		};
		wrongTable.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		wrongTable.setFont(new Font("돋움체", Font.BOLD, 20));
		wrongTable.setBackground(new Color(MyFrame.wordColor[0]));
		wrongTable.setRowHeight(20);
		scrolltable = new JScrollPane(wrongTable);
	    scrolltable.setVisible(false);
	    scrolltable.setBounds(50,50,400,400);
        this.add(scrolltable);
        
		group.add(wordButton);
		group.add(meanButton);
		wordButton.setBounds(150, 90, 200, 100);
		meanButton.setBounds(150, 160, 200, 100);
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
				score=0;
				testcount=0;
				testcnt=0;
				wrongcnt=0;
				wordButton.setVisible(false);
				meanButton.setVisible(false);
				startButton1.setVisible(false);
				testField.setVisible(true);
				submitButton.setVisible(true);
				wordLabel.setVisible(true);
				group.clearSelection();
				for(int i=0;i<MyFrame.SubjectWordCnt[MyFrame.currentSubject];i++) {
					if(MyFrame.SubjectWord[MyFrame.currentSubject][i][3].equals(true)) {
						array.add(i);
						testcnt++;
					}
				}
				Collections.shuffle(array);
				wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][2]);
			});
		});
		
		meanButton.addActionListener(e->{
			startButton1.addActionListener(f -> {	
				score=0;
				testcount=0;
				testcnt=0;
				wrongcnt=0;
				wordButton.setVisible(false);
				meanButton.setVisible(false);
				startButton1.setVisible(false);
				testField.setVisible(true);
				submitButton.setVisible(true);
				wordLabel.setVisible(true);
				group.clearSelection();
				for(int i=0;i<MyFrame.SubjectWordCnt[MyFrame.currentSubject];i++) {
					if(MyFrame.SubjectWord[MyFrame.currentSubject][i][3].equals(true)) {
						array.add(i);
						testcnt++;
					}
				}
				Collections.shuffle(array);
				wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][1]);
			});
		});
		
		wordButton.addActionListener(e->{
			submitButton.addActionListener(f->{
				if(MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1].equals(testField.getText()))
					score++;
				else {
					wrong[wrongcnt][0]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[wrongcnt++][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2];
					
				}
				testcount++;
				testField.setText("");
				if(testcnt==testcount) {
					if(score==testcnt) {
						JOptionPane.showMessageDialog(null, "다 맞췄습니다!!");
						dispose();
					}
					else {
						result = JOptionPane.showConfirmDialog(null,wrongcnt+"개 틀렸습니다\n틀린 문제를 확인하겠습니까?",null,  JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							scrolltable.setVisible(true);
							submitButton.setVisible(false);
						}
						else
							dispose();
					}	
				}
				else
					wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2]);
			});
		});
		
		meanButton.addActionListener(e->{
			submitButton.addActionListener(f->{
				if(MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2].equals(testField.getText()))
					score++;
				else {
					wrong[wrongcnt][0]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[wrongcnt++][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2];
					
				}
				testcount++;
				testField.setText("");
				if(testcnt==testcount) {
					if(score==testcnt) {
						JOptionPane.showMessageDialog(null, "다 맞췄습니다!!");
						dispose();
					}
					else {
						result = JOptionPane.showConfirmDialog(null,wrongcnt+"개 틀렸습니다\n틀린 문제를 확인하겠습니까?",null,  JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							scrolltable.setVisible(true);
							submitButton.setVisible(false);
						}
						else
							dispose();
					}	
				}
				else
					wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1]);
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
