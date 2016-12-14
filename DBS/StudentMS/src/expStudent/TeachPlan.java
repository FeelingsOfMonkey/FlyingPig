package expStudent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TeachPlan {
	private static LinkDB linkDB = new LinkDB();
	private static Connection dbconn;
	
	public JPanel jpTeachPlan = new JPanel();
	private JPanel jpTP_xueyuan=null,jpTP_zhuanye=null,jpTP_xuenian=null,
			jpTP_xueqi=null,jpTP_kecheng=null,jpTP_xuefen=null,jpTP_anniu=null;
	public JScrollPane jspTeachPlan = null;
	private JTable jtTeachPlan = null;
	private Vector rowData = new Vector();
	private Vector columName = new Vector();
	
	private String[] str = {"--��ѡ������--"};
	private String str0 = "--��ѡ������--";
	private JLabel jlTP_xueyuan = new JLabel("ѧԺ��");
	private JComboBox jcbTP_xueyuan = new JComboBox(str);
	private JLabel jlTP_zhuanye = new JLabel("רҵ��");
	private JComboBox jcbTP_zhuanye = new JComboBox(str);
	private JLabel jlTP_xuenian = new JLabel("ѧ�꣺");
	private JComboBox jcbTP_xuenian = new JComboBox(str);
	private JLabel jlTP_xueqi = new JLabel("ѧ�ڣ�");
	private JComboBox jcbTP_xueqi = new JComboBox(str);
	private JLabel jlTP_kecheng = new JLabel("�γ̣�");
	private JComboBox jcbTP_kecheng = new JComboBox(str);
	private JLabel jlTP_xuefen = new JLabel("ѧ�֣�");
	private JButton jbTP_tianjia = new JButton("����");
	
	public TeachPlan(){
		jcbTP_xueyuan.setPreferredSize(new Dimension(160, 30));
		jcbTP_zhuanye.setPreferredSize(new Dimension(170,30));
		jcbTP_xuenian.setPreferredSize(new Dimension(110,30));
		jcbTP_xueqi.setPreferredSize(new Dimension(110,30));
		jcbTP_kecheng.setPreferredSize(new Dimension(160,30));
		
		columName.add("ѧԺ");
		columName.add("רҵ");
		columName.add("ѧ��");
		columName.add("ѧ�ں�");
		columName.add("�γ�");
		columName.add("ѧ��");
		
		jpTP_xueyuan = new JPanel();
		jpTP_xueyuan.add(jlTP_xueyuan);
		jpTP_xueyuan.add(jcbTP_xueyuan);
		
		jpTP_zhuanye = new JPanel();
		jpTP_zhuanye.add(jlTP_zhuanye);
		jpTP_zhuanye.add(jcbTP_zhuanye);
		
		jpTP_xuenian = new JPanel();
		jpTP_xuenian.add(jlTP_xuenian);
		jpTP_xuenian.add(jcbTP_xuenian);
		
		jpTP_xueqi = new JPanel();
		jpTP_xueqi.add(jlTP_xueqi);
		jpTP_xueqi.add(jcbTP_xueqi);
		
		jpTP_kecheng = new JPanel();
		jpTP_kecheng.add(jlTP_kecheng);
		jpTP_kecheng.add(jcbTP_kecheng);
		
		jpTP_xuefen = new JPanel();
		jpTP_xuefen.add(jlTP_xuefen);
		
		jpTP_anniu = new JPanel();
		jpTP_anniu.add(jbTP_tianjia);
		
		jpTeachPlan.setLayout(new GridLayout(2,2));
		jpTeachPlan.add(jpTP_xueyuan);
		jpTeachPlan.add(jpTP_zhuanye);
		jpTeachPlan.add(jpTP_xuenian);
		jpTeachPlan.add(jpTP_xueqi);
		jpTeachPlan.add(jpTP_kecheng);
		jpTeachPlan.add(jpTP_xuefen);
		jpTeachPlan.add(jpTP_anniu);
		
		jtTeachPlan = new JTable(rowData,columName);
		jtTeachPlan.setPreferredScrollableViewportSize(new Dimension(880, 400)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtTeachPlan.setDefaultRenderer(Object.class, dtcr);
		jspTeachPlan = new JScrollPane(jtTeachPlan);
		
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
		    ResultSet rsAcademy = stmt.executeQuery("select distinct academy_name from Academy"); //����ҳ����ݵ�������
		    while(rsAcademy.next()){
		    	jcbTP_xueyuan.addItem(rsAcademy.getString("academy_name"));
		    }
		    ResultSet rsSchoolYear = stmt.executeQuery("select distinct school_yesr from SchoolYear");
		    while(rsSchoolYear.next()){
		    	jcbTP_xuenian.addItem(rsSchoolYear.getString("school_yesr"));
		    }
		    ResultSet rsCourse = stmt.executeQuery("select distinct course_name from Course");
		    while(rsCourse.next()){
		    	jcbTP_kecheng.addItem(rsCourse.getString("course_name"));
		    }
		    
		    ResultSet rs = stmt.executeQuery("select distinct a.academy_name, b.major_name, c.school_yesr, c.term_id, d.course_name,d.course_credit " +
		    		"from Academy a, Major b, SchoolYear c, Course d where a.academy_code=b.academy_code " +
		    		"and b.major_code=c.major_code and c.course_code=d.course_code");
		    DefaultTableModel dtmTP = new DefaultTableModel();
		    Vector data = new Vector();
		    while(rs.next()){
		    	Vector vNext = new Vector();
		    	vNext.add(rs.getString("academy_name"));
		    	vNext.add(rs.getString("major_name"));
		    	vNext.add(rs.getString("school_yesr"));
		    	vNext.add(rs.getString("term_id"));
		    	vNext.add(rs.getString("course_name"));
		    	vNext.add(rs.getString("course_credit"));
		    	data.add(vNext);
		    	dtmTP.setDataVector(data, columName);
		    	jtTeachPlan.setModel(dtmTP);	
		    }
		    dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ѡ��ѧԺ������Ӧרҵ
		jcbTP_xueyuan.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					System.out.println(jcbTP_xueyuan.getSelectedItem());
					try {
						jcbTP_zhuanye.removeAllItems();
						jcbTP_zhuanye.addItem("--��ѡ������--");
						dbconn = linkDB.lindDataBase();
						Statement stmtMajor = dbconn.createStatement();//����SQL�������
					    ResultSet rsMajor = stmtMajor.executeQuery("select distinct major_name from Major " +
					    		"where academy_code=(select academy_code from Academy where academy_name='"+jcbTP_xueyuan.getSelectedItem()+"')"); //����ҳ����ݵ�������
					    while(rsMajor.next()){
					    	jcbTP_zhuanye.addItem(rsMajor.getString("major_name"));
					    }
					    dbconn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		//ѡ��ѧ�������Ӧѧ��
		jcbTP_xuenian.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					jcbTP_xueqi.removeAllItems();
					jcbTP_xueqi.addItem("--��ѡ������--");
					try {
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();//����SQL�������
						ResultSet rsTerm = stmt.executeQuery("select distinct term_id from SchoolYear " +
							"where school_yesr='"+jcbTP_xuenian.getSelectedItem()+"'"); //����ҳ����ݵ�������
						System.out.println(jcbTP_xuenian.getSelectedItem());
						while(rsTerm.next()){
							jcbTP_xueqi.addItem(rsTerm.getString("term_id"));
						}
						dbconn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		//ѡ��γ̳�����Ӧѧ��
		jcbTP_kecheng.addItemListener(new ItemListener() {
					
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					//jcbTP_kecheng.removeAllItems();
					//jcbTP_kecheng.addItem("--��ѡ������--");
					jlTP_xuefen.setText("ѧ�֣�");
					try {
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();//����SQL�������
						ResultSet rsCredit = stmt.executeQuery("select distinct course_credit from Course " +
								"where course_name='"+jcbTP_kecheng.getSelectedItem()+"'"); //����ҳ����ݵ�������
						System.out.println(jcbTP_kecheng.getSelectedItem());
						while(rsCredit.next()){
							jlTP_xuefen.setText("ѧ�֣�"+rsCredit.getString("course_credit"));
						}
						dbconn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		
		//����
		jbTP_tianjia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(jcbTP_xueyuan.getSelectedItem()==str0)
						JOptionPane.showMessageDialog(null, "��ѡ����Ӧ��ѧԺ��","��ʾ",JOptionPane.ERROR_MESSAGE);
					else if(jcbTP_zhuanye.getSelectedItem()==str0)
						JOptionPane.showMessageDialog(null, "��ѡ����Ӧ��רҵ��","��ʾ",JOptionPane.ERROR_MESSAGE);
					else if(jcbTP_xuenian.getSelectedItem()==str0)
						JOptionPane.showMessageDialog(null, "��ѡ����Ӧ��ѧ�꣡","��ʾ",JOptionPane.ERROR_MESSAGE);
					else if(jcbTP_xueqi.getSelectedItem()==str0)
						JOptionPane.showMessageDialog(null, "��ѡ����Ӧ��ѧ�ڣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
					else if(jcbTP_kecheng.getSelectedItem()==str0)
						JOptionPane.showMessageDialog(null, "��ѡ����Ӧ�Ŀγ̣�","��ʾ",JOptionPane.ERROR_MESSAGE);
					else if(jcbTP_xueyuan.getSelectedItem()!=str0&&jcbTP_zhuanye.getSelectedItem()!=str0&&jcbTP_xuenian.getSelectedItem()!=str0
							&&jcbTP_xueqi.getSelectedItem()!=str0&&jcbTP_kecheng.getSelectedItem()!=str0){
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();//����SQL�������
						String strAdd = "insert into SchoolYear(course_code,major_code,school_yesr,term_id) " +
							"select distinct a.course_code,b.major_code,c.school_yesr, c.term_id from Course a, Major b, SchoolYear c " +
							"where a.course_code=(select course_code from Course where course_name='"+jcbTP_kecheng.getSelectedItem()+"') " +
							"and b.major_code=(select major_code from Major where major_name='"+jcbTP_zhuanye.getSelectedItem()+"') " +
							"and c.school_yesr='"+jcbTP_xuenian.getSelectedItem()+"' and c.term_id='"+jcbTP_xueqi.getSelectedItem()+"'";
						stmt.executeUpdate(strAdd);
						dbconn.close();
						ShuaXin();
						JOptionPane.showMessageDialog(null, "�ɹ����ӣ�","������ʾ",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "��ѡ��ÿγ̣������ظ�ѡ��","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	//ˢ�±�������
	public void ShuaXin(){
		try {
			dbconn = linkDB.lindDataBase();
		    Statement stmt = dbconn.createStatement();//����SQL�������
		    ResultSet rs = stmt.executeQuery("select distinct a.academy_name, b.major_name, c.school_yesr, c.term_id, d.course_name,d.course_credit " +
		    		"from Academy a, Major b, SchoolYear c, Course d where a.academy_code=b.academy_code " +
		    		"and b.major_code=c.major_code and c.course_code=d.course_code");
		    DefaultTableModel dtmTP = new DefaultTableModel();
		    Vector data = new Vector();
		    while(rs.next()){
		    	Vector vNext = new Vector();
		    	vNext.add(rs.getString("academy_name"));
		    	vNext.add(rs.getString("major_name"));
		    	vNext.add(rs.getString("school_yesr"));
		    	vNext.add(rs.getString("term_id"));
		    	vNext.add(rs.getString("course_name"));
		    	vNext.add(rs.getString("course_credit"));
		    	data.add(vNext);
		    	dtmTP.setDataVector(data, columName);
		    	jtTeachPlan.setModel(dtmTP);	
		    }
		    dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}