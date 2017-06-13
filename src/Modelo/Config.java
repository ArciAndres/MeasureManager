package Modelo;
// Generated Jun 11, 2017 11:33:42 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Config generated by hbm2java
 */
public class Config  implements java.io.Serializable {


     private Integer id;
     private Meter meter;
     private String element;
     private String text;
     private Double value;
     private Boolean boolean_;
     private Date timespan;

    public Config() {
    }

    public Config(Meter meter, String element, String text, Double value, Boolean boolean_, Date timespan) {
       this.meter = meter;
       this.element = element;
       this.text = text;
       this.value = value;
       this.boolean_ = boolean_;
       this.timespan = timespan;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Meter getMeter() {
        return this.meter;
    }
    
    public void setMeter(Meter meter) {
        this.meter = meter;
    }
    public String getElement() {
        return this.element;
    }
    
    public void setElement(String element) {
        this.element = element;
    }
    public String getText() {
        return this.text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    public Double getValue() {
        return this.value;
    }
    
    public void setValue(Double value) {
        this.value = value;
    }
    public Boolean getBoolean_() {
        return this.boolean_;
    }
    
    public void setBoolean_(Boolean boolean_) {
        this.boolean_ = boolean_;
    }
    public Date getTimespan() {
        return this.timespan;
    }
    
    public void setTimespan(Date timespan) {
        this.timespan = timespan;
    }




}


