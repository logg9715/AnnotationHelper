package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui extends JFrame {
	// 폰트
	private Font font_textArea = new Font("D2Coding", Font.PLAIN, 14);
	private String star[] = { "/*******************************************************", "\n* ", " *", "*******************************************************/"};

	// getter
	public Font getFont_textArea() {
		return font_textArea;
	}

	// 텍스트 에어리어
	JTextArea txta_input;
	JTextArea txta_output;

	// 생성자
	// ()
	Gui() {
		// ****************************************************** START 기본설정 ******************************************************
		setTitle("주석 도우미");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true); // 나중에 false로 바꾸자
		// END 기본설정

		this.createElements();

		// ****************************************************** START 화면설정 ******************************************************
		setSize(800, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		// END 화면설정
	}

	// 메소드 :
	// ()
	// 텍스트 에어리어에서 텍스트를 받아서 변환 함수로 연결
	void BtEvent_convert() {
		String convertString = this.convert_boxStyle(txta_input.getText());
		this.txta_output.setText(convertString);

	}

	// 메소드 :
	// (수정할 문자열)
	// input -> output으로 변환
	String convert_boxStyle(String str) {
		String strArr[];
		str = "\n\n\n" + str;
		str += "\n\n\n ";
		
		strArr = str.split("\n");
		
		for (String string : strArr) {
			System.out.println("[" + string + "]");
		}
		
		this.convert_Sortbylen(strArr, star[_def.LONG].length(), star[_def.NEWLINE], star[_def.END]);
		
		// 리스트를 하나의 문자열로 병합
		String result = this.star[_def.LONG];
		for (String tmpStr : strArr) {
			result += this.star[_def.NEWLINE];
			result += tmpStr;
			result += this.star[_def.END];
		}
		result += "\n";
		result += this.star[_def.ENDLONG];
		


		return result;
	}
	
	// 메소드
	// (문자열 배열, 최종길이, 개행문자열, 닫는문자열)
	// 각 문자열들을 
	String[] convert_Sortbylen(String[] arr, int len, String startStr, String endStr) {
		len -= startStr.length() + endStr.length();
		len++; // 개행문자열의 개행문자는 제거.
		
		 for (int i = 0; i < arr.length; i++) {
			 int lineLength  = 0;
			 for (int k = 0; k < arr[i].length(); k++) {
				 if (arr[i].charAt(k) > 500) lineLength += 2;
				 else lineLength++;
			 }
			 lineLength = len - lineLength;
			 
			 for(int k = 0; k < lineLength; k++) {
				 arr[i] += " ";
			 }
		 }
		
		return arr;
	}

	// 메소드 :
	// ()
	// 그래픽 요소 구현
	void createElements() {
		// ****************************************************** 컨테이너 설정 ******************************************************
		Container con = this.getContentPane();
		// con.setLayout(new FlowLayout());
		// END 컨테이너 설정

		// ****************************************************** 패널 생성 ******************************************************
		// 중앙 요소들 패널
		JPanel pan_mainGui = new JPanel();
		GridLayout gridLayout01 = new GridLayout();
		gridLayout01.setColumns(1);
		gridLayout01.setRows(2);
		pan_mainGui.setLayout(gridLayout01);

		// 아래쪽 버튼 패널
		JPanel pan_underButtons = new JPanel();

		// 컨테이너에 추가
		con.add(pan_mainGui, BorderLayout.CENTER);
		con.add(pan_underButtons, BorderLayout.SOUTH);
		// END 패널 생성

		// ****************************************************** 메뉴바 생성 ***************************************************************************
		// 메뉴 생성
		JMenu menu_01 = new JMenu("1번메뉴");
		menu_01.add(new JMenuItem("아이템1"));
		menu_01.add(new JMenuItem("아이템2"));
		JMenu menu_02 = new JMenu("설정");
		JMenuItem menu_02_1 = new JMenuItem("입력 설정");
		menu_02_1.addActionListener(e -> {
			System.out.println("ffdsafd");
		});
		menu_02.add(menu_02_1);

		// 메뉴바 구현, 메뉴바에 메뉴 추가
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu_01);
		menuBar.add(menu_02);

		// 메뉴바 반영
		setJMenuBar(menuBar);
		// END 메뉴바 생성

		// ****************************************************** 텍스트필드 생성 ******************************************************

		// 입력 텍스트에어리어
		txta_input = new JTextArea("입력");
		txta_input.setFont(font_textArea);
		pan_mainGui.add(txta_input);

		// 출력 텍스트에어리어
		txta_output = new JTextArea("출력");
		txta_output.setFont(font_textArea);
		txta_output.setBackground(Color.black);
		txta_output.setForeground(new Color(51, 204, 51));

		// 하단 버튼 구현
		pan_mainGui.add(txta_output);

		JButton butt_convert = new JButton("변환");
		butt_convert.addActionListener(e -> {
			this.BtEvent_convert();
		});

		pan_underButtons.add(new JButton("입력 전체선택"));
		pan_underButtons.add(butt_convert);
		pan_underButtons.add(new JButton("초기화"));
		pan_underButtons.add(new JButton("출력 전체선택"));
		// END 텍스트필드 생성
	}
}
