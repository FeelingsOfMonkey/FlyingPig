package expStudent;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Student {
	private LinkDB linkDB = new LinkDB();
	private Connection dbconn;
	private boolean x=false;//�����ж��Ƿ������
	private String strXueHaoD = null;
	
	public JPanel jpSelect = null;
	public JScrollPane jspSelect=null; //�����ӱ��������
	//��ѯ
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jlStr0 = new JLabel("ѧ����ѯ");
	
	//�ؼ�
	private JLabel jlSelect = null;
	private JTextField jtfSelect = null;
	private JButton jbtnSelect =null, jbtnDelete = null;
	private JTable jtSelect=null; //����
	
	private Vector columName; //�Զ������Ķ�������
	public Student(){
		
		jpSelect = new JPanel(new GridLayout(2,1));
		jlSelect = new JLabel("���������ѧ����ѧ�ţ�");
		jtfSelect = new JTextField(15);
		jbtnSelect = new JButton("��ѯ");
		jbtnDelete = new JButton("ɾ��");
		jpOne.add(jlStr0);
		jpTwo.add(jlSelect);
		jpTwo.add(jtfSelect);
		jpTwo.add(jbtnSelect);
		jpTwo.add(jbtnDelete);
		jpSelect.add(jpOne);
		jpSelect.add(jpTwo);
		
		
		//��������ʾ�����ݺ�����
		columName = new Vector();
		columName.add("ѧ��");
		columName.add("����");
		columName.add("�Ա�");
		columName.add("����");
		columName.add("����");
		columName.add("ѧԺ");
		columName.add("רҵ");
		columName.add("�༶");
		columName.add("������ò");
		columName.add("��������");
		columName.add("��ͥ��ַ");
		columName.add("���˼���");
		jtSelect = new JTable(null,columName);//���ݼӵ�������
		jtSelect.setPreferredScrollableViewportSize(new Dimension(880, 240)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtSelect.setDefaultRenderer(Object.class, dtcr);
		jspSelect = new JScrollPane(jtSelect);
		
		//��ѯ
		jbtnSelect.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfSelect.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				else{
					try {
						dbconn = linkDB.lindDataBase();//�������ݿ�
						Statement stmt = dbconn.createStatement();//����SQL�������
						ResultSet rs = stmt.executeQuery("select student_id,student_name,student_sex,student_age,nation_name,c.academy_name," +
							"d.major_name,e.class_name,ps_name,student_date,student_address,student_resume " +
							"from Student a,Nation b, Academy c, Major d, Class e, PoliticsStatus f " +
							"where a.nation_code=b.nation_code and c.academy_code=d.academy_code and a.major_code=d.major_code " +
							"and a.class_code=e.class_code and a.ps_id=f.ps_id and a.student_id='"+jtfSelect.getText().trim()+"'");
						DefaultTableModel dtmSelect = new DefaultTableModel();
						boolean b = false;
						while(rs.next()){
							Vector data = new Vector();
							Vector vNext = new Vector();
							vNext.add(rs.getString("student_id"));
							vNext.add(rs.getString("student_name"));
							vNext.add(rs.getString("student_sex"));
							vNext.add(rs.getString("student_age"));
							vNext.add(rs.getString("nation_name"));
							vNext.add(rs.getString("academy_name"));
							vNext.add(rs.getString("major_name"));
							vNext.add(rs.getString("class_name"));
							vNext.add(rs.getString("ps_name"));
							vNext.add(rs.getString("student_date"));
							vNext.add(rs.getString("student_address"));
							vNext.add(rs.getString("student_resume"));
							data.add(vNext);
							dtmSelect.setDataVector(data, columName);
							jtSelect.setModel(dtmSelect);
							b = true;
						}
						dbconn.close();
						if(b==true){
							x=true;
							strXueHaoD = jtfSelect.getText().trim();
							JOptionPane.showMessageDialog(null, "ѧ����Ϣ��ѯ�ɹ���","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}else
							JOptionPane.showMessageDialog(null, "��ѯʧ�ܣ��޸�ѧ����Ϣ","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "�˲�ѯ��Ч��","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		//ɾ��
		jbtnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(x==true){
					try{
						Object[] options={"ȷ��","ȡ��"};
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();
						int response=JOptionPane.showOptionDialog(null, "ȷ��ɾ���ÿγ̣�","ɾ����ʾ��",
							JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
						if(response==0){
							stmt.executeUpdate("delete Student where student_id='"+strXueHaoD+"'");
							x = false;
							StuLoading();
							JOptionPane.showMessageDialog(null, "��ѧ�����ɹ�ɾ����","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						dbconn.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "���Ȳ�ѯ��Ҫɾ����ѧ����","������ʾ",JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
	}
	
	public void StuLoading(){
		try {
			dbconn = linkDB.lindDataBase();//�������ݿ�
			Statement stmt = dbconn.createStatement();//����SQL�������
			ResultSet rs = stmt.executeQuery("select student_id,student_name,student_sex,student_age,nation_name,c.academy_name," +
				"d.major_name,e.class_name,ps_name,student_date,student_address,student_resume " +
				"from Student a,Nation b, Academy c, Major d, Class e, PoliticsStatus f " +
				"where a.nation_code=b.nation_code and c.academy_code=d.academy_code and a.major_code=d.major_code " +
				"and a.class_code=e.class_code and a.ps_id=f.ps_id order by d.academy_code");
			DefaultTableModel dtmSelect = new DefaultTableModel();
			Vector data = new Vector();
			while(rs.next()){
				Vector vNext = new Vector();
				vNext.add(rs.getString("student_id"));
				vNext.add(rs.getString("student_name"));
				vNext.add(rs.getString("student_sex"));
				vNext.add(rs.getString("student_age"));
				vNext.add(rs.getString("nation_name"));
				vNext.add(rs.getString("academy_name"));
				vNext.add(rs.getString("major_name"));
				vNext.add(rs.getString("class_name"));
				vNext.add(rs.getString("ps_name"));
				vNext.add(rs.getString("student_date"));
				vNext.add(rs.getString("student_address"));
				vNext.add(rs.getString("student_resume"));
				data.add(vNext);
				dtmSelect.setDataVector(data, columName);
				jtSelect.setModel(dtmSelect);
			}
			dbconn.close();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}