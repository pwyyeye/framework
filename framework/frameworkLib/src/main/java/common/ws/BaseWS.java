package common.ws;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import org.apache.axis.description.TypeDesc;
import common.utils.SemAppUtils;

public class BaseWS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static TypeDesc typeDesc ;

	protected String getDisplayDate(java.util.Calendar pCalendar) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if (pCalendar != null)
			return format.format(pCalendar.getTime());
		else
			return "";
	}

	protected String toStr(Object obj) {
		if (obj != null)
			return obj.toString();
		else
			return null;
	}

	protected Integer str2Integer(String str) {
		Integer tempInt = null;
		try {
			if (str != null)
				tempInt = Integer.valueOf(str);
		} catch (NumberFormatException e) {
		}
		return tempInt;
	}

	protected String convertString2Edi(String str) {
		if (str == null)
			return "";
		try {
			return new String((str.trim()).getBytes("GBK"), "ISO8859_1");
		} catch (Exception ee) {
			return str;
		}

	}

	/**
	 * Return type metadata object
	 */
	public static  org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static  org.apache.axis.encoding.Serializer getSerializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static  org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}
	public String toString() {
		 return SemAppUtils.object2String(this);
	}

}
