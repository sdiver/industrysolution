/*
*AUTHOR:MT
*DATE:2017-1-15
*REVISER:
*LAST CHANGE:
*/

app.controller("loginCtrl",function($scope,$state,$http,factory_cookie,jsonToStr,info){

	//幻灯片背景
	$scope.myInterval = 3000;
	$scope.noWrapSlides = false;
	$scope.active = 0;
	var slides = $scope.slides = [];
	var currIndex = 0;

	$scope.addSlide = function() {
		var newWidth = 1280 + slides.length + 1;
		slides.push({
			image: "https://unsplash.it/" + newWidth + "/760/?random",
			id: currIndex++
		});
	}

	slides.push({
		image: "app/img/bg_unicom.jpg",
		id: currIndex++
	});
	for (var i = 0; i < 2; i++) {
    	$scope.addSlide();
  	}

  	//登录窗口初始化
  	$scope.username = (factory_cookie.getCookie("username")==null)?"":factory_cookie.getCookie("username");
  	$scope.userpassword = (factory_cookie.getCookie("userpassword")==null)?"":factory_cookie.getCookie("userpassword");
	$scope.ifSavePw = (factory_cookie.getCookie("ifSavePw")=="true")?true:false;

	//登录事件
	$scope.btn_login = function () {
		send_login();
	}
	$scope.btn_enter_login = function (ev) {
		if (ev.keyCode !== 13) return; 
		// your code 
		send_login();
	}

	function send_login() {
		var t1 = "usermanager/login.do";
		var t2 = {
			username:$scope.username,
			userpassword:$scope.userpassword
		};
		//重置密码
		$scope.userpassword = "";

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
        		alert("登录名或密码错误");
        	} else {
	        	//cookie
	        	factory_cookie.setCookie("username",t2.username,30);

	        	factory_cookie.setCookie("uname",data.result.uname,30);
	        	factory_cookie.setCookie("userid",data.result.userid,30);
	        	factory_cookie.setCookie("token",data.result.token,30);
	        	factory_cookie.setCookie("logintime",data.result.logintime,30);

	        	//登录菜单初始化配置
	        	factory_cookie.setCookie("menu_id","0");
	        	factory_cookie.setCookie("firsttype",1);
				factory_cookie.setCookie("secondtype",1);

	        	if ($scope.ifSavePw == true) {
	        		factory_cookie.setCookie("ifSavePw",$scope.ifSavePw,30);
	        		factory_cookie.setCookie("userpassword",t2.userpassword,30);
	        	} else {
	        		factory_cookie.delCookie("ifSavePw");
	        		factory_cookie.delCookie("userpassword");
	        	}

	        	//跳转
	        	$state.go("workspace.solutionmanager_1_0");
        	}	
        }).catch(function (r){
        	var data = r.data;
        	var status = r.status;
        	var statusText = r.statusText;

        	console.error("error :" + data);
        });
	}
});