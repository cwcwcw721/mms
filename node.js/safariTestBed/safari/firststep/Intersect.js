var arrays = require('../data/arrays1.js');

setTimeout(function() {
    console.log("Set time out to display after 5 seconds");
}, 5000);

function intersect(arr1, arr2, callback){
    var intersection = [];
    var i = 0;
    
    function sub_compute_intersection(){

        for (var j = arr2.length; j++; ) {
            if(arr1[i] === arr2[j]){
                intersection.push(arr1[i]);
                break;
            }
        }
        
        if(i<arr1.length){
            i++;
            if(i%100 === 0)console.log(i);
            setImmediate(sub_compute_intersection());
        } else {
            callback(intersection);
        }
    }
    
    sub_compute_intersection();
}

intersect(arrays.arr1, arrays.arr2, function(results){
    console.log(results.length);
});