package jugador.clases;


import java.util.*;

import jadex.adapter.fipa.*;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
//import jdk.nashorn.internal.runtime.Scope;
import ontologia.*;
import ontologia.acciones.*;
import ontologia.conceptos.*;
import ontologia.predicados.*;

public class Mision{
    private Resultado resultado;
    private int fracasos;
    private Lista_Jugadores lista;

    
		public Mision()
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