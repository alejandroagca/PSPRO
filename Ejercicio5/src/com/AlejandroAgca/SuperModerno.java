package com.AlejandroAgca;

public class SuperModerno {

	public static boolean estadoCajas[];
	public static int numCajas;
	public static double tiempoMedio;
	
	public static void main(String[] args) {
		tiempoMedio = 0;
		int resultado = 0;
		Caja caja = new Caja();
		numCajas = Integer.parseInt(args[0]);
		int numClientes = Integer.parseInt(args[1]);
		estadoCajas = new boolean[numCajas];
		Cliente clientes [] = new Cliente [numClientes];
		for (int i = 0; i < estadoCajas.length; i++) {
			estadoCajas[i] = true;
		}
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(i, caja);
		}
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
			resultado += clientes[i].precioCompra;
		}
		
		for (int i = 0; i < clientes.length; i++) {
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El tiempo medio que han esperado los clientes es: " + tiempoMedio/numClientes);
		 System.out.println("El valor de todas las compras es: " + resultado + " euros");
		
	}
}

class Cliente extends Thread{
	int numero;
	Caja caja;
	int precioCompra;
	int suCaja;
	double termina, empieza;
	
	public Cliente(int numero, Caja caja) {
		this.numero = numero;
		this.caja = caja;
		precioCompra = (int)Math.floor(Math.random() * 10 + 1);
	}
	
	@Override
	public void run() {
		realizarCompra();
		empieza = System.currentTimeMillis();
		suCaja = caja.asignarCaja(this);
		termina = System.currentTimeMillis() - empieza;
		caja.compra(precioCompra,this, suCaja);
		caja.dejarCaja(suCaja, this);
		SuperModerno.tiempoMedio += termina;
	}
	
	public void realizarCompra() {
		try {
			Thread.sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Caja{
	boolean esperando;
	
	public Caja() {
		this.esperando = true;
	}
	
	public synchronized int asignarCaja(Cliente cliente) {
		System.out.println("El cliente " +cliente.numero + " se ha puesto en cola");
        while (esperando) {
        	
            for (int i = 0; i < SuperModerno.numCajas; i++) {
                if (SuperModerno.estadoCajas[i]) {
                    SuperModerno.estadoCajas[i] = false;
                    System.out.println("Se le ha asignado la caja " + i + " al cliente " + cliente.numero);
                    return i;
                }
            }
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
}
	
	public synchronized void dejarCaja(int caja, Cliente cliente) {
		SuperModerno.estadoCajas[caja] = true;
		System.out.println("El cliente " + cliente.numero + " ha dejado libre la caja " + caja);
		notify();
	}
	
	public void compra(int precio, Cliente cliente, int caja) {
		System.out.println("El cliente " + cliente.numero + " está realizando una compra de " + precio + " euros en la caja " + caja);
	}
}