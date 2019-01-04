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

  
public class InfoMisionPlan extends Plan
{
	
	public void body()
	{	
		/* Con este plan almacenaremos todos los datos con respecto a la mision*/
		IMessageEvent informe = (IMessageEvent)getInitialEvent();
		Votacion_publicada_mision inform = (Votacion_publicada_mision) informe.getContent();
		Resultado resultado = (Resultado) inform.getResultado();
		int fracasos = (int) inform.getFracasos();
		/* Crear estructura para almacenar resultado equipo fracasos*/
		InfoMision infomision = (InfoMision) getBeliefbase().getBelief("InfoMision").getFact();
		ArrayList<Mision> Informacion= infomision.getInfoMision();

		Lista_Jugadores equipo = (Lista_Jugadores) getBeliefbase().getBelief("Equipo").getFact();
		Mision mision = new Mision();
		mision.setResultado(resultado);
		mision.setFracasos(fracasos);
		mision.setLista_jugadores(equipo);
		
		boolean soyEspia = (boolean) getBeliefbase().getBelief("soy_Espia").getFact();
		Jugador yo = (Jugador) getBeliefbase().getBelief("jugador").getFact();
		ArrayList<Jugador> lista = (ArrayList) equipo.getjugadores();
		ArrayList<Jugador> nuevosespias = new ArrayList<Jugador>();
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();

		if(ronda==1){
		/* En la ronda uno simplemente almaceno la informacion*/
		Informacion.add(mision);
		infomision.setInfoMision(Informacion);
		getBeliefbase().getBelief("InfoMision").setFact(infomision);

		}else{
		/* Ahora procederemos a analizar todos los datos que tengamos*/
		/* Solo me interesara analizar esta informacion si soy resistencia*/
		if(!soyEspia){
			/* Si he participado en la mision y la mision a fracasado entonces enviare a todos los jugadores que son espias*/
			Es_espia sonespias = new Es_espia();
			ArrayList<Jugador> enviarespias= new ArrayList<Jugador>();
			if(fracasos>0){
				/* Comprobamos si he participado en la mision */
				for(int b=0; b>lista.size();b++){
					if(lista.get(b).getIDAgente() == yo.getIDAgente()){
						for(int w=0; w<lista.size();w++){
							if(lista.get(w).getIDAgente() != yo.getIDAgente()){
								enviarespias.add(lista.get(w));
									}
								}
						IMessageEvent enviare = createMessageEvent("Inform_es_Espia");
						sonespias.setLista_jugadores_sospechoso((List) enviarespias);
						enviare.setContent(sonespias);
						Lista_Jugadores jugadoresenviar = (Lista_Jugadores) getBeliefbase().getBelief("jugadores").getFact();
						ArrayList<Jugador> listaenviar = (ArrayList) jugadoresenviar.getjugadores();
						for(int e=0; e<listaenviar.size(); e++){
								/* Compruebo que no soy yo, para no enviarmelo a mi mismo*/
								if(listaenviar.get(e).getIDAgente()!= yo.getIDAgente()){
									enviare.getParameterSet(SFipa.RECEIVERS).addValue(listaenviar.get(e).getIDAgente());
								}
								
						}
						sendMessage(enviare);
					}
				}
				
			}
			if(infomision!=null){
				for(int i=0; i<lista.size();i++){
					for(int j=0;j<Informacion.size();j++){
						Lista_Jugadores auxiliar = Informacion.get(j).getLista_jugadores();
						fracasos = Informacion.get(j).getFracasos();
						ArrayList<Jugador> aux = (ArrayList) auxiliar.getjugadores();
						if(fracasos>0){
							for(int k=0; k<aux.size();k++){
								/* Si ya ha aparecido en una mision antes que ha fracasado y no soy yo
									se le añade a la lista de espias
								*/
								if((lista.get(i).getIDAgente() == aux.get(k).getIDAgente()) && (lista.get(i).getIDAgente()!= yo.getIDAgente())){
									if(nuevosespias==null) nuevosespias.add(lista.get(i));
									/* Recorremos la lista de los espias para no almacenar el mismo dos veces*/
									int contador=0;
									for(int a=0; a<nuevosespias.size(); a++){
										if(lista.get(i).getIDAgente() == nuevosespias.get(a).getIDAgente()){
											contador++;
										}
									}
									if(contador==0) nuevosespias.add(lista.get(i));
									
								}
							}

						}
					}
				}
					/* Añado a los espias a la base de creencias */
					Lista_Jugadores espias = new Lista_Jugadores();
					espias.setjugadores((List)nuevosespias);
					getBeliefbase().getBelief("espias").setFact(espias);
			} else{
					/* Si no hay nada en la base de creencias*/
					for(int c=0;c<lista.size();c++){
						if(lista.get(c).getIDAgente() != yo.getIDAgente()){
							nuevosespias.add(lista.get(c));
						}
						
					}
					Lista_Jugadores espias = new Lista_Jugadores();
					espias.setjugadores((List)nuevosespias);
					getBeliefbase().getBelief("espias").setFact(espias);
			}
		/* Añado la informacion de la nueva mision a la base de creencias
		Solo vas a querer almacenar esta informacion en el caso de ser resistencia*/
		Informacion.add(mision);
		infomision.setInfoMision(Informacion);
		getBeliefbase().getBelief("InfoMision").setFact(infomision);

	} else{ /* En caso de ser espia voy a querer cubrirme a mi y a mis compañeros diciendo que son resistencia
		e incriminando al resto*/
		if(fracasos>0){
			IMessageEvent enviarespiasfalsos = createMessageEvent("Inform_es_Espia");
			IMessageEvent enviarresistenciafalsos = createMessageEvent("Inform_es_Resistencia");
			Lista_Jugadores somosespias = (Lista_Jugadores) getBeliefbase().getBelief("espias").getFact();
			ArrayList<Jugador> listasomosespias = (ArrayList) somosespias.getjugadores();
			ArrayList<Jugador> espiasfalsos = new ArrayList<Jugador>();
			ArrayList<Jugador> resistenciafalsos = new ArrayList<Jugador>();
			Es_espia claseespias = new Es_espia();
			Es_resistencia claseresistencia = new Es_resistencia();
			for(int c=0;c<lista.size();c++){
				for(int d=0; d<listasomosespias.size();d++){
					if(lista.get(c).getIDAgente() == listasomosespias.get(d).getIDAgente()){
						resistenciafalsos.add(lista.get(c));
						//Le añado al mensaje de los otros
					}else{
						espiasfalsos.add(lista.get(c));
					}
				}
			}	
				claseespias.setLista_jugadores_sospechoso((List) espiasfalsos);
				enviarespiasfalsos.setContent(claseespias);
				claseresistencia.setLista_jugadores_resistencia((List) espiasfalsos);
				enviarresistenciafalsos.setContent(claseresistencia);
				Lista_Jugadores jugadoresenviar = (Lista_Jugadores) getBeliefbase().getBelief("jugadores").getFact();
				ArrayList<Jugador> listaenviar = (ArrayList) jugadoresenviar.getjugadores();
				for(int e=0; e<listaenviar.size(); e++){
					/* Compruebo que no soy yo, para no enviarmelo a mi mismo*/
					if(listaenviar.get(e).getIDAgente()!= yo.getIDAgente()){
						enviarresistenciafalsos.getParameterSet(SFipa.RECEIVERS).addValue(listaenviar.get(e).getIDAgente());
						enviarespiasfalsos.getParameterSet(SFipa.RECEIVERS).addValue(listaenviar.get(e).getIDAgente());
					}
					
				}
			sendMessage(enviarespiasfalsos);
			sendMessage(enviarresistenciafalsos);
		}
		
	}
}

	 	/* Aumentar el valor de ronda y de los demas contadores*/
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();
		ronda = ronda+1;
		int completadas = (int) getBeliefbase().getBelief("MisionesCompletadas").getFact();
		if(fracasos == 0){
			completadas= completadas +1;
		}
		getBeliefbase().getBelief("Ronda").setFact(ronda);
		getBeliefbase().getBelief("MisionesCompletadas").setFact(completadas);
		getBeliefbase().getBelief("VotacionesRechazadas").setFact(0);
	}
}