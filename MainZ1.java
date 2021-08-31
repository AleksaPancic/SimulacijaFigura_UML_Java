package aleksa;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class MainZ1 {

	
	interface Grupa{
		public String getTip();
		public void setBoja(String tip);
	}
	
	class Tim implements Grupa{
		public /*static*/ int id;
		public String tip;
		public ArrayList<Figura> figure;
		public ArrayList<Tim> timovi;
		@Override
		public String getTip() {
			return tip;
		}

		@Override
		public void setBoja(String tip) {
			this.tip = tip;
			
		}

		public Tim(int id, String tip, ArrayList<Figura> figure, ArrayList<Tim> timovi) {
			super();
			this.id = id;
			this.tip = tip;
			this.figure = figure;
			this.timovi = timovi;
		}
		public void dodajFiguru(Figura f, int i, int j) {
			f.setI(i);
			f.setJ(j);
			figure.add(f);
		}
		
	}
	class Koordinata{
		private int i, j;

		public Koordinata(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}
	}

	class Figura{
		Koordinata koordinata;
		Moguca_polja strategija1; //na kojoj koordinati moze da se pomeri
		Odabir strategija2; //uzima listu koordinata i nalazi gde se figura pomera
		private int i;
		private int j;
		public Figura(Moguca_polja s1, Odabir s2){
			koordinata = new Koordinata(i,j); 
			strategija1 = s1;
			strategija2 = s2;
		}
		public void setMogucaPolja(Moguca_polja imogucapolja) {
			strategija1 = imogucapolja;
		}
		public void pomeri(Odabir odabir) {
			strategija2 = odabir;
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public int getJ() {
			return j;
		}
		public void setJ(int j) {
			this.j = j;
		}
	}

	class Pijun extends Figura{

		public Pijun() {
			super(new Strategija_pijun(), new Strategija_cilj_novcic());
		}
		
	}

	class Kraljica extends Figura{

		public Kraljica() {
			super(new Strategija_kraljica(),  new Strategija_cilj_novcic());
		}

	}

	class Novcic extends Figura{
		private int id;
		public Novcic() {
			super(new Strategija_novcic(),  new Strategija_slucajno());
			id++;
		}
		public int getId() {
			return id;
		}
	}

	
	interface Moguca_polja{
		public ArrayList<Koordinata> mogucaPolja(int i, int j); //ovo su sva moguca polja gde moze da se pomeri
	}
	class Strategija_pijun implements Moguca_polja{

		@Override
		public ArrayList<Koordinata> mogucaPolja(int i, int j) { //ide samo po horizontali i vertikali po jedno polje
			ArrayList<Koordinata> lista = new ArrayList<Koordinata>();
			lista.add(new Koordinata(i,j)); //trenutna koordinata
			lista.add(new Koordinata(i+1,j));
			lista.add(new Koordinata(i-1,j));
			lista.add(new Koordinata(i+1,j+1));
			lista.add(new Koordinata(i+1,j-1));
			lista.add(new Koordinata(i-1,j+1));
			lista.add(new Koordinata(i-1,j-1));
			return lista;
		}
		
	}

	class Strategija_novcic implements Moguca_polja{ 

		@Override
		public ArrayList<Koordinata> mogucaPolja(int i, int j) {
			ArrayList<Koordinata> lista = new ArrayList<Koordinata>();
			lista.add(new Koordinata(i,j));
			lista.add(new Koordinata(i+2,j+1));
			lista.add(new Koordinata(i+1,j+2));
			lista.add(new Koordinata(i-2,j-1));
			lista.add(new Koordinata(i-2,j+1));
			lista.add(new Koordinata(i+2,j-1));
			lista.add(new Koordinata(i+1,j-2));
			lista.add(new Koordinata(i-1,j+2));
			lista.add(new Koordinata(i-1,j-2));
			return lista;
		}
	}

	class Strategija_kraljica implements Moguca_polja{
		@Override
		public ArrayList<Koordinata> mogucaPolja(int i, int j) {
			ArrayList<Koordinata> lista = new ArrayList<Koordinata>();
			lista.add(new Koordinata(i,j));
			lista.add(new Koordinata(i-3,j));
			lista.add(new Koordinata(i+3,j));
			lista.add(new Koordinata(i,j-3)); 
			lista.add(new Koordinata(i,j+3));
			lista.add(new Koordinata(i+3,j+3));
			lista.add(new Koordinata(i-3,j-3));
			lista.add(new Koordinata(i,j));
			return null;
			
		}
		
	}
	interface Odabir{
		public Koordinata odabir(ArrayList<Koordinata> lista);
	}

	class Strategija_cilj_novcic implements Odabir{ //treba da stigne do novcic 
		Novcic n;

	@Override
	public Koordinata odabir(ArrayList<Koordinata> lista) {
		return null;
	}
		
	}

	class Strategija_slucajno implements Odabir{

		@Override
		public Koordinata odabir(ArrayList<Koordinata> lista) {
			int n = lista.size();
			Random rand = new Random();
			int i = rand.nextInt(n);
			return lista.get(i);
		}
		
	}
	
	class Tabla{
		private static Tabla instanca = null; //treba da ima instancu zato sto je singleton
		private ArrayList<Figura> figura; //listu figura 
		private ArrayList<Tim> timovi; //lista timova
		private int i,j,m,n; //koridinate matrice i,j i dimenzije matrice m,n
		private int[][] mat; //ima samu matricu
		private Tabla(int[][]mat,int n, int m) {
			figura = new ArrayList<Figura>();
			timovi = new ArrayList<Tim>();
			this.mat = mat;
			this.m = m;
			this.n = n;
		}
		public static Tabla getInstance() {
			if(instanca == null) {
				instanca = new Tabla();
			}
			return instanca;
		}
		public void pomeri() {
			for(int i = 0; i < figura.size(); i++) {
				figura.pomeri(new Strategija_cilj_novcic());
			}
		}
		public void dodajFiguru(Figura f, int i, int j) {
			f.setI(i);
			f.setJ(j);
			figura.add(f);
		}
		public void promeniTipTima(int idTima, String tip) {
			for(int i = 0; i < timovi.size(); i++) {
				if(timovi.get(i).id == idTima) {
					timovi.get(i).setBoja(tip);
				}else {
					throw new NoSuchElementException("Ne postoji id tima");
				}
			}
		}
		public void promeniTimFigure(int idFigure, int idTima) {
			//
		}
		
		
	}

	public static void main(String[] args) {
		
	}

}
