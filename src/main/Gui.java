package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

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
	private String star[] = { "/******************************************************************************", "\n* ",
			" *", "******************************************************************************/" };

	// 텍스트 에어리어
	private JTextArea txta_input;
	private JTextArea txta_output;

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
		setSize(600, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		// END 화면설정
	}
	// ****************************************************** START getter, setter ******************************************************

	public void resetTxtFont(Font font) {
		this.font_textArea = font;
		this.txta_input.setFont(font_textArea);
		this.txta_output.setFont(font_textArea);
	}

	// ****************************************************** END getter, setter ******************************************************

	// 메소드 : 
	// ()
	// 입력 텍스트 에어리어를 초기화 
	public void initInput() {
		this.txta_input.setText("");
	}

	// 메소드 : 
	// ()
	// 텍스트를 클립보드에 복사
	public void clipboardCopy() {
		String copyStr = this.txta_output.getText();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(copyStr);
		clipboard.setContents(strSel, null);
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
			int lineLength = 0;
			for (int k = 0; k < arr[i].length(); k++) {
				if (arr[i].charAt(k) > 500)
					lineLength += 2;
				else
					lineLength++;
			}
			lineLength = len - lineLength;

			for (int k = 0; k < lineLength; k++) {
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
		// start 메뉴 - 1번메뉴 : 
		JMenu menu_01 = new JMenu("1번메뉴");
		menu_01.add(new JMenuItem("아이템1"));
		menu_01.add(new JMenuItem("아이템2"));
		// end 메뉴 - 1번메뉴

		// start 메뉴 - 설정 :
		JMenu menu_02 = new JMenu("설정");

		JMenuItem menu_02_1 = new JMenuItem("글꼴 설정");
		menu_02_1.addActionListener(e -> {

			new Gui_mini01("글꼴 설정", false, font_textArea);
		});
		menu_02.add(menu_02_1);

		JMenuItem menu_02_2 = new JMenuItem("프리셋 설정");
		menu_02_2.addActionListener(e -> {
			new Gui_mini02("프리셋 설정", true);
		});
		menu_02.add(menu_02_2);
		// end 메뉴 - 설정

		// 메뉴바 구현, 메뉴바에 메뉴 추가
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu_01);
		menuBar.add(menu_02);

		// 메뉴바 반영
		setJMenuBar(menuBar);
		// END 메뉴바 생성

		// ****************************************************** 텍스트영역 생성 ******************************************************

		// 입력 텍스트에어리어
		txta_input = new JTextArea("입력");
		txta_input.setFont(font_textArea);
		pan_mainGui.add(txta_input);

		// 출력 텍스트에어리어
		txta_output = new JTextArea("출력");
		txta_output.setFont(font_textArea);
		txta_output.setBackground(Color.black);
		txta_output.setForeground(new Color(51, 204, 51));

		// END 텍스트영역 생성

		// 하단 버튼 구현
		pan_mainGui.add(txta_output);

		JButton butt_convert = new JButton("변환");
		butt_convert.addActionListener(e -> {
			this.BtEvent_convert();
		});

		JButton butt_init = new JButton("초기화");
		butt_init.addActionListener(e -> {
			this.initInput();
		});

		JButton butt_copy = new JButton("출력내용 복사");
		butt_copy.addActionListener(e -> {
			this.clipboardCopy();
		});

		pan_underButtons.add(butt_convert);
		pan_underButtons.add(butt_init);
		pan_underButtons.add(butt_copy);

	}

	// =================================================== 글꼴 설정 팝업창 ================================================= 
	class Gui_mini01 extends PopupWindow implements PopupInterface {
		private String fontName;
		private int fontSize;

		// 상속 생성자
		Gui_mini01(String init_title, boolean init_reisze, Font newFont) {
			super(init_title, init_reisze);

			// 인스턴스 지정
			this.getStyle(newFont);

			this.createElements();

			super.visibleSetting(260, 200);
		}

		void getStyle(Font font) {
			this.fontSize = font.getSize();
			this.fontName = font.getName();
		}

		// 메소드:
		// ()
		// 화면 요소 구현
		@Override
		public void createElements() {
			Container con = this.getContentPane();
			con.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

			// START 글꼴 표시 부분
			JPanel pan_fontName = new JPanel();
			pan_fontName.setLayout(new BorderLayout());
			pan_fontName.setPreferredSize(new Dimension(120, 30));

			JTextField txtf_fontName = new JTextField();
			txtf_fontName.setText(this.fontName);
			//txtf_fontName.setBackground(Color.blue);

			pan_fontName.add(txtf_fontName, BorderLayout.CENTER);
			// END 글꼴 표시 부분

			// START 글자크기 표시 부분
			JPanel pan_fontSize = new JPanel();
			pan_fontSize.setLayout(new BorderLayout());
			pan_fontSize.setPreferredSize(new Dimension(120, 30));

			JTextField txtf_fontSize = new JTextField();
			txtf_fontSize.setText(String.valueOf(this.fontSize));
			//txtf_fontSize.setBackground(Color.green);

			pan_fontSize.add(txtf_fontSize, BorderLayout.CENTER);
			// END 글자크기 표시 부분

			JButton butt_Ok = new JButton("확인");
			butt_Ok.addActionListener(e -> {
				resetTxtFont(new Font(txtf_fontName.getText(), Font.PLAIN, Integer.parseInt(txtf_fontSize.getText())));
				this.closeWindow();
			});

			JButton butt_Cancel = new JButton("취소");
			butt_Cancel.addActionListener(e -> {
				this.closeWindow();
			});

			// START 컨테이너에 반영
			con.add(new JLabel("글꼴 이름 : "));
			con.add(pan_fontName);
			con.add(new JLabel("글자 크기 : "));
			con.add(pan_fontSize);
			con.add(butt_Ok);
			con.add(butt_Cancel);
			// END 컨테이너에 반영
		}

		// 메소드 :
		// ()
		// 창 닫는 기능
		@Override
		public void closeWindow() {
			dispose();
		}
	}
	// =================================================== 프리셋 설정 팝업창 ================================================= 

	class Gui_mini02 extends PopupWindow implements PopupInterface {
		private String presetStr;
		private JTextArea txta_preView;

		Gui_mini02(String init_title, boolean init_reisze) {
			super(init_title, init_reisze);


			this.createElements();

			super.visibleSetting(600, 300);
		}

		@Override
		public void createElements() {
			Container con = this.getContentPane();
			con.setLayout(new BorderLayout());

			txta_preView = new JTextArea();
			txta_preView.setText(this.txtaDraw());

			// start 하단버튼
			JPanel pan1 = new JPanel();

			JButton butt_Ok = new JButton("확인");
			butt_Ok.addActionListener(e -> {
				this.updatePreset();
			});

			JButton butt_Cancel = new JButton("취소");
			butt_Cancel.addActionListener(e -> {
				this.closeWindow();
			});

			pan1.add(butt_Ok);
			pan1.add(butt_Cancel);
			// end 하단버튼

			con.add(txta_preView, BorderLayout.CENTER);
			con.add(pan1, BorderLayout.SOUTH);

		}

		public String txtaDraw() {
			String tmp = "";
			tmp += "@@ 헤드라인 @@\n";
			tmp += star[_def.LONG];
			tmp += "\n";
			tmp += "@@ 엔드라인 @@\n";	
			tmp += star[_def.ENDLONG];
			return tmp;
		}
		
		public void updatePreset() {
			String tmp[] = txta_preView.getText().split("\n");
			star[_def.LONG] = tmp[1];
			star[_def.ENDLONG] = tmp[3];
			this.closeWindow();
		}

		@Override
		public void closeWindow() {
			dispose();
		}

	}
}
