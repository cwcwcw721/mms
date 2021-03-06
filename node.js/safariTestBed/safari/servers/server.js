var http = require('http')
	, fs = require("fs")
	, url = require("url")
	, path = require('path');

function handle_incoming_request(req, res) {
  console.log("Incoming request: (" + req.method + ") " + req.url +'\n');
  req.parsed_url = url.parse(req.url, true);
  var core_url = req.parsed_url.pathname;

  if(core_url.substr(0,7) === '/pages/') {
	  serve_page(req,res);
  } else if(core_url.substr(0,9) === '/content/') {
	  serve_static_content('../../content/', core_url.substr(9), req,res);
  } else if(core_url.substr(0,11) === '/templates/') {
	  serve_static_content('../../templates/', core_url.substr(11), req,res);
  } else if(core_url === '/albums'){
    handle_load_albums(req,res);
  } else if(core_url.substr(0,8) === '/albums/' && core_url.length > 8){
    handle_load_photos(req,res);
  } else {
    res.writeHead(400, {'Content-Type': 'application/json'});
    res.end(JSON.stringify({error: 'Bad Request', message: "The request cannot be fulfilled due to bad syntax."}) + '\n');
  }
}

function serve_page(req,res){
	var page_name = req.parsed_url.pathname.substr(7);
	fs.readFile('../../basic.html', 'utf8', function(err, contents){
		if(err){
			res.writeHead(503, {'Content-Type': 'text/html'});
			res.end("Oh, what's happening");
			return;
		}
		
		res.writeHead(200, {'Content-Type':'text/html'});
		res.end(contents.replace('{{PAGE_NAME}}', page_name));
	});
}

function serve_static_content(folder, fn, req,res){
	var fn = folder + fn;
	var rs = fs.createReadStream(fn);
	var ct = get_content_type(fn);
	
	res.writeHead(200, {'Content-Type': ct});
	
	rs.on(
		'error',
		function(){
			res.writeHead(400, {'Content-Type':'text/json'});
			res.end(JSON.stringify({error: 'resource_not_found', message: 'something horrible happens!'}));
		}
	);

	rs.pipe(res);
}

function get_content_type(fn){
	var ext = path.extname(fn).toLowerCase();
	
	switch(ext){
	case '.jpg': case '.jpeg':
		return 'image/jpeg';
	case '.png':
		return 'image/png';
	case '.gif':
		return 'image/gif'
	case '.js':
		return 'text/javascript';
	case '.css':
		return 'text/css';
	case '.html': case '.htm':
		return 'text/html';
	default:
		return 'text/plain';
	}
}

function handle_load_albums(req, res){
  load_album_list(function(err, albums){
    if(err !== null){
      res.writeHead(503, {'Content-Type': 'application/json'});
      res.end(JSON.stringify({error: 'file error', message: err.message}) + '\n');
      return;
    }

    res.writeHead(200, {'Content-Type': 'application/json'});
    res.end(JSON.stringify({error: null, data: {albums: albums}}) + '\n');
  });
}

function handle_load_photos(req, res){
  var subfoldername = req.parsed_url.pathname.substr(8, req.url.length-1);
  var page = parseInt(req.parsed_url.query.page);
  var page_size = parseInt(req.parsed_url.query.page_size);
  
  page = isNaN(page) ? 0 : page;
  //console.log(page);
  page_size = isNaN(page_size) ? 100 : page_size;
  //console.log(page_size);

  load_photo_list(subfoldername, page, page_size, function(err, photos){
    if(err !== null){
      res.writeHead(503, {'Content-Type': 'application/json'});
      res.end(JSON.stringify({error: 'file error', message: err.message}) + '\n');
      return;
    }

    res.writeHead(200, {'Content-Type': 'application/json'});
    res.end(JSON.stringify({error: null, data: {albums: photos}}) + '\n');
  });
}

function load_album_list(callback){
  fs.readdir('../../albums', function(err, file_list){
    
    if(err){
      callback(err);
      return;
    }
    
    var dir_only = [];
    (function iterator(i){
      if(i>=file_list.length){
        callback(null, dir_only);
        return;
      }
      
      fs.stat("../../albums/" + file_list[i], function(err, stats) {
          if(err){
            callback(err);
            return;
          }
          
          if(stats.isDirectory()){
            dir_only.push(file_list[i]);
          }
          
          iterator(i+1);
      })
      
    })(0);
  });
}

function load_photo_list(foldername, page, page_size, callback){
  fs.readdir('../../albums/' + foldername, function(err, file_list){
    
    if(err){
      callback(err);
      return;
    }
    
    var file_only = [];
    (function iterator(i){
      if(i>=file_list.length){
        var subset = file_list.splice(page * page_size, page_size);
        callback(null, subset);
        return;
      }
      
      fs.stat("../../albums/" + foldername + "/" + file_list[i], function(err, stats) {
          if(err){
            callback(err);
            return;
          }
          
          if(stats.isFile()){
            file_only.push(file_list[i]);
          }
          
          iterator(i+1);
      })
      
    })(0);
  });
}

http.createServer(handle_incoming_request).listen(3000);