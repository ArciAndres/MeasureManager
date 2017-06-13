/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloInterno;

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class Fmeasure{ //formatmeasure
    private double value;
    private int quantityID;

    public Fmeasure(){}
    
    public void setValue(double value) {
        this.value = value;
    }

    public void setQuantityID(int quantityID) {
        this.quantityID = quantityID;
    }

    public double getValue() {
        return value;
    }

    public int getQuantityID() {
        return quantityID;
    }
}