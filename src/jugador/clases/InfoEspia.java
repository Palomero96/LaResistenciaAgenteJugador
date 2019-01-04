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

public class InfoEspia {
    private ArrayList<InformacionEspia> lista;
   
		public InfoEspia()
		{ ; }
      public ArrayList<InformacionEspia> getInfoEspia() {
           return lista;
       }
       public void setInfoEspia(ArrayList<InformacionEspia> l) {
    	   lista=l;
       }
}