var fs = require('fs');

fs.open('../data/test.txt', 'r', function(err, fd){
  
  if(err == null)
  {
    var fileHandle = fd;
    var buffer = new Buffer(100000);
    fs.read(fileHandle, buffer, 0, 100000, null, function(err, bytesRead){
      if(err == null){
        console.log(buffer.toString("utf8", 0, bytesRead));
      } else {
        console.log("File read error " + err.code + " " + err.message);
      }
    });
    fs.close(fileHandle);
  } else {
    console.log("File open error " + err.code + " " + err.message);
  }
});

