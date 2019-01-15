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
 *  PLan que guarda la lista de espias en las creencias del jugador
 */
public class ElegirEquipoPlan extends Plan
{
	public void body()
	{	
		/* La estrategia que utilizare en este caso será para la ronda uno me elegire a mi y a otro. En el caso de ser espia, el otro que elegire no lo será*/
		
		IMessageEvent	inform	= (IMessageEvent)getInitialEvent();
		Lider_asignado rj = (Lider_asignado) inform.getContent();
		Lista_Jugadores jugadores = (Lista_Jugadores) getBeliefbase().getBelief("jugadores").getFact();
		ArrayList<Jugador> lista = (ArrayList) jugadores.getjugadores();
		/* Inicializo las variables necesarias para guardar el equipo*/
		Equipo_elegido equipoelegido = new Equipo_elegido();
		Lista_Jugadores jugadoresequipo = new Lista_Jugadores();
		ArrayList<Jugador> listaequipo = new ArrayList<Jugador>();
		int equipo = (int) rj.getEquipo();
		IMessageEvent enviar = createMessageEvent("Inform_Equipo_elegido");
		/* Obtengo las creencias que necesito de la base de creencias*/
		Jugador yo = (Jugador) getBeliefbase().getBelief("jugador").getFact();
		Lista_Jugadores espias = (Lista_Jugadores) getBeliefbase().getBelief("jugadores").getFact();
		ArrayList<Jugador> listaespias = (ArrayList) espias.getjugadores();
		boolean soyEspia = (boolean) getBeliefbase().getBelief("soy_Espia").getFact();
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();
		System.out.println("Antes de elegir equipo");
		
		/* Si es la ronda uno */
		if(ronda==1){
				listaequipo.add(yo);
				int contador= 1;
				if(soyEspia){
					for(int i=0; i<lista.size() && contador<2;i++){
						for(int a=0; a<listaespias.size() && contador<2;a++){
							if(lista.get(i).getIDAgente() != listaespias.get(a).getIDAgente() && listaequipo.get(1).getIDAgente() != lista.get(i).getIDAgente()){
								listaequipo.add(lista.get(i));
								contador++;
							}
						}
					}
				}else{
					int random = (int) (Math.random() * 7);
					listaequipo.add(lista.get(random));		
				}
		}else{
				
				if(soyEspia){
					System.out.println("Soy espia empiezo a elegir");
					Jugador auxiliar = new Jugador();
					int  random = (int) (Math.random() *3);
					auxiliar = listaespias.get(random);
					listaequipo.add(auxiliar);
					int contador=1;
					for(int b=0; b<lista.size() && contador<equipo; b++){
						if(lista.get(b).getIDAgente() != auxiliar.getIDAgente()){
							listaequipo.add(lista.get(b));
							contador++;
						}
					}
					System.out.println("Soy espia acabo de elegir");
				}else{
					listaequipo.add(yo);
					System.out.println("Soy resistencia empiezo a elegir");
					int contador=1;
					int auxiliar=0;
						for( int c=0; c<lista.size() && contador<equipo;c++){
							for(int d=0; d<listaespias.size() && contador<equipo;d++){
								if(lista.get(c).getIDAgente() == listaespias.get(d).getIDAgente() && listaequipo.get(0).getIDAgente() != lista.get(c).getIDAgente()){
									auxiliar++;
								}
							}
							if(auxiliar==0){
								listaequipo.add(lista.get(c));
								contador++;
							}
							auxiliar=0;
						}
					
					System.out.println("Soy resistencia acabo de elegir");
					/* Si no se ha rellenado el equipo habra que rellenarlo con jugadores que no esten independientemente de si son espias o no*/
					if(listaequipo.size()<equipo){
						auxiliar=0;
						for( int c=0; c<lista.size() && contador<equipo;c++){
							for(int d=0; d<listaequipo.size() && contador<equipo;d++){
								if(lista.get(c).getIDAgente() == listaespias.get(d).getIDAgente()){
									auxiliar++;
								}
							}
							if(auxiliar==0){
								listaequipo.add(lista.get(c));
								contador++;
							}
							auxiliar=0;
						}
					}
				}
		}
		
		jugadoresequipo.setjugadores((List)listaequipo);
		getBeliefbase().getBelief("Equipo").setFact(jugadoresequipo);
		equipoelegido.setLista_jugadores(jugadoresequipo);
		enviar.setContent(equipoelegido);
		for(int j=0; j<lista.size(); j++){
			if(lista.get(j).getIDAgente() != yo.getIDAgente()){
							enviar.getParameterSet(SFipa.RECEIVERS).addValue(lista.get(j).getIDAgente());
			}
			
		}
		//Al tablero tambien le enviamos el mensaje
		enviar.getParameterSet(SFipa.RECEIVERS).addValue((AgentIdentifier)getBeliefbase().getBelief("tablero").getFact());
		sendMessage(enviar);
		System.out.println("Equipo elegido por el lider.");
	}

}