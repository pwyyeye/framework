package common.web.tag;

public class LineCombo  extends BaseCombo {

	public String getDefaultFieldLabel() {
		return "线别";
	}

	public String getDefaultFieldName() {
		return "lineField";
	}

	public String getDefaultHiddenName() {
		return "lineID";
	}

	public String getDefaultName() {
		return "lineID";
	}

	public String getUrl() {
		return "../PPWeb/lineAction.do?action=list";
	}

}

