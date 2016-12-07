package common.web.tag;

public class ControlPointCombo extends BaseCombo {

	public String getDefaultFieldLabel() {
		return "管制点";
	}

	public String getDefaultFieldName() {
		return "controlPointField";
	}

	public String getDefaultHiddenName() {
		return "controlPointId";
	}

	public String getDefaultName() {
		return "controlPointId";
	}

	public String getUrl() {
		return "../MesWeb/controlPointAction.do?action=list";
	}

	public String getDisplayName() {
		
		return "controlPointName";
	}

}
