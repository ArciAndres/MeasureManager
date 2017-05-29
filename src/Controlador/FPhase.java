package Controlador;


import Controlador.Fmeasure;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class FPhase{ //Format Phase
    private int phase;
    private List<Fmeasure> fmeasures ;

    public FPhase(){}
    
    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setFmeasures(List<Fmeasure> fmeasures) {
        this.fmeasures = fmeasures;
    }

    public int getPhase() {
        return phase;
    }

    public List<Fmeasure> getFmeasures() {
        return fmeasures;
    }
}
