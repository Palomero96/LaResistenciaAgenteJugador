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
import jugador.clases.*;

public class InformacionResistencia {
    private AgentIdentifier IDAgente;
    private List<Jugador> jugadores;
   
		public InformacionResistencia()
		{ ; }
      public AgentIdentifier getIDAgente() {
           return IDAgente;
       }
       public void setIDAgente(AgentIdentifier a) {
           IDAgente=a;
       }
       public List<Jugador> getjugadores() {
        return jugadores;
    }
         public void setjugadores(List<Jugador> a) {
        jugadores=a;
    }
}