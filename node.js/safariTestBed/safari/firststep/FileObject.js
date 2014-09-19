var fs = require('fs');

function FileObject(){
  this.filename = null;
  this.exists = function(callback){
    var me = this;
    fs.open(this.filename, 'r', function (err, handle) {
      if(err === null){
        console.log(me.filename + " does indeed exist.\n");
        callback(true);
        fs.close(handle);
      } else {
        console.log(me.filename + " does not exist with the following error " + err.message  + "\n");
        callback(false);
      }
    });
  };
}

var fo = new FileObject();
fo.filename = "../data/test.txt";
fo.exists(function(does_it_exist){
  console.log("Callback function shows the result " + does_it_exist  + "\n");
});