package com.AlejandroAgca;

public class CenaFilosofos {

	Palillo palillos[];
	int comensales;
	
	public CenaFilosofos(int cuantaGente) 
	{
		comensales = cuantaGente;
		palillos = new Palillo[comensales];
		for (int i = 0; i < comensales; i++) {
			palillos[i] = new Palillo(i);
		}
	}
	public Palillo getPalillo(int x) 
	{
		return palillos[x];
	}
	
	public int getPalilloD (int x) 
	{
		return (x+1) % comensales;
	}
	
	public int getPalilloI ( int x) 
	{
		return x;
	}
}
