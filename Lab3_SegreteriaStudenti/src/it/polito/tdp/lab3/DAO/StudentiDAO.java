package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polito.tdp.lab3.controller.Corso;
import it.polito.tdp.lab3.controller.Studente;





public class StudentiDAO {
	
	public List<Corso> getAllCorse() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso s = new Corso(
						rs.getString("codins"),
						rs.getInt("crediti"),
						rs.getString("nome"),
						rs.getInt("pd")
						
						);
				corsi.add(s);
			}
			
			return corsi;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public Corso getCorso(String codins) {
		

		final String sql = "SELECT * FROM corso where codins=?";

		Corso c=null;

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				c = new Corso(
						rs.getString("codins"),
						rs.getInt("crediti"),
						rs.getString("nome"),
						rs.getInt("pd")
						
						);
				
			}
			
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public boolean getControllo(int matricola, String codins) {

		final String sql = "SELECT * FROM iscrizione where codins=? and matricola=?";

		int contatore=0;
		boolean controllo=false;

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			st.setInt(2, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				contatore++;
				
			}
			if(contatore==0){controllo=false;}else{controllo=true;}
			
			return controllo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public boolean inscrivi(int matricola, String codins) {

		final String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES("+matricola+",'"+codins+"');";
		/*String sql = "INSERT INTO `rubrica`.`voce` (`nome`, `email`, `telefono`) VALUES ('" + v.getNome() + "', '"
				+					+ v.getEmail() + "', '" + v.getTelefono() + "');";*/
		//INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES (146101, '01PDYPG');

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			//st.setInt(1, matricola);
			//st.setString(2, codins);

			//ResultSet rs = st.executeQuery();
			int res = st.executeUpdate(sql);
			
						conn.close();
			
					if (res == 1)
						return true;
						else
							return false;

			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public List<String> getSingleCorse(int matricola) {

		final String sql = "SELECT distinct codins FROM iscrizione where matricola=?";

		List<String> corsi = new LinkedList<String>();;

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

			String s = rs.getString("codins");
				corsi.add(s);
			}
			
			return corsi;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public String[] getNomeCognome(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";

		String [] nomeCognome = new String[2];

		try {
			Connection conn = StudentiConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				
					nomeCognome[0]=	rs.getString("nome");
					nomeCognome[1]=	rs.getString("cognome");
						
				
			}
			
			return nomeCognome;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
public static void main(String args[]) {
		
		

	}
public Set<Integer> getMatricolePerCorso(String chiave) {
	final String sql = "SELECT distinct matricola FROM iscrizione where codins=?";

	Set<Integer> matricolePerCorso = new TreeSet<Integer>();

	try {
		Connection conn = StudentiConnect.getInstance().getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, chiave);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			int matricola =rs.getInt("matricola");
			matricolePerCorso.add(matricola);
		}
		
		return matricolePerCorso;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
}
public Studente getNomeCognomeCompleto(Integer matricola) {
	;
	final String sql = "SELECT * FROM studente where matricola=?";
	

	try {
		Studente s = null;
		Connection conn = StudentiConnect.getInstance().getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, matricola);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			 s = new Studente(
					rs.getInt("matricola"),
					rs.getString("cognome"),
					rs.getString("nome"),
					rs.getString("cds")
					
					);
					
			
		}
		st.close();
		conn.close();
		return s;	}
	catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
}

	
	

	
	
	
	
	


}
