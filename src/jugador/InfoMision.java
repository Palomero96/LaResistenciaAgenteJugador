package jugador;

import ontologia;
import Mision;

public class InfoMision extends Predicado {
    private ArrayList<Mision> lista;
   
		public InfoMision()
		{ ; }
      public ArrayList<Mision> getInfoMision() {
           return lista;
       }
       public void setInfoMision(ArrayList<Mision> l) {
    	   lista=l;
       }
}