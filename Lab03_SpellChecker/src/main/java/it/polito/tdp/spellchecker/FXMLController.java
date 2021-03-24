package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary model;
	private String language;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> CBoxLanguage;

    @FXML
    private TextArea TxtInput;

    @FXML
    private Button BtnSpellCheck;

    @FXML
    private TextArea TxtErrori;

    @FXML
    private Label LabelNErrori;

    @FXML
    private Button BtnClear;

    @FXML
    private Label LabelTime;

    void setModel(Dictionary model)
    {
    	this.model=model;
    	CBoxLanguage.getItems().addAll("English","Italian");
    }
    
    @FXML
    void doClearText(ActionEvent event) {
    	this.TxtErrori.clear();
    	this.TxtInput.clear();
    	this.LabelNErrori.setVisible(false);
    	this.LabelNErrori.setText("");
    	this.LabelTime.setVisible(false);
    	this.LabelTime.setText("");

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long inizio = System.nanoTime();
    	language = CBoxLanguage.getValue();
    	model.loadDictionary(language);
    	
    	String input = this.TxtInput.getText();
    	if(input.equals(null)) {
    		this.TxtInput.setText("Errore: inserire un testo da controllare");
    		return;
    	}
    	input = input.toLowerCase();
    	input = input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	
    	String[] arrayInput= input.split(" ");
    	List<String>listInput = new ArrayList<String>();
    	for(int i=0;i<arrayInput.length;i++) {
    		listInput.add(arrayInput[i]);
    	}
   
    	List<RichWord> parole = model.spellCheckText(listInput);
    	List<RichWord> sbagliate = model.wrongWord(parole);
    	model.setWrong(sbagliate);
    	String output=model.toString();
    	long fine = System.nanoTime();
    	long time = (fine-inizio)/(10^9);
    	
    	this.TxtErrori.setText(output);
    	this.LabelNErrori.setVisible(true);
    	this.LabelNErrori.setText("The text contains "+sbagliate.size()+" errors");
    	this.LabelTime.setText("Spell check completed in "+time+ " seconds");
    	this.LabelTime.setVisible(true);
    }

    @FXML
    void initialize() {
        assert CBoxLanguage != null : "fx:id=\"CBoxLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert TxtInput != null : "fx:id=\"TxtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert BtnSpellCheck != null : "fx:id=\"BtnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert TxtErrori != null : "fx:id=\"TxtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert LabelNErrori != null : "fx:id=\"LabelNErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert BtnClear != null : "fx:id=\"BtnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert LabelTime != null : "fx:id=\"LabelTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}