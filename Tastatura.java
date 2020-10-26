package prm;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JTextField;


public class Tastatura {
	public Panel ploca;
	private JTextField unos;
	public Funkcija f; 
	private JButton[] cifre= new JButton[10];
	private JButton[] operacije=new JButton[20];
	private JButton promenljiva;
	private JButton tacka;
//	private boolean unisti=false;
	@SuppressWarnings("unused")
	private boolean prethOp=false; //da li je poslednje uneta operacija
	private int brZagrada=0; //broj otvorenih zagrada
	
	
	private integrator intgr;
	
	public JTextField setUnos(JTextField l) {
		unos=l;
		return unos;
	}
	
	
	public void reset() {
		f=null;
	}
	
	
	private void ploProm(Panel ploca) {
		BrojAkcija osluskivac=new BrojAkcija();
		promenljiva=new JButton("x");
		promenljiva.addActionListener(osluskivac);
		promenljiva.setEnabled(true);
		promenljiva.setVisible(true);
		ploca.add(promenljiva);
	}

	private void ploCifre(Panel ploca) {
		BrojAkcija osluskivac= new BrojAkcija();
		for(int i=0; i<10; i++) {
			ploca.add(cifre[i]=new JButton(Integer.toString(i)));
			cifre[i].addActionListener(osluskivac);
			cifre[i].setEnabled(true);
			//preth=false;
		}	
		tacka=new JButton(".");
		tacka.addActionListener(osluskivac);
		tacka.setEnabled(true);
		tacka.setVisible(true);
		ploca.add(tacka);
	}
	
	private void ploOperatori(Panel ploca) {
		String[] ozn= {"-","+", "*", "/", "^", 
						"√", "log", "ln", "e^",
						"sin", "cos", "tg", "ctg", 
						"asin", "acos", "atan", "acot", 
						"π", "(", ")"};
		OperAkcija osluskivac=new OperAkcija();
		for(int i=0; i<20; i++) {
			ploca.add(operacije[i]=new JButton(ozn[i].toString()));
			operacije[i].addActionListener(osluskivac);
			if(i<5) operacije[i].setEnabled(false);
			else if(i==19) 	operacije[i].setEnabled(brZagrada>0);
			else operacije[i].setEnabled(true);
		}
		 operacije[0].setEnabled(true);
	}

	
	
	//enable dugmica za funkciju
	public void inicijalizacija() {
		for (int i = 0; i < 10; i++)
			cifre[i].setEnabled(true);
		for (int i = 0; i < 20; i++) {
			if (i < 5)
				operacije[i].setEnabled(false);
			else if (i == 19)
				operacije[i].setEnabled(brZagrada > 0);
			else
				operacije[i].setEnabled(true);
		}
	}
	
	
	public void inicijalizacijaG(String staro) {
		for(int i=0; i<10; i++) cifre[i].setEnabled(true);
		tacka.setEnabled(staro.length()==0);
		for(int i=0; i<20; i++)
			if (i==0 || i==17) operacije[i].setEnabled(true);
			else operacije[i].setEnabled(false);
	}
	
	//enable dugmica za preciznost 
	public void inicijalizacijaP() {
		for (int i = 0; i < 10; i++)
			cifre[i].setEnabled(true);
		tacka.setEnabled(true);
		for (int i = 0; i < 20; i++)
			operacije[i].setEnabled(false);
	}
	
		
		
		
	private class OperAkcija implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent dog) {
			String staro;
			String op = dog.getActionCommand();

			switch (intgr.lbl) {
			case 0: {
				staro = intgr.DonjaGranica.getText();
				staro = staro + op;
				intgr.DonjaGranica.setText(staro);
				if (op == "-") {
					intgr.mdg = true;
					operacije[12].setEnabled(false);
				}

			}
				;
				break;

			case 1: {
				staro = intgr.GornjaGranica.getText();
				staro = staro + op;
				intgr.GornjaGranica.setText(staro);
				if (op == "-") {
					intgr.mgg = true;
					operacije[12].setEnabled(false);
				}
			}
				;
				break;

			case 3: {
				staro = unos.getText();
				switch(op) {
				case "sin": f=new Sin(); break;
				case "cos": f=new Cos(); break;
				case "tan": f=new Tan(); break;
				case "cot": f=new Cot(); break;
				case "asin": f=new Asin(); break;
				case "acos": f=new Acos(); break;
				case "atan": f=new Atan(); break;
				case "acot": f=new Acot(); break;
				case "ln": f=new Ln(); break;
				case "log": f=new Log(); break;
				case "e^": f=new E2(); break;
				case "√": f=new Root(); break;
				
				}
				
				/*
				if (op == "(")
					brZagrada++;
				if (op == ")")
					brZagrada--;

				// enable dugmica za operacije
				if (op == "π" || op == ")") {
					for (int i = 0; i < 19; i++)
						operacije[i].setEnabled(true);
					operacije[19].setEnabled(brZagrada > 0);
				} else
					for (int i = 0; i < 20; i++) {
						if (i < 5)
							operacije[i].setEnabled(false);
						else if (i == 19)
							operacije[i].setEnabled(false);
						else
							operacije[i].setEnabled(true);
					}

				tacka.setEnabled(false);
			*/
				unos.setText(staro + " " + op+" x");
				prethOp = true;
			};
				break;

			default: {
			}
			}
		}
	}
		
	
	
	
	
	private class BrojAkcija implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent dog) {
			
			String cif=dog.getActionCommand();
			String staro;

			switch(intgr.lbl) {
			case 0: {
				staro=intgr.DonjaGranica.getText();
				staro=staro+cif;
				intgr.DonjaGranica.setText(staro);
		
			}; break;
			
			case 1:{
				staro=intgr.GornjaGranica.getText();
				staro=staro+cif;
				intgr.GornjaGranica.setText(staro);
			}; break;
			
			case 2:{
				staro=intgr.preciznost.getText();
				staro=staro+cif;
				intgr.preciznost.setText(staro);
			}; break;
			
			case 3:{
				staro=unos.getText();
				unos.setText(staro+cif+" x");
				for(int i=0; i<20; i++) {
					if (cif==".") operacije[i].setEnabled(false);
					else operacije[i].setEnabled(true);
				}
				operacije[19].setEnabled(brZagrada>0);
				if (cif=="." || cif=="x") tacka.setEnabled(false);
				else tacka.setEnabled(true);
			
			
				prethOp=false;

		
			}; break;
		}
	}
	}
	
	
	
	
	protected double rGran(String staro) {
		String s=staro, s1=""; char c;
		double d=0;
		if(!s.contains("π")) d=Double.parseDouble(s);
		else 
		{
			for(int i=0; i<s.length(); i++)
				if ((c=s.charAt(i))!='π') s1+=c;
			if (s1.length()!=0)
				d=Double.parseDouble(s1)*Math.PI;
			else d=Math.PI;
		}
		return d;
	}
	
	
	

	public Tastatura(integrator i) {
		
		ploca=new Panel();
		ploca.setLayout(new GridLayout(8,4));
		
		ploProm(ploca);
		ploCifre(ploca);
		ploOperatori(ploca);
		
		intgr=i;
	}
	
	
	
	
	
	
}
