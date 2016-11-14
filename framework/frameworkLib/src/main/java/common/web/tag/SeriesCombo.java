package common.web.tag;

import common.web.utils.SemWebAppUtils;

public class SeriesCombo extends BaseCombo {
	private String seriesType;

	public String getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	private int getSeriesTypeInt() {
		int seriesTypeInt = -1;
		if (SemWebAppUtils.isEmpty(seriesType)) {
			try {
				seriesTypeInt = Integer.parseInt(seriesType);
			} catch (Exception ee) {
				seriesTypeInt = -1;
			}
		}
		return seriesTypeInt;
	}

	public String getDefaultFieldLabel() {
		return "车系";
	}

	public String getDefaultFieldName() {
		return "seriesField";
	}

	public String getDefaultHiddenName() {
		return "seriesID";
	}

	public String getDefaultName() {
		return "seriesID";
	}

	public String getUrl() {
		return "../PPWeb/seriesAction.do?action=list"
				+ (getSeriesTypeInt() == -1 ? ""
						: ("&seriesType=" + getSeriesTypeInt()));
	}
}
