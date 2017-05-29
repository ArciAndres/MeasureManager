/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Measure;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class FormatMeasure {
    private Date date;
    private List<FPhase> Fphases;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFPhases(List<FPhase> phases) {
        this.Fphases = phases;
    }

    public Date getDate() {
        return date;
    }

    public List<FPhase> getFphases() {
        return Fphases;
    }
    
}

