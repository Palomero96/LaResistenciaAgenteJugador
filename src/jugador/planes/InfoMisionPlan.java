package jugador.planes;
import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.predicados.Equipo_elegido;

/**	Acabado
 *  Plan para conocer el posible equipo que ira a la mision
 */
public class InfoMisionPlan extends Plan
{
	
	public void body()
	{	
		/* Con este plan almacenaremos todos los datos con respecto a la mision*/
		IMessageEvent inform = (IMessageEvent)getInitialEvent();
		System.out.println("HE RECIBIDO EL MENSAJE DE LA INFO DE VOTACION");
		Resultado resultado = (Resultado) inform.getResultado();
		int fracasos = (int) inform.getFracasos();
		/* Crear estructura para almacenar resultado equipo fracasos*/
		InfoMision infomision = (InfoMision) getBeliefbase().getBelief("InfoMision").getFact();
		ArrayList<Mision> Informacion= infomision.getInfoMision();

		Lista_jugadores equipo = (Lista_jugadores) getBeliefbase().getBelief("Equipo").getFact();
		Mision mision = new Mision();
		mision.setResultado(resultado);
		mision.setFracasos(fracasos);
		mision.setLista_jugadores(equipo);
		
		boolean soyEspia = (boolean) getBeliefbase().getBelief("soy_Espia").getFact();
		Jugador yo = (Jugador) getBeliefbase().getBelief("jugador").getFact();
		ArrayList<Jugador> lista = (ArrayList) equipo.getjugadores();
		ArrayList<Jugador> nuevosespias = new ArrayList<Jugador>();
		/* Ahora procederemos a analizar todos los datos que tengamos*/
		/* Solo me interesara analizar esta informacion si soy resistencia*/
		if(!soyEspia){
			if(infomision!=null){
				for(int i=0; i<lista.size();i++){
					for(int j=0;j<Informacion.size();j++){
						Lista_jugadores auxiliar = Informacion.get(j).getLista_Jugadores();
						int fracasos = Informacion.get(j).getFracasos();
						ArrayList<Jugador> aux = (ArrayList) auxiliar.getjugadores();
						if(fracasos>0){
							for(int k=0; k<aux.size();k++){
								/* Si ya ha aparecido en una mision antes que ha fracasado y no soy yo
									se le añade a la lista de espias
								*/
								if((lista.get(i).getIDAgente() == aux.get(k).getIDAgente()) && (lista.get(i).getIDAgente()!= yo.getIDAgente())){
									nuevosespias.add(lista.get(i));
								}
							}
						}
					}
				}
			}
	}
		/* Añado a los espias a la base de creencias */
		Lista_jugadores espias = new Lista_jugadores();
		espias.setjugadores((List)nuevosespias);
		getBeliefbase().getBelief("espias").setFact(espias);
		/* Añado la informacion de la nueva mision a la base de creencias*/
		infomision.add(mision);
		getBeliefbase().getBelief("InfoMision").setFact(infomision);
		

	 	/* Aumentar el valor de ronda y de los */
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();
		ronda = ronda+1;
		int completadas = (int) getBeliefbase().getBelief("MisionesCompletadas").getFact()
		if(fracasos == 0){
			completadas= completadas +1;
		}
		getBeliefbase().getBelief("Ronda").setFact(ronda);
		getBeliefbase().getBelief("MisionesCompletadas").setFact(completadas);
		getBeliefbase().getBelief("VotacionesRechazadas").setFact(0);
	}
}