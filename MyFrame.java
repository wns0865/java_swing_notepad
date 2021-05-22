package project1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame{
	BufferedReader br = new BufferedReader(new FileReader("word.txt"));
	String[][][] SubjectWord = new String[6][][];
	JButton[] Subjects = new JButton[6];
	int cntSubject=1,count;
	int currentSubject;
	String tempSubjectName;
	String line;
	String[] columnNames = {"단어","뜻"};
	JTable[] wordTable = new JTable[6];
	JScrollPane[] scroll_table = new JScrollPane[6];
	int[] SubjectWordCnt= {0,0,0,0,0,0};
	MyFrame() throws IOException{
		ImageIcon icon2 = new ImageIcon("noteIcon.png");
		JButton startButton = new JButton("start");  	//처음 start 및 뒤로 가는 버튼
		JButton addSubjectButton = new JButton("+"); 	//주제 추가 버튼
		JButton testButton = new JButton("test");    	//해당 과목에 있는 것들 테스트 버튼
		JButton deleteCurSubject = new JButton("del");	//해당 주제 삭제 버튼
		JButton addWord = new JButton("add");         	//해당 주제 테이블에 새로운 단어 추가 버튼
		JTextField subjectText = new JTextField();      //주제 추가할 때 쓰는 텍스트필드 
		//SubjectWord라는 각 주제 별 단어 저장용 배열 생성
		SubjectWord[0]=new String[500][2];
		for(int i=1;i<6;i++) 
			SubjectWord[i]=new String[100][2];
		//Subjects라는 각 주제 별 메인 화면에서 클릭할 버튼 생성
		for(int i=0;i<6;i++) {
			Subjects[i] = new JButton();
			Subjects[i].setVisible(false);
			Subjects[i].setFont(new Font("Comic Sans", Font.BOLD, 30));
			Subjects[i].setBounds(50+350*((i)%2),100+(i)/2*150,200,100);
			this.add(Subjects[i]);
		}
		//Subjects 눌렀을 때 보여지는 스크롤 가능한 테이블 생성
		for(int i=0;i<6;i++) {
			wordTable[i] = new JTable(SubjectWord[i],columnNames);
			wordTable[i].setEnabled(false);
			scroll_table[i] = new JScrollPane(wordTable[i]);
		    scroll_table[i].setVisible(false);
		    scroll_table[i].setBounds(100,100,400,400);
	        this.add(scroll_table[i]);
		}
		
		//버퍼 리더로 기존에 있는 값 불러와서 배열에 넣어주기
		int wordcnt=0;
		int subjectcnt=1;
		line = br.readLine();
		count = Integer.parseInt(line);
		cntSubject=count;
		Subjects[0].setText("ALL");
		for(int i=1;i<count;i++) 
			Subjects[i].setText(br.readLine());
		while(true) {
		 	line = br.readLine();
            if (line==null) break;
            count = Integer.parseInt(line);
		 	for(int i=0;i<count;i++) {
	            line = br.readLine();
	            String[] wordline = line.split(" ");
		 		SubjectWord[0][wordcnt][0]=wordline[0];
	            SubjectWord[0][wordcnt++][1]=wordline[1];
	            SubjectWord[subjectcnt][i][0]=wordline[0];
	            SubjectWord[subjectcnt][i][1]=wordline[1];
		 	}
			SubjectWordCnt[subjectcnt]+=count;
			SubjectWordCnt[0]+=count;
		 	subjectcnt++;
        }
		
		
		subjectText.setVisible(false);
		subjectText.setFont(new Font("Comic Sans", Font.BOLD, 20));
		
		testButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		testButton.setVisible(false);
		testButton.setBounds(50,20,100,50);
		
		deleteCurSubject.setFont(new Font("Comic Sans", Font.BOLD, 30));
		deleteCurSubject.setVisible(false);
		deleteCurSubject.setBounds(170,20,100,50);

		addWord.setFont(new Font("Comic Sans", Font.BOLD, 30));
		addWord.setVisible(false);
		addWord.setBounds(290,20,100,50);

		addSubjectButton.setBounds(50+350*((cntSubject)%2),100+(cntSubject)/2*150,200,100);
		addSubjectButton.setFont(new Font("Comic Sans", Font.BOLD, 50));
		addSubjectButton.setVisible(false);
		
		startButton.setBounds(225,400,180,100);
		startButton.setFocusable(false);
		startButton.setVerticalTextPosition(JButton.BOTTOM);
		startButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		
		//새로운 주제 추가하기 버튼
		addSubjectButton.addActionListener(e -> {
			addSubjectButton.setVisible(false);
			for(int i=0;i<cntSubject;i++) 
				Subjects[i].setEnabled(false);
			if(cntSubject<6) {
				Subjects[cntSubject].setVisible(false);
				subjectText.setBounds(50+350*((cntSubject)%2),100+(cntSubject)/2*150,200,100);
				subjectText.setVisible(true);
				cntSubject++;
				if(cntSubject==6) //5개가 다 차면 더 이상 추가 못하게 막음
					addSubjectButton.setVisible(false);
			}
		});
		//각 주제인 Subjects[i]가 눌렸을 때 
		for(int i=0;i<6;i++) {
			int k=i;
			Subjects[i].addActionListener(e -> {
				currentSubject=k;
				startButton.setVisible(true);
				testButton.setVisible(true);
				deleteCurSubject.setVisible(true);
				addWord.setVisible(true);
				scroll_table[currentSubject].setVisible(true);
				for(int j=0;j<6;j++) 
					Subjects[j].setVisible(false);
				addSubjectButton.setVisible(false);
			});
		}
		//현재 주제 전체 삭제하기
		deleteCurSubject.addActionListener(e -> {
			int a=JOptionPane.showConfirmDialog(this,"Delete this Subject?"); 
			if(a==JOptionPane.YES_OPTION){  
				for(int i=currentSubject;i<5;i++) {
					Subjects[i].setText(Subjects[i+1].getText());
					for(int j=0;j<Math.max(SubjectWordCnt[i],SubjectWordCnt[i+1]);j++) {
						SubjectWord[i][j]=SubjectWord[i+1][j];
					}
				}
				int cnt=0;
				for(int i=1;i<6;i++) {
					for(int j=0;j<SubjectWordCnt[i];j++) {
						SubjectWord[0][cnt++]=SubjectWord[i][j];
					}
				}
				startButton.setBounds(410,20,180,50);
				startButton.setText("<-");
				startButton.setVisible(false);
				testButton.setVisible(false);
				deleteCurSubject.setVisible(false);
				addWord.setVisible(false);
				cntSubject-=1;
				for(int i=0;i<cntSubject;i++) {
					scroll_table[i].setVisible(false);
					Subjects[i].setVisible(true);
				}

				scroll_table[cntSubject].setVisible(false);
				addSubjectButton.setBounds(50+350*((cntSubject)%2),100+(cntSubject)/2*150,200,100);
				if(cntSubject<6)
					addSubjectButton.setVisible(true);
			}
		});
		//메인화면으로 가기
		startButton.addActionListener(e -> {
			startButton.setBounds(410,20,180,50);
			startButton.setText("<-");
			startButton.setVisible(false);
			testButton.setVisible(false);
			deleteCurSubject.setVisible(false);
			addWord.setVisible(false);
			for(int i=0;i<cntSubject;i++) {
				scroll_table[i].setVisible(false);
				Subjects[i].setVisible(true);
			}
			if(cntSubject<6)
				addSubjectButton.setVisible(true);
		});
		
		// 단어 추가 버튼 만들기
		addWord.addActionListener(e -> {
		  JTextField WordField = new JTextField(10);
	      JTextField MeanField = new JTextField(10);

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("Word:"));
	      myPanel.add(WordField);
	      myPanel.add(Box.createHorizontalStrut(20));
	      myPanel.add(new JLabel("Mean:"));
	      myPanel.add(MeanField);
	      
	      int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Word and Mean", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	        String Word = WordField.getText();
			String Mean = MeanField.getText();
			SubjectWord[currentSubject][SubjectWordCnt[currentSubject]][0]=Word;
			SubjectWord[currentSubject][SubjectWordCnt[currentSubject]++][1]=Mean;
			SubjectWord[0][SubjectWordCnt[0]][0]=Word;
			SubjectWord[0][SubjectWordCnt[0]++][1]=Mean;
			scroll_table[currentSubject].setVisible(false);
			scroll_table[currentSubject].setVisible(true);
	      }
		});
		
        //TextField에 추가 할 주제의 이름을 입력받았을 때, 그 이름을 가진 주제 버튼 만들기
		subjectText.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(subjectText.getText().equals("")) {
		    		cntSubject--;
		    	}
		    	else {
		    		tempSubjectName=subjectText.getText();
			    	Subjects[cntSubject-1].setVisible(true);
			    	Subjects[cntSubject-1].setText(tempSubjectName);
			    	subjectText.setText("");
					addSubjectButton.setBounds(50+350*((cntSubject)%2),100+(cntSubject)/2*150,200,100);
		    	}
		    	if(cntSubject!=6)
		    		addSubjectButton.setVisible(true);
				subjectText.setVisible(false);
				for(int i=0;i<cntSubject;i++) {
					Subjects[i].setEnabled(true);
				}
		    }
		});
		
		this.setIconImage(icon2.getImage());
        this.setTitle("Fancy Note Pad");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650,650);
        this.setLayout(null);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0xf8b195));
        this.add(startButton);
        this.add(addSubjectButton);
        this.add(deleteCurSubject);
        this.add(subjectText);
        this.add(testButton);
        this.add(addWord);
        
        br.close();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
	        	try {
	        		saveFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
        });
	}
	void saveFile() throws IOException {
    	FileWriter fw = new FileWriter("word.txt");
		fw.write(cntSubject+"\n");
		for(int i=1;i<cntSubject;i++) {
		 	fw.write(Subjects[i].getText()+"\n");
		}
		for(int i=1;i<cntSubject;i++) {
			fw.write(SubjectWordCnt[i]+"\n");
			for(int j=0;j<SubjectWordCnt[i];j++) {
				fw.write(SubjectWord[i][j][0]+" "+SubjectWord[i][j][1]+"\n");
			}
		}
		fw.close();
    }
}
