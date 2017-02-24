/*
*AUTHOR:MT
*DATE:2017-1-15
*REVISER:
*LAST CHANGE:
*/

app.controller("workspaceCtrl",function($uibModal,$scope,$log,$state,$http,factory_cookie,jsonToStr,factory_json){
	var work = this;

	//show弹窗
	//标题，文本
	work.modal_Show = function (m_title,m_text,m_size) {
		$uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-show-title',
			ariaDescribedBy: 'modal-show-body',
			templateUrl: 'modal_show',
			size: m_size,
			controller: function($scope) {
				$scope.modal_show_title = m_title;
				$scope.modal_show_text = m_text;
			}
		});
	};

	//注销弹窗
	work.modal_logout = function(){
		work.p = {};

		var modalInstance = $uibModal.open({
			animation:true, //Default: true
			appendTo:angular.element(document.getElementById('main')), //Default: body
			ariaDescribedBy:"modal-body",
			ariaLabelledBy:"modal-title",
			templateUrl:"modal_logout",
			controller:"modal_logoutCtrl",
			controllerAs:"work",
			size:"sm",
			resolve:{
				p:function(){
					return work.p;
				}
			}
		});
		modalInstance.result.then(function(data){
			if (data == 1) {
				$state.go("login");
			}
		},function(){
			$log.info('Modal cancel at: ' + new Date());
		});
	}

	//菜单
	$scope.info = {};
	$scope.info.uname = factory_cookie.getCookie("uname");
	$scope.info.logintime = factory_cookie.getCookie("logintime");
	$scope.img_uname = ($scope.info.uname==null || $scope.info.uname=="")?"空":$scope.info.uname.charAt($scope.info.uname.length - 1);
	$scope.img_style = "menu_header_img_s" + Math.floor(Math.random()*10);

	//菜单案例
	// $scope.menus = [
	// 	{
	// 		menu_name:"管理1",
	// 		menu_icon:"user",
	// 		hasChild:false,
	// 		isOpen:false,
	// 		menu_go:"workspace.setting",
	// 		menu_child:[
	// 			{
	// 				menu_name:"ch1",
	// 				menu_go:"workspace.setting"
	// 			},
	// 			{
	// 				menu_name:"ch2",
	// 				menu_go:"workspace.setting"
	// 			}
	// 		]
	// 	}
	// ];
	$scope.menus = [
		{
			id:"0",
			tab:"solutionmanager",
			menu_name:"解决方案库",
			menu_icon:"cloud",
			hasChild:true,
			isOpen:true,
			isSelect:false,
			menu_go:"workspace.solutionmanager_1_0",
			menu_child:[
				{
					id:"00",
					tab:"solutionmanager",
					menu_name:"基础解决方案",
					isSelect:false,
					menu_go:"workspace.solutionmanager_1_1"
				},
				{
					id:"01",
					tab:"solutionmanager",
					menu_name:"行业解决方案",
					isSelect:false,
					menu_go:"workspace.solutionmanager_1_2"
				},
				{
					id:"02",
					tab:"solutionmanager",
					menu_name:"项目解决方案",
					isSelect:false,
					menu_go:"workspace.solutionmanager_1_3"
				}
			]
		},
		{
			id:"1",
			tab:"solutionmanager",
			menu_name:"案例分享库",
			menu_icon:"cloud",
			hasChild:true,
			isOpen:true,
			isSelect:false,
			menu_go:"workspace.solutionmanager_2_0",
			menu_child:[
				{
					id:"10",
					tab:"solutionmanager",
					menu_name:"总部案例",
					isSelect:false,
					menu_go:"workspace.solutionmanager_2_1"
				},
				{
					id:"11",
					tab:"solutionmanager",
					menu_name:"其他省份案例",
					isSelect:false,
					menu_go:"workspace.solutionmanager_2_2"
				},
				{
					id:"12",
					tab:"solutionmanager",
					menu_name:"福建省案例",
					isSelect:false,
					menu_go:"workspace.solutionmanager_2_3"
				}
			]
		}
	];

	//菜单跳转
	var last_menu = factory_cookie.getCookie("menu_id"); //登录默认首页
	set_menu_isSelect(last_menu,true);
	$scope.menu_goto = function(p) {
		//菜单选中
		set_menu_isSelect(last_menu,false);
		factory_cookie.setCookie("menu_id",p.id); //防止页面刷新
		last_menu = p.id; //重置上一个菜单
		set_menu_isSelect(last_menu,true);

		//跳转
		$state.go(p.menu_go);
	};

	function set_menu_isSelect(id,tf){
		if (id) {
			if (id.length == 1) {
				for (var i = 0; i < $scope.menus.length; i++) {
					if ($scope.menus[i].id == id) {
						$scope.menus[i].isSelect = tf;
					}	
				}
			} else if (id.length == 2) {
				for (var i = 0; i < $scope.menus.length; i++) {
					if ($scope.menus[i].id == id.substr(0,1)) {
						for (var j = 0; j < $scope.menus[i].menu_child.length; j++) {
							if ($scope.menus[i].menu_child[j].id == id) {
								$scope.menus[i].menu_child[j].isSelect = tf;
							}
						}
					}	
				}
			}
		}	
	}
});

//登出
app.controller("modal_logoutCtrl",function($uibModalInstance, p){
	var work = this;
	work.p = p; //同步p参数

	work.ok = function(){ //返回success
		$uibModalInstance.close(1);
	};
	work.cancel = function(){ //返回error
		$uibModalInstance.dismiss();
	};
});