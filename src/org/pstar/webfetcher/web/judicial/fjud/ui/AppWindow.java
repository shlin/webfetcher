package org.pstar.webfetcher.web.judicial.fjud.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.pstar.webfetcher.formatter.DateLabelFormatter;
import org.pstar.webfetcher.web.judicial.fjud.controller.FJUDCtrlViewerImpl;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AppWindow {
	private JButton btnChoiceFile;

	private JButton btnStart;
	private JButton btnStop;
	private JDatePickerImpl dateEndPicker;
	private Properties datePickerProperties;
	private JDatePickerImpl dateStartPicker;
	private FJUDCtrlViewerImpl fjudFormListener;
	private JFrame frame;
	private CheckBoxList jListCourt;
	private JLabel lblFJUDTitleExclude;
	private JList<String> listFJUDList;
	private JProgressBar progressBarCourts;
	private JSpinner spinnerFetchTimeInterval;
	private JTextField txtDatabaseName;
	private JTextField txtFilePath;
	private JTextPane txtFJUDContent;
	private JTextField txtFJUDTitle;
	private JTextField txtFJUDTitleExclude;
	private JTextField txtFulltextKeyword;
	private JTextField txtMySQLHost;
	private JPasswordField txtMySQLPassword;
	private JTextField txtMySQLUser;
	private JTextField txtTableName;
	private JProgressBar progressBarDocs;

	/**
	 * Create the application.
	 */
	public AppWindow(FJUDCtrlViewerImpl listener) {
		this.fjudFormListener = listener;

		this.initTheme();
		this.initDatePickerProperties();
		this.initialize();
		this.frame.setVisible(true);
	}

	/**
	 * @return the btnChoiceFile
	 */
	public JButton getBtnChoiceFile() {
		return btnChoiceFile;
	}

	/**
	 * @return the btnStart
	 */
	public JButton getBtnStart() {
		return btnStart;
	}

	/**
	 * @return the btnStop
	 */
	public JButton getBtnStop() {
		return btnStop;
	}

	/**
	 * @return the dateEndPicker
	 */
	public JDatePickerImpl getDateEndPicker() {
		return dateEndPicker;
	}

	/**
	 * @return the dateStartPicker
	 */
	public JDatePickerImpl getDateStartPicker() {
		return dateStartPicker;
	}

	/**
	 * @return the jListCourt
	 */
	public CheckBoxList getjListCourt() {
		return jListCourt;
	}

	/**
	 * @return the lblFJUDTitleExclude
	 */
	public JLabel getLblFJUDTitleExclude() {
		return lblFJUDTitleExclude;
	}

	/**
	 * @return the listFJUDList
	 */
	public JList<String> getListFJUDList() {
		return listFJUDList;
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBarCourts;
	}

	/**
	 * @return the spinnerFetchTimeInterval
	 */
	public JSpinner getSpinnerFetchTimeInterval() {
		return spinnerFetchTimeInterval;
	}

	/**
	 * @return the txtDatabaseName
	 */
	public JTextField getTxtDatabaseName() {
		return txtDatabaseName;
	}

	/**
	 * @return the txtFilePath
	 */
	public JTextField getTxtFilePath() {
		return txtFilePath;
	}

	/**
	 * @return the txtFJUDContent
	 */
	public JTextPane getTxtFJUDContent() {
		return txtFJUDContent;
	}

	/**
	 * @return the txtFJUDTitle
	 */
	public JTextField getTxtFJUDTitle() {
		return txtFJUDTitle;
	}

	/**
	 * @return the txtFJUDTitleExclude
	 */
	public JTextField getTxtFJUDTitleExclude() {
		return txtFJUDTitleExclude;
	}

	/**
	 * @return the txtFulltextKeyword
	 */
	public JTextField getTxtFulltextKeyword() {
		return txtFulltextKeyword;
	}

	/**
	 * @return the txtMySQLHost
	 */
	public JTextField getTxtMySQLHost() {
		return txtMySQLHost;
	}

	/**
	 * @return the txtMySQLPassword
	 */
	public JPasswordField getTxtMySQLPassword() {
		return txtMySQLPassword;
	}

	/**
	 * @return the txtMySQLUser
	 */
	public JTextField getTxtMySQLUser() {
		return txtMySQLUser;
	}

	/**
	 * @return the txtTableName
	 */
	public JTextField getTxtTableName() {
		return txtTableName;
	}

	private void initDatePickerProperties() {
		this.datePickerProperties = new Properties();

		this.datePickerProperties.put("text.today", "今天");
		this.datePickerProperties.put("text.month", "月");
		this.datePickerProperties.put("text.year", "年");
		this.datePickerProperties.put("text.clear", "清除");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 750);
		frame.getContentPane()
				.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("5px"),
						ColumnSpec.decode("right:150px"), ColumnSpec.decode("left:200px"), ColumnSpec.decode("5px"),
						ColumnSpec.decode("400px:grow"), ColumnSpec.decode("5px"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("100px:grow"),
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblOutputType = new JLabel("輸出方式：");
		frame.getContentPane().add(lblOutputType, "2, 2");

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panel, "3, 2, fill, fill");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		ButtonGroup btnGOutputType = new ButtonGroup();
		ActionListener outputTypeActListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fjudFormListener.doChangeOutputType("1".equals(e.getActionCommand()));
			}
		};

		JRadioButton rdbtnSqlFile = new JRadioButton("SQL File");
		btnGOutputType.add(rdbtnSqlFile);
		rdbtnSqlFile.setActionCommand("0");
		rdbtnSqlFile.addActionListener(outputTypeActListener);
		panel.add(rdbtnSqlFile);

		JRadioButton rdbtnMysql = new JRadioButton("MySQL");
		rdbtnMysql.setSelected(true);
		btnGOutputType.add(rdbtnMysql);
		rdbtnMysql.setActionCommand("1");
		rdbtnMysql.addActionListener(outputTypeActListener);
		panel.add(rdbtnMysql);

		listFJUDList = new JList<String>();
		listFJUDList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.getContentPane().add(new JScrollPane(listFJUDList), "5, 2, 1, 17, fill, fill");
		listFJUDList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					fjudFormListener.doPaintShowContent();
				}
			}
		});

		JLabel lblSQLFile = new JLabel("SQL輸出檔：");
		frame.getContentPane().add(lblSQLFile, "2, 4");

		JPanel panelSQLFile = new JPanel();
		panelSQLFile.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panelSQLFile, "3, 4, fill, fill");
		panelSQLFile.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("left:default:grow"), ColumnSpec.decode("right:100px"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, }));

		txtFilePath = new JTextField();
		txtFilePath.setEnabled(false);
		panelSQLFile.add(txtFilePath, "1, 1, fill, center");
		txtFilePath.setColumns(10);

		btnChoiceFile = new JButton("選擇檔案");
		btnChoiceFile.setEnabled(false);
		panelSQLFile.add(btnChoiceFile, "2, 1, center, center");

		JLabel lblMySQLHost = new JLabel("MySQL 伺服器：");
		frame.getContentPane().add(lblMySQLHost, "2, 6, right, default");

		txtMySQLHost = new JTextField();
		frame.getContentPane().add(txtMySQLHost, "3, 6, fill, default");
		txtMySQLHost.setColumns(10);

		JLabel lblMySQLUser = new JLabel("MySQL 帳號：");
		frame.getContentPane().add(lblMySQLUser, "2, 8, right, default");

		txtMySQLUser = new JTextField();
		frame.getContentPane().add(txtMySQLUser, "3, 8, fill, default");
		txtMySQLUser.setColumns(10);

		JLabel lblMySQLPassword = new JLabel("MySQL 密碼：");
		frame.getContentPane().add(lblMySQLPassword, "2, 10, right, default");

		txtMySQLPassword = new JPasswordField();
		frame.getContentPane().add(txtMySQLPassword, "3, 10, fill, default");

		JLabel lblDatabaseName = new JLabel("Database 名稱：");
		frame.getContentPane().add(lblDatabaseName, "2, 12, right, default");

		txtDatabaseName = new JTextField();
		frame.getContentPane().add(txtDatabaseName, "3, 12, fill, default");
		txtDatabaseName.setColumns(10);

		JLabel lblTableName = new JLabel("Table 名稱：");
		frame.getContentPane().add(lblTableName, "2, 14, right, default");

		txtTableName = new JTextField();
		txtTableName.setText("judicial");
		frame.getContentPane().add(txtTableName, "3, 14, fill, default");
		txtTableName.setColumns(10);

		JLabel lblFJUDCategory = new JLabel("裁判類別：");
		frame.getContentPane().add(lblFJUDCategory, "2, 16");

		JPanel panelFJUDCategory = new JPanel();
		panelFJUDCategory.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panelFJUDCategory, "3, 16, fill, fill");
		FlowLayout fl_panelFJUDCategory = new FlowLayout(FlowLayout.LEFT, 1, 1);
		panelFJUDCategory.setLayout(fl_panelFJUDCategory);

		JCheckBox chkFJUDCategory = new JCheckBox("刑事");
		chkFJUDCategory.setSelected(true);
		panelFJUDCategory.add(chkFJUDCategory);

		JLabel lblFJUDTitle = new JLabel("判決案由：");
		frame.getContentPane().add(lblFJUDTitle, "2, 18, right, default");

		txtFJUDTitle = new JTextField();
		frame.getContentPane().add(txtFJUDTitle, "3, 18, fill, default");
		txtFJUDTitle.setColumns(10);

		lblFJUDTitleExclude = new JLabel("判決案由排除：");
		frame.getContentPane().add(lblFJUDTitleExclude, "2, 20, right, default");

		txtFJUDTitleExclude = new JTextField();
		frame.getContentPane().add(txtFJUDTitleExclude, "3, 20, fill, default");
		txtFJUDTitleExclude.setColumns(10);

		txtFJUDContent = new JTextPane();
		txtFJUDContent.setEditable(false);

		frame.getContentPane().add(new JScrollPane(txtFJUDContent), "5, 20, 1, 15, fill, fill");

		JLabel lblFulltextKeyword = new JLabel("全文檢索語詞：");
		frame.getContentPane().add(lblFulltextKeyword, "2, 22, right, default");

		txtFulltextKeyword = new JTextField();
		frame.getContentPane().add(txtFulltextKeyword, "3, 22, fill, default");
		txtFulltextKeyword.setColumns(10);

		JLabel lblDateRangeStart = new JLabel("判決日期（開始）：");
		frame.getContentPane().add(lblDateRangeStart, "2, 24");

		UtilDateModel dateStartModel = new UtilDateModel();
		JDatePanelImpl dateStartPanel = new JDatePanelImpl(dateStartModel, datePickerProperties);
		dateStartPicker = new JDatePickerImpl(dateStartPanel, new DateLabelFormatter());

		dateStartModel.setDate(2000, 0, 1);
		dateStartModel.setSelected(true);
		frame.getContentPane().add(dateStartPicker, "3, 24, left, fill");

		JLabel lblDateRangeEnd = new JLabel("判決日期（結束）：");
		frame.getContentPane().add(lblDateRangeEnd, "2, 26");

		UtilDateModel dateEndModel = new UtilDateModel();
		JDatePanelImpl dateEndPanel = new JDatePanelImpl(dateEndModel, datePickerProperties);
		dateEndPicker = new JDatePickerImpl(dateEndPanel, new DateLabelFormatter());

		dateEndModel.setSelected(true);
		frame.getContentPane().add(dateEndPicker, "3, 26, fill, fill");

		JLabel lblFetchTimeInterval = new JLabel("抓取間隔時間（秒）：");
		frame.getContentPane().add(lblFetchTimeInterval, "2, 28");

		spinnerFetchTimeInterval = new JSpinner();
		spinnerFetchTimeInterval.setModel(new SpinnerNumberModel(3, 1, 100, 1));
		frame.getContentPane().add(spinnerFetchTimeInterval, "3, 28, left, center");

		JLabel lblCourtList = new JLabel("法院名稱：");
		frame.getContentPane().add(lblCourtList, "2, 30, left, default");

		jListCourt = new CheckBoxList();
		jListCourt.setModel(this.fjudFormListener.getCourtListModel());

		frame.getContentPane().add(new JScrollPane(jListCourt), "2, 32, 2, 1, fill, fill");

		btnStop = new JButton("停止");
		btnStop.setEnabled(false);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnStop.isEnabled()) {
					fjudFormListener.doStop();
					btnStop.setEnabled(false);
					btnStart.setEnabled(true);
				}
			}
		});
		frame.getContentPane().add(btnStop, "2, 34");

		btnStart = new JButton("開始");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnStart.isEnabled()) {
					btnStart.setEnabled(false);
					btnStop.setEnabled(true);
					fjudFormListener.doFetch();
				}
			}
		});
		frame.getContentPane().add(btnStart, "3, 34");

		JLabel label = new JLabel("完成進度（法院）：");
		frame.getContentPane().add(label, "2, 36");

		progressBarCourts = new JProgressBar();
		progressBarCourts.setStringPainted(true);
		frame.getContentPane().add(progressBarCourts, "3, 36, 3, 1, fill, center");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		// frame.getContentPane().add(panel_1, "5, 36, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("right:200px"), ColumnSpec.decode("left:default:grow"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblProgressBarDocs = new JLabel("完成進度（單一法院裁判書）：");
		panel_1.add(lblProgressBarDocs, "1, 1");

		progressBarDocs = new JProgressBar();
		progressBarDocs.setStringPainted(true);
		panel_1.add(progressBarDocs, "2, 1, fill, default");
	}

	private void initTheme() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	/**
	 * @param fjudFormListener
	 *            the fjudFormListener to set
	 */
	public void setFjudFormListener(FJUDCtrlViewerImpl fjudFormListener) {
		this.fjudFormListener = fjudFormListener;
	}
}