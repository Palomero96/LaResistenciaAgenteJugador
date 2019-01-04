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

public class InfoResistencia {
    private ArrayList<InformacionResistencia> lista;
   
		public InfoResistencia()
		{ ; }
      public ArrayList<InformacionResistencia> getInfoResistencia() {
           return lista;
       }
       public void setInfoResistencia(ArrayList<InformacionResistencia> l) {
    	   lista=l;
       }
}