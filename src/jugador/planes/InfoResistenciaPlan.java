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

  
public class InfoResistenciaPlan extends Plan
{
	
	public void body()
	{
        IMessageEvent informe = (IMessageEvent)getInitialEvent();
        Es_resistencia inform = (Es_resistencia) informe.getContent();
        InfoResistencia inforesistencia = (InfoResistencia) getBeliefbase().getBelief("InfoResistencia").getFact();
        ArrayList<InformacionResistencia> lista = inforesistencia.getInfoResistencia();
        InformacionResistencia informacionResistencia = new InformacionResistencia();
        AgentIdentifier id = (AgentIdentifier) informe.getParameter("sender").getValue();
        informacionResistencia.setIDAgente(id);
        informacionResistencia.setjugadores(inform.getLista_jugadores_resistencia());
        /* Guardare toda la informacion, es decir, no actualizare la informacion que me manda un jugador*/
        lista.add(informacionResistencia);
        inforesistencia.setInfoResistencia(lista);
        getBeliefbase().getBelief("InfoResistencia").setFact(inforesistencia);
    }
} 