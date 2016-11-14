package common.web.tag;

public class ColorCombo extends BaseCombo {

	public String getDefaultFieldLabel() {
		return "颜色";
	}

	public String getDefaultFieldName() {
		return "colorField";
	}

	public String getDefaultHiddenName() {
		return "colorID";
	}

	public String getDefaultName() {
		return "modelID";
	}

	public String getUrl() {
		return "../PPWeb/colorAction.do?action=list";
	}

}
