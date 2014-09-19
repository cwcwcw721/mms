var http = require('http'), qs = require('querystring');

function handle_incoming_request(req, res) {
  console.log("Incoming request: (" + req.method + ") " + req.url +'\n');
  
  var req_data = "";
  req.on(
    'readable',
    function(){
      var d = req.read();
      if(typeof d == 'string'){
        req_data += d;
      } else if(typeof d == 'object' && d instanceof Buffer) {
        req_data += d.toString('utf8');
      }
    }
  );
  
  req.on(
    'end',
    function() {
        var out = '';
        if(!req_data){
          out = "I got no request data";
        } else {
          var obj = qs.parse(req_data);
          if(!obj)
            out = "Request data didn't parse.";
          else
            out = "I got request data " + JSON.stringify(obj) 
        }
        
        res.end(out + '\n');
    }
  );
}

http.createServer(handle_incoming_request).listen(3000);