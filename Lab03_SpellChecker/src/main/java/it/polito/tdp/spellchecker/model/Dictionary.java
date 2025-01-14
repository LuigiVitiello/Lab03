package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	
	private List<String>dizionario;
	private List<RichWord>wrong;
	
	public void setWrong(List<RichWord>wrongs) {
		this.wrong=new ArrayList<RichWord>(wrongs);
	}
	public void loadDictionary(String language) {
		
		dizionario = new LinkedList<String>();
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
	
	/* ESERCIZIO 1
	 * public List<RichWord> spellCheckText(List<String> inputTextList){
	 * 
	 * List<RichWord>parole = new ArrayList<RichWord>(); for(String s :
	 * inputTextList) { if(dizionario.contains(s)) { RichWord r = new
	 * RichWord(s,true); parole.add(r); } else { RichWord r = new RichWord(s,false);
	 * parole.add(r); } } return parole; }
	 */
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		//List<RichWord>parole = new ArrayList<RichWord>();
		List<RichWord>parole = new LinkedList<RichWord>();
		for(String si :inputTextList) {
			boolean trovata=false;
			for(String s :dizionario ) {
			if(s.equals(si)) {
				trovata =true;
				RichWord r = new RichWord(si,trovata); 
				parole.add(r);
			 }
			}
			if(!trovata) {
				RichWord r = new RichWord(si,trovata); 
				parole.add(r);
			}
			
		}
		return parole;
		
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		//List<RichWord>parole = new ArrayList<RichWord>();
		List<RichWord>parole = new LinkedList<RichWord>();
		String meta = dizionario.get((int)dizionario.size()/2);
		for(String si :inputTextList) {
			boolean trovata=false;
			if(si.equals(meta)) {
				trovata =true;
				RichWord r = new RichWord(si,trovata); 
				parole.add(r);
				
			}
			else if(si.compareTo(meta)>0) {
				for(int i=0;i<(int)dizionario.size()/2;i++) {
					if(si.equals(dizionario.get(i))) {
						trovata =true;
						RichWord r = new RichWord(si,trovata); 
						parole.add(r);
						break;
					 }
				}
			}
			else if(si.compareTo(meta)<0) {
				for(int i=(int)dizionario.size()/2;i<dizionario.size();i++) {
					if(si.equals(dizionario.get(i))) {
						trovata =true;
						RichWord r = new RichWord(si,trovata); 
						parole.add(r);
						break;
					 }
				}
			}
			if(!trovata) {
				RichWord r = new RichWord(si,trovata); 
				parole.add(r);
			}	
		}
		return parole;
		//Con arraylist è più lenta
		
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
