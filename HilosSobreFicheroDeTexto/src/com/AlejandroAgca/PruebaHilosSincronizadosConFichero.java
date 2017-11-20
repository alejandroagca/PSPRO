package com.AlejandroAgca;

public class PruebaHilosSincronizadosConFichero {

	public static void main(String[] args) throws InterruptedException {
		ControladorFichero cf = new ControladorFichero("poema.txt");
		String parrafo1 = "¡Ser o no ser, esa es la cuestión!";
		String parrafo2 = "En un lugar de la mancha, hace tiempo, se cayó una persona a un pozo";
		Escritor Cervantes = new Escritor(cf);
		Escritor Shakespeare = new Escritor(cf);
		
		Shakespeare.FraseAdd(parrafo1);
		Cervantes.FraseAdd(parrafo2);
		
		Shakespeare.start();
		Cervantes.start();
		
		Shakespeare.join();
		Cervantes.join();
		
		cf.close();
		System.out.println("Los datos han sido escritos correctamente");
	}

}
