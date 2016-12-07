package common.web.tag;

import common.web.utils.SemWebAppUtils;

public class ModelCombo  extends BaseCombo{
	
	public String noEndFlag;
	public String getNoEndFlag() {
		return noEndFlag;
	}

	public void setNoEndFlag(String noEndFlag) {
		this.noEndFlag = noEndFlag;
	}
    public boolean isNoEnd(){
    	if(SemWebAppUtils.isNotEmpty(noEndFlag)){
			if("Y".equals(noEndFlag)||"TRUE".equals(noEndFlag)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
    }
	public String getDefaultFieldLabel() {
		return "车型";
	}

	public String getDefaultFieldName() {
		return "modelField";
	}

	public String getDefaultHiddenName() {
		return "modelID";
	}

	public String getDefaultName() {
		return "modelID";
	}

	public String getUrl() {
		return "../PPWeb/carModelBaseAction.do?action=list"+(this.isNoEnd()?"":("&endcode=3"));
	}
}
