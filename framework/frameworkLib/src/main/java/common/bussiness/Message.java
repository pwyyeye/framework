package common.bussiness;

public class Message implements java.io.Serializable{
        private int type;//��������
        private String content;//����
        private String subject;//����
        private String[] attachs;
        public String[] getAttachs() {
			return attachs;
		}
		public void setAttachs(String[] attachs) {
			this.attachs = attachs;
		}
		public Message(int type, String content, String subject) {
                this.type = type;
                this.content = content;
                this.subject = subject;
        }
		public Message(int type, String content, String subject,String[] attachs) {
            this.type = type;
            this.content = content;
            this.subject = subject;
            this.attachs=attachs;
    }
        public String getContent() {
                return content;
        }
        public void setContent(String content) {
                this.content = content;
        }
        public String getSubject() {
                return subject;
        }
        public void setSubject(String subject) {
                this.subject = subject;
        }
        public int getType() {
                return type;
        }
        public void setType(int type) {
                this.type = type;
        }

}
