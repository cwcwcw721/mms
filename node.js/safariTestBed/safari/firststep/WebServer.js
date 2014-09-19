var http = require("http");

var s = http.createServer(function(req, res){
    res.end("Hey, I am listening!\n");
});

s.listen(3000);

