
public class winston implements Comparable<winston> {
double problity ;
String word;


@Override
public String toString() {
	return this.word +" "+this.problity ;
}
public winston() {
	// TODO Auto-generated constructor stub
}
public winston(double problity, String word) {
	super();
	this.problity = problity;
	this.word = word;
}

@Override
public int compareTo(winston o) {
	if(this.problity==o.problity)
	return 0;
	else
		if(this.problity>o.problity)
			return -1;
		else
			return 1;
}

}
