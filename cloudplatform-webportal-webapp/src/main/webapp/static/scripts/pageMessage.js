/**
 * 
 */

function pageMessage(type,content) {
	var htmlContent;
	if(type == "warning"){
		htmlContent = "<p class=\"bg-warning\" style=\"color:red;font-size:16px;\"><strong>警告!</strong>"+content+"</p>"
	}else if(type == "success"){
		htmlContent = "<p class=\"bg-success\" style=\"color:red;font-size:16px;\"><strong>成功!</strong>"+content+"</p>"
	}else if(type == "danger"){
		htmlContent = "<p class=\"bg-danger\" style=\"color:red;font-size:16px;\"><strong>失败!</strong>"+content+"</p>"
	}else{
		htmlContent = "<p class=\"bg-info\" style=\"color:red;font-size:16px;\"><strong>信息!</strong>"+content+"</p>"
	}
	return htmlContent;
}
