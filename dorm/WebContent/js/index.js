var totalPage;
var institue = 0;
var profession = 0;
var building = 0;
var mode;
$(document).ready(function () {
	$("#institute").change(function () {
		institue = $("#institute").get(0).selectedIndex
		changeInstitute("#institute","#profession",false,"")
		$("#room-search").click()
	})
	$("#profession").change(function () {
		profession = $("#profession").get(0).selectedIndex
		$("#room-search").click()
	})
	$("#building").change(function () {
		building = $("#building").get(0).selectedIndex
		$("#room-search").click()
	})
	$("#page").change(function () {
		dormQuery()
	})
	$("#room-search").click(function () {
		mode = "condition"
		getTotalPage()
		dormQuery()
	})
	$("#name-search").click(function () {
		mode = "fuzzyName"
		getTotalPage()
		dormQuery()
	})
	$("#number-search").click(function () {
		mode = "fuzzyNumber"
		getTotalPage()
		dormQuery()
	})

	$("#firstPage").click(function(){
		if($("#page").get(0).selectedIndex!=0){
			$("#page").val("1")
			dormQuery()
		}
	})
	$("#up").click(function(){
		if($("#page").get(0).selectedIndex!=0){
			$("#page").find("option:selected").prev().prop('selected', true);
			dormQuery()
		}
	})
	$("#down").click(function(){
		if($("#page").get(0).selectedIndex!=parseInt(totalPage)-1){
			$("#page").find("option:selected").next().prop('selected', true);
			dormQuery()
		}
	})

	$("#lastPage").click(function(){
		if($("#page").get(0).selectedIndex!=parseInt(totalPage)-1){
			$("#page").val(totalPage)
			dormQuery()
		}
	})
	
	$('#add').click(function(){
		changeInstitute("#addInstituteModal","#addProfessionModal",true,"")
	})
	$('#addInstituteModal').change(function(){
		changeInstitute("#addInstituteModal","#addProfessionModal",true,"")
	})
	
	$('#mdfInstituteModal').change(function(){
		changeInstitute("#mdfInstituteModal","#mdfProfessionModal",true,"")
	})
	
	$('#delete').click(function () {
		var number = new Array()
		var td = document.getElementsByClassName('isChecked')
		for (var i in td) {
			if (td[i].checked) {
				number.push($(td[i]).parent().parent().attr('value'))
			}
		}
		if (number.length > 0) {
			$.post("DeleteServlet", {
				"stu_number": JSON.stringify(number)
			}, function (data, status) {
				getTotalPage()
				dormQuery()
			})
		}

	})
})

function dormQuery() {
	$.post("DormServlet", {
		"institute": institue,
		"profession": profession,
		"building": building,
		"room": $("#room").val(),
		"mode": mode,
		"fuzzyName": $("#name").val(),
		"fuzzyNumber": $("#number").val(),
		"page": $("#page").get(0).selectedIndex
	}, function (data, status) {
		var request = JSON.parse(data);
		dormInfoUpdate(request.object)
	})
}

function getTotalPage() {
	$.post("PageServlet", {
		"institute": institue,
		"profession": profession,
		"building": building,
		"room": $("#room").val(),
		"mode": mode,
		"fuzzyName": $("#name").val(),
		"fuzzyNumber": $("#number").val()
	}, function (data, status) {
		changeTotalPage(JSON.parse(data))
	})
}

function changeTotalPage(data) {
	totalPage = 0
	if (data.object % 5 == 0 && data.object != 0) totalPage = parseInt(data.object / 5)
	else totalPage = parseInt(data.object / 5) + 1
	$("#page").empty();
	for (var i = 1; i <= totalPage; i++) {
		$("#page").append($('<option></option>').text(i).attr('value',i))
	}
}

function changeInstitute(institute,profession,modal,select) {
	if(modal)var i = $(institute).get(0).selectedIndex+1
	else var i = $(institute).get(0).selectedIndex
	$.post("ProfessionServlet", {
		"institute": i
	}, function (data, status) {
		data = JSON.parse(data)
		$(profession).empty();
		if(!modal)$(profession).append("<option>全部</option>")
		for (var i in data) {
			$(profession).append("<option>"+data[i]+"</option>")
		}
		if(select!=""){
			$(profession).val(select)
		} 
	})
	if(!modal)
	if (i == 0) {
		$("#profession").get(0).selectedIndex = 0
		profession = 0
		$("#profession").attr("disabled", "disabled")
	} else $("#profession").removeAttr("disabled")
}

function dormInfoUpdate(data) {
	$("#mytableBody").empty();
	var head = $('<tr></tr>')
	$(head).append($('<td></td>').text("序号"))
	$(head).append($('<td></td>').text("姓名"))
	$(head).append($('<td></td>').text("学号"))
	$(head).append($('<td></td>').text("学院"))
	$(head).append($('<td></td>').text("专业"))
	$(head).append($('<td></td>').text("房间"))
	$(head).append($('<td></td>').text("宿舍楼"))
	$(head).append($('<td></td>').text("选择"))
	$("#mytableBody").append(head)
	for (var i in data) {
		var tr = $('<tr></tr>')
		for (var j in data[i]) {
			$(tr).append($('<td></td>').text(data[i][j]))
		}
		$(tr).append($('<td></td>').append($('<input class="choseDelete" type="checkbox">').addClass("isChecked")))
		$(tr).attr({
			'value': data[i].stu_number,
			'onclick': 'changeStuInfo(this)',
			'data-toggle':'modal',
			'data-target':'#mdfModal'
		});
		$("#mytableBody").append(tr);
	}
}


function addStudentInfo() {
	$.post("AddTestServlet", {
		"stu_name": $("#addName").val(),
		"stu_number": $("#addNumber").val(),
		"building": $("#addBuildingModal").get(0).selectedIndex,
		"room_name": $("#addRoomModal").val(),
		"institute_id":$("#addInstituteModal").get(0).selectedIndex,
		"profession_rank": $("#addProfessionModal").get(0).selectedIndex
	}, function (data, status) {
		var jsdata = JSON.parse(data)
		mode = "condition"
		getTotalPage()
		dormQuery()
		alert("代码：" + jsdata.status + "--" + jsdata.statusMsg)
		return false
	})
	return false
}

function changeStuInfo(tr) {
	var td = $(tr).children()
	$("#mdfName").val($(td[1]).text())
	$("#mdfNumber").val($(td[2]).text())
	$("#mdfNumber").addClass('disabled')
	$("#mdfInstituteModal").val($(td[3]).text())
	changeInstitute("#mdfInstituteModal","#mdfProfessionModal",true,$(td[4]).text())
	$("#mdfRoomModal").val($(td[5]).text())
	$("#mdfBuildingModal").val($(td[6]).text())
}
function mdfStudentInfo(){
	$.post("ModifyServlet", {
		"stu_name": $("#mdfName").val(),
		"stu_number": $("#mdfNumber").val(),
		"building": $("#mdfBuildingModal").get(0).selectedIndex,
		"room_name": $("#mdfRoomModal").val(),
		"institute_id":$("#mdfInstituteModal").get(0).selectedIndex,
		"profession_rank": $("#mdfProfessionModal").get(0).selectedIndex
	}, function (data, status) {
		var jsdata = JSON.parse(data)
		mode = "condition"
		getTotalPage()
		dormQuery()
		alert("代码：" + jsdata.status + "--" + jsdata.statusMsg)
		return false
	})
}








window.onload = function () {
	changeInstitute("#institute","#profession",false,"")
	$("#room-search").click()
}