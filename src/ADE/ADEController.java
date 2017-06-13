/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADE;

import ADE.ADERegister;
import ModeloInterno.Fmeasure;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class ADEController {
    static I2CDevice device;

    public static void inicializacionADE() throws I2CFactory.UnsupportedBusNumberException, IOException, InterruptedException{
        try {
            initComponents();
            initRegister();
        } catch (SGMUdenarException ex) {
            Logger.getLogger(ADEController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }
    
    private static void initRegister() throws InterruptedException, SGMUdenarException {
        ADERegister regInit1 = new ADERegister("regInit1", new byte[]{(byte)0xE7,(byte)0xFE}, 8, 1, false);
        ADERegister regInit2 = new ADERegister("regInit1", new byte[]{(byte)0xE7,(byte)0xE2}, 8, 1, false);
        
        //Initialization // suggested in ADE7880 Datasheet (Energy meter - quick start)
        writeRegister(ADERegister.getByName("COMPMODE"), "0x41FF"); //  Secuencia de inicialización
        writeRegister(regInit1,"0xAD");
        writeRegister(regInit2,"0x14");
        Thread.sleep(10);
        writeRegister(regInit1,"0xAD");
        writeRegister(regInit2,"0x04");
        writeRegister(regInit2,"0x04");
        writeRegister(ADERegister.getByName("RUN"), "0x0001"); //Set SELFREQ
        System.out.println("Registro RUN (RAM Registers): " + getStrHex(readRegister(ADERegister.getByName("RUN"))));

    }

    private static String writeRegister(ADERegister register, String strValue) {
        try {
            if (register == null) {
                throw new SGMUdenarException("Se ingresó un registro nulo como parámetro. Verifique los parámetros de entrada."); 
            }
            byte[] buffer = stringValidation(strValue);
            
            if (register.byteLengthDuringCommunication != buffer.length) {
                System.out.println("El número de bits indicado para la escritura es incorrecto");
                return "El número de bits indicado para la escritura es incorrecto";
            }
            try {
                byte[] writeBuffer = new byte[register.byteLengthDuringCommunication+2]; //Se hace un buffer que tiene en sus dos primeros espacios el registro, y después los bytes requeridos para comunicación
                writeBuffer[0] = register.address[0];
                writeBuffer[1] = register.address[1];
                for (int i = 0; i < register.byteLengthDuringCommunication; i++) {
                    writeBuffer[i+2] = buffer[i];
                }
                //writeBuffer[6] = buffer[0];
                device.write(writeBuffer);
                //device.write();
                //return readRegister(register);
                return "ok";
            } catch (IOException ex) {
                Logger.getLogger(ADEController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Probablemente no esté conectado o encendido rl módulo ADE");
                System.out.println(ex.toString());
                return ex.toString();
            } catch (Exception ex){
                System.out.println("Probablemente no esté conectado o encendido rl módulo ADE");
                System.out.println(ex.toString());
                return ex.toString();
            }
        } catch (SGMUdenarException ex) {
            System.out.println(ex.toString());
            return ex.toString();
        }
    }
    
    public static byte[] readRegister(ADERegister register) throws SGMUdenarException{
        byte readbuffer[] = new byte[register.byteLengthDuringCommunication];
        try {
            device.read(register.address, 0, 2, readbuffer, 0, register.byteLengthDuringCommunication);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            throw new SGMUdenarException(ex.toString());
        }
        return readbuffer;          
    }
    
    private static long getMeanRegisterValue(ADERegister register, int i) throws SGMUdenarException {
        long numValue = 0;
        for (int j = 0; j < i; j++) {
            numValue += getNumValue(readRegister(register),0);
        }
        return numValue/i;        
    }
    
    private static long getNumValue(byte[] byteValue, int selector){ //selector: 0=RMS ;
        long numValue = 0;
        
        for (int i = 0; i < byteValue.length; i++) {
            numValue += Byte.toUnsignedLong(byteValue[byteValue.length - 1 - i]) << i*8 ;
        }
        
        switch(selector){
            case 0:
                if (numValue > 5326737){
                    numValue = -(numValue - 5326737);
                    break;
                }
            default:
                break;
        }
        return numValue;
    }
    
    public static List<ADERegister> getRegistersByQuantityID(int[] quantitiesIDtoMeasureInst) {
        List<ADERegister> ADEregisters = ADERegister.initRegisterlist();
        List<ADERegister> requestedRegisters = new ArrayList<ADERegister>();

        for (Iterator<ADERegister> j = ADEregisters.iterator(); j.hasNext();) {
            ADERegister register = j.next();
                for (int i = 0; i < quantitiesIDtoMeasureInst.length; i++) {
                    if (register.quantityIDInDatabase == quantitiesIDtoMeasureInst[i]) {
                        requestedRegisters.add(register);
                    }
            }
        }
        return requestedRegisters;
    }

    public static List<Fmeasure> getValuesFromRegisters(List<ADERegister> instRegisters, int iterations) throws SGMUdenarException {
       
        List<Fmeasure> fmeasures = new ArrayList<>();
        for (Iterator<ADERegister> i = instRegisters.iterator(); i.hasNext();) {
            ADERegister register = i.next();
            
            Fmeasure fmeasure = new Fmeasure();
            fmeasure.setQuantityID(register.quantityIDInDatabase);
            fmeasure.setValue(getMeanRegisterValue(register, iterations));
            fmeasures.add(fmeasure);
        }
        return fmeasures;
    }
    
    public static byte[] stringValidation(String strByte) throws SGMUdenarException{
        if (strByte.length()%2 != 0) {
            throw new SGMUdenarException("La cedena de entrada debe tener un numero par de caracteres. Especificamente 8 para binario, aparte del identificador '0b'.");
        }
        if (strByte.substring(0,2).equals("0x")) {
            byte[] buffer = new byte[strByte.length()/2-1];
            for (int i = 0; i < strByte.length()/2-1; i++) {

                buffer[i] = (byte)Integer.parseInt(strByte.substring(2*i+2, 2*i+4),16);
            }
            return buffer;
        }
        else if (strByte.substring(0,2).equals("ob") && strByte.substring(2).length()%8 == 0){
            byte[] buffer = new byte[(strByte.length()-2)/8];
            for (int i = 2; i < (strByte.length()-2)/8; i++) {
                buffer[i] = Byte.parseByte(strByte.substring(i, i+8),2);
            }
            return buffer;
        }
        else{
            throw new SGMUdenarException("Error. Wrong format: String input must start with '0x'(hex) or 'ob'(binary)");
        }
    }

    private static String getStrHex(byte[] readBuffer) {
        String hexNum = "0x";
        for (int i = 0; i < readBuffer.length; i++) {
            hexNum += String.format("%02x", readBuffer[i]);
        }
        return hexNum;
     //   printtf(hexNum);
    }
    private static void initComponents() {
        
        try {
            gpio = GpioFactory.getInstance();
            pinIRQ1  = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29); // GPIO_29 -> Pin 40 in header
            pinPM1   = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "PM1", PinState.LOW); // GPIO_00 -> Pin 11 in header
            pinPM0   = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "PM0", PinState.HIGH); // GPIO_02 -> Pin 13 in header
            pinReset = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Reset", PinState.HIGH); //GPIO_03 -> Pin 15 in header
            
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            device = i2c.getDevice(ADERegister.ADE7880_ADDR);
        } catch (I2CFactory.UnsupportedBusNumberException ex) {
            Logger.getLogger(ADEController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(ADEController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } catch (Exception ex)  {
            Logger.getLogger(ADEController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        
    }
//   <editor-fold desc="pinDefinition">
    private static GpioController gpio;
    private static GpioPinDigitalInput pinIRQ1;
    private static GpioPinDigitalOutput pinPM0;
    private static GpioPinDigitalOutput pinPM1;
    private static GpioPinDigitalOutput pinReset;
 //   </editor-fold>


   
}

class SGMUdenarException extends Exception {
   public SGMUdenarException(String msg){
      super(msg);
   }
}