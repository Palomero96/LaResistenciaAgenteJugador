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


public class AprobarEquipoPlan extends Plan
{
	public void body()
	{	
		/* En este plan esta todo lo relacionado con la estrategia de aprobar */
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		Aprobar_equipo rj = (Aprobar_equipo) request.getContent();
		boolean soyEspia = (boolean) getBeliefbase().getBelief("soy_Espia").getFact();
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();
		int votacionesrechazadas = (int) getBeliefbase().getBelief("VotacionesRechazadas").getFact(); //Habra que hacer un plan que lo aumente cuando reciba el mensaje del tablero con el resultado
		int misionesCompletadas = (int) getBeliefbase().getBelief("MisionesCompletadas").getFact();
		int misionesEspias = ronda - misionesCompletadas;
		Lista_Jugadores equipo = (Lista_Jugadores) getBeliefbase().getBelief("Equipo").getFact(); //Hay que hacer un plan para que reciba el equipo y lo guarde
		ArrayList<Jugador> listaequipo = (ArrayList) equipo.getjugadores();
		Lista_Jugadores espias = (Lista_Jugadores) getBeliefbase().getBelief("espias").getFact();
		ArrayList<Jugador> listaespias = new ArrayList<Jugador>();
		System.out.println("Fallo aqui");
		if(espias !=null){
			System.out.println("Fallo aca");
			listaespias = (ArrayList) espias.getjugadores();
		}
		
		Voto voto= new Voto();
		int numeroEspias=0;
		if(listaespias !=null){
		for(int i=0; i<listaequipo.size();i++){
			for(int j=0; j<listaespias.size();j++){
				
				if(listaequipo.get(i).getIDAgente() == listaespias.get(j).getIDAgente()){
					numeroEspias++;
				}
			}
		}
	}
		/* Si soy espia votare que si siempre a no ser que sea la ronda 3 y no hayan ganado los espias ninguna ronda*/
		if(soyEspia){
			voto.setequipo(true);
			rj.setVoto(voto);
			if(numeroEspias==0){
				if(ronda==3 && misionesEspias==0) voto.setequipo(false);
				if(ronda==4 && misionesEspias==1) voto.setequipo(false);
				if(ronda==5 && misionesEspias==2) voto.setequipo(false);
			}
		
		}else{
			voto.setequipo(true);
			rj.setVoto(voto);
			/* TODO revisar si añadir algo mas*/
			/* Si soy resistencia y pienso que hay un espia en el equipo entonces votare */
			if(numeroEspias!=0){
				voto.setequipo(false);
			}
			/* Si ya se han rechazado 4 veces el equipo tendremos que votar si obligatoriamente para no perder la partida*/
			if(votacionesrechazadas==4){
				voto.setequipo(true);
			}
		}

		
		sendMessage(request.createReply("Agree_Aprobar_equipo", rj));
		System.out.println(" He votado afirmativo");
	}
}