package projectDB;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

// 상품 재고 관리 시스템
// 1. 상품명, 수량, 가격
// 2. 이벤트 -> 상품추가, 삭제, 수정
// 3. GUI 환경

public class InMs01 extends JFrame { // GUI 사용을 위한 (extends JFrame) 추가
	// 필드 선언
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField nameField, quantityField, priceField, searchField;
	
	// 생성자 선언
	public InMs01() {
		setTitle("재고관리 시스템(InMs)");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// 테이블 모델 생성
		tableModel = new DefaultTableModel(new Object[] {"상품명", "수량", "가격"}, 0);
		table = new JTable(tableModel);
		
		// 스크롤 패널 -> table
		JScrollPane scrollPane = new JScrollPane(table);
		
		// 입력 패널
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
		
		// 상품명, 수량, 가격
		inputPanel.add(new JLabel("상품명"));
		nameField = new JTextField();
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("수량"));
		quantityField = new JTextField();
		inputPanel.add(quantityField);
		inputPanel.add(new JLabel("가격"));
		priceField = new JTextField();
		inputPanel.add(priceField);
		
		// 검색 필드 추가
		inputPanel.add(new JLabel("상품검색 : "));
		searchField = new JTextField();
		inputPanel.add(searchField);
		
		// 버튼
		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("추가");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem(); // 상품 추가 -> Method 구현
			}
		});
		buttonPanel.add(addButton);
		JButton deleteButton = new JButton("삭제");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteItem(); // 선택한 항목 제거 -> Method 구현
			}
		});
		buttonPanel.add(deleteButton);
		JButton updateButton = new JButton("수정");
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateItem(); // 선택한 항목 수정 -> Method 구현
			}	
		});
		buttonPanel.add(updateButton);
		JButton exitButton = new JButton("종료");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonPanel.add(exitButton);
		
		// 검색 버튼 추가
		JButton searchButton = new JButton("검색");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchItem(); // 이벤트 처리 메서드
			}	
		});
		buttonPanel.add(searchButton);
		// 차트 그래프
		JButton viewGraphButton = new JButton("재고현황 그래프보기");
		viewGraphButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayGraph() ; /* 그래프 출력 메서드 호출 */
			}
		});
		buttonPanel.add(viewGraphButton);
		
		// 전체 Layout 구성
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	// Method 선언
	private void addItem() { // 상품 등록 메서드
		String name = nameField.getText();						// 상품명
		String quantityText = quantityField.getText();			// 상품 수량
		String priceText = priceField.getText();				// 상품 가격
		if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) { // 공백으로 입력하였을 경우 에러 메세지
			JOptionPane.showMessageDialog(this, "모든정보를 입력하세요", "입력오류", JOptionPane.ERROR_MESSAGE);	
			return;
		}
		try {
			// 자료형 변환
			int quantity = Integer.parseInt(quantityText);
			double price = Double.parseDouble(priceText);
			
			// 테이블 모델에 데이터 추가
			tableModel.addRow(new Object[] {name, quantity, price});
			
			// 추가 후 입력필드 초기화
			nameField.setText("");
			quantityField.setText("");
			priceField.setText("");
		} catch(NumberFormatException e) { // 예외 처리
			JOptionPane.showMessageDialog(this, "상품수량과 가격은 숫자로 입력해야 합니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void deleteItem() { // 등록된 상품 삭제 메서드
		int selectedRow = table.getSelectedRow();			// 상품정보가 있는 줄
		if(selectedRow >= 0) {								// 선택한 줄이 1 이상이면
			tableModel.removeRow(selectedRow);				// 선택 한 상품정보 줄 삭제
		} else { // 선택 안 했을 시 에러 메세지
			JOptionPane.showMessageDialog(this, "삭제할 상품정보를 선택하세요.", "삭제오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void updateItem() { // 등록된 상품 수정 메서드
		int selectedRow = table.getSelectedRow();			// 상품정보가 있는 줄
		if (selectedRow >= 0) {
			String name = nameField.getText();
			String quantityText = quantityField.getText();
			String priceText = priceField.getText();
			// 빈 칸일 시
			if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
				JOptionPane.showMessageDialog(this, "모든정보를 입력하세요", "입력오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				// 자료형 변환
				int quantity = Integer.parseInt(quantityText);
				double price = Integer.parseInt(priceText);
				// 선택한 행의 데이터 수정
				tableModel.setValueAt(name, selectedRow, 0);
				tableModel.setValueAt(quantityText, selectedRow, 1);
				tableModel.setValueAt(priceText, selectedRow, 2);
				// 입력필드 초기화
				nameField.setText("");
				quantityField.setText("");
				priceField.setText("");
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "상품수량과 가격은 숫자로 입력해야 합니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "수정할 상품정보를 선택하세요.", "삭제오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void searchItem() { // 상품검색 메서드
		String searchName = searchField.getText().trim(); /* 검색어 : 상품명 */
		if (searchName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "검색할 상품명을 입력하세요", "입력오류", JOptionPane.ERROR_MESSAGE);	
			return;
		}
		for(int i=0; i < tableModel.getRowCount(); i++) {
			String itemName = tableModel.getValueAt(i, 0).toString();
			if(itemName.equalsIgnoreCase(searchName)) {
				/* 해당 행 (검색 상품이 있는 행) */
				table.setRowSelectionInterval(i, i);
				JOptionPane.showMessageDialog(this, "상품명이 검색되었습니다 ", "검색 성공", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		JOptionPane.showMessageDialog(this, "검색 상품명이 존재하지 않습니다. ", "검색 실패", JOptionPane.ERROR_MESSAGE);
	}
	private void displayGraph() { // 그래프 출력 메서드
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		/* 테이블에서 데이터 수량, 상품명 을 가져와서 그래프로 출력 */
		for(int i=0; i <tableModel.getRowCount(); i++ ) {
			String productName = tableModel.getValueAt(i, 0).toString();
			int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
			dataset.addValue(quantity, "수량", productName);
		}
		// 그래프 생성
		JFreeChart barChart = ChartFactory.createBarChart("재고현황", "상품명", "수량", dataset, PlotOrientation.VERTICAL, false, true, false);
		// 폰트 설정
		Font font = new Font("Malgun Gothic", Font.PLAIN, 12);
		barChart.getTitle().setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		// 타이틀
		CategoryPlot plot = barChart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rangeAxis = plot.getRangeAxis();
		domainAxis.setTickLabelFont(font); /* X축 레이블 폰트 */
		domainAxis.setLabelFont(font); /* X축 제목 폰트 */
		rangeAxis.setTickLabelFont(font); /* Y축 레이블 폰트 */
		rangeAxis.setLabelFont(font); /* Y축 제목 폰트 */
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setItemMargin(0.02);
		// 그래프 패널 생성
		ChartPanel chartPanel = new ChartPanel(barChart);
		JFrame chartFrame = new JFrame("재고현황 그래프");
		chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		chartFrame.setSize(600, 400);
		chartFrame.add(chartPanel);
		chartFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new InMs01().setVisible(true);
			}
		});
	}
}
