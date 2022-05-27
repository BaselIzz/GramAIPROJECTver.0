
public class Model {
private int  wordcount; 
private double wordsproblity ;
private  int gramtype;
public int getWordcount() {
	return wordcount;
}
public void setWordcount(int wordcount) {
	this.wordcount = wordcount;
}
public double getWordsproblity() {
	return wordsproblity;
}
public void setWordsproblity(double wordsproblity) {
	this.wordsproblity = wordsproblity;
}
@Override
public String toString() {
	return  wordcount + ""+ wordsproblity ;
}

public Model(int wordcount, double wordsproblity, int gramtype) {
	super();
	this.wordcount = wordcount;
	this.wordsproblity = wordsproblity;
	this.gramtype = gramtype;
}
public int getGramtype() {
	return gramtype;
}
public void setGramtype(int gramtype) {
	this.gramtype = gramtype;
}



}
