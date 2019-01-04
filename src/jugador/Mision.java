package jugador;

import ontologia;
import Mision;

public class InfoMision extends Predicado {
    private Resultado resultado;
    private int fracasos;
    private Lista_Jugadores lista;

    
		public Votacion_publicada_mision()
		{ ; }
      public Resultado getResultado() {
           return resultado;
       }
       public void setResultado(Resultado l) {
    	   resultado=l;
       }
       public int getFracasos() {
           return fracasos;
       }

       public void setFracasos(int l) {
    	   fracasos=l;
       }
       public Lista_Jugadores getLista_jugadores() {
        return lista;
    }
    public void setLista_jugadores(Lista_Jugadores l) {
        lista=l;
    }
}