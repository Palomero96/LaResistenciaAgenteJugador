package jugador.planes;


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

  
public class InfoEspiaPlan extends Plan
{
	
	public void body()
	{
        IMessageEvent informe = (IMessageEvent)getInitialEvent();
        Es_espia inform = (Es_espia) informe.getContent();
        InfoEspia infoespia = (InfoEspia) getBeliefbase().getBelief("InfoEspia").getFact();
        ArrayList<InformacionEspia> lista = infoespia.getInfoEspia();
        InformacionEspia informacionEspia = new InformacionEspia();
        AgentIdentifier id = (AgentIdentifier) informe.getParameter("sender").getValue();
        informacionEspia.setIDAgente(id);
        informacionEspia.setjugadores(inform.getLista_jugadores_sospechoso());
        /* Guardare toda la informacion, es decir, no actualizare la informacion que me manda un jugador*/
        lista.add(informacionEspia);
        infoespia.setInfoEspia(lista);
        getBeliefbase().getBelief("InfoEspia").setFact(infoespia);
    }
}    