/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ANDRES ARCINIEGAS
 */
public class ADERegister {
        //Convention: 
        //Address of Register = 0xbyte1byte2;
        //byte[]{ 0xbyte1, 0xbyte2 , BitLength, ByteLengthDuringCommunication, SignedOrUnsigned, Phase, QuantityID in Database}   //Signed = 1, Unsigned = 0
        // 0 = Neutro, 1,2,3 = Fases
        //Refer to ADE7880 Datasheet Table of Registers/
    public static final int ADE7880_ADDR = 0x38; // address pin not connected (FLOATING)
    public static List<ADERegister> ADEregisters ;

    String name ;
    byte[] address ;
    int bitLength ;
    int byteLengthDuringCommunication ;
    boolean signed ;
    int phase ;
    int quantityIDInDatabase ;


    public ADERegister(String name, byte[] address, int bitLength, int byteLengthDuringCommunication, boolean signed, int phase, int quantityIDInDatabase) {
        this.name = name;
        this.address = address;
        this.bitLength = bitLength;
        this.byteLengthDuringCommunication = byteLengthDuringCommunication;
        this.signed = signed;
        this.phase = phase;
        this.quantityIDInDatabase = quantityIDInDatabase;        
    }
    
    public ADERegister(String name, byte[] address, int bitLength, int byteLengthDuringCommunication, boolean signed) {
        this.name = name;
        this.address = address;
        this.bitLength = bitLength;
        this.byteLengthDuringCommunication = byteLengthDuringCommunication;
        this.signed = signed;
    }
    
    public static List<ADERegister> initRegisterlist(){ //No lo pude inicializar directamente como una variable estática de lista
    
        ADEregisters = new ArrayList<>();
        
        ADEregisters.add(new ADERegister("AIGAIN"	         ,new byte[] {(byte)0x43,(byte)0x80 }, 24 , 4 , true , 0 , 2 )); //AIGAIN	 0    
        ADEregisters.add(new ADERegister("AVGAIN"	         ,new byte[] {(byte)0x43,(byte)0x81} , 24 , 4 , true , 0 , 1 )); //AVGAIN	 1    
        ADEregisters.add(new ADERegister("BIGAIN"	         ,new byte[] {(byte)0x43,(byte)0x82} , 24 , 4 , true , 1 , 2 )); //BIGAIN	 2    
        ADEregisters.add(new ADERegister("BVGAIN"	         ,new byte[] {(byte)0x43,(byte)0x83} , 24 , 4 , true , 1 , 1 )); //BVGAIN	 3    
        ADEregisters.add(new ADERegister("CIGAIN"	         ,new byte[] {(byte)0x43,(byte)0x84} , 24 , 4 , true , 2 , 2 )); //CIGAIN	 4    
        ADEregisters.add(new ADERegister("CVGAIN"	         ,new byte[] {(byte)0x43,(byte)0x85} , 24 , 4 , true , 2 , 1 )); //CVGAIN	 5    
        ADEregisters.add(new ADERegister("NIGAIN"	         ,new byte[] {(byte)0x43,(byte)0x86} , 24 , 4 , true  )); //NIGAIN	         6
        ADEregisters.add(new ADERegister("DICOEFF"               ,new byte[] {(byte)0x43,(byte)0x88} , 24 , 4 , true  )); //DICOEFF           7
        ADEregisters.add(new ADERegister("APGAIN"	         ,new byte[] {(byte)0x43,(byte)0x89} , 24 , 4 , true  )); //APGAIN	         8
        ADEregisters.add(new ADERegister("AWATTOS"               ,new byte[] {(byte)0x43,(byte)0x8A} , 24 , 4 , true  )); //AWATTOS           9
        ADEregisters.add(new ADERegister("BPGAIN"	         ,new byte[] {(byte)0x43,(byte)0x8B} , 24 , 4 , true  )); //BPGAIN	         10
        ADEregisters.add(new ADERegister("BWATTOS"        	 ,new byte[] {(byte)0x43,(byte)0x8C} , 24 , 4 , true  )); //BWATTOS        	 11
        ADEregisters.add(new ADERegister("CPGAIN"	         ,new byte[] {(byte)0x43,(byte)0x8D} , 24 , 4 , true  )); //CPGAIN	         12
        ADEregisters.add(new ADERegister("CWATTOS"               ,new byte[] {(byte)0x43,(byte)0x8E} , 24 , 4 , true  )); //CWATTOS           13
        ADEregisters.add(new ADERegister("AIRMSOS"               ,new byte[] {(byte)0x43,(byte)0x8F} , 24 , 4 , true  )); //AIRMSOS           14
        ADEregisters.add(new ADERegister("AVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x90} , 24 , 4 , true  )); //AVRMSOS	         15
        ADEregisters.add(new ADERegister("BIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x91} , 24 , 4 , true  )); //BIRMSOS	         16
        ADEregisters.add(new ADERegister("BVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x92} , 24 , 4 , true  )); //BVRMSOS	         17
        ADEregisters.add(new ADERegister("CIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x93} , 24 , 4 , true  )); //CIRMSOS	         18
        ADEregisters.add(new ADERegister("CVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x94} , 24 , 4 , true  )); //CVRMSOS	         19
        ADEregisters.add(new ADERegister("NIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0x95} , 24 , 4 , true  )); //NIRMSOS	         20
        ADEregisters.add(new ADERegister("HPGAIN"	         ,new byte[] {(byte)0x43,(byte)0x98} , 24 , 4 , true  )); //HPGAIN	         21
        ADEregisters.add(new ADERegister("ISUMLVL"	         ,new byte[] {(byte)0x43,(byte)0x99} , 24 , 4 , true  )); //ISUMLVL	         22
        ADEregisters.add(new ADERegister("VLEVEL"	         ,new byte[] {(byte)0x43,(byte)0x9F} , 28 , 4 , true  )); //VLEVEL	         23
        ADEregisters.add(new ADERegister("AFWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xA2} , 24 , 4 , true  )); //AFWATTOS	         24
        ADEregisters.add(new ADERegister("BFWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xA3} , 24 , 4 , true  )); //BFWATTOS	         25
        ADEregisters.add(new ADERegister("CFWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xA4} , 24 , 4 , true  )); //CFWATTOS	         26
        ADEregisters.add(new ADERegister("AFVAROS"	         ,new byte[] {(byte)0x43,(byte)0xA5} , 24 , 4 , true  )); //AFVAROS	         27
        ADEregisters.add(new ADERegister("BFVAROS"	         ,new byte[] {(byte)0x43,(byte)0xA6} , 24 , 4 , true  )); //BFVAROS	         28
        ADEregisters.add(new ADERegister("CFVAROS"	         ,new byte[] {(byte)0x43,(byte)0xA7} , 24 , 4 , true  )); //CFVAROS	         29
        ADEregisters.add(new ADERegister("AFIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xA8} , 24 , 4 , true  )); //AFIRMSOS	         30
        ADEregisters.add(new ADERegister("BFIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xA9} , 24 , 4 , true  )); //BFIRMSOS	         31
        ADEregisters.add(new ADERegister("CFIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xAA} , 24 , 4 , true  )); //CFIRMSOS	         32
        ADEregisters.add(new ADERegister("AFVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xAB} , 24 , 4 , true  )); //AFVRMSOS	         33
        ADEregisters.add(new ADERegister("BFVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xAC} , 24 , 4 , true  )); //BFVRMSOS	         34
        ADEregisters.add(new ADERegister("CFVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xAD} , 24 , 4 , true  )); //CFVRMSOS	         35
        ADEregisters.add(new ADERegister("HXWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xAE} , 24 , 4 , true  )); //HXWATTOS	         36
        ADEregisters.add(new ADERegister("HYWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xAF} , 24 , 4 , true  )); //HYWATTOS	         37
        ADEregisters.add(new ADERegister("HZWATTOS"	         ,new byte[] {(byte)0x43,(byte)0xB0} , 24 , 4 , true  )); //HZWATTOS	         38
        ADEregisters.add(new ADERegister("HXVAROS"	         ,new byte[] {(byte)0x43,(byte)0xB1} , 24 , 4 , true  )); //HXVAROS	         39
        ADEregisters.add(new ADERegister("HYVAROS"	         ,new byte[] {(byte)0x43,(byte)0xB2} , 24 , 4 , true  )); //HYVAROS	         40
        ADEregisters.add(new ADERegister("HZVAROS"	         ,new byte[] {(byte)0x43,(byte)0xB3} , 24 , 4 , true  )); //HZVAROS	         41
        ADEregisters.add(new ADERegister("HXIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB4} , 24 , 4 , true  )); //HXIRMSOS	         42
        ADEregisters.add(new ADERegister("HYIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB5} , 24 , 4 , true  )); //HYIRMSOS	         43
        ADEregisters.add(new ADERegister("HZIRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB6} , 24 , 4 , true  )); //HZIRMSOS	         44
        ADEregisters.add(new ADERegister("HXVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB7} , 24 , 4 , true  )); //HXVRMSOS	         45
        ADEregisters.add(new ADERegister("HYVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB8} , 24 , 4 , true  )); //HYVRMSOS	         46
        ADEregisters.add(new ADERegister("HZVRMSOS"	         ,new byte[] {(byte)0x43,(byte)0xB9} , 24 , 4 , true  )); //HZVRMSOS	         47
        ADEregisters.add(new ADERegister("AIRMS"	         ,new byte[] {(byte)0x43,(byte)0xC0} , 24 , 4 , true , 0, 19 )); //AIRMS	 48    
        ADEregisters.add(new ADERegister("AVRMS"	         ,new byte[] {(byte)0x43,(byte)0xC1} , 24 , 4 , true , 0, 18 )); //AVRMS	 49    
        ADEregisters.add(new ADERegister("BIRMS"	         ,new byte[] {(byte)0x43,(byte)0xC2} , 24 , 4 , true , 1, 19 )); //BIRMS	 50    
        ADEregisters.add(new ADERegister("BVRMS"	         ,new byte[] {(byte)0x43,(byte)0xC3} , 24 , 4 , true , 1, 18 )); //BVRMS	 51    
        ADEregisters.add(new ADERegister("CIRMS"	         ,new byte[] {(byte)0x43,(byte)0xC4} , 24 , 4 , true , 2, 19 )); //CIRMS	 52    
        ADEregisters.add(new ADERegister("CVRMS"	         ,new byte[] {(byte)0x43,(byte)0xC5} , 24 , 4 , true , 2, 18 )); //CVRMS	 53    
        ADEregisters.add(new ADERegister("NIRMS"	         ,new byte[] {(byte)0x43,(byte)0xC6} , 24 , 4 , true  )); //NIRMS	         54
        ADEregisters.add(new ADERegister("ISUM"	                 ,new byte[] {(byte)0x43,(byte)0xC7} , 28 , 4 , true  )); //ISUM	         55
        ADEregisters.add(new ADERegister("RUN"	                 ,new byte[] {(byte)0xE2,(byte)0x28} , 16 , 2 , false  )); //RUN	         56
        ADEregisters.add(new ADERegister("AWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x00} , 32 , 4 , true  )); //AWATTHR	         57
        ADEregisters.add(new ADERegister("BWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x01} , 32 , 4 , true  )); //BWATTHR	         58
        ADEregisters.add(new ADERegister("CWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x02} , 32 , 4 , true  )); //CWATTHR	         59
        ADEregisters.add(new ADERegister("AFWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x03} , 32 , 4 , true  )); //AFWATTHR	         60
        ADEregisters.add(new ADERegister("BFWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x04} , 32 , 4 , true  )); //BFWATTHR	         61
        ADEregisters.add(new ADERegister("CFWATTHR"	         ,new byte[] {(byte)0xE4,(byte)0x05} , 32 , 4 , true  )); //CFWATTHR	         62
        ADEregisters.add(new ADERegister("AFVARHR"	         ,new byte[] {(byte)0xE4,(byte)0x09} , 32 , 4 , true  )); //AFVARHR	         63
        ADEregisters.add(new ADERegister("BFVARHR"	         ,new byte[] {(byte)0xE4,(byte)0x0A} , 32 , 4 , true  )); //BFVARHR	         64
        ADEregisters.add(new ADERegister("CFVARHR"	         ,new byte[] {(byte)0xE4,(byte)0x0B} , 32 , 4 , true  )); //CFVARHR	         65
        ADEregisters.add(new ADERegister("AVAHR"	         ,new byte[] {(byte)0xE4,(byte)0x0C} , 32 , 4 , true  )); //AVAHR	         66
        ADEregisters.add(new ADERegister("BVAHR"	         ,new byte[] {(byte)0xE4,(byte)0x0D} , 32 , 4 , true  )); //BVAHR	         67
        ADEregisters.add(new ADERegister("CVAHR"	         ,new byte[] {(byte)0xE4,(byte)0x0E} , 32 , 4 , true  )); //CVAHR	         68
        ADEregisters.add(new ADERegister("IPEAK"	         ,new byte[] {(byte)0xE5,(byte)0x00} , 32 , 4 , false  )); //IPEAK	         69
        ADEregisters.add(new ADERegister("VPEAK"	         ,new byte[] {(byte)0xE5,(byte)0x01} , 32 , 4 , false  )); //VPEAK	         70
        ADEregisters.add(new ADERegister("STATUS0"	         ,new byte[] {(byte)0xE5,(byte)0x02} , 32 , 4 , false  )); //STATUS0	         71
        ADEregisters.add(new ADERegister("STATUS1"	         ,new byte[] {(byte)0xE5,(byte)0x03} , 32 , 4 , false  )); //STATUS1	         72
        ADEregisters.add(new ADERegister("AIMAV"	         ,new byte[] {(byte)0xE5,(byte)0x04} , 20 , 4 , false  )); //AIMAV	         73
        ADEregisters.add(new ADERegister("BIMAV"	         ,new byte[] {(byte)0xE5,(byte)0x05} , 20 , 4 , false  )); //BIMAV	         74
        ADEregisters.add(new ADERegister("CIMAV"	         ,new byte[] {(byte)0xE5,(byte)0x06} , 20 , 4 , false  )); //CIMAV	         75
        ADEregisters.add(new ADERegister("OILVL"	         ,new byte[] {(byte)0xE5,(byte)0x07} , 24 , 4 , false  )); //OILVL	         76
        ADEregisters.add(new ADERegister("OVLVL"	         ,new byte[] {(byte)0xE5,(byte)0x08} , 24 , 4 , false  )); //OVLVL	         77
        ADEregisters.add(new ADERegister("SAGLVL"	         ,new byte[] {(byte)0xE5,(byte)0x09} , 24 , 4 , false  )); //SAGLVL	         78
        ADEregisters.add(new ADERegister("MASK0"	         ,new byte[] {(byte)0xE5,(byte)0x0A} , 32 , 4 , false  )); //MASK0	         79
        ADEregisters.add(new ADERegister("MASK1"	         ,new byte[] {(byte)0xE5,(byte)0x0B} , 32 , 4 , false  )); //MASK1	         80
        ADEregisters.add(new ADERegister("IAWV"	                 ,new byte[] {(byte)0xE5,(byte)0x0C} , 24 , 4 , true  )); //IAWV	         81
        ADEregisters.add(new ADERegister("IBWV"	                 ,new byte[] {(byte)0xE5,(byte)0x0D} , 24 , 4 , true  )); //IBWV	         82
        ADEregisters.add(new ADERegister("ICWV"	                 ,new byte[] {(byte)0xE5,(byte)0x0E} , 24 , 4 , true  )); //ICWV	         83
        ADEregisters.add(new ADERegister("INWV"	                 ,new byte[] {(byte)0xE5,(byte)0x0F} , 24 , 4 , true  )); //INWV	         84
        ADEregisters.add(new ADERegister("VAWV"	                 ,new byte[] {(byte)0xE5,(byte)0x10} , 24 , 4 , true  )); //VAWV	         85
        ADEregisters.add(new ADERegister("VBWV"	                 ,new byte[] {(byte)0xE5,(byte)0x11} , 24 , 4 , true  )); //VBWV	         86
        ADEregisters.add(new ADERegister("VCWV"	                 ,new byte[] {(byte)0xE5,(byte)0x12} , 24 , 4 , true  )); //VCWV	         87
        ADEregisters.add(new ADERegister("AWATT"	         ,new byte[] {(byte)0xE5,(byte)0x13} , 24 , 4 , true  )); //AWATT	         88
        ADEregisters.add(new ADERegister("BWATT"	         ,new byte[] {(byte)0xE5,(byte)0x14} , 24 , 4 , true  )); //BWATT	         89
        ADEregisters.add(new ADERegister("CWATT"	         ,new byte[] {(byte)0xE5,(byte)0x15} , 24 , 4 , true  )); //CWATT	         90
        ADEregisters.add(new ADERegister("AFVAR"	         ,new byte[] {(byte)0xE5,(byte)0x16} , 24 , 4 , true  )); //AFVAR	         91
        ADEregisters.add(new ADERegister("BFVAR"	         ,new byte[] {(byte)0xE5,(byte)0x17} , 24 , 4 , true  )); //BFVAR	         92
        ADEregisters.add(new ADERegister("CFVAR"	         ,new byte[] {(byte)0xE5,(byte)0x18} , 24 , 4 , true  )); //CFVAR	         93
        ADEregisters.add(new ADERegister("AVA"	                 ,new byte[] {(byte)0xE5,(byte)0x19} , 24 , 4 , true  )); //AVA	         94
        ADEregisters.add(new ADERegister("BVA"	                 ,new byte[] {(byte)0xE5,(byte)0x1A} , 24 , 4 , true  )); //BVA	         95
        ADEregisters.add(new ADERegister("CVA"	                 ,new byte[] {(byte)0xE5,(byte)0x1B} , 24 , 4 , true  )); //CVA	         96
        ADEregisters.add(new ADERegister("CHECKSUM"	         ,new byte[] {(byte)0xE5,(byte)0x1F} , 32 , 4 , false  )); //CHECKSUM	         97
        ADEregisters.add(new ADERegister("VNOM"	                 ,new byte[] {(byte)0xE5,(byte)0x20} , 24 , 4 , true  )); //VNOM	         98
        ADEregisters.add(new ADERegister("LAST_RWDATA_24bit"     ,new byte[] {(byte)0xE5,(byte)0xFF} , 32 , 4 , false  )); //LAST_RWDATA_24bit 99
        ADEregisters.add(new ADERegister("PHSTATUS"              ,new byte[] {(byte)0xE6,(byte)0x00} , 16 , 2 , false  )); //PHSTATUS          100
        ADEregisters.add(new ADERegister("ANGLE0"	         ,new byte[] {(byte)0xE6,(byte)0x01} , 16 , 2 , false  )); //ANGLE0	         101
        ADEregisters.add(new ADERegister("ANGLE1"	         ,new byte[] {(byte)0xE6,(byte)0x02} , 16 , 2 , false  )); //ANGLE1	         102
        ADEregisters.add(new ADERegister("ANGLE2"	         ,new byte[] {(byte)0xE6,(byte)0x03} , 16 , 2 , false  )); //ANGLE2	         103
        ADEregisters.add(new ADERegister("PHNOLOAD"	         ,new byte[] {(byte)0xE6,(byte)0x08} , 16 , 2 , false  )); //PHNOLOAD	         104
        ADEregisters.add(new ADERegister("LINECYC"	         ,new byte[] {(byte)0xE6,(byte)0x0C} , 16 , 2 , false  )); //LINECYC	         105
        ADEregisters.add(new ADERegister("ZXTOUT"	         ,new byte[] {(byte)0xE6,(byte)0x0D} , 16 , 2 , false  )); //ZXTOUT	         106
        ADEregisters.add(new ADERegister("COMPMODE"	         ,new byte[] {(byte)0xE6,(byte)0x0E} , 16 , 2 , false  )); //COMPMODE	         107
        ADEregisters.add(new ADERegister("Gain"	                 ,new byte[] {(byte)0xE6,(byte)0x0F} , 16 , 2 , false  )); //Gain	         108
        ADEregisters.add(new ADERegister("CFMODE"	         ,new byte[] {(byte)0xE6,(byte)0x10} , 16 , 2 , false  )); //CFMODE	         109
        ADEregisters.add(new ADERegister("CF1DEN"	         ,new byte[] {(byte)0xE6,(byte)0x11} , 16 , 2 , false  )); //CF1DEN	         110
        ADEregisters.add(new ADERegister("CF2DEN"	         ,new byte[] {(byte)0xE6,(byte)0x12} , 16 , 2 , false  )); //CF2DEN	         111
        ADEregisters.add(new ADERegister("CF3DEN"	         ,new byte[] {(byte)0xE6,(byte)0x13} , 16 , 2 , false  )); //CF3DEN	         112
        ADEregisters.add(new ADERegister("APHCAL"	         ,new byte[] {(byte)0xE6,(byte)0x14} , 10 , 2 , true  )); //APHCAL	         113
        ADEregisters.add(new ADERegister("BPHCAL"	         ,new byte[] {(byte)0xE6,(byte)0x15} , 10 , 2 , true  )); //BPHCAL	         114
        ADEregisters.add(new ADERegister("CPHCAL"	         ,new byte[] {(byte)0xE6,(byte)0x16} , 10 , 2 , true  )); //CPHCAL	         115
        ADEregisters.add(new ADERegister("PHSIGN"	         ,new byte[] {(byte)0xE6,(byte)0x17} , 16 , 2 , false  )); //PHSIGN	         116
        ADEregisters.add(new ADERegister("CONFIG"	         ,new byte[] {(byte)0xE6,(byte)0x18} , 16 , 2 , false  )); //CONFIG	         117
        ADEregisters.add(new ADERegister("MMODE"	         ,new byte[] {(byte)0xE7,(byte)0x00} , 8  , 1 , false  )); //MMODE	         118
        ADEregisters.add(new ADERegister("ACCMODE"	         ,new byte[] {(byte)0xE7,(byte)0x01} , 8  , 1 , false  )); //ACCMODE	         119
        ADEregisters.add(new ADERegister("LCYCMODE"	         ,new byte[] {(byte)0xE7,(byte)0x02} , 8  , 1 , false  )); //LCYCMODE	         120
        ADEregisters.add(new ADERegister("PEAKCYC"	         ,new byte[] {(byte)0xE7,(byte)0x03} , 8  , 1 , false  )); //PEAKCYC	         121
        ADEregisters.add(new ADERegister("SAGCYC"	         ,new byte[] {(byte)0xE7,(byte)0x04} , 8  , 1 , false  )); //SAGCYC	         122
        ADEregisters.add(new ADERegister("CFCYC"	         ,new byte[] {(byte)0xE7,(byte)0x05} , 8  , 1 , false  )); //CFCYC	         123
        ADEregisters.add(new ADERegister("HSDC_CFG"	         ,new byte[] {(byte)0xE7,(byte)0x06} , 8  , 1 , false  )); //HSDC_CFG	         124
        ADEregisters.add(new ADERegister("Version"	         ,new byte[] {(byte)0xE7,(byte)0x07} , 8  , 1 , false  )); //Version	         125
        ADEregisters.add(new ADERegister("LAST_RWDATA_8bit"      ,new byte[] {(byte)0xE7,(byte)0xFD} , 8  , 1 , false  )); //LAST_RWDATA_8bit  126
        ADEregisters.add(new ADERegister("FVRMS"	         ,new byte[] {(byte)0xE8,(byte)0x80} , 24 , 4 , true  )); //FVRMS	         127
        ADEregisters.add(new ADERegister("FIRMS"	         ,new byte[] {(byte)0xE8,(byte)0x81} , 24 , 4 , true  )); //FIRMS	         128
        ADEregisters.add(new ADERegister("FWATT"	         ,new byte[] {(byte)0xE8,(byte)0x82} , 24 , 4 , true  )); //FWATT	         129
        ADEregisters.add(new ADERegister("FVAR"	                 ,new byte[] {(byte)0xE8,(byte)0x83} , 24 , 4 , true  )); //FVAR	         130
        ADEregisters.add(new ADERegister("FVA"	                 ,new byte[] {(byte)0xE8,(byte)0x84} , 24 , 4 , true  )); //FVA	         131
        ADEregisters.add(new ADERegister("FPF"	                 ,new byte[] {(byte)0xE8,(byte)0x85} , 24 , 4 , true  )); //FPF	         132
        ADEregisters.add(new ADERegister("VTHDN"	         ,new byte[] {(byte)0xE8,(byte)0x86} , 24 , 4 , true  )); //VTHDN	         133
        ADEregisters.add(new ADERegister("ITHDN"	         ,new byte[] {(byte)0xE8,(byte)0x87} , 24 , 4 , true  )); //ITHDN	         134
        ADEregisters.add(new ADERegister("HXVRMS"	         ,new byte[] {(byte)0xE8,(byte)0x88} , 24 , 4 , true  )); //HXVRMS	         135
        ADEregisters.add(new ADERegister("HXIRMS"	         ,new byte[] {(byte)0xE8,(byte)0x89} , 24 , 4 , true  )); //HXIRMS	         136
        ADEregisters.add(new ADERegister("HXWATT"	         ,new byte[] {(byte)0xE8,(byte)0x8A} , 24 , 4 , true  )); //HXWATT	         137
        ADEregisters.add(new ADERegister("HXVAR"	         ,new byte[] {(byte)0xE8,(byte)0x8B} , 24 , 4 , true  )); //HXVAR	         138
        ADEregisters.add(new ADERegister("HXVA"	                 ,new byte[] {(byte)0xE8,(byte)0x8C} , 24 , 4 , true  )); //HXVA	         139
        ADEregisters.add(new ADERegister("HXPF"	                 ,new byte[] {(byte)0xE8,(byte)0x8D} , 24 , 4 , true  )); //HXPF	         140
        ADEregisters.add(new ADERegister("HXVHD"	         ,new byte[] {(byte)0xE8,(byte)0x8E} , 24 , 4 , true  )); //HXVHD	         141
        ADEregisters.add(new ADERegister("HXIHD"	         ,new byte[] {(byte)0xE8,(byte)0x8F} , 24 , 4 , true  )); //HXIHD	         142
        ADEregisters.add(new ADERegister("HYVRMS"	         ,new byte[] {(byte)0xE8,(byte)0x90} , 24 , 4 , true  )); //HYVRMS	         143
        ADEregisters.add(new ADERegister("HYIRMS"	         ,new byte[] {(byte)0xE8,(byte)0x91} , 24 , 4 , true  )); //HYIRMS	         144
        ADEregisters.add(new ADERegister("HYWATT"	         ,new byte[] {(byte)0xE8,(byte)0x92} , 24 , 4 , true  )); //HYWATT	         145
        ADEregisters.add(new ADERegister("HYVAR"	         ,new byte[] {(byte)0xE8,(byte)0x93} , 24 , 4 , true  )); //HYVAR	         146
        ADEregisters.add(new ADERegister("HYVA"	                 ,new byte[] {(byte)0xE8,(byte)0x94} , 24 , 4 , true  )); //HYVA	         147
        ADEregisters.add(new ADERegister("HYPF"	                 ,new byte[] {(byte)0xE8,(byte)0x95} , 24 , 4 , true  )); //HYPF	         148
        ADEregisters.add(new ADERegister("HYVHD"	         ,new byte[] {(byte)0xE8,(byte)0x96} , 24 , 4 , true  )); //HYVHD	         149
        ADEregisters.add(new ADERegister("HYIHD"	         ,new byte[] {(byte)0xE8,(byte)0x97} , 24 , 4 , true  )); //HYIHD	         150
        ADEregisters.add(new ADERegister("HZVRMS"	         ,new byte[] {(byte)0xE8,(byte)0x98} , 24 , 4 , true  )); //HZVRMS	         151
        ADEregisters.add(new ADERegister("HZIRMS"	         ,new byte[] {(byte)0xE8,(byte)0x99} , 24 , 4 , true  )); //HZIRMS	         152
        ADEregisters.add(new ADERegister("HZWATT"	         ,new byte[] {(byte)0xE8,(byte)0x9A} , 24 , 4 , true  )); //HZWATT	         153
        ADEregisters.add(new ADERegister("HZVAR"	         ,new byte[] {(byte)0xE8,(byte)0x9B} , 24 , 4 , true  )); //HZVAR	         154
        ADEregisters.add(new ADERegister("HZVA"	                 ,new byte[] {(byte)0xE8,(byte)0x9C} , 24 , 4 , true  )); //HZVA	         155
        ADEregisters.add(new ADERegister("HZPF"	                 ,new byte[] {(byte)0xE8,(byte)0x9D} , 24 , 4 , true  )); //HZPF	         156
        ADEregisters.add(new ADERegister("HZVHD"	         ,new byte[] {(byte)0xE8,(byte)0x9E} , 24 , 4 , true  )); //HZVHD	         157
        ADEregisters.add(new ADERegister("HZIHD"	         ,new byte[] {(byte)0xE8,(byte)0x9F} , 24 , 4 , true  )); //HZIHD	         158
        ADEregisters.add(new ADERegister("HCONFIG"	         ,new byte[] {(byte)0xE9,(byte)0x00} , 16 , 2 , false  )); //HCONFIG	         159
        ADEregisters.add(new ADERegister("APF"                   ,new byte[] {(byte)0xE9,(byte)0x02} , 16 , 2 , false  )); //APF	         160
        ADEregisters.add(new ADERegister("BPF"                   ,new byte[] {(byte)0xE9,(byte)0x03} , 16 , 2 , false  )); //BPF	         161
        ADEregisters.add(new ADERegister("CPF"                   ,new byte[] {(byte)0xE9,(byte)0x04} , 16 , 2 , false  )); //CPF	         162
        ADEregisters.add(new ADERegister("APERIOD"	         ,new byte[] {(byte)0xE9,(byte)0x05} , 16 , 2 , false  )); //APERIOD	         163
        ADEregisters.add(new ADERegister("BPERIOD"	         ,new byte[] {(byte)0xE9,(byte)0x06} , 16 , 2 , false  )); //BPERIOD	         164
        ADEregisters.add(new ADERegister("CPERIOD"	         ,new byte[] {(byte)0xE9,(byte)0x07} , 16 , 2 , false  )); //CPERIOD	         165
        ADEregisters.add(new ADERegister("APNOLOAD"	         ,new byte[] {(byte)0xE9,(byte)0x08} , 16 , 2 , false  )); //APNOLOAD	         166
        ADEregisters.add(new ADERegister("VARNOLOAD"	         ,new byte[] {(byte)0xE9,(byte)0x09} , 16 , 2 , false  )); //VARNOLOAD	 167    
        ADEregisters.add(new ADERegister("VANOLOAD"	         ,new byte[] {(byte)0xE9,(byte)0x0A} , 16 , 2 , false  )); //VANOLOAD	         168
        ADEregisters.add(new ADERegister("LAST_ADD"	         ,new byte[] {(byte)0xE9,(byte)0xFE} , 16 , 2 , false  )); //LAST_ADD	         169
        ADEregisters.add(new ADERegister("LAST_RWDATA_16bit"     ,new byte[] {(byte)0xE9,(byte)0xFF} , 16 , 2 , false  )); //LAST_RWDATA_16bit 170
        ADEregisters.add(new ADERegister("CONFIG3"	         ,new byte[] {(byte)0xEA,(byte)0x00} , 8  , 1 , false  )); //CONFIG3	         171
        ADEregisters.add(new ADERegister("LAST_OP"	         ,new byte[] {(byte)0xEA,(byte)0x01} , 8  , 1 , false  )); //LAST_OP	         172
        ADEregisters.add(new ADERegister("WTHR"                  ,new byte[] {(byte)0xEA,(byte)0x02} , 8  , 1 , false  )); //WTHR	         173
        ADEregisters.add(new ADERegister("VARTHR"	         ,new byte[] {(byte)0xEA,(byte)0x03} , 8  , 1 , false  )); //VARTHR	         174
        ADEregisters.add(new ADERegister("VATHR"	         ,new byte[] {(byte)0xEA,(byte)0x04} , 8  , 1 , false  )); //VATHR	         175
        ADEregisters.add(new ADERegister("HX_reg"	         ,new byte[] {(byte)0xEA,(byte)0x08} , 8  , 1 , false  )); //HX_reg	         176
        ADEregisters.add(new ADERegister("HY_reg"	         ,new byte[] {(byte)0xEA,(byte)0x09} , 8  , 1 , false  )); //HY_reg	         177
        ADEregisters.add(new ADERegister("HZ_reg"	         ,new byte[] {(byte)0xEA,(byte)0x0A} , 8  , 1 , false  )); //HZ_reg	         178
        ADEregisters.add(new ADERegister("LPOILVL"	         ,new byte[] {(byte)0xEC,(byte)0x00} , 8  , 1 , false  )); //LPOILVL	         179
        ADEregisters.add(new ADERegister("CONFIG2"	         ,new byte[] {(byte)0xEC,(byte)0x01} , 8  , 1 , false  )); //CONFIG2	         180
        return ADEregisters;
    };

    public String getName() {
        return name;
    }

    public int getPhase() {
        return phase;
    }

    public int getQuantityIDInDatabase() {
        return quantityIDInDatabase;
    }
    
    
    static ADERegister getByName(String regString) {
        try{
            ADERegister reg = ADEregisters.stream().filter(r -> r.name.equals(regString)).findFirst().get();
            return reg;
        }
        catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }         
    }


    
}
