package expStudent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainInfo extends JFrame {
	private Student ss= new Student();
	private StudentInfo si = new StudentInfo();
	private Course course = new Course();
	private StudentChooseCourse scc = new StudentChooseCourse();
	private StudentScore sscore = new StudentScore();
	private TeachPlan tp = new TeachPlan();
	
	private CardLayout cardLayout = new CardLayout(); //����һ������СΪ 0 ���¿�Ƭ����
	private JPanel jpMain = new JPanel(cardLayout); //�����
	private JPanel jpZhuJieMian=null, jpStudent=null,jpCourse=null,jpTeachPlan=null,jpStudentCCourse=null,
			jpStudentScore=null,jpStudentR=null,jpStudentP=null;
	private JMenuBar jMB = new JMenuBar(); //�˵���
	private JMenu jmMenu, jmMenu_StuRP; //�˵�ѡ��
	private JMenuItem jmiStudent,jmiCourse,jmiTeachPlan,jmiStudentCCourse,jmiStudentScore;
	private JMenuItem jmiStudentR, jmiStudentP;
	private ImageIcon icon = new ImageIcon("F:\\���ݿ�\\������\\ʵ���� java���ݿ⿪��\\StudentMS\\src\\Img\\icon.jpg");
	private JLabel jlIcon = new JLabel(); 
	
	public MainInfo() {
		super();
		this.setTitle("��Ϣ����ϵͳ1.0");
		this.setSize(900, 600);
		Toolkit tool = Toolkit.getDefaultToolkit(); //��ȡ��Ļ��С
		Dimension dim = tool.getScreenSize(); //��Ż�ȡ��Ļ�Ĵ�С
		int swidth = (int)dim.getWidth(); //���߶�ת��Ϊint��
		int sheight = (int)dim.getHeight();
		this.setLocation((swidth-900)/2,(sheight-600)/2); //����
		
		jpZhuJieMian = new JPanel();
		jlIcon.setIcon(icon);
		jpZhuJieMian.add(jlIcon);
		jpMain.add(jpZhuJieMian, "������ͼƬ");
		cardLayout.show(jpMain, "������ͼƬ");
		
		jmMenu = new JMenu("�����˵�");
		jmiStudent = new JMenuItem("ѧ����Ϣ");
		jmiCourse = new JMenuItem("�γ���Ϣ");
		jmiTeachPlan = new JMenuItem("��ѧ�ƻ�");
		jmiStudentCCourse = new JMenuItem("ѧ��ѡ��");
		jmiStudentScore = new JMenuItem("ѧ���ɼ�");
		jmMenu_StuRP = new JMenu("ѧ������");
		jmiStudentR = new JMenuItem("��������");
		jmiStudentP = new JMenuItem("�ͷ�����");
		jmMenu.add(jmiStudent);
		jmMenu.add(jmiCourse);
		jmMenu.add(jmiTeachPlan);
		jmMenu.add(jmiStudentCCourse);
		jmMenu.add(jmiStudentScore);
		jmMenu.add(jmMenu_StuRP);
		jmMenu_StuRP.add(jmiStudentR);
		jmMenu_StuRP.add(jmiStudentP);
		jMB.add(jmMenu);
		this.setJMenuBar(jMB); //���ò˵���
		
		jpStudent = new JPanel();
		jpCourse = new JPanel();
		jpStudentCCourse = new JPanel();
		
		jmiStudent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("ѧ����Ϣ");
				ss.StuLoading();
				jpStudent.add(ss.jpSelect,"North");
				jpStudent.add(ss.jspSelect);
				jpStudent.add(si.jpStuInsert);
				jpMain.add(jpStudent, "ѧ����Ϣģ��");
				cardLayout.show(jpMain, "ѧ����Ϣģ��");
				MainInfo.this.setVisible(true);
			}
		});
		//�γ̹���
		jmiCourse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("�γ̹���");
				course.Loading();
				jpCourse.add(course.jpAddCourse,"North");
				jpCourse.add(course.jspCourse);
				jpMain.add(jpCourse, "�γ̹���ģ��");
				cardLayout.show(jpMain, "�γ̹���ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		
		//��ѧ�ƻ�
		jmiTeachPlan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("��ѧ�ƻ�");
				jpTeachPlan = new JPanel();
				jpTeachPlan.add(tp.jpTeachPlan);
				jpTeachPlan.add(tp.jspTeachPlan);
				jpMain.add(jpTeachPlan, "��ѧ�ƻ�����ģ��");
				cardLayout.show(jpMain, "��ѧ�ƻ�����ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		
		//ѧ��ѡ�ι���
		jmiStudentCCourse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("ѧ��ѡ��");
				jpStudentCCourse.add(scc.jpSCC,"North");
				jpStudentCCourse.add(scc.jspSCC);
				jpStudentCCourse.add(scc.jpXuanKe);
				jpMain.add(jpStudentCCourse, "ѧ��ѡ�ι���ģ��");
				cardLayout.show(jpMain, "ѧ��ѡ�ι���ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		//ѧ���ɼ�����
		jmiStudentScore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpStudentScore = new JPanel();
				MainInfo.this.setTitle("ѧ���ɼ�");
				jpStudentScore.add(sscore.jpChaXun,"North");
				jpStudentScore.add(sscore.jspStudentScore);
				jpMain.add(jpStudentScore,"ѧ���ɼ�����ģ��");
				cardLayout.show(jpMain, "ѧ���ɼ�����ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		//ѧ����������
		jmiStudentR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("ѧ������");
				jpStudentR = new JPanel();
				StudentR sR = new StudentR();
				jpStudentR.add(sR.jpStuR, "North");
				jpStudentR.add(sR.jspStu_R);
				jpMain.add(jpStudentR, "ѧ����������ģ��");
				cardLayout.show(jpMain, "ѧ����������ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		//ѧ���ͷ�����
		jmiStudentP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainInfo.this.setTitle("ѧ���ͷ�");
				jpStudentP = new JPanel();
				StudentP sp = new StudentP();
				jpStudentP.add(sp.jpStuP,"North");
				jpStudentP.add(sp.jspStu_P);
				jpMain.add(jpStudentP,"ѧ���ͷ�����ģ��");
				cardLayout.show(jpMain, "ѧ���ͷ�����ģ��");
				MainInfo.this.setVisible(true);
			}
		});
		
		//����ͼ��
		String path="/Img/RGB.jpg";
		try{
			Image img = ImageIO.read(this.getClass().getResource(path));
			this.setIconImage(img);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		this.add(jpMain);
		this.setResizable(false); //�������
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MainInfo();
	}
}