package expStudent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
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

public class Course {
	private Point point;
	//��ʼ��
	private static LinkDB linkDB = new LinkDB();
	private static Connection dbconn;
	public JScrollPane jspCourse = null;
	public JTable jtCourse = null;
	public Vector columName = new Vector();
	//����
	public JPanel jpAddCourse = new JPanel(new GridLayout(4,1));
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JPanel jpThree = new JPanel();
	private JLabel jlAdd = new JLabel("���ӿγ�");
	private JLabel jlCourseId = new JLabel("�γ̱�ţ�");
	private JTextField jtfCourseId = new JTextField(10);
	private JLabel jlCourseName = new JLabel("       �γ����ƣ�");
	private JTextField jtfCourseName = new JTextField(15);
	private JLabel jlCourseCredit = new JLabel("       ѧ�֣�");
	private JTextField jtfCourseCredit = new JTextField(5);
	private JButton jbtnAdd = new JButton("����");
	//����
	private JButton jbtnSelect_CNo = new JButton("����");
	private JButton jbtnSelect_CName = new JButton("����");
	private JLabel jlStr1 = new JLabel("                                                           ");
	private JLabel jlStr2 = new JLabel("                                  ");
	//ɾ��
	private JButton jbtnDel = new JButton("ɾ��");
	private int isOK = 2; //�����ж��ǿγ̺Ż��ǿγ�����
	private String strCNo = null, strCName = null;
	
	public Course(){
		//����
		jpTwo.add(jlAdd);
		jpOne.add(jlCourseId);
		jpOne.add(jtfCourseId);
		jpOne.add(jlCourseName);
		jpOne.add(jtfCourseName);
		jpOne.add(jlCourseCredit);
		jpOne.add(jtfCourseCredit);
		jpOne.add(jbtnAdd);
		jpAddCourse.add(jpTwo);
		jpAddCourse.add(jpOne);
		jpAddCourse.add(jpThree);
		//����
		jpThree.add(jbtnSelect_CNo);
		jpThree.add(jlStr1);
		jpThree.add(jbtnSelect_CName);
		//ɾ��
		jpThree.add(jlStr2);
		jpThree.add(jbtnDel);
		//��ʼ��
		columName.add("�γ̴���");
		columName.add("�γ�����");
		columName.add("�γ�ѧ��");
		jtCourse = new JTable(null,columName);//���ݼӵ�������
		jtCourse.setPreferredScrollableViewportSize(new Dimension(880, 350)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtCourse.setDefaultRenderer(Object.class, dtcr);
		jspCourse = new JScrollPane(jtCourse);
		
		//���Ӱ�ť����¼�
		jbtnAdd.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(jtfCourseId.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "�γ̺Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else if(jtfCourseName.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "�γ�������Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else if(jtfCourseCredit.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "ѧ�ֲ���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else if(!jtfCourseId.getText().trim().equals("")&&!jtfCourseName.getText().trim().equals("")&&!jtfCourseCredit.getText().trim().equals("")){
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();
						String strAdd = "insert Course values('"+jtfCourseId.getText().trim()+"','"+jtfCourseName.getText().trim()+"','"+jtfCourseCredit.getText().trim()+"')";
						stmt.executeUpdate(strAdd);
						dbconn.close();
						Loading(); //ˢ���б�		
						JOptionPane.showMessageDialog(null, "���ӿγ̳ɹ���","������ʾ",JOptionPane.INFORMATION_MESSAGE);
						Clear();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "�γ̱�Ų����ظ���","������ʾ",JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		point = jbtnSelect_CNo.getLocation();
		jbtnSelect_CNo.setBounds(point.x-100, point.y, 100, 100);
		//����
		jbtnSelect_CNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfCourseId.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "�γ̺Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}else{
				    Select(jtfCourseId.getText().trim(), 0);
				    strCNo = jtfCourseId.getText().trim();
				    Clear();
				}
			}
		});
		jbtnSelect_CName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfCourseName.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "�γ�������Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}else{
				    Select(jtfCourseName.getText().trim(), 1);
				    strCName = jtfCourseName.getText().trim();
				    Clear();
				}
			}
		});
		//ɾ��
		jbtnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isOK==0){
					Delete(strCNo, isOK);
					Clear();
					Loading();
					isOK = 2;
				}
				else if(isOK==1){
					Delete(strCName, isOK);
					Clear();
					Loading();
					isOK = 2;
				}
				else if(isOK!=0||isOK!=1){
					JOptionPane.showMessageDialog(null, "���Ȳ�ѯ��Ҫɾ���Ŀγ̣�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	//���ر��е�����
	public void Loading(){
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
		    ResultSet rs = stmt.executeQuery("select * from Course order by course_code"); //����ҳ����ݵ�������
		    
		    Vector data = new Vector();
		    DefaultTableModel dtmCourse = new DefaultTableModel();
		    while(rs.next()){
		    	Vector vNext = new Vector();
		    	vNext.add(rs.getString("course_code"));
		    	vNext.add(rs.getString("course_name"));
		    	vNext.add(rs.getString("course_credit"));
		    	data.add(vNext);
		    	dtmCourse.setDataVector(data, columName);
		        jtCourse.setModel(dtmCourse);
		    }
		    dbconn.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ѯ
	public void Select(String str, int i){
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
			ResultSet rs=null;
			if(i==0){
		        rs = stmt.executeQuery("select * from Course where course_code='"+str+"' order by course_code"); //����ҳ����ݵ�������
			    isOK = 0;
			}
			else if(i==1){
				rs = stmt.executeQuery("select * from Course where course_name='"+str+"' order by course_code");
			    isOK = 1;
			}
		    Vector data = new Vector();
		    DefaultTableModel dtmCourse = new DefaultTableModel();
		    boolean b = false;
		    while(rs.next()){
		    	Vector vNext = new Vector();
		    	vNext.add(rs.getString("course_code"));
		    	vNext.add(rs.getString("course_name"));
		    	vNext.add(rs.getString("course_credit"));
		    	data.add(vNext);
		    	dtmCourse.setDataVector(data, columName);
		        jtCourse.setModel(dtmCourse);
		        b = true;
		    }
		    dbconn.close();
		    if(b==true)
				JOptionPane.showMessageDialog(null, "�ɹ��ҵ��ÿγ̣�","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "��ѯʧ�ܣ����޸ÿγ���Ϣ","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ɾ��
	public void Delete(String str, int i){
		try{
			Object[] options={"ȷ��","ȡ��"};
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();
			if(i==0){
				int response=JOptionPane.showOptionDialog(null, "ȷ��ɾ���ÿγ̣�","ɾ����ʾ��",
						JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
			    if(response==0){
				    stmt.executeUpdate("delete Course where course_code='"+str+"'");
				}
			    //else if(response==1)
			}
			else if(i==1){
				int response=JOptionPane.showOptionDialog(null, "ȷ��ɾ���ÿγ̣�","ɾ����ʾ��",
						JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
			    if(response==0){
				    stmt.executeUpdate("delete Course where course_name='"+str+"'");
				}
			}
			dbconn.close();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�ÿγ��ѱ�ѡ���޷�ɾ����","������ʾ",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void Clear(){
		jtfCourseId.setText(null);
		jtfCourseName.setText(null);
		jtfCourseCredit.setText(null);
	}
}