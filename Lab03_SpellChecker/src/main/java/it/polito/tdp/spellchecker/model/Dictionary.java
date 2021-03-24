package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Dictionary {
	
	private HashSet<String>dizionario;
	private List<RichWord>wrong;
	
	public void setWrong(List<RichWord>wrongs) {
		this.wrong=new ArrayList<RichWord>(wrongs);
	}
	public void loadDictionary(String language) {
		
		dizionario = new HashSet<String>();
		if(language.equals("English")) {
		try {
			FileReader fr = new FileReader("src/main/resources/English.txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word=br.readLine())!=null) {
				dizionario.add(word);
			}
		}catch(IOException e) {
			System.out.println("Errore nella lettura del file");
		}
		}
		if(language.equals("Italian")) {
			try {
				FileReader fr = new FileReader("src/main/resources/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word=br.readLine())!=null) {
					dizionario.add(word);
				}
			}catch(IOException e) {
				System.out.println("Errore nella lettura del file");
			}
		}
		
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		List<RichWord>parole = new ArrayList<RichWord>();
		for(String s : inputTextList) {
			if(dizionario.contains(s)) {
				RichWord r = new RichWord(s,true);
				parole.add(r);
			}
			else {
				RichWord r = new RichWord(s,false);
				parole.add(r);
			}
		}
		return parole;
	}
	
	public List<RichWord> wrongWord(List<RichWord> inputTextList){
		List<RichWord>parole = new ArrayList<RichWord>();
		for(RichWord r:inputTextList) {
			if(r.isCorretta()==false) {
				parole.add(r);
			}
		}
		return parole;
	}

	public String toString() {
		String s = "";
		for(RichWord r: wrong) {
			s=s+r.getParola()+"\n";
		}
		return s;
	}
}
