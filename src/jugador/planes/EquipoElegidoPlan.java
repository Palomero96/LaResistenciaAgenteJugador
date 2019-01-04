package jugador.planes;


import java.util.*;
import jadex.adapter.fipa.*;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
//import jdk.nashorn.internal.runtime.Scope;
import ontologia.acciones.*;
import ontologia.conceptos.*;
import ontologia.predicados.*;


/**
 *  Plan para guardar el equipo elegido 
 */
public class EquipoElegidoPlan extends Plan
{
	public void body()
	{
        IMessageEvent	inform	= (IMessageEvent)getInitialEvent();
        Equipo_elegido equipoelegido = (Equipo_elegido)inform.getContent(); 
        Lista_Jugadores listaequipo = (Lista_Jugadores) equipoelegido.getLista_jugadores();
        getBeliefbase().getBelief("Equipo").setFact(listaequipo);

    }

}