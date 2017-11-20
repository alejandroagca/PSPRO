package com.AlejandroAgca;

public class HilosJugadoresConArbitro {

	public static void main(String[] args) {
		Arbitro mallenco = new Arbitro(4);
		Jugador j0 = new Jugador(0, mallenco);
		Jugador j1 = new Jugador(1, mallenco);
		Jugador j2 = new Jugador(2, mallenco);
		Jugador j3 = new Jugador(3, mallenco);
		
		j0.start();
		j1.start();
		j2.start();
		j3.start();
		
		try {
			j0.join();
			j1.join();
			j2.join();
			j3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hilo principal finalizado");
	}

}

class Jugador extends Thread {
	
	Arbitro _arbitro;
	int _dorsal;
	
	public Jugador(int dorsal, Arbitro arbitro) 
	{
		_dorsal = dorsal;
		_arbitro = arbitro;
	}
	
	@Override
	public void run() {
		while (!_arbitro.seAcabo()) {
			_arbitro.jugar(_dorsal, 1 + (int) (_arbitro._maximo * Math.random()));
		}
	}
}

class Arbitro 
{
	int _numJugadores;
	int _turno;
	int _objetivo;
	boolean _acertado;
	public static final int _maximo = 1000;
	
	public Arbitro(int numJugadores) 
	{
		_numJugadores = numJugadores;
		_objetivo = 1 +(int) (Math.random()*_maximo);
		_acertado = false;
		_turno = (int) (numJugadores * Math.random());
		System.out.println("Numero a acertar: " + _objetivo);
	}
	
	public int esTurnoDe() 
	{
		return _turno;
	}
	
	public synchronized boolean seAcabo() 
	{
		return _acertado;
	}
	
	public synchronized void jugar(int jugador, int jugada) 
	{
		while (jugador != _turno && !_acertado) {
			try {
				System.out.println("[PRE] Jugador" + jugador + " y es turno de " + _turno);
				wait();
				System.out.println("[POST] Jugador" + jugador + " y es turno de " + _turno);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!_acertado) {
			System.out.println("El jugador " + _turno + " prueba con " + jugada);
			if (jugada == _objetivo) {
				_acertado = true;
				System.out.println("Jugador " + _turno + " WINS!");
			}
			else {
				_turno = (_turno + 1) % _numJugadores;	
			}
		}
		notifyAll();
	}
}
