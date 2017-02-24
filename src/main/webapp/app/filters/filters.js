/*
*AUTHOR:MT
*DATE:2017-1-22
*/

app.filter('filter_file', function() {
	return function(input,p1) {
		//过滤器
		var flag=[];
		if (p1=="" || p1 == null) {
			return input;
		}
		
		for (var i = 0; i < input.length; i++) {
			if (input[i].filename.toLowerCase().indexOf(p1.toLowerCase())>-1) { //包含
				flag.push(input[i]);
			}
		}

		return flag;
	};
});