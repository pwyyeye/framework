package common.web.tag;

public class MaterialCombo extends BaseCombo {

	public String getDefaultFieldLabel() {
		return "件号";
	}

	public String getDefaultFieldName() {
		return "materialField";
	}

	public String getDefaultHiddenName() {
		return "materialNo";
	}

	public String getDefaultName() {
		return "materialNo";
	}

	public String getUrl() {
		return "../commonWeb/materialAction.do?action=list";
	}

}
