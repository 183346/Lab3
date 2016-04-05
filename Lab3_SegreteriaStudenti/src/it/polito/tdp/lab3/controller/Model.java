package it.polito.tdp.lab3.controller;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polito.tdp.lab3.DAO.StudentiDAO;

public class Model {
	
	Set<Integer> matricolePerCorso = new TreeSet<Integer>();
	List<String> output = new LinkedList<String>();
	List<String> corsiPerMatricola = new LinkedList<String>();

	
	
	public boolean controlloInput(String text) {
		// Pattern p = Pattern.compile("^[a-z,A-Z]+$");
				//pattern per imserimento di numeri reali
				 Pattern p = Pattern.compile("^\\d{6}$");
				 
				 Matcher m = p.matcher(text);
				 boolean b = m.matches();
		return b;
	}

	public String[] getNomeCognome(int matricola) {
		StudentiDAO dao = new StudentiDAO();
		String [] nomeCognome = new String[2];
		nomeCognome=dao.getNomeCognome(matricola);
				
		return nomeCognome;
	}

	public String cercaStudentiperCorso(Corso value) {
		String result="";
		this.corsiPerMatricola.clear();
		this.matricolePerCorso.clear();
		
		String chiave=value.getCodins();
		StudentiDAO dao = new StudentiDAO();
		matricolePerCorso=dao.getMatricolePerCorso(chiave);
		String tempo="";
		for(Integer matricola :matricolePerCorso){
			Studente s=dao.getNomeCognomeCompleto(matricola);
			//per matricola
			String sMatricola=""+s.getMatricola();
			int lunghezza1=sMatricola.length();
			for (int i1=0;i1<7-lunghezza1;i1++){
				tempo=tempo+" ";
			}
			String fineMatricola=s.getMatricola()+tempo;
			tempo="";
			// fine matricola
			//inizio cognome
			String sMatricola1=s.getCognome();
			int lunghezza2=sMatricola1.length();
			for (int i2=lunghezza2+1;i2<50;i2++){
				tempo=tempo+" ";
			}
			
			String fineCognome=s.getCognome()+tempo;
			tempo="";
			
			//fine cognome
			//inizio nome
			String sMatricola2=""+s.getNome();
			int lunghezza3=sMatricola2.length();
			for (int i3=0;i3<50-lunghezza3;i3++){
				tempo=tempo+" ";
			}
			String fineNome=s.getNome()+tempo;
			tempo="";
			// fine nome
			//inizio cds
			String sMatricola3=""+s.getCds();
			int lunghezza4=sMatricola3.length();
			for (int i4=0;i4<50-lunghezza4;i4++){
				tempo=tempo+" ";
			}
			String fineCds=s.getCds()+tempo;
			tempo="";
			//fine cds
			
			
			//output.add(""+String.format("%6d",s.getMatricola()));
			//output.add("  ");
			output.add(fineMatricola);
			//output.add("  ");
			output.add(String.format("%-50s",fineCognome));
			//output.add("  ");
			output.add(String.format("%-50s", fineNome));
			output.add(fineCds);
			
			output.add("\n");
			//StringBuilder sb = new StringBuilder();
			//Formatter formatter = new Formatter(sb, Locale.US);
			//result = result+String.format("%1$d  %2$-20s  %3$-20s  %4$s\n", s.getMatricola(),s.getCognome(),s.getNome(),s.getCds());
			//result = result+s.toString()+"\n";
		}
		
		for(int y=0;y<output.size();y++){
			result=result+output.get(y);
		}
		
		
		return result;
	}

	public String cercaCorsiPerStudente(int matricola) {
		String result="";
		this.corsiPerMatricola.clear();
		this.matricolePerCorso.clear();
		StudentiDAO dao = new StudentiDAO();
		corsiPerMatricola=dao.getSingleCorse(matricola);
		for(String s1:this.corsiPerMatricola){
			Corso c=dao.getCorso(s1);
			result = result+c.getCodins()+"  "+c.getCrediti()+" "+ c.getNome()+" pd:"+c.getPd()+"\n";		}
		
		
		
		
		
		
		return result;
	}

	public String controllaMC(int matricola, String corso) {
		String result="";
		StudentiDAO dao = new StudentiDAO();
		Studente s =dao.getNomeCognomeCompleto(matricola);
		
		
		Corso c= dao.getCorso(corso);
		
		boolean controllo=dao.getControllo(matricola, corso);
		if(controllo){result=s.getCognome()+" "+s.getNome()+"  è iscritto al corso   "+c.getNome()+"\n";}else
		{result=s.getCognome()+" "+s.getNome()+"  non è iscritto al corso   "+c.getNome()+"\n";}
		
		
		return result;
	}

	public boolean controllaMC1(int matricola, String corso) {
		boolean verifica=false;
		StudentiDAO dao = new StudentiDAO();
		Studente s =dao.getNomeCognomeCompleto(matricola);
		
		
		Corso c= dao.getCorso(corso);
		
		boolean controllo=dao.getControllo(matricola, corso);
		if(controllo){verifica=true;}else
		{verifica=false;}
		
		
		return verifica; 
		
		
		
		
		
		
		
	}

	public String inscrivi(int matricola, String codins) {
		String result="";
		StudentiDAO dao = new StudentiDAO();
		boolean verifica=dao.inscrivi(matricola, codins);
		if(verifica){result="studente iscritto";}else{result="studente non iscritto per problemi di sistema";}
		
		
		
		
		return result;
	}

}
