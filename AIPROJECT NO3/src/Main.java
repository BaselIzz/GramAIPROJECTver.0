import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;





public class Main extends Application {
public static void main(String[] args) throws IOException {
launch(args);



}

public static String[] ReadFormFile(String fileName)

		throws UnsupportedEncodingException, FileNotFoundException, IOException {
	String[] arabicwords = null ;
	Reader reader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
Scanner br = new Scanner(reader);
	while (br.hasNext()) {
		String a = br.nextLine();
		arabicwords= a.split(" ");
		
	}
	

	
	System.out.println(arabicwords.length);

	br.close();
	return arabicwords;
}


@Override
public void start(Stage ps) throws Exception {
	HashMap<String ,Model> hashgram= new HashMap<String, Model>();
	
	ArrayList<winston> Arrayword=new ArrayList<winston>();
	Path path = Paths.get("Gram.csv");
	Scanner fr = new Scanner(path);
	if(Files.notExists(path)) {
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Gram.csv"), "UTF-8"));
	
	String[] arabicwords =ReadFormFile("datafrominternet.txt");


for (String string : arabicwords) {
	if(hashgram.containsKey(string)) {
		int wordcounter = hashgram.get(string).getWordcount();
		hashgram.put(string,new Model(wordcounter + 1,0,1));
	}
	else
		hashgram.put(string, new Model(1,0,1));
}

for (int i = 0; i < arabicwords.length-1; i++) {
	String biGram = arabicwords[i]+" "+arabicwords[i+1];
	if(hashgram.containsKey(biGram)) {
		int wordcounter = hashgram.get(biGram).getWordcount();
		hashgram.put(biGram,new Model(wordcounter + 1,(wordcounter+1)/(double)hashgram.get(arabicwords[i]).getWordcount(),2));
	}
	hashgram.put(biGram,new Model(1,(1/(double)hashgram.get(arabicwords[i]).getWordcount()),2));
	
}
for (int i = 0; i < arabicwords.length-2; i++) {
	String tryGram = arabicwords[i]+" "+arabicwords[i+1]+" "+arabicwords[i+2];
	if(hashgram.containsKey(tryGram)) {
		int wordcounter = hashgram.get(tryGram).getWordcount();
		hashgram.put(tryGram,new Model(wordcounter + 1,(wordcounter+1)/(double)hashgram.get(arabicwords[i]+" "+arabicwords[i+1]).getWordcount(),3));
	}
	hashgram.put(tryGram,new Model(1,1/(double)hashgram.get(arabicwords[i]+" "+arabicwords[i+1]).getWordcount(),3));
}

for ( String s  : hashgram.keySet() ) {
	
	bw.append( s +","+hashgram.get(s).getWordcount()+","+hashgram.get(s).getWordsproblity()+","+hashgram.get(s).getGramtype()+"\n");
}
bw.close();
	}
	else
	{
		while (fr.hasNext()) {
			String[] str= fr.nextLine().split(",");
			hashgram.put(str[0],new Model(Integer.parseInt(str[1]),Double.parseDouble(str[2]), Integer.parseInt(str[3])));
			
		}
		fr.close();
	}
	
	
AnchorPane AP = new AnchorPane();
TextArea TxTspell= new TextArea();
TextArea TXTwrite = new TextArea();
Button btnspell = new Button("spell ");

AP.getChildren().addAll(TxTspell,TXTwrite,btnspell);
TxTspell.setLayoutX(324);
TxTspell.setLayoutY(45);
AP.setPrefHeight(474);
AP.setPrefWidth(736);
TXTwrite.setLayoutX(321);
TXTwrite.setLayoutY(294);
TxTspell.setPrefHeight(200);
TxTspell.setPrefWidth(351);
TXTwrite.setPrefHeight(166);
TXTwrite.setPrefWidth(378);
btnspell.setLayoutX(232);
btnspell.setLayoutY(120);
Scene scene= new Scene(AP);
ps.setScene(scene);
ps.show();
btnspell.setOnAction(e->{
	TXTwrite.clear();
	
	if(!TxTspell.getText().isEmpty()) {
		String [] s=  TxTspell.getText().split(" ");
		int index =0;
		for (int i = 0; i < s.length; i++) {
		if(s[i].trim().equals("#")) {
			index=i-1;
			
			}
		}
		if(s.length>2) {
		for (String  Str : hashgram.keySet()) 
			if(Str.startsWith(s[index-1]+" "+s[index]) &&(hashgram.get(Str).getGramtype()==3)  ) {
			System.out.println(Str+" "+ hashgram.get(Str).getWordcount()+" "+hashgram.get(Str).getWordsproblity()+"\n");
			String [] strr=Str.split(" ");
			Arrayword.add(new winston(hashgram.get(Str).getWordsproblity(), strr[2]));
			
			}
		}
		if(Arrayword.isEmpty()) {
			for (String  Str : hashgram.keySet()) 
				if(Str.startsWith(s[index]) &&(hashgram.get(Str).getGramtype()==2)  ) {
				System.out.println(Str+" "+ hashgram.get(Str).getWordcount()+" "+hashgram.get(Str).getWordsproblity()+"\n");
				String [] strr=Str.split(" ");
				Arrayword.add(new winston(hashgram.get(Str).getWordsproblity(), strr[1]));
				if(Arrayword.size()==50)
					break;				
				}
			}
			
			if(!Arrayword.isEmpty()) {
			Collections.sort(Arrayword);
		for (int i = 0; i<Arrayword.size(); i++) {
			TXTwrite.appendText(Arrayword.get(i)+"\n");
			if(i==5)
				break;
			
		}
			}
		Arrayword.clear();
	
	
		
	
	}
	
	
});


}

}
