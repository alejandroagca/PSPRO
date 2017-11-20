package com.AlejandroAgca;

public class Parking {

	public static boolean [] estadoParking;
	public static int [] cocheAparcado;
	public static int numPlazas;
	
	public static void main(String[] args) {
		numPlazas = Integer.parseInt(args[0]);
		int numCoches = Integer.parseInt(args[1]);
		Coche coches [] = new Coche[numCoches];
		cocheAparcado = new int [numPlazas];
		estadoParking = new boolean [numPlazas];
		Plaza plaza = new Plaza();
		for (int i = 0; i < estadoParking.length; i++) {
			estadoParking[i] = true;
		}
		
		for (int i = 0; i < numCoches; i++) {
			coches[i] = new Coche(i+1, plaza);
		}
		
		for (int i = 0; i < coches.length ; i++) {
			coches[i].start();
		}
		
		for (int i = 0; i < coches.length; i++) {
			try {
				coches[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}

class Coche extends Thread {
	
	int matricula;
	int suPlaza;
	Plaza plaza;
	
	public Coche(int matricula, Plaza plaza) {
		this.matricula = matricula;
		this.plaza = plaza;
		
	}
	
	@Override
	public void run() {
		while (true) {
			suPlaza = plaza.asignarPlaza(this);
			tiempodeEspera();
			plaza.dejarPlaza(suPlaza, this);
			tiempodeEspera();
		}
	}
	
	public void tiempodeEspera() {
		try {
			Thread.sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Plaza {
	boolean esperando;
	public Plaza() {
		this.esperando = true;
	}
	
	public synchronized int asignarPlaza(Coche coche) {
		while(esperando) {
			for (int i = 0; i < Parking.numPlazas; i++) {
				if (Parking.estadoParking[i]) {
					Parking.estadoParking[i] = false;
					Parking.cocheAparcado[i] = coche.matricula;
					System.out.println("ENTRADA: Coche " + coche.matricula + " aparca en la plaza " + (i+1));
					comprobarPlazasLibres();
					cocheOcupaPlaza();
					System.out.println("\n");
					return i;
				}
			}
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public synchronized void dejarPlaza(int plazaAsignada, Coche coche) {
		Parking.estadoParking[plazaAsignada] = true;
		Parking.cocheAparcado[plazaAsignada] = 0;
		System.out.println("Salida: Coche " + coche.matricula + " saliendo de la plaza " + (plazaAsignada + 1));
		comprobarPlazasLibres();
		cocheOcupaPlaza();
		System.out.println("\n");
		notify();
	}
	
	public void comprobarPlazasLibres() {
		int plazasLibres = 0;
		for (int i = 0; i < Parking.estadoParking.length; i++) {
			if(Parking.estadoParking[i]) {
				plazasLibres++;
			}
		}
		System.out.println("Plazas libres: " + plazasLibres);
	}
	
	public void cocheOcupaPlaza() {
		for (int i = 0; i < Parking.cocheAparcado.length; i++) {
			System.out.print("[" + Parking.cocheAparcado[i] + "]");
		}
	}
}