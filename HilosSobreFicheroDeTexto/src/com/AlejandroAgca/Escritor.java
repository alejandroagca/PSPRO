package com.AlejandroAgca;

public class Escritor extends Thread {
	private ControladorFichero destino;
	private String contenido = "";
	
	public Escritor(ControladorFichero fich) {
		destino = fich;
	}
	
	public void FraseAdd(String cadena) {
		contenido += cadena;
	}
	
	@Override
	public void run() {
		destino.println(contenido);
	}
}
