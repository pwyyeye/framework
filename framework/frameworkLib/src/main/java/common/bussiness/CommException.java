package common.bussiness;

import java.io.Serializable;

public class CommException extends Exception implements Serializable{
   private static final long serialVersionUID = 1L;
   public static String  message ;
        public CommException(String msg, Throwable th)
        {
                super(msg);
                message=msg;
        }

        public CommException(String msg, Exception ex)
        {
                super(msg);
                message=msg;
        }

        public CommException(Exception ex)
        {
                super(ex);
        }

        public CommException(String msg)
        {
                super(msg);
                message=msg;
        }
        public String getMessage(){
          return message;
        }


}
