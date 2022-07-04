<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">


<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
	    //给"创建"按钮添加单击事件
        $("#createClueBtn").click(function () {
            //初始化工作
            $("#createClueForm")[0].reset();
            //弹出模态窗口
            $("#createClueModal").modal("show");
        });

		//给"保存"按钮添加单击事件
        $("#saveCreateClueBtn").click(function () {
            //收集参数
            var fullname       =$.trim($("#create-fullname").val());
            var appellation    =$("#create-appellation").val();
            var owner          =$("#create-owner").val();
            var company        =$.trim($("#create-company").val());
            var job            =$.trim($("#create-job").val());
            var email          =$.trim($("#create-email").val());
            var phone          =$.trim($("#create-phone").val());
            var website        =$.trim($("#create-website").val());
            var mphone         =$.trim($("#create-mphone").val());
            var state          =$("#create-state").val();
            var source         =$("#create-source").val();
            var description    =$.trim($("#create-description").val());
            var contactSummary =$.trim($("#create-contactSummary").val());
            var nextContactTime=$.trim($("#create-nextContactTime").val());
            var address        =$.trim($("#create-address").val());
			//表单验证(作业)
			//带*非空

			if(fullname == ""){
				alert("姓名不能为空");
				return;
			}
			if(owner == ""){
				alert("所有者不能为空");
				return;
			}
			if(company == ""){
				alert("公司不能为空");
				return;
			}
			//正则表达式验证
			/*
			  正则表达式：
			     1，语言，语法：定义字符串的匹配模式，可以用来判断指定的具体字符串是否符合匹配模式。
			     2,语法通则：
			       1)//:在js中定义一个正则表达式.  var regExp=/...../;
			       2)^：匹配字符串的开头位置
			         $: 匹配字符串的结尾
			       3)[]:匹配指定字符集中的一位字符。 var regExp=/^[abc]$/;
			                                    var regExp=/^[a-z0-9]$/;
			       4){}:匹配次数.var regExp=/^[abc]{5}$/;
			            {m}:匹配m此
			            {m,n}：匹配m次到n次
			            {m,}：匹配m次或者更多次
			       5)特殊符号：
			         \d:匹配一位数字，相当于[0-9]
			         \D:匹配一位非数字
			         \w：匹配所有字符，包括字母、数字、下划线。
			         \W:匹配非字符，除了字母、数字、下划线之外的字符。

			         *:匹配0次或者多次，相当于{0,}
			         +:匹配1次或者多次，相当于{1,}
			         ?:匹配0次或者1次，相当于{0,1}
			 */
			var regExp1=/^[1-9]\d{10}$/;
			if(!regExp1.test(mphone)){
				alert("电话号码格式错误");
				return;
			}
			var regExp2=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
			if(!regExp2.test(email)){
				alert("邮箱格式错误");
				return;
			}
			// var regExp3=/^[0-9]{3}-[0-9]{5,20}&/
			// var regExp3=/^\d{3}-\d[7-8]&/
			// if (!regExp3.test(phone)){
			// 	alert("公司座机格式错误");
			// 	return;
			// }
            //发送请求
            $.ajax({
                url:'workbench/clue/saveCreateClue.do',
                data:{
                     fullname       :fullname       ,
                     appellation    :appellation    ,
                     owner          :owner          ,
                     company        :company        ,
                     job            :job            ,
                     email          :email          ,
                     phone          :phone          ,
                     website        :website        ,
                     mphone         :mphone         ,
                     state          :state          ,
                     source         :source         ,
                     description    :description    ,
                     contactSummary :contactSummary ,
                     nextContactTime:nextContactTime,
                     address        :address
                },
                type:'post',
                dataType:'json',
                success:function (data) {
                    if(data.code=="1"){
                        //关闭模态窗口
                        $("#createClueModal").modal("hide");
                        //刷新线索列表，显示第一页数据，保持每页显示条数不变(作业)
						// queryClueByConditionForPage(1,10);

						//刷新市场活动列，显示第一页数据，保持每页显示条数不变
						queryClueByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                    }else{
                        //提示信息
                        alert(data.message);
                        //模态窗口不关闭
                        $("#createClueModal").modal("show");
                    }
                }
            });
        });

        //查询首页线索信息，并更新列表
		queryClueByConditionForPage(1,10);

		//给"查询"按钮添加单击事件
		$("#queryClueBtn").click(function () {
			//查询所有符合条件数据的第一页以及所有符合条件数据的总条数;
			queryClueByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
		});

		//给"全选"按钮添加单击事件
		$("#chckAll").click(function () {
			//如果"全选"按钮是选中状态，则列表中所有checkbox都选中
			/*if(this.checked==true){
				$("#tBody input[type='checkbox']").prop("checked",true);
			}else{
				$("#tBody input[type='checkbox']").prop("checked",false);
			}*/

			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});

		$("#tBody").on("click","input[type='checkbox']",function () {
			//如果列表中的所有checkbox都选中，则"全选"按钮也选中
			if($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
				$("#chckAll").prop("checked",true);
			}else{//如果列表中的所有checkbox至少有一个没选中，则"全选"按钮也取消
				$("#chckAll").prop("checked",false);
			}
		});

		//删除选中的线索信息
		$("#removeClueBtn").click(function () {
			//收集参数
			//获取列表中所有被选中的checkbox
			var chekkedIds=$("#tBody input[type='checkbox']:checked");
			if(chekkedIds.size()==0){
				alert("请选择要删除的市场活动");
				return;
			}

			if(window.confirm("确定删除吗？")){
				var ids="";
				$.each(chekkedIds,function () {//id=xxxx&id=xxx&.....&id=xxx&
					ids+="id="+this.value+"&";
				});
				ids=ids.substr(0,ids.length-1);//id=xxxx&id=xxx&.....&id=xxx

				//发送请求
				$.ajax({
					url:'workbench/clue/removeClueByIds.do',
					data:ids,
					type:'post',
					dataType:'json',
					success:function (data) {
						if(data.code=="1"){
							//刷新市场活动列表,显示第一页数据,保持每页显示条数不变
							queryClueByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
						}else{
							//提示信息
							alert(data.message);
						}
					}
				});
			}
		})

		//给"修改"按钮添加单击事件，弹出模态窗口，显示指定线索的详细信息
		$("#editClueBtn").click(function () {
			//收集参数
			//获取列表中被选中的checkbox
			var chkedIds=$("#tBody input[type='checkbox']:checked");
			if(chkedIds.size()==0){
				alert("请选择要修改的市场活动");
				return;
			}
			if(chkedIds.size()>1){
				alert("每次只能修改一条市场活动");
				return;
			}
			//var id=chkedIds.val();
			//var id=chkedIds.get(0).value;
			var id=chkedIds[0].value;
			//发送请求
			$.ajax({
				url:'workbench/clue/queryClueById.do',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					//把市场活动的信息显示在修改的模态窗口上
					$("#edit-fullname").val(data.fullname);
					$("#edit-appellation").val(data.appellation);
					$("#edit-owner").val(data.owner);
					$("#edit-company").val(data.company);
					$("#edit-job").val(data.job);
					$("#edit-email").val(data.email);
					$("#edit-phone").val(data.phone);
					$("#edit-website").val(data.website);
					$("#edit-mphone").val(data.mphone);
					$("#edit-state").val(data.state);
					$("#edit-source").val(data.source);
					$("#edit-description").val(data.description);
					$("#edit-contactSummary").val(data.contactSummary);
					$("#edit-nextContactTime").val(data.nextContactTime);
					$("#edit-address").val(data.address);
					//弹出模态窗口
					$("#editClueModal").modal("show");
				}
			});
		})

		//给"更新"按钮添加单击事件,修改选中线的索信息
		$("#editClueForDetailBtn").click(function () {
			//获取列表中被选中的checkbox
			var chkedIds=$("#tBody input[type='checkbox']:checked");
			//获取指定修改的线索信息Id
			var id=chkedIds[0].value;
			//获取修改后的线索参数
			var fullname = $("#edit-fullname").val();
			var appellation = $("#edit-appellation").val();
			var owner = $("#edit-owner").val();
			var company = $("#edit-company").val();
			var job = $("#edit-job").val();
			var email = $("#edit-email").val();
			var phone = $("#edit-phone").val();
			var website = $("#edit-website").val();
			var mphone = $("#edit-mphone").val();
			var state = $("#edit-state").val();
			var source = $("#edit-source").val();
			var description = $("#edit-description").val();
			var contactSummary = $("#edit-contactSummary").val();
			var nextContactTime = $("#edit-nextContactTime").val();
			var address = $("#edit-address").val();

			//发送请求
			$.ajax({
				url : 'workbench/clue/editClueForDetailById.do',
				data : {
					id : id,
					fullname : fullname,
					appellation : appellation,
					owner : owner,
					company : company,
					job : job,
					email : email,
					phone : phone,
					website : website,
					mphone : mphone,
					state : state,
					source : source,
					description : description,
					contactSummary : contactSummary,
					nextContactTime : nextContactTime,
					address : address
				},
				type : 'post',
				dataType : 'json',
				success : function (data) {
					if(data.code=="1"){
						//关闭模态窗口
						$("#editClueModal").modal("hide");
						//刷新市场活动列表,保持页号和每页显示条数都不变
						queryClueByConditionForPage($("#demo_pag1").bs_pagination('getOption', 'currentPage'),$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#editClueModal").modal("show");
					}
				}
			})
		})
	});


	//分页条件查询线索信息
	function queryClueByConditionForPage(pageNo,pageSize){
		//收集查询条件查询参数
		var fullname = $("#query-fullname").val();
		var company = $("#query-company").val();
		var phone = $("#query-phone").val();
		var source = $("#query-source").val();
		var owner = $("#query-owner").val();
		var mphone = $("#query-mphone").val();
		var state = $("#query-state").val();


		//发送请求
		$.ajax({
			url: 'workbench/clue/queryClueByConditionForPage.do',
			data:{
				fullname:fullname,
				company:company,
				phone:phone,
				source:source,
				owner:owner,
				mphone:mphone,
				state:state,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type: 'post',
			dataType: 'json',
			success:function (data) {
				//显示总条数
				//$("#totalRowsB").text(data.totalRows);
				//显示市场活动的列表
				//遍历activityList，拼接所有行数据
				var htmlStr="";
				$.each(data.clueList,function (index,obj) {
					htmlStr+="<tr class=\"active\">";
					htmlStr+="	<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					htmlStr+="	<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/clueDetail.do?id="+obj.id+"'\">"+obj.fullname+"</a></td>";
					htmlStr+="	<td>"+obj.company+"</td>";
					htmlStr+="	<td>"+obj.phone+"</td>";
					htmlStr+="	<td>"+obj.mphone+"</td>";
					htmlStr+="	<td>"+obj.source+"</td>";
					htmlStr+="	<td>"+obj.owner+"</td>";
					htmlStr+="	<td>"+obj.state+"</td>";
					htmlStr+="</tr>";
				});
				$("#tBody").html(htmlStr);

				//取消"全选"按钮
				$("#chckAll").prop("checked",false);

				//计算总页数
				var totalPages=1;
				if(data.totalRows % pageSize==0){
					totalPages=data.totalRows/pageSize;
				}else{
					totalPages=parseInt(data.totalRows/pageSize)+1;
				}

				//对容器调用bs_pagination工具函数，显示翻页信息
				$("#demo_pag1").bs_pagination({
					currentPage:pageNo,//当前页号,相当于pageNo

					rowsPerPage:pageSize,//每页显示条数,相当于pageSize
					totalRows:data.totalRows,//总条数
					totalPages: totalPages,  //总页数,必填参数.

					visiblePageLinks:5,//最多可以显示的卡片数

					showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
					showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
					showRowsInfo:true,//是否显示记录的信息，默认true--显示

					//用户每次切换页号，都自动触发本函数;
					//每次返回切换页号之后的pageNo和pageSize
					onChangePage: function(event,pageObj) { // returns page_num and rows_per_page after a link has clicked
						//js代码
						// alert(pageObj.currentPage);
						// alert(pageObj.rowsPerPage);
						queryClueByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		})
	}

</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form id="createClueForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  <c:forEach items="${userList}" var="u">
                                      <option value="${u.id}">${u.name}</option>
                                  </c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
								  <option></option>
								  <c:forEach items="${appellationList}" var="app">
                                    <option value="${app.id}">${app.value}</option>
                                  </c:forEach>
								</select>
							</div>
							<label for="create-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-state">
								  <option></option>
								  <c:forEach items="${clueStateList}" var="cs">
                                        <option value="${cs.id}">${cs.value}</option>
                                  </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								  <c:forEach items="${sourceList}" var="sl">
                                      <option value="${sl.id}">${sl.value}</option>
                                  </c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateClueBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
                                    <c:forEach items="${userList}" var="u">
                                        <option value="${u.id}">${u.name}</option>
                                    </c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
								  <option></option>
                                    <c:forEach items="${appellationList}" var="app">
                                        <option value="${app.id}">${app.value}</option>
                                    </c:forEach>
								</select>
							</div>
							<label for="edit-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone">
							</div>
							<label for="edit-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-state">
								  <option></option>
                                    <c:forEach items="${clueStateList}" var="cs">
                                        <option value="${cs.id}">${cs.value}</option>
                                    </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
                                    <c:forEach items="${sourceList}" var="sl">
                                        <option value="${sl.id}">${sl.value}</option>
                                    </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="editClueForDetailBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-fullname">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="query-company">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="query-phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="query-source">
					  	  <option></option>
                          <c:forEach items="${sourceList}" var="sl">
                              <option value="${sl.id}">${sl.value}</option>
                          </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="query-mphone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="query-state">
					  	<option></option>
                          <c:forEach items="${clueStateList}" var="cs">
                              <option value="${cs.id}">${cs.value}</option>
                          </c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryClueBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createClueBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editClueBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="removeClueBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="chckAll"/></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="tBody">
<%--						<tr>--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/clue/clueDetail.do?id=7591cbff259c4d9591c50ec4b30bd935';">张三教授</a></td>--%>
<%--							<td>动力节点</td>--%>
<%--							<td>010-84846003</td>--%>
<%--							<td>12345678901</td>--%>
<%--							<td>广告</td>--%>
<%--							<td>zhangsan</td>--%>
<%--							<td>已联系</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>--%>
<%--                            <td>动力节点</td>--%>
<%--                            <td>010-84846003</td>--%>
<%--                            <td>12345678901</td>--%>
<%--                            <td>广告</td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>已联系</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>
			
<%--			<div style="height: 50px; position: relative;top: 60px;">--%>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
<%--			</div>--%>
			
		</div>
		
	</div>
</body>
</html>