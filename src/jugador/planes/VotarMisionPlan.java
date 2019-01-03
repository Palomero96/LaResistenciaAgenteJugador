package jugador.planes;

import java.util.List;

import jadex.adapter.fipa.*;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.Unirse_a_la_partida;
import ontologia.acciones.Votar_mision;
import ontologia.conceptos.Jugador;
import ontologia.conceptos.Lista_Jugadores;
import ontologia.conceptos.Resultado;
import ontologia.conceptos.Voto;
import ontologia.predicados.Partida_Finalizada;
import ontologia.predicados.Tarjetas_exito_fracaso_repartidas;
import ontologia.predicados.Votacion_publicada_equipo;

/**	Acabado
 *  Plan para votar la mision (jugador)
 */
public class VotarMisionPlan extends Plan
{
	
	public void body()
	{		
		/* Lo que hara el jugador en este plan sera votar la exito o fracaso en la mision */
		IMessageEvent request	= (IMessageEvent)getInitialEvent();
		Jugador jugador = (Jugador) getBeliefbase().getBelief("jugador").getFact();
		boolean soyEspia = jugador.getEspia();
		int ronda = (int) getBeliefbase().getBelief("Ronda").getFact();
		Votar_mision votar_mision = new Votar_mision();
		Voto voto = new Voto();
		votar_mision.setVoto(voto);
		/* En el caso de ser espia votara fracaso en todas las rondas, salvo en la primera para no ser descubierto*/
		if(soyEspia) {
			votar_mision.getvoto().setmision(false);
			if(ronda==1){
				votar_mision.getvoto().setmision(true);
			}
			IMessageEvent  reply = request.createReply("Agree_Votar_mision", votar_mision);		
			sendMessage(reply);
		/* En el caso de ser resistencia siempre votará exito */
		}else {
			votar_mision.getvoto().setmision(true);
			IMessageEvent  reply = request.createReply("Agree_Votar_mision", votar_mision);		
			sendMessage(reply);
		}
	}
}