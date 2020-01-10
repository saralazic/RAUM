package raum;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class integrator extends JFrame 
{
	protected TextArea prikaz = new TextArea();
	protected JTextField preciznost = new JTextField("",6);
	protected JTextField GornjaGranica = new JTextField("",6);
	protected JTextField DonjaGranica = new JTextField("",6);
	protected JTextField fja = new JTextField("",6);
	protected JTextField rezultat = new JTextField("",6);
	private Checkbox radio1, radio2, radio3;
	private JButton radi, restart;
	private  Tastatura t=new Tastatura(this);
	
	private static int ROMBERG=0, SIMPSON=1, TRAPEZNA=2;
	private int Metod;
	Panel ploca = new Panel();
	
	protected double dole, gore;
	protected int prec;
	protected boolean tdg, tgg, mdg, mgg; 
	protected int dgs, ggs;
	
	@SuppressWarnings("unused")
	private String dg, gg, p;//donja granica, gornja granica, preciznost
	protected static int DGr=0, GGr=1, Prec=2 ,Fn=3;
	protected int lbl;

	
	
	public integrator()
	{
		super("Numericka integracija");
		setBounds(200,200,850,500);
		popuniProzor();
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
//				if(akt!=null)
//					akt.zavrsi(); 
				dispose(); 
				}
		});
		dole=0; 
		gore=0;
		tdg=tgg=false;
	}
	
	public void IzborMetoda(Panel pl2) {
		Panel pomocni5 = new Panel();
		Panel pomocni6 = new Panel();
		Panel pomocni7 = new Panel();
		Panel pomocni8 = new Panel();
		pomocni6.setLayout(new GridLayout(1, 2));
		pomocni7.setLayout(new GridLayout(1, 2));
		pomocni8.setLayout(new GridLayout(1, 2));
		pomocni5.setLayout(new GridLayout(1, 2));
		
		
		Panel ppomocni5 = new Panel();
		Panel ppomocni6 = new Panel();
		Panel ppomocni7 = new Panel();
		Panel ppomocni8 = new Panel();
		
		pl2.setLayout(new GridLayout(4, 1));
		
		
		ppomocni7.add(new Label("  Preciznost:", Label.LEFT));
		ppomocni8.add(preciznost);
		pomocni5.add(ppomocni5);
		pomocni6.add(ppomocni6);
		pomocni7.add(ppomocni7);
		pomocni8.add(ppomocni8);
		
		pomocni5.add(new Label("Metoda integracije:", Label.LEFT));
		CheckboxGroup grupa = new CheckboxGroup();
		radio1 = new Checkbox("Trapezna formula",grupa,false);
		pomocni6.add(radio1);
		radio2 = new Checkbox("Simpsonova formula",grupa,false);
		pomocni7.add(radio2);
		radio3 = new Checkbox("Rombergova metoda",grupa,true);
		pomocni8.add(radio3);
		
		OsluskivacIzbora osluskivac=new OsluskivacIzbora();
		
		radio1.addItemListener(osluskivac);
		radio2.addItemListener(osluskivac);
		radio3.addItemListener(osluskivac);
		
		pl2.add(pomocni5);
		pl2.add(pomocni6);
		pl2.add(pomocni7);
		pl2.add(pomocni8);

		ploca.add(pl2);
	}
		
	private class OsluskivacIzbora implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent dog) {
			Checkbox izvor=(Checkbox)dog.getSource();
			String s=izvor.getLabel();
			switch(s) {
				case "Trapezna formula": Metod=TRAPEZNA; break;
				case "Simpsonova formula": Metod=SIMPSON; break;
				case "Rombergova metoda": Metod=ROMBERG; break;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void PoljaZaUnos(Panel pomocni3) {
		Color c2=new Color(254,255,205);

		
		Panel pomocni1 = new Panel();
		Panel pomocni2 = new Panel();
		
		Panel panel1 = new Panel();
		panel1.setLayout(new GridLayout(1,2));

		
		Panel pl1 = new Panel();
		Panel ppl1 = new Panel();
		Panel ppl2 = new Panel();
		Panel IP = new Panel();
		ppl2.setLayout(new GridLayout(1,3));

		Panel ppl3 = new Panel();
		IP.setLayout(new GridLayout(1,2));
		ImagePanel imageP = new ImagePanel();
		IP.setBackground(c2);
		IP.add(new Label("Integral: ", Label.RIGHT));
		IP.add(imageP, IP.RIGHT_ALIGNMENT);
	
		
		panel1.add(IP);
	
		
		ppl1.setLayout(new GridLayout(1,2));
		ppl1.add(GornjaGranica);
		ppl1.add(new Label("                                                                                                                                             ", Label.LEFT));
		
		ppl2.add(t.setUnos(fja));
		ppl3.setLayout(new GridLayout(1,2));
		ppl3.add(DonjaGranica);
		ppl3.add(new Label("                                                                                                                                              ", Label.LEFT));
		
		pl1.setLayout(new GridLayout(5,1));
		pl1.add(ppl1);
		pl1.add(pomocni1);
		pl1.add(ppl2);
		pl1.add(pomocni2);
		pl1.add(ppl3);
		pl1.setBackground(c2);
		
		panel1.add(pl1);
		ploca.add(panel1);
	
		
		Panel pl2 = new Panel();
		IzborMetoda(pl2);
		
		
		//Opcija da kad kliknem na TextField unosim tacno tu vrednost
		GornjaGranica.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t.inicijalizacijaG(GornjaGranica.getText()); //enable odgovarajucih dugmica
				//DODAJ
				lbl=GGr;
			}
		});
		
		DonjaGranica.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t.inicijalizacijaG(DonjaGranica.getText()); //enable odgovarajucih dugmica
				//DODAJ
				lbl=DGr;
			}
		});
		
		preciznost.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t.inicijalizacijaP(); //enable odgovarajucih dugmica
				//DODAJ
				lbl=Prec;
			}	
		});
		
		fja.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t.inicijalizacija(); //enable odgovarajucih dugmica
				//DODAJ
				lbl=Fn;
			}
		});
	}
	
	public void DugmeZaRezultat() {
		radi = new JButton("Izracunaj");
		radi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.f.printfun();
				Double rez;
				rez=0.0;
				String prec=preciznost.getText();
				gore=t.rGran(GornjaGranica.getText());
				dole=t.rGran(DonjaGranica.getText());
				
				switch(Metod) {
					case 0: {
						Romberg r=new Romberg(Double.parseDouble(prec));
						if (gore>dole)
							try {
								rez=r.Racunaj(t.f, gore, dole);
							} catch (Greska e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						else
							try {
								rez=-r.Racunaj(t.f, dole, gore);
							} catch (Greska e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}; 
						case 1:{
							Simpson r=new Simpson(Double.parseDouble(prec));
							if (gore>dole)
								try {
									rez=r.Racunaj(t.f, gore, dole);
								} catch (Greska e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							else
								try {
									rez=-r.Racunaj(t.f, dole,  gore);
								} catch (Greska e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						};
						case 2:{
						//DODAJ TRAPEZNU
						}
					}
				rezultat.setText(""+rez);
			
			String s1;
			s1=(""+gore+"\n"+dole+"\n"+prec);
			prikaz.setText(s1);
			
		}
		});
	}
	
	public void RestartDugme() {
		restart = new JButton("Restartuj");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				t.reset(); 
				
				//enable dugmica na tastaturi se vraca na pocetne vrednosti
			   t.inicijalizacija();
			
				GornjaGranica.setText("");
				DonjaGranica.setText("");
				fja.setText("");
				rezultat.setText("");
				preciznost.setText("");
			}	
		});
	}
		

	private void popuniProzor()
	{

	add(prikaz, "Center");
	
		ploca.setLayout(new GridLayout(1, 4));
	//	Color c=new Color(201,241,227);
		Color c2=new Color(254,255,205);
		ploca.setBackground(c2);
		add(ploca, "North");
		
		Panel pomocni3 = new Panel();

		PoljaZaUnos(pomocni3);	
		
		DugmeZaRezultat();
		
		RestartDugme();
		
		Panel pl3 = new Panel();
		pl3.setBackground(c2);
		pl3.setLayout(new GridLayout(4, 1));
		Panel pll3 = new Panel();
		pll3.add(radi);
		pl3.add(pll3);
		Panel pll2 = new Panel();
		pll2.add(restart);
		pl3.add(pll2);
		pl3.add(pomocni3);
		
		Panel pppl1 = new Panel();
		pppl1.add(new Label("Rezultat:", Label.LEFT));
		pppl1.add(rezultat);
		pl3.add(pppl1);
		ploca.add(pl3);

		add(t.ploca, "West");;
		
	}

	public static void main(String[] args)
	{
		integrator i=new integrator();
		i.rezultat.setText("");
		
	}
	
}
