import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * Author: konglie [ at ] outlook [ dot ] com
 * Date: 6/24/2014
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GUI extends JFrame {
	public GUI(){
		super();
		initComponents();
		initHandlers();

		setTitle("MultiThread Queue Consumer");
		setMinimumSize(new Dimension(800, 400));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel container;
	private JButton btnDecrease, btnIncrease, btnJob;
	private JLabel lblWorkerNum;
	private JTable tblJobs;
	private DefaultTableModel tblJobsModel;
	private JTextArea txtStatus;

	private void initComponents(){
		container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(container, BorderLayout.CENTER);

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		JPanel header = new JPanel(){
			@Override
			public Dimension getMaximumSize(){
				return new Dimension(super.getMaximumSize().width, 80);
			}
		};
		header.setLayout(new BorderLayout());
		header.setBorder(BorderFactory.createTitledBorder("Jumlah Thread Pekerja / Number of Workers"));

		btnDecrease = new JButton("Kurangi / Decrease");
		header.add(btnDecrease, BorderLayout.WEST);

		lblWorkerNum = new JLabel("0");
		lblWorkerNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkerNum.setHorizontalTextPosition(SwingConstants.CENTER);
		header.add(lblWorkerNum, BorderLayout.CENTER);

		btnIncrease = new JButton("Tambah / Increase");
		header.add(btnIncrease, BorderLayout.EAST);

		btnJob = new JButton("Tambah / add JOB");
		header.add(btnJob, BorderLayout.SOUTH);

		JPanel body = new JPanel();
		body.setLayout(new BoxLayout(body, BoxLayout.X_AXIS));

		final String[] cols = new String[]{"#", "Job Name", "Job Status"};
		tblJobsModel = new DefaultTableModel(null, cols){
			@Override
			public boolean isCellEditable(int x, int y){
				return false;
			}
		};
		tblJobs = new JTable(tblJobsModel);
		tblJobsModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(final TableModelEvent e) {
				if( e.getType() == TableModelEvent.INSERT ){
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							int viewRow = tblJobs.convertRowIndexToView(e.getFirstRow());
							tblJobs.scrollRectToVisible(tblJobs.getCellRect(viewRow, 0, true));
						}
					});
				}
			}
		});
		tblJobs.setRowHeight(20);
		tblJobs.getColumnModel().getColumn(0).setMaxWidth(70);
		tblJobs.getColumnModel().getColumn(0).setMinWidth(30);
		tblJobs.setAutoCreateRowSorter(true);

		JScrollPane sp = new JScrollPane(tblJobs);
		body.add(sp);
		body.add(Box.createHorizontalStrut(10));

		txtStatus = new JTextArea(){
			@Override
			public Dimension getPreferredSize(){
				return new Dimension(350, super.getPreferredSize().height);
			}
		};
		txtStatus.setEditable(false);
		JScrollPane sp1 = new JScrollPane(txtStatus);
		sp1.setOpaque(false);
		sp1.setBorder(BorderFactory.createTitledBorder("Log"));
		body.add(sp1);

		container.add(header);
		container.add(Box.createVerticalStrut(10));
		container.add(body);
	}

	private int jobCounter = 1;
	private void initHandlers(){
		btnIncrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.queue.addWorker();
			}
		});

		btnDecrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.queue.decreaseWorker();
			}
		});

		btnJob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.queue.enqueue(new Job(jobCounter++));
			}
		});
	}

	public void refreshJobList(Job j){
		try {
			tblJobsModel.setValueAt(j.getStatus(), j.name - 1, 2);
		} catch (Exception e){}
	}

	public void log(final String l){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				txtStatus.append(l.trim() + "\n");
			}
		});
	}

	public void setNumWorker(final int n){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				lblWorkerNum.setText("" + n);
			}
		});
	}

	public void addRow(String[] dt){
		tblJobsModel.addRow(dt);
	}
}
