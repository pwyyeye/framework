	<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/controls.tld" prefix="controls"%>
	<%@ include file="common.jsp"%>
	<%@ include file="uploadFile.jsp"%>
<html>
<body>

	<div class="container">
			<div class="body">
					<table class="table table-striped table-hover text-left"
						style="margin-top: 0px; "S>
						<thead>
							<tr>
								<th class="col-md-4">
									Filename
								</th>
								<th class="col-md-2">
									Size
								</th>
								<th class="col-md-6">
									Detail
								</th>
							</tr>
						</thead>
						<tbody id="fsUploadProgress">
						</tbody>
					</table>
				</div>
	<div id=container><input type=button name=pick value=上传文件 id=pickfiles></input></div>
				
			</div>
		
		</body></html>