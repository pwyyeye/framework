package common.value;

import java.io.Serializable;


import common.ws.BaseWS;

public class MailMessage 	extends BaseWS{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String[] to;
    
    private  String[] cc;
    private String subject;
    private String text;
	private String from;
	private String host;
	private String[] filenames;

	public MailMessage(String[] to, String[] cc, String subject, String text, String from, String host, String[] filenames) {
		super();
		
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.text = text;
		this.from = from;
		this.host = host;
		this.filenames = filenames;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getFilenames() {
		return filenames;
	}
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
 

 



    static {
    	typeDesc = new org.apache.axis.description.TypeDesc(MailMessage.class, true);
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://value.common", "MailMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("to");
        elemField.setXmlName(new javax.xml.namespace.QName("", "to"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("", "text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField.setFieldName("from");
        elemField.setXmlName(new javax.xml.namespace.QName("", "from"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField.setFieldName("host");
        elemField.setXmlName(new javax.xml.namespace.QName("", "host"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField.setFieldName("filenames");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filenames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
		elemField.setMinOccurs(0);
		elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        
        
        

    }



}
