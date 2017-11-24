package com.AlejandroAgca;

public class RepasoSuper {

	public static boolean [] estadoCajas;
	public static void main(String[] args) {
		int resultado = 0;
		int numCajas = Integer.parseInt(args[0]);
		int numClientes = Integer.parseInt(args[1]);
		estadoCajas = new boolean[numCajas];
		for (int i = 0; i < estadoCajas.length; i++) {
			estadoCajas[i] = true;
		}
		GestionCola cola = new GestionCola();
		ClienteModerno [] clientes  = new ClienteModerno[numClientes];
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new ClienteModerno(i,cola);
			
		}
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
			resultado += clientes[i].dinero;
		}
		for (int i = 0; i < clientes.length; i++) {
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*Caja [] cajas = new Caja[numCajas];
		Cliente [] clientes  = new Cliente[numClientes];
		for (int i = 0; i < cajas.length; i++) {
			cajas[i] = new Caja(i);
		}
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(i, cajas[(int) Math.floor(Math.random() * numCajas)]);
		}
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
			resultado += clientes[i].dinero;
		}
		
		for (int i = 0; i < clientes.length; i++) {
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		
		System.out.println("El resultado de todas las compras es: " + resultado + " €");
		}
}

class Cliente extends Thread{
	int numero;
	Caja caja;
	int dinero;
	public Cliente(int numero, Caja caja) {
		this.numero = numero;
		this.caja = caja;
		this.dinero = (int) Math.floor(Math.random() * 10 + 1);
	}
	
	@Override
	public void run() {
		tiempoComprando();
		caja.asignarCaja(this);
		caja.dejarCaja(this);
	}
	
	public void tiempoComprando() {
		try {
			Thread.sleep((long) (Math.floor(Math.random() * 1000 + 1)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Caja{
	boolean ocupada;
	int numero;
	public Caja(int numero) {
		ocupada = false;
		this.numero = numero;
	}
	
	public synchronized void asignarCaja(Cliente cliente) {
		System.out.println("Al cliente " + cliente.numero + " se le ha asignado la caja " + numero);
		while(ocupada) {
			System.out.println("El cliente " + cliente.numero + " intenta comprar en la caja " + numero + " pero está ocupada");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El cliente " + cliente.numero + " está comprando en la caja " + numero);
		ocupada = true;
	}
	
	public synchronized void dejarCaja(Cliente cliente) {
		System.out.println("El cliente " + cliente.numero + " deja la caja " + numero);
		ocupada=false;
		notifyAll();
	}
}

class GestionCola{
	boolean esperando;
	public GestionCola() {
		esperando = true;
	}
	
	public synchronized int asignarCaja(ClienteModerno cliente) {
		System.out.println("El cliente " + cliente.numero + " se ha puesto en cola");
		while(esperando) {
			for (int i = 0; i < RepasoSuper.estadoCajas.length; i++) {
				if (RepasoSuper.estadoCajas[i]) {
					RepasoSuper.estadoCajas[i] = false;
					System.out.println("El cliente " + cliente.numero + " está comprando en la caja " + i);
					return i;					
				}
			}
			System.out.println("El cliente " + cliente.numero + " quiere comprar pero todas las cajas están ocupadas");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public synchronized void dejarCaja(int caja, ClienteModerno cliente) {
		System.out.println("El cliente " + cliente.numero + " ha dejado libre la caja " + caja);
		RepasoSuper.estadoCajas[caja] = true;
		notifyAll();
	}
}

class ClienteModerno extends Thread{
	int numero;
	int dinero;
	int caja;
	GestionCola cola;
	
	public ClienteModerno(int numero, GestionCola cola) {
		this.numero = numero;
		this.dinero = (int) Math.floor(Math.random() * 10 + 1);
		this.cola = cola;
	}
	
	@Override
	public void run() {
		//tiempoComprando();
		caja = cola.asignarCaja(this);
		cola.dejarCaja(caja, this);
	}
	
	public void tiempoComprando() {
		try {
			Thread.sleep((long) (Math.floor(Math.random() * 1000 + 1)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}