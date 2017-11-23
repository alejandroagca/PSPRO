package com.AlejandroAgca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcesosComunicados {

	public static void main(String[] args) {
		ProcessBuilder procesoA = new ProcessBuilder("ls","-l", "/home/usuario");
		ProcessBuilder procesoB = new ProcessBuilder("/usr/bin/tr", "a", "@");

		Process pA;
		Process pB;
		try {
			pA = procesoA.start();
			pB = procesoB.start();
			
			BufferedReader salidaA = new BufferedReader(new InputStreamReader(pA.getInputStream(), "utf-8"));
			BufferedWriter entradaB = new BufferedWriter(new OutputStreamWriter(pB.getOutputStream(), "utf-8"));
			BufferedReader salidaB = new BufferedReader(new InputStreamReader(pB.getInputStream(), "utf-8"));
			
			
			// Bucle de lectura/escritura
			String resultadoA;
			while ((resultadoA = salidaA.readLine()) != null) {
				entradaB.write(resultadoA);
				entradaB.newLine();
			}
			
			
			salidaA.close();
			entradaB.close();
			
			// Salida de pB por consola
			String resultadoB;
			while ((resultadoB = salidaB.readLine()) != null) {
				System.out.println(resultadoB);
			}
			
			salidaB.close();
					
			int finA = pA.waitFor();
			int finB = pB.waitFor();
			System.out.println("Procesos A y B finalizados con resultado" + finA + " y " + finB);
		} catch (IOException | InterruptedException e) {
			System.out.println("Error de E/S o excepción por interrupción");
		}
	}

}