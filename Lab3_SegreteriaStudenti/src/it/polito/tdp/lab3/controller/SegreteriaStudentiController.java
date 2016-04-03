package it.polito.tdp.lab3.controller;


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polito.tdp.lab3.DAO.StudentiDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class SegreteriaStudentiController {
	
	private Model model;
	List<Corso> corsi = new LinkedList<Corso>();
	

    public void setModel(Model model) {
    	StudentiDAO dao = new StudentiDAO();
    	Corso c = new Corso();
    	corsi=dao.getAllCorse();
    	Collections.sort(corsi);
    	c.setCodins("X");
    	corsi.add(0,c);
    	this.comboCorso.getItems().addAll(corsi);
		this.model = model;
	}

	@FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnCercaNome;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnReset;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCognome;

    @FXML
    void doReset(ActionEvent event) {
    	this.txtMatricola.clear();
    	this.txtResult.clear();
    	this.txtNome.clear();
    	this.txtCognome.clear();
    	this.comboCorso.getSelectionModel().clearSelection();
    	
    	//this.comboCorso.getItems().addAll(corsi);
    	

    }

    @FXML
    void doCercaNome(ActionEvent event) {
    	this.txtResult.clear();
    	String result="";
    	boolean controllo=model.controlloInput(this.txtMatricola.getText());
    	if(!controllo){this.txtResult.appendText("formato matricola errato");return;}
    	int matricola=Integer.parseInt(this.txtMatricola.getText());
    	String [] nomeCognome = new String[2];
    	nomeCognome= model.getNomeCognome(matricola);
    	this.txtNome.setText(nomeCognome[0]);
    	this.txtCognome.setText(nomeCognome[1]);
    	if(nomeCognome[0]==null){this.txtResult.appendText("nessun risultato:matricola inesistente");return;}
    	

    }

    @FXML
    void doCerca(ActionEvent event) {
    	
    	   	
    	this.txtResult.clear();
    	String result="";
    	//if(this.comboCorso.getValue().getCodins().compareTo(" ")==0){this.comboCorso.getSelectionModel().clearSelection();return;}
    	if(this.txtMatricola.getText().compareTo("")==0 && (this.comboCorso.getValue()==null ||this.comboCorso.getValue().getCodins().compareTo("X")==0 )){
    		this.txtResult.appendText("occorre selezionare un corso o introdurre una matricola");
    		this.comboCorso.getSelectionModel().clearSelection();return;	
    	}
    	if(this.txtMatricola.getText().compareTo("")!=0 && this.comboCorso.getValue()!=null && this.comboCorso.getValue().getCodins().compareTo("X")!=0){
    		boolean controllo=model.controlloInput(this.txtMatricola.getText());
        	if(!controllo){this.txtResult.appendText("formato matricola errato");return;}
        	int matricola=Integer.parseInt(this.txtMatricola.getText());
        	String [] nomeCognome = new String[2];
        	nomeCognome= model.getNomeCognome(matricola);
        	if(nomeCognome[0]==null){this.txtResult.appendText("matricola inesistente");this.comboCorso.getSelectionModel().clearSelection();return;}
        	Corso corso=this.comboCorso.getValue();
        	result=model.controllaMC(matricola,corso.getCodins());
        	if(result.compareTo("")==0){this.txtResult.appendText("nessun risultato");return;}
        	this.txtResult.appendText(result);
    		return;
    		
    		
    		
    			
    	}
    	if(this.txtMatricola.getText().compareTo("")==0 && this.comboCorso.getValue()!=null){
    	result=model.cercaStudentiperCorso(this.comboCorso.getValue());
    	if(result.compareTo("")==0){this.txtResult.appendText("nessun risultato");return;}
    	this.txtResult.appendText(result);}
    	if(this.txtMatricola.getText().compareTo("")!=0 &&( this.comboCorso.getValue()==null||this.comboCorso.getValue().getCodins().compareTo("X")==0 ))
    	{
    		boolean controllo=model.controlloInput(this.txtMatricola.getText());
        	if(!controllo){this.txtResult.appendText("formato matricola errato");return;}
        	int matricola=Integer.parseInt(this.txtMatricola.getText());
    		result=model.cercaCorsiPerStudente(matricola);
    		if(result.compareTo("")==0){this.txtResult.appendText("nessun risultato");return;}
        	this.txtResult.appendText(result);
    	}
    	
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	String result="";
    	if(this.txtMatricola.getText().compareTo("")!=0 && this.comboCorso.getValue()!=null && this.comboCorso.getValue().getCodins().compareTo("X")!=0){
    	//controllo matricola
    		boolean controllo=model.controlloInput(this.txtMatricola.getText());
        	if(!controllo){this.txtResult.appendText("formato matricola errato");return;}
        	int matricola=Integer.parseInt(this.txtMatricola.getText());
        	String [] nomeCognome = new String[2];
        	nomeCognome= model.getNomeCognome(matricola);
        	this.txtNome.setText(nomeCognome[0]);
        	this.txtCognome.setText(nomeCognome[1]);
        	if(nomeCognome[0]==null){this.txtResult.appendText("nessun risultato:matricola inesistente");return;}
        	Corso corso=this.comboCorso.getValue();
        	boolean verifica=model.controllaMC1(matricola,corso.getCodins());
        	if(verifica){this.txtResult.appendText("Studente già inscritto a questo corso");return;}
        	result=model.inscrivi(matricola,corso.getCodins());
        	if(result.compareTo("")==0){this.txtResult.appendText("errore inserimento dati");return;}
        	this.txtResult.appendText(result);

    }
    	}
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        /*StudentiDAO dao = new StudentiDAO();
    	Corso c = new Corso();
    	corsi=dao.getAllCorse();
    	Collections.sort(corsi);
    	corsi.add(0,c);
    	this.comboCorso.getItems().addAll(corsi);*/

    }

}
