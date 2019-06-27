const functions = require('firebase-functions');

var admin = require("firebase-admin");
var CronJob = require("cron").CronJob;
var cheerio = require('cheerio');
var request = require('request');
var express = require('express');
var app = express();

admin.initializeApp({
    apiKey: "AIzaSyAADa1e4P9WSU8xXcidzOaqrqN5DTrbJ4Q",
    authDomain: "gapana-3283b.firebaseapp.com",
    databaseURL: "https://gapana-3283b.firebaseio.com",
    projectId: "gapana-3283b",
    storageBucket: "gapana-3283b.appspot.com",
    messagingSenderId: "407432302370",    
});

// const messaging = firebase.messaging();
// messaging.usePublicVapidKey('BMcGSJvgOSDbL5PD16Z-33i8ZDFsJDrDjPTD-hE3rducy00L4YfTbSuj2JPqd_S05JIVHfdgT-BJLSX5eSLOOlg');

const d = new Date();
const now = `${d.getDate()}/${d.getMonth()+1}/${d.getFullYear()}`;
const job = new CronJob('0 */60 * * * *', ()=>{    
    console.log('Sending Notification...');
    scrap();
});
app.listen(3000, ()=> console.log('app listening on port 3000'));
app.get('/scrap', (req,res)=>{
    res.send('scrap begin...');
    console.log('scrap begin...');
    scrap();
    job.start();
})
scrap();
// sendFCMTest();

function scrap(url = 'http://www.bmkg.go.id/') {
    console.log('scrap begin...');
    request.get(url, (err,res,body)=>{
        let $ = cheerio.load(body);
        let dirasakan = $('#dirasakan').find($('.list-unstyled'));        
        var waktu, magnitude, latitude, longitude, lokasi;        
        
        dirasakan.each((i, elem)=>{
        
            if (i==0) {
                waktu = $(elem).find($('.waktu')).text();
                
                magnitude = $(elem).find($('.magnitude').parent()).contents().filter(function(){
                    return this.nodeType == 3;
                }).text();

                var coord = $(elem).find($('.koordinat').parent()).contents().filter(function(){
                    return this.nodeType == 3;
                }).text().split(' ');

                switch (coord[1]) {
                    case 'LU':
                        latitude = coord[0];
                        break;
                    case 'LS':
                        latitude = coord[0] *= -1;
                        break;                    
                }                
                
                switch (coord[3]) {
                    case 'BT':
                        longitude = coord[2];
                        break;
                    case 'BB':
                        longitude = coord[2] *= -1;
                        break;                    
                }
            }
            if (i==1) {
                lokasi = $(elem).find($('.lokasi').parent()).contents().filter(function(){
                    return this.nodeType == 3;
                }).text();
            }                                                
        })

        if (isSendFcm(waktu.split(',')[0].split(' '))) {      
            console.log('tidak aman');  
            console.log(`latitude ${typeof latitude.toString()}, longitude ${typeof longitude.toString()}, lokasi ${typeof lokasi}, waktu ${typeof waktu}, magnitude ${typeof magnitude}`);
                            
            sendFCM(latitude.toString(),longitude.toString(),lokasi,waktu,'Gempa Bumi',magnitude); 
        } else{
            console.log('aman');            
        }
    })
}

function isSendFcm(waktu) {    
    var bulan = ['Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni', 'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'];            
    // var dateScrap = `${parseInt(waktu[0])}/${bulan.indexOf(waktu[1])+1}/${waktu[2]}`
    var dateScrap = now;
    console.log(dateScrap);
    console.log(now);
    
    return now == dateScrap;
}

function sendFCM(lat,long,loc,date,type,magnitude) {
    console.log('send fcm');
    
    const messaging = admin.messaging();
    var message = {
        "topic": "bencana",
        "data": {
            'magnitude': magnitude,
            'lat': '-7.3735920',
            'long': '112.6389210',
            'loc': loc,
            'date': date,
            'type': type
        },        
    }
    console.log(`data ${message}`);
    
    messaging.send(message).then((res)=>{
        console.log(`successful ? ${res}`);    
    }).catch((err)=>{
        console.log(`failed ? ${err}`);
        
    })
}

function sendFCMTest() {
    console.log('send fcm test');
    
    const messaging = admin.messaging();
    var message = {
        "topic": "bencana",
        "data": {
            'magnitude': '2.4',
            'lat': '85.121',
            'long': '121.42869',
            'loc': 'loc',
            'date': 'date',
            'type': 'tsunami'
        },        
    }
    messaging.send(message).then((res)=>{
        console.log(`successful ? ${res}`);    
    }).catch((err)=>{
        console.log(`failed ? ${err}`);
        
    })
}

exports.scrapper = functions.https.onRequest(app);