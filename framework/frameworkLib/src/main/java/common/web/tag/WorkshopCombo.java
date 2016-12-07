package common.web.tag;

public class WorkshopCombo extends BaseCombo{

	public String getDefaultFieldLabel() {
		return "车间";
	}

	public String getDefaultFieldName() {
		return "workshopField";
	}

	public String getDefaultHiddenName() {
		return "workshopID";
	}

	public String getDefaultName() {
		return "workshopID";
	}

	public String getUrl() {
		return  "../PPWeb/workshopAction.do?action=list";
	}

}
