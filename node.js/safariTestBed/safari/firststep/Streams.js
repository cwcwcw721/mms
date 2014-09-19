var fs = require('fs');

function load_file_content(filename, callback){
	var rs = fs.createReadStream(filename);
	var filecontent = '';
	
	rs.on(
		'readable',
		function(){
			var d = rs.read();
			if(d){
				if(typeof d == 'string'){
					filecontent += d;
				} else if(typeof d == 'object' && d instanceof Buffer){
					filecontent += d.toString('utf8');
				}
			}
		}
	);
	
	rs.on(
		'end',
		function(){
			callback(null, filecontent);
		}
	);
	
	rs.on(
		'error',
		function(err){
			callback(err);
		}
	);
}

load_file_content('../../data/test.txt', function(err, result){
	if(err){
		console.log(err);
	} else {
		console.log(result);
	}
});