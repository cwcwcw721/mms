var express = require('express')
  , app = express() // Web framework to handle routing requests
  , cons = require('consolidate') // Templating library adapter for Express
  , MongoClient = require('mongodb').MongoClient // Driver for connecting to MongoDB
  , routes = require('./routes') // Routes for our application
  , con = require('connect');

MongoClient.connect('mongodb://mongo1:27017/blog', function(err, db) {
    "use strict";
    if(err) throw err;

    // Register our templating engine
    app.engine('html', cons.swig);
    app.set('view engine', 'html');
    app.set('views', __dirname + '/views');

    // Express middleware to populate 'req.cookies' so we can access cookies
    app.use(con.cookieParser());

    // Express middleware to populate 'req.body' so we can access POST variables
    app.use(con.bodyParser());

    // Application routes
    routes(app, db);

    app.listen(8082);
    console.log('Express server listening on port 8082');
});
