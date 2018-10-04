const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./ExpensesReviewSystem-key.json");
var mysql = require('mysql');

var database = "akash"

var MySQLEvents = require('./node_modules/mysql-events');
var dsn = {
  host: "localhost",
  user: "root",
  password: "root",
  database: database
};

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: database
});

var result;
var i = 1;
var Eatery_Location = "ANC";

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://expensesreviewsystem-gh.firebaseio.com"
});

var db = admin.database();
//ref.remove();


var mysqlEventWatcher = MySQLEvents(dsn);
con.connect(function(err) {
var watcher = mysqlEventWatcher.add(
  'akash.student_takes',
  function (oldRow, newRow, event) {
     //row inserted 
    if (oldRow === null) {
      //insert code goes here 
	//console.log(newRow);
	var data = newRow.fields;
     if(data.ITEM_ID !== null){
	var item_id = data.ITEM_ID.toString();
	var BillId = data.BILL_ID.toString();
	var t_id = data.T_ID.toString();
	
  		if (err) throw err;
  			con.query("SELECT NAME FROM item where ITEM_ID = " + item_id, function (err, result, fields) {
    			if (err) throw err;
    			//console.log('result > ',result);
			var mittal = JSON.stringify(result);
			var json = JSON.parse(mittal);
			console.log(mittal);
			data.ITEM_ID = json[0].NAME.toString();
			var d = new Date("2015-03-25T12:00:00-05:30");
			d = data.DOR;
			console.log(d);
			d.setHours(d.getHours() + 5);
			d.setMinutes(d.getMinutes() + 30);
			console.log(data.BILL_ID);
			console.log(data.ITEM_ID);
			console.log(d.toUTCString());
			var dat = d.toISOString();
			var date = dat.substr(0,10) + "  " + dat.substr(11,8);
			console.log(date);
			var ref = db.ref(data.S_ID.toString() + "/" + date + "/" + Eatery_Location.toString() + "/" + BillId + "/" + t_id)
			ref.update({data});
			
  		});
	
     }
    }
 
     //row deleted 
    if (newRow === null) {
	//insert code goes here
	//console.log(oldRow);
	var data = oldRow.fields;
     if(data.ITEM_ID !== null){
	var BillId = data.BILL_ID.toString();
	var t_id = data.T_ID.toString();
	var d = new Date("2015-03-25T12:00:00-05:30");
	d = data.DOR;
	d.setHours(d.getHours() + 5);
	d.setMinutes(d.getMinutes() + 30);
	console.log(data.BILL_ID);
	console.log(data.ITEM_ID);
	console.log(d.toUTCString());
	var dat = d.toISOString();
	var date = dat.substr(0,10) + "  " + dat.substr(11,8);
	console.log(date);
	var ref = db.ref(data.S_ID.toString() + "/" + date + "/" + Eatery_Location.toString() + "/" + BillId + "/" + t_id)
	console.log(ref.toString());
	ref.remove();
    }
	
      //delete code goes here 
    }
 
     //row updated 
    if (oldRow !== null && newRow !== null) {
      //update code goes here 
    }
 
    //detailed event information 
    
  }
);
});