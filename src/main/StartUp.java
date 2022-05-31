package main;

import cui.SokobanApplicatie;
import domein.DomeinController;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		SokobanApplicatie sapp = new SokobanApplicatie(dc);
		sapp.start();
	}

}
