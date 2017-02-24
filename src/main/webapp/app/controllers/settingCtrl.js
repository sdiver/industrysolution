/*
*AUTHOR:MT
*DATE:2017-1-15
*REVISER:
*LAST CHANGE:
*/

app.controller("settingCtrl",function($uibModal,$log,$scope,$state,$http,factory_cookie,jsonToStr,info,Upload){
	var ctrl = this;
    //Cookie
	$scope.myCookie = {
		userid:factory_cookie.getCookie("userid"),
		token:factory_cookie.getCookie("token")
	};
	//修改密码
	$scope.pwd = {
	    userpassword: "",
	    newpassword:"",
	    newpassword_repeat:""
	};
	//标签信息
	$scope.taginfo=[];
	//修改密码方法
	$scope.changpassword = function(){
		if($scope.pwd.userpassword!=""&$scope.pwd.newpassword!=""&$scope.pwd.newpassword_repeat!=""){
			if($scope.pwd.newpassword!=$scope.pwd.newpassword_repeat){
	            $scope.work.modal_Show("提示","新密码输入不一致！","sm");
	            $scope.pwd.newpassword="";
	            $scope.pwd.newpassword_repeat="";
	        }
	        else{
                var t1 = "usermanager/changepwd.do"
				var t2 = {
					userid:$scope.myCookie.userid,
					token:$scope.myCookie.token,
					userpassword:$scope.pwd.userpassword,
					newpassword:$scope.pwd.newpassword
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
						$scope.work.modal_Show("提示","新密码不合法！","sm");
					} else if (data.result == 0) {
						$scope.work.modal_Show("提示","旧密码错误！","sm");
					} else {
						$scope.work.modal_Show("提示","修改密码成功，请重新登录！","sm");
						$state.go("login");
					}
				}).catch(function (r){
					var data = r.data;
		        	var status = r.status;
		        	var statusText = r.statusText;
				});
	        }
		}
		else{
			$scope.work.modal_Show("提示","请输入完整的密码","sm");
		}
	}
	listtap();
	//新增弹窗
	//添加标签
	ctrl.modal_addtag = function() {
		ctrl.p = {};

		var modalInstance = $uibModal.open({
			animation:true, //Default: true
			appendTo:angular.element(document.getElementById('setting')), //Default: body
			ariaDescribedBy:"modal-body",
			ariaLabelledBy:"modal-title",
			templateUrl:"modal_addtag",
			controller:"modal_addtagCtrl",
			controllerAs:"ctrl",
			//size:"sm",
			resolve:{
				p:function(){
					return ctrl.p;
				}
			}
		});
		modalInstance.result.then(function(data){
			if(data == 1) {
				$scope.work.modal_Show("提示","标签添加成功！","sm");
			}
			listtap();
		},function(){
			$log.info('Modal cancel at: ' + new Date());
		});
	}
	//列出标签
	function listtap(){
		var t1 = "tapController/listtap.do"
		var t2 = {
			userid:$scope.myCookie.userid,
			token:$scope.myCookie.token
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
			}
			else {
				$scope.taginfo=data.result;
			}
		}).catch(function (r){
			var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;
		});
	}
});

//添加标签 addtag
app.controller("modal_addtagCtrl",function($uibModalInstance,p,$scope,$state,$http,factory_cookie,jsonToStr){
	var ctrl = this;
	ctrl.p = p; //同步p参数

	ctrl.ok = function(){ //返回success
		if (!ctrl.projecttag) {
			alert("请输入标签名！");
		} 
		else {
			send_addtag();
		}
	};
	ctrl.cancel = function(){ //返回error
		$uibModalInstance.dismiss();
	};
    function send_addtag() {
		var t1 = "tapController/addtap.do"
		var t2 = {
			userid:factory_cookie.getCookie("userid"),
			token:factory_cookie.getCookie("token"),
			tapname:ctrl.projecttag
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
