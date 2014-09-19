var http = require('http'), fs = require("fs"), url = require("url");

function handle_incoming_request(req, res) {
  console.log("Incoming request: (" + req.method + ") " + req.url +'\n');
  req.parsed_url = url.parse(req.url, true);
  //console.log(req.parsed_url);
  
  if(req.parsed_url.pathname === '/albums'){
    handle_load_albums(req,res);
  } else if(req.parsed_url.pathname.substr(0,8) === '/albums/' && req.parsed_url.pathname.length > 8){
    handle_load_photos(req,res);
  } else {
    res.writeHead(400, {'Content-Type': 'application/json'});
    res.end(JSON.stringify({error: 'Bad Request', message: "The request cannot be fulfilled due to bad syntax."}) + '\n');
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