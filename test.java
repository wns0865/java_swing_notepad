package projectTTS;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

public class test extends JFrame  {
	
	VoiceSelectionParams voice =VoiceSelectionParams.newBuilder().setLanguageCode("en-US")
			.setSsmlGender(SsmlVoiceGender.NEUTRAL).build();
	AudioConfig audioConfig =AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();
	JRadioButton wordButton = new JRadioButton("단어 시험"); //단어 시험 버튼
	JRadioButton meanButton = new JRadioButton("뜻 시험"); // 뜻 시험 버튼
	ButtonGroup group = new ButtonGroup();
	JButton startButton1 = new JButton("시험"); // start 버튼
	JButton submitButton = new JButton("제출"); // submit 버튼
	JTextField testField = new JTextField(); // 입력 텍스트필드
	JLabel wordLabel = new JLabel(); // 문제 띄우는 라벨
	Object [][]wrong = new Object[100][3];
	JScrollPane scrolltable = new JScrollPane();
	JProgressBar cntBar = new JProgressBar(); // 진행 게이지바
	ImageIcon iconO = new ImageIcon("o.jpg");
	ImageIcon iconX = new ImageIcon("x.png");
	int testcnt;
	int[] currenttestnum = new int[100];
	int testcount;
	int result;
	List<Integer> array = new ArrayList<>();
	test() throws IOException, LineUnavailableException, UnsupportedAudioFileException{
		
		
		
		JRootPane  rootPane  =  this.getRootPane();
	    rootPane.setDefaultButton(submitButton);  
		Object[] column = {"(O,X)","단어","뜻"};
		// 시험 끝에 정답 확인하는 테이블 생성
		JTable wrongTable = new JTable(wrong,column) {
			@Override
			public Class getColumnClass(int column) {
	            switch (column) {
	                case 0:
	                    return Icon.class;
	                case 1:
	                    return String.class;
	                default:
	                    return String.class;
	            }
	        }
			
			@Override
			public boolean isCellEditable(int row, int col) {
		        return false;
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
		cntBar.setBounds(300,0,150,40);
		
		// 글자 폰트
		wordButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		meanButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		startButton1.setFont(new Font("Comic Sans", Font.BOLD, 30));
		testField.setFont(new Font("Comic Sans", Font.BOLD, 30));
		submitButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		wordLabel.setFont(new Font("Comic Sans", Font.BOLD, 30));
		cntBar.setFont(new Font("Comic Sans", Font.BOLD, 30));
		
		testField.setVisible(false);
		submitButton.setVisible(false);
		wordLabel.setVisible(false);
		cntBar.setVisible(false);
		
		cntBar.setForeground(Color.BLUE);
		cntBar.setBackground(Color.WHITE);
		
		// 단어시험 시작버튼
		wordButton.addActionListener(e->{
			startButton1.addActionListener(f -> {
				testcount=0;
				testcnt=0;
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
				cntBar.setValue(testcount);
				cntBar.setString(testcount+"/"+testcnt);
				cntBar.setStringPainted(true);
				cntBar.setVisible(true);
				Collections.shuffle(array);
				wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][2]);
				try {
					tts((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][1]);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		});
		
		// 뜻시험 시작버튼
		meanButton.addActionListener(e->{
			startButton1.addActionListener(f -> {	
				testcount=0;
				testcnt=0;
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
				cntBar.setValue(testcount);
				cntBar.setString(testcount+"/"+testcnt);
				cntBar.setStringPainted(true);
				cntBar.setVisible(true);
				Collections.shuffle(array);
				wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][1]);

				try {
					tts((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(0)][1]);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		});
		
		// 단어시험 제출버튼
		wordButton.addActionListener(e->{
			submitButton.addActionListener(f->{
				if(MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1].equals(testField.getText())) {
					wrongTable.setValueAt(iconO, testcount, 0);
					wrong[testcount][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[testcount][2]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount++)][2];
				}
				else {
					wrongTable.setValueAt(iconX, testcount, 0);
					wrong[testcount][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[testcount][2]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount++)][2];
				}
				cntBar.setValue((testcount*100)/testcnt);
				cntBar.setString(testcount+"/"+testcnt);
				testField.setText("");
				if(testcnt==testcount) {
					testField.setVisible(false);
					scrolltable.setVisible(true);
					submitButton.setVisible(false);
				}
				else {
					wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2]);
					try {
						tts((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2]);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		});
		
		// 뜻시험 제출버튼
		meanButton.addActionListener(e->{
			submitButton.addActionListener(f->{
				if(MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][2].equals(testField.getText())) {
					wrongTable.setValueAt(iconO, testcount, 0);
					wrong[testcount][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[testcount][2]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount++)][2];
				}
				else {
					wrongTable.setValueAt(iconX, testcount, 0);
					wrong[testcount][1]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1];
					wrong[testcount][2]=MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount++)][2];

				}
				cntBar.setValue((testcount*100)/testcnt);
				cntBar.setString(testcount+"/"+testcnt);
				testField.setText("");
				
				if(testcnt==testcount) {
					testField.setVisible(false);
					scrolltable.setVisible(true);
					submitButton.setVisible(false);
				}
				else {
					wordLabel.setText((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1]);
					try {
						tts((String) MyFrame.SubjectWord[MyFrame.currentSubject][array.get(testcount)][1]);
					} catch (Exception e1) {
						e1.printStackTrace();
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
		this.add(cntBar);
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
	void tts(String subjectWord)throws Exception {
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
			SynthesisInput input = SynthesisInput.newBuilder().setText(subjectWord).build();
			
			SynthesizeSpeechResponse response =textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
			ByteString audioContents = response.getAudioContent();
			try (OutputStream out = new FileOutputStream("output.wav")) {
				out.write(audioContents.toByteArray());
				System.out.println("WOW");
			}
		}
		File file = new File("output.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}
}
