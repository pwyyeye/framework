package common.web.tag;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.web.utils.SemWebAppConstants;
import common.web.utils.SemWebAppUtils;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
public class DateKeyTag
    extends TagSupport {
  public static Log dLogger = LogFactory.getLog(DateKeyTag.class);
  public static Log sysLogger = LogFactory.getLog("sys");
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private String formName;
  private String defaultValue;
  private String type;
  private String sidVar;
  private String scope;
  private String importedDateStr;

  public String getDefaultValue() {
    return defaultValue;
  }

  public int doEndTag() throws JspException {
    //如果是日报
    dLogger.debug("start tag,argument:type=" + type + formName + defaultValue);
    JspWriter out = pageContext.getOut();
    Integer typeObj = (Integer) pageContext.getAttribute(type,
        PageContext.PAGE_SCOPE);
    int typeInt = typeObj.intValue();
    dLogger.debug("报表类型:" + typeInt);
    String sid = "";

    if (formName == null || typeInt == -1)return (this.EVAL_PAGE);

    if (defaultValue == null) defaultValue = "" +
    SemWebAppUtils.getCurrentDateKey(typeInt,false,0);
    if (sidVar != null) {
      dLogger.debug("index var=" + sidVar);
      try {
        sid = this.getSID();
        dLogger.debug("index =" + sid);
      }
      catch (Exception ee) {
    	  dLogger.error("sid值有误",ee);
        sid = "";
      }
    }

    try {
      switch (typeInt) {
        //日报
        case SemWebAppConstants.DAILY_REPORT: {
          defaultValue = defaultValue.substring(0, 4) + "-" +
              defaultValue.substring(4, 6) + "-" + defaultValue.substring(6, 8);
          out.print("<div id='calendarDIV'></div>");
          out.print("<script>");
          out.print("var dateField=new Ext.form.DateField({allowBlank:false,format :'Y-m-d',emptyText :'请选择', width : 200,name : '"+ formName+sid +"',fieldLabel:'日期'});");
          out.print("dateField.render('calendarDIV');");
          out.print("</script>");
          // out.print("日期:<input class='txtClass' type=\"text\" name=\"" + formName+sid + "\" value=" +
         //           defaultValue +
         //           " onclick='calendar()' size=10/>");
          break;
        }
        //月报
        case SemWebAppConstants.MONTHLY_REPORT: {
          out.print("<input type=\"hidden\" name=\"" + formName +sid+ "\" value=" +
                    defaultValue + ">");
          int dateKey;
          dateKey = Integer.parseInt(SemWebAppUtils.getCurrentDateKey(typeInt,false,0));
          int currentKey = dateKey;
          if (defaultValue != null) {
            currentKey = Integer.parseInt(defaultValue);
          }
          int currentYear = currentKey / 100;
          int currentMonth = currentKey % 100;

          int maxYear = dateKey / 100;

          out.print("<select class='txtClass' name='yearSelect" + sid +
                    "' size='1' onblur='selectYear" + sid + "()' value='" +
                    currentYear + "'>");
          for (int i = -10; i <= 1; i++) {
            int year = maxYear + i;
            if(year!=currentYear){
              out.print("<option value='" + year + "'>" + year + "</option>");
            }else{
              out.print("<option selected value='" + year + "'>" +
                        year + "</option>");
            }
          }

          out.print("</select>年");
          out.print(
              "<select class='txtClass'  name='monthSelect" + sid +
              "' size='1' onblur='selectYear" + sid + "()' value='" +
              currentMonth + "'>");
          for (int i = 1; i <= 12; i++) {
            DecimalFormat nf = new DecimalFormat("00");
            String dayStr = nf.format(i);
            if (i == currentMonth) out.print("<option value='" + dayStr +
                                             "' selected>" + dayStr +
                                             "</option>");
            else {
              out.print("<option value='" + dayStr + "'>" + dayStr +
                        "</option>");
            }
          }
          out.print("</select>月");
          out.print("<script>");
          out.print("function selectYear" + sid + "(){");
          out.print("var obj1=document.getElementById('yearSelect" + sid +
                    "');");
          out.print("var obj3=document.getElementById('" + formName+sid + "');");
          out.println("var obj2=document.getElementById('monthSelect" +
                      sid + "');");
          out.print("var i=obj1.selectedIndex;");
          out.print("var j=obj2.selectedIndex;");
          out.print("obj3.value=obj1.options[i].value+obj2.options[j].value;");
          out.print("}");
          out.print("</script>");
          break;
        }
        //年报
        case SemWebAppConstants.YEARLY_REPORT: {
          out.print("<input class='txtClass' type=\"hidden\" name=\"" + formName+sid + "\" value=" +
                    defaultValue + ">");

          int maxYear = Integer.parseInt(SemWebAppUtils.getCurrentDateKey(typeInt,false,0));
           int dateKey = maxYear;
          if (defaultValue != null) {
            dateKey = Integer.parseInt(defaultValue);
          }
          out.print("<select class='txtClass'  name='yearSelect" + sid +
                    "' size='1' onblur='selectYear" + sid + "()' value='" +
                    dateKey + "'>");
          for (int i = -10; i <=1; i++) {
            int year = maxYear + i;
            if (year != dateKey) {
              out.print("<option value='" + year + "'>" + year + "</option>");
            }
            else
              out.print("<option selected value='" + dateKey + "'>" +
                        dateKey + "</option>");
          }
          out.print("</select>年");
          out.print("<script>");
          out.print("function selectYear" + sid + "(){");
          out.print("var obj1=document.getElementById('yearSelect" + sid +
                    "');");
          out.print("var obj2=document.getElementById('" + formName+sid + "');");
          out.print("var i=obj1.selectedIndex;");
          out.print("obj2.value=obj1.options[i].value;");
          out.print("}");
          out.print("</script>");
          break;
        }
        //周报
        case SemWebAppConstants.WEEKLY_REPORT: {
          out.print("日期:<input class='txtClass' type=\"text\" name=" + formName + " value=" +
                    defaultValue + " onclick='calendar()'/>");
          break;
        }
        //季报
        case SemWebAppConstants.QUARTERLY_REPORT: {
          out.print("<input type=\"hidden\" name=\"" + formName +sid+ "\" value=" +
                   defaultValue + ">");
         int dateKey;
         dateKey = Integer.parseInt(SemWebAppUtils.getCurrentDateKey(typeInt,false,0));
         int currentKey = dateKey;
         if (defaultValue != null) {
           currentKey = Integer.parseInt(defaultValue);
         }
         int currentYear = currentKey / 10;
         int currentMonth = currentKey % 10;

         int maxYear = dateKey / 10;

         out.print("<select class='txtClass' name='yearSelect" + sid +
                   "' size='1' onblur='selectYear" + sid + "()' value='" +
                   currentYear + "'>");
         for (int i = -10; i <= 1; i++) {
           int year = maxYear + i;
           if(year!=currentYear){
             out.print("<option value='" + year + "'>" + year + "</option>");
           }else{
             out.print("<option selected value='" + year + "'>" +
                       year + "</option>");
           }
         }

         out.print("</select>年");
         out.print(
             "<select class='txtClass'  name='quarterlySelect" + sid +
             "' size='1' onblur='selectYear" + sid + "()' value='" +
             currentMonth + "'>");
         for (int i = 1; i <= 4; i++) {
           DecimalFormat nf = new DecimalFormat("00");
           String dayStr = nf.format(i);
           if (i == currentMonth) out.print("<option value='" + dayStr +
                                            "' selected>" + dayStr +
                                            "</option>");
           else {
             out.print("<option value='" + dayStr + "'>" + dayStr +
                       "</option>");
           }
         }
         out.print("</select>月");
         out.print("<script>");
         out.print("function selectYear" + sid + "(){");
         out.print("var obj1=document.getElementById('yearSelect" + sid +
                   "');");
         out.print("var obj3=document.getElementById('" + formName+sid + "');");
         out.println("var obj2=document.getElementById('quarterlySelect" +
                     sid + "');");
         out.print("var i=obj1.selectedIndex;");
         out.print("var j=obj2.selectedIndex;");
         out.print("obj3.value=obj1.options[i].value+obj2.options[j].value;");
         out.print("}");
         out.print("</script>");
         break;
        }
        default:
          return EVAL_PAGE;
      }
    }
    catch (IOException ee) {
      ee.printStackTrace();
    }

    return (EVAL_PAGE);
  }

  /**
   * Release all state information set by this tag.
   */
  public void release() {
    this.defaultValue = null;
    this.formName = null;
    this.type = null;
  }

  public String getFormName() {
    return formName;
  }

  public void setFormName(String formName) {
    this.formName = formName;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getSidVar() {
    return sidVar;
  }

  public String getScope() {
    return scope;
  }

  public void setSidVar(String sidVar) {
    this.sidVar = sidVar;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  protected String getSID() throws JspException {

    Object treeControl = null;
    if (treeControl == null)
      treeControl = pageContext.findAttribute(sidVar);
    if (treeControl == null)
      treeControl =
          pageContext.getAttribute(sidVar, PageContext.PAGE_SCOPE);
    if (treeControl == null)
      treeControl =
          pageContext.getAttribute(sidVar, PageContext.REQUEST_SCOPE);
    if (treeControl == null)
      treeControl =
          pageContext.getAttribute(sidVar, PageContext.SESSION_SCOPE);
    if (treeControl == null)
      treeControl =
          pageContext.getAttribute(sidVar, PageContext.APPLICATION_SCOPE);
    Integer result = (Integer) treeControl;
    return (result.toString());

  }

  public static void main(String[] args) {
    int temp = 200605;
    int currentYear = temp / 100;
    int currentMonth = temp % 100;
    System.out.println(currentYear + ":" + currentMonth);
  }
  public String getImportedDateStr() {
    return importedDateStr;
  }
  public void setImportedDateStr(String importedDateStr) {
    this.importedDateStr = importedDateStr;
  }
}
