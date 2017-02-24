/*
*AUTHOR:MT
*DATE:2017-1-15
*REVISER:
*LAST CHANGE:
*/

app.controller("solutionmanagerCtrl_1_0",function($uibModal,$log,$scope,$state,$http,factory_cookie,jsonToStr,Upload){
	var ctrl = this;

	//切换Panel
	$scope.isSwitch = true;

	//Cookie
	$scope.myCookie = {
		userid:factory_cookie.getCookie("userid"),
		token:factory_cookie.getCookie("token")
	};

	//查询
	$scope.search = {
		show:true,
		s_projectname:"", //用于查询
		s_uueser:{ //获取的第一个值，后续用于查询
			checkenter:0,
			uname:"无条件",
			userid:""
		},
		search_uueser:[], //获取的值列表，用于展现
		s_pagePerNum:10, //每页显示的个数
		s_pageNum:1, //页数
		s_items:0 //获取到的数组取length
	};
	$scope.projectinfo = []; //项目列表数据声明

	//info面板
	var flag_projectID;
	var file_temp=[[],[],[]];
	$scope.tab_info = { //tab配置
		active:0,
		isChange:false, //控制修改相关
		isPerson:false, //是否可查看参与人面板
		isUpfile:false, //是否可以修改文件
		info:{ //基本信息-初始化
			projectname:"", //项目名
			projectuname:"", //创建者
			projectinfo:"", //项目概述
			projectid:"", //项目id
			projectuserid:"", //创建者ID
			countperson:0, //参与人数
			countxq:0, //需求文件数量
			countsc:0, //素材文件数量
			countal:0, //案例文件数量
			rownum:""
		},
		s_userinfo:"", //项目参与人-用于展示
		userinfo:[], //项目参与人-仅显示添加的
		usercheck:[], //项目参与人-用于操作
		files:{
			isFile_1:true, //展示用
			isFile_2:true,
			isFile_3:true,
			files_1:[], //存放获取到的文件
			files_2:[],
			files_3:[],
			u_file_1:[], //上传文件用
			u_file_3:[],
			u_file_3:[]
		}
	};
	ko();

	//刷新页面
	$scope.solutionmanager_refresh = function() {
		$state.reload("workspace.solutionmanager_1_0");
	}
	//每页显示个数
	$scope.set_pagePerNum = function(p) { 
		$scope.search.s_pagePerNum = p;
		ko();
	}
	//查询按钮
	$scope.searchproject = function() { 
		ko();
	}
	//重置查询按钮
	$scope.clean_searchproject = function() {
		$state.reload("workspace.solutionmanager_1_0");
	}
	//查看按钮
	$scope.project_info = function(p) { 
		$scope.tab_info.active = 0;
		$scope.tab_info.files.u_file_1=[];
		$scope.tab_info.files.u_file_2=[];
		$scope.tab_info.files.u_file_3=[];
		send_projectinfo(p);
	}
	$scope.close_project_info = function() {
		$scope.isSwitch=true;
		ko();
	}
	//列出项目信息-修改项目信息
	$scope.project_info_modifyproject = function() {
		send_modifyproject();
	}
	//列出项目信息-添加删除参与人
	$scope.person_addOrDelete = function(p) {
		send_addOrDeletePerson(p);
	}
	//列出项目信息-下载文件
	$scope.file_download = function(p) {
		send_downloadfile(p);
	}
	//列出项目信息-删除文件
	$scope.file_delete = function(p) {
		send_deletefile(p);
	}

	//神圣的加载表格！！！
	function ko(){
		send_listuser();
		send_searchproject($scope.search);
	}

	//弹窗
	//删除弹窗
	ctrl.modal_deleteproject = function(f) {
		ctrl.p = {
			projectid:f.projectid
		};

		var modalInstance = $uibModal.open({
			animation:true, //Default: true
			appendTo:angular.element(document.getElementById('main')), //Default: body
			ariaDescribedBy:"modal-body",
			ariaLabelledBy:"modal-title",
			templateUrl:"modal_deleteproject",
			controller:"modal_deleteprojectCtrl",
			controllerAs:"ctrl",
			size:"sm",
			resolve:{
				p:function(){
					return ctrl.p;
				}
			}
		});
		modalInstance.result.then(function(data){
			switch(data) {
				case 0:
				$scope.work.modal_Show("提示","删除出错！","sm");
				break;
				case 1:
				$scope.work.modal_Show("提示","删除成功！","sm");
				break;
				case 2:
				$scope.work.modal_Show("提示","无删除权限！","sm");
				break;
			}
			ko();
		},function(){
			$log.info('Modal cancel at: ' + new Date());
		});
	}

	//SEND
	//列出项目信息-上传文件，更新文件
	//var file_temp=[[],[],[]];
	$scope.upload = function (files,t,fileid) { //文件，类型，文件id
		if ($scope.tab_info.isUpfile != true) {
			$scope.work.modal_Show("提示","您没有上传文件的权限！","sm");
		} else {
			var t1 = "uploadmanager/uploadfile.do";
			var t2 = "uploadmanager/updatefile.do";

			var flag = 0;
			angular.forEach(files,function(file){
	        	for(var i=0;i<file_temp[t-1].length;i++){
	        		if(file_temp[t-1][i]==file){
	                    flag=1;
	        		}
	        	}
	        	file_temp[t-1].push(file);
	        	if(flag==0){
	                Upload.upload({
				        url: (fileid==null||fileid=="")?t1:t2,
				        data: {
							projectid:flag_projectID,
					    	userid:$scope.myCookie.userid,
					    	file:file,
					    	filetype:t,
					    	fileid:fileid
						}
			        }).then(function(resp) {
			        	if(resp.data.result=="1"){
			        		$scope.work.modal_Show("提示","文件上传成功！","sm");
			        		//更新INFO
			        		send_projectinfo(flag_projectID);
			        	} else {
			        		$scope.work.modal_Show("提示","文件上传失败！","sm");
			        	}
				    }, function(resp) {
					  // handle error
					   logger.log('error');
					}, function(evt) {
			           file.progress=Math.min(100,parseInt(100.0 * evt.loaded / evt.total));
					});
	        	}
	        	flag=0;
	        });
		}	
	}

	//列出项目信息-下载文件
	var a = document.createElement("a");  
    document.getElementById("solutionmanager").appendChild(a);  
    a.style = "display:none";
	function send_downloadfile(p) {
		var t1 = "uploadmanager/downloadfile.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			url:p.fileurl
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            },
            responseType: 'arraybuffer'
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;

        	var flag_type;
        	switch(p.tail) {
        		case 'xls':
        			flag_type = "application/vnd.ms-excel";
        			break;
        		case 'xlsx':
        			flag_type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        			break;
        		case 'doc':
        			flag_type = "application/msword";
        			break;
        		case 'docx':
        			flag_type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        			break;
        		case 'ppt':
        			flag_type = "application/vnd.ms-powerpoint"
        			break;
        		case 'pptx':
        			flag_type = "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        			break;
        		case 'pdf':
        			flag_type = "application/pdf";
        			break;
        		case 'zip':
        			flag_type = "application/zip";
        			break;
        		case 'rar':
        			flag_type = "application/x-rar-compressed";
        			break;
        		case 'bmp','tiff','gif','jpeg','svg','psd','png','jpg','wmf':
        			flag_type = "image/*";
        			break;
        	}
        	var file = new Blob([data], {type:flag_type});
            var fileURL = (window.URL || window.webkitURL).createObjectURL(file);
            var fileName = p.filename+'.'+p.tail;

            a.download = fileName;
            a.href = fileURL;
            a.click();
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//列出项目信息-删除文件
	function send_deletefile(p) {
		var t1 = "uploadmanager/deletefile.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			projectid:flag_projectID,
			fileid:p.fileid
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else if (data.result == 2) {
				$scope.work.modal_Show("提示","您没有删除权限！","sm");
			} else if (data.result == 0) {
				$scope.work.modal_Show("提示","删除失败！","sm");
			} else {
				$scope.work.modal_Show("提示","删除成功","sm");
			}
			//更新info
			send_projectinfo(flag_projectID);
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//列出项目信息-修改项目信息
	function send_modifyproject() {
		var t1 = "solutionmanager/modifyproject.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			projectid:$scope.tab_info.info.projectid,
			projectname:$scope.tab_info.info.projectname,
			projectinfo:$scope.tab_info.info.projectinfo,
			projecttime:"1970-01-01"
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else if (data.result == 2) {
				$scope.work.modal_Show("提示","您没有修改权限！","sm");
			} else if (data.result == 4) {
				$scope.work.modal_Show("提示","项目名重名，修改失败！","sm");
			} else {
				$scope.work.modal_Show("提示","修改成功","sm");
				$scope.tab_info.isChange = false;
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//列出项目信息-列出参与人
	function send_addpersonlist() {
		var t1 = "solutionmanager/addpersonlist.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			projectid:flag_projectID
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				$scope.tab_info.isPerson = (data.result == 2)?false:true;
				$scope.tab_info.usercheck = data.usercheck;
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//列出项目信息-添加删除参与人
	function send_addOrDeletePerson(p) {
		if (flag_projectID == null) {
			alert("信息错误，将重新加载页面！");
			$state.reload("workspace.solutionmanager_1_0");
		}

		var t1 = "solutionmanager/addperson.do"
		var t2 = "solutionmanager/deleteperson.do";
		var t3 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			projectid:flag_projectID,
			useridlist:p.userid,
			uuserid:p.userid
		};

		$http({
			method:"POST",
            url:(p.checkenter==1)?t1:t2,
            data:jsonToStr.transform(t3),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				switch(data.result) {
					case 0:
						$scope.work.modal_Show("提示","参与人操作失败！","sm");
						break;
					case 1:
						$scope.work.modal_Show("提示","参与人操作成功！","sm");
						break;
					case 2:
						$scope.work.modal_Show("提示","无权限修改参与人！","sm");
						break;
					case 4:
						$scope.work.modal_Show("提示","无法删除创建者！","sm");
						break;
				}
				//刷新参与人
				send_addpersonlist();
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//列出项目信息
	//p:项目ID号
	function send_projectinfo(p) {
		var t1 = "solutionmanager/projectinfo.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			projectid:p
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				//界面激活
				$scope.isSwitch = false;
				//项目id绑定
				flag_projectID = data.projectinfo.projectid;
				//参与人列表展示
				send_addpersonlist();
				//INFO界面
				$scope.tab_info.info = data.projectinfo;
				$scope.tab_info.userinfo = data.projectuserinfo;
				var f_s_userinfo = "";
				//每个项目至少有一个参与，因此不报错
				//记录自己是否是参与人
				for (var i = 0; i < data.projectuserinfo.length; i++) {
					if (i == data.projectuserinfo.length-1) {
						f_s_userinfo = f_s_userinfo + data.projectuserinfo[i].uname;
						$scope.tab_info.isUpfile = (data.projectuserinfo[i].userid == factory_cookie.getCookie("userid"))?true:false;
					} else {
						f_s_userinfo = f_s_userinfo + data.projectuserinfo[i].uname + "，";
						$scope.tab_info.isUpfile = (data.projectuserinfo[i].userid == factory_cookie.getCookie("userid"))?true:false;
					}
				};
				$scope.tab_info.s_userinfo = f_s_userinfo;

				//INFO界面-文件展示
				$scope.tab_info.files.files_1=[];
				$scope.tab_info.files.files_2=[];
				$scope.tab_info.files.files_3=[];
				for (var i = 0; i < data.fileinfomation.length; i++) {
					switch(data.fileinfomation[i].filetype) {
						case 1:
							$scope.tab_info.files.files_1.push(data.fileinfomation[i]);
							break;
						case 2:
							$scope.tab_info.files.files_2.push(data.fileinfomation[i]);
							break;
						case 3:
							$scope.tab_info.files.files_3.push(data.fileinfomation[i]);
							break;
					}
				}
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//查询项目
	function send_searchproject(p) {
		var t1 = "solutionmanager/searchproject.do"
		var t2 = {
			userid:$scope.myCookie.userid, //用户ID
			token:$scope.myCookie.token, //验证Token
			pagePerNum:p.s_pagePerNum, //每页个数
			pageNum:p.s_pageNum, //页码
			order:0, //0正序1逆序
			projectname:p.s_projectname, //项目名称关键字 
			uuserid:p.s_uueser.userid, //用户名称
			firsttype:1,
			secondtype:0,
			projectMintime:"",
			projectMaxtime:""
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				$scope.projectinfo = data.projectinfo;
				$scope.search.s_items = data.projectnum;
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//查询-选择用户初始化
	function send_listuser() {
		var t1 = "solutionmanager/listuser.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token,
			firstType:1,
			secondType:0
		};

		$http({
			method:"POST",
            url:t1,
            data:jsonToStr.transform(t2),
            headers:{
                "Accept" : "*/*",
                "Content-Type" : "application/x-www-form-urlencoded; charset=UTF-8"
            }
		}).then(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
			
			if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				//操作
				$scope.search.search_uueser = data.projectuser;
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}
});