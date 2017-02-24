/*
*AUTHOR:MT
*DATE:2017-2-21
*REVISER:
*LAST CHANGE:
*/

//发起项目 addproject
app.controller("modal_addprojectCtrl",function($filter,$uibModalInstance,p,$scope,$state,$http,factory_cookie,jsonToStr){
	var ctrl = this;
	ctrl.p = p; //同步p参数

	//初始化值
	ctrl.projectname = "";
	ctrl.projectinfo = "";
	ctrl.projecttime = "";
	ctrl.timeIsOpend = false;

	//列出taps
	ctrl.taps = [];//展示
	ctrl.taps_s_show = "";//选择展示
	ctrl.taps_s_send = 1;//选择发送
	send_listtap();

	ctrl.ok = function(){ //返回success
		if (!ctrl.projectname || !ctrl.projectinfo) {
			alert("请输入项目名称与项目概述！");
		} else {
			send_addproject();
		}
	};
	ctrl.cancel = function(){ //返回error
		$uibModalInstance.dismiss();
	};
	ctrl.getTaps = function(){
		get_Taps(ctrl.taps);
	};

	//判断taps值
	function get_Taps(t) {
		var flag = [];
		for (var i = 0; i < t.length; i++) {
			if (t[i].select == true) {
				flag.push(t[i]);
			}
		}
		if (flag.length == 0) {
			ctrl.taps_s_show = "未选择";
			ctrl.taps_s_send = 1;
		} else if (flag.length == 1) {
			ctrl.taps_s_show = flag[0].thirdtype;
			ctrl.taps_s_send = flag[0].thirdtypeid;
		} else {
			ctrl.taps_s_show = "";
			ctrl.taps_s_send = "";
			for (var i = 0; i < flag.length; i++) {
				if (i == flag.length-1) {
					ctrl.taps_s_show += flag[i].thirdtype;
					ctrl.taps_s_send += flag[i].thirdtypeid;
				} else {
					ctrl.taps_s_show += flag[i].thirdtype + "，";
					ctrl.taps_s_send += flag[i].thirdtypeid + ",";
				}
			}
		}
	}

	//列出标签
	function send_listtap() {
		var t1 = "tapController/listtap.do"
		var t2 = {
			userid:factory_cookie.getCookie("userid"),
			token:factory_cookie.getCookie("token")
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
				ctrl.taps.show = data.result;
				if (data.result.length == 1 || data.result.length == 0) {
					ctrl.taps = false;
				} else { //存在值
					for (var i = 1; i < data.result.length; i++) {
						ctrl.taps.push({
							thirdtypeid:data.result[i].thirdtypeid, //id
							thirdtype:data.result[i].thirdtype, //name
							select:false //select
						});
					}
					get_Taps(ctrl.taps);
				}
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}

	//新增项目
	function send_addproject() {
		var t1 = "solutionmanager/addproject.do"
		var t2 = {
			userid:factory_cookie.getCookie("userid"),
			token:factory_cookie.getCookie("token"),
			projectname:ctrl.projectname,
			projectinfo:ctrl.projectinfo,
			firsttype:ctrl.p.firsttype,
			secondtype:ctrl.p.secondtype,
			thirdtype:ctrl.taps_s_send,
			projecttime:$filter('date')(ctrl.projecttime,'yyyy-MM-dd')
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
			
			if (data.result == 0) {
				alert("您的项目名称重名了，请重新命名！");
			} else if (data.result == 3) {
				alert("您的密钥已过期，请重新登录！");
				$state.go("login");
			} else {
				//操作
				$uibModalInstance.close(data.result);
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}
});

//删除项目 deleteproject
app.controller("modal_deleteprojectCtrl",function($uibModalInstance,p,$scope,$state,$http,factory_cookie,jsonToStr){
	var ctrl = this;
	ctrl.p = p; //同步p参数

	ctrl.ok = function(){ //返回success
		send_deleteproject();
	};
	ctrl.cancel = function(){ //返回error
		$uibModalInstance.dismiss();
	};

	function send_deleteproject() {
		var t1 = "solutionmanager/deleteproject.do"
		var t2 = {
			userid:factory_cookie.getCookie("userid"),
			token:factory_cookie.getCookie("token"),
			projectid:ctrl.p.projectid
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
				$uibModalInstance.close(data.result);
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}
});