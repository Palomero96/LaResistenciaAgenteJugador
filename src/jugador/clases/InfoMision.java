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

public class InfoMision {
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