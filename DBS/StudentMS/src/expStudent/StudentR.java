package expStudent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentR {
	private LinkDB linkDB = new LinkDB();
	private Connection dbconn;
	
	public JPanel jpStuR = new JPanel(new GridLayout(3,1));
	public JScrollPane jspStu_R = null;
	private JTable jtR = null;
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jlStr0 = new JLabel("������ѯ");
	private JLabel jlStudentId = new JLabel("ѧ��ѧ�ţ�");
	private JTextField jtfStudentId = new JTextField(15);
	private JButton jbtnChaxun = new JButton("��ѯ");
	private Vector columnName_R = new Vector();
	
	public StudentR(){
		//��ѯ
		jpOne.add(jlStr0);
		jpTwo.add(jlStudentId);
		jpTwo.add(jtfStudentId);
		jpTwo.add(jbtnChaxun);
		jpStuR.add(jpOne);
		jpStuR.add(jpTwo);
		columnName_R.add("�������");
		columnName_R.add("ѧ��");
		columnName_R.add("����");
		columnName_R.add("ѧԺ");
		columnName_R.add("רҵ");
		columnName_R.add("�༶");
		columnName_R.add("����ʱ��");
		columnName_R.add("��������");
		
		jtR = new JTable(null,columnName_R);
		jtR.setPreferredScrollableViewportSize(new Dimension(880, 350)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtR.setDefaultRenderer(Object.class, dtcr);
		jspStu_R = new JScrollPane(jtR);
		
		//��ѯ
		jbtnChaxun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfStudentId.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				else{
					try{
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();
						ResultSet rsR = stmt.executeQuery("select a.reward_code, a.student_id, b.student_name, c.academy_name, d.major_name,e.class_name, a.reward_time, a.reward_notes " +
				    		"from Reward a, Student b, Academy c, Major d, Class e " +
				    		"where a.student_id=b.student_id and c.academy_code=d.academy_code and b.major_code=d.major_code " +
				    		"and b.class_code=e.class_code and a.student_id='"+jtfStudentId.getText().trim()+"'");
						Vector dataR = new Vector();
						DefaultTableModel dtmStuR = new DefaultTableModel();
						boolean b = false;
						while(rsR.next()){
							Vector vNext = new Vector();
							vNext.add(rsR.getString("reward_code"));
							vNext.add(rsR.getString("student_id"));
							vNext.add(rsR.getString("student_name"));
							vNext.add(rsR.getString("academy_name"));
							vNext.add(rsR.getString("major_name"));
							vNext.add(rsR.getString("class_name"));
							vNext.add(rsR.getString("reward_time"));
							vNext.add(rsR.getString("reward_notes"));
							dataR.add(vNext);
							dtmStuR.setDataVector(dataR, columnName_R);
							jtR.setModel(dtmStuR);
							b = true;
						}
						dbconn.close();
						if(b==true)
							JOptionPane.showMessageDialog(null, "������ѯ�ɹ���","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "��ѧ�����κν�����","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
					}catch(Exception e2){
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "�˲�ѯ��Ч��","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}