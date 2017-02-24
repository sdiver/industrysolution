/*
*AUTHOR:MT
*DATE:2016-7-25
*/

//factory
app.factory("factory_cookie",function(){
	var service = {
		setCookie:function(c_name,value,expiredays){
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie = c_name + "=" + escape(value) + ((expiredays==null)?"":";expires=" + exdate.toGMTString());
		},
		getCookie:function(c_name){
			var c_start = -1,
				c_end = -1;
			if(document.cookie.length > 0){
				c_start = document.cookie.indexOf(c_name + "=");
				if(c_start != -1){
					//检索cookie位置
					c_start = c_start + c_name.length + 1;
					c_end = document.cookie.indexOf(";",c_start);
					if(c_end == -1) c_end = document.cookie.length;
					
					return unescape(document.cookie.substring(c_start,c_end));
				}
			}
			return null;
		},
		delCookie:function(c_name){
			var date = new Date();
			date.setTime(date.getTime()-10000);
			document.cookie = c_name + "=;expire="+date.toGMTString();
		}
	};

	return service;
});

app.factory("factory_json",function($http,$q){
	var service = {
		get_json:function(Jtype,Jname){
			var deferred = $q.defer();

			$http.get("app/json/string.json").then(function(res){
				var flag = res.data;
				alert(typeof(flag.draftlist)); //???
		        if(Jtype == null) {
					deferred.resolve();
				} else if (Jname != null) {
		        	deferred.resolve(res[Jtype][Jname]);
		        } else{
		        	deferred.resolve(res[Jtype]);
		        };
		    }).catch(function(err){
		    	deferred.reject(err);
		    });

		    return deferred.promise;
		} //END json_sql
	};

	return service;
});

app.service('jsonToStr',function(){
	var service = {
		transform:function(jsonData){
			var string = '';
	        
	        for(str in jsonData){
	            string = string + str +'=' + jsonData[str] +'&';
	        }
	        
	        var _last = string.lastIndexOf('&');
	        
	        string = string.substring(0,_last);
	        
	        return string;
		}
	};

	return service;
});

// 例子
app.service("serviceName",function($window,$rootScope){
	function subsFunc() {
		$window.addEventListener("message",function(e) {
			$rootScope.$broadcast("message",e.data);
			console.log(e.data);
		});
	};

	return {
		"test" : subsFunc
	};
});

app.constant("baseUrl","http://210.13.199.111/ydxj/");

app.value("info",{
	uname:"",
	userid:"",
	username:"",
	token:"",
	logintime:""
});

app.value("listarea",{
	arealist:[],
	village:[],
	contributelist:[],
	foundlist:[]
});