const AlphaKey = "2M724GKFUTU88TZX";
const alpha = require('alphavantage')({ key: AlphaKey });

const express = require('express')
const bodyParser = require('body-parser');
const FCM = require('fcm-push');
//const fs = require('fs');

const FCM_SERVER_KEY = 'AIzaSyDpnxTe2QeZBzulfvbsm5VNgNMNKKLBuNU';
const port = 8080

let app = express()
app.use(bodyParser.json());

let fcm = new FCM(FCM_SERVER_KEY);

let tokens = {}; // this should probably be in a database


app.post('/:user/token', (req, res, next) => {
    let token = req.body.token;
    console.log(`Received save token request from ${req.params.user} for token=${token}`);

    if (!token) return res.status(400).json({ err: "missing token" });

    tokens[req.params.user] = token;
    res.status(200).json({ msg: "saved ok" });
});



app.get('/stock', (req, res, next) => { //show   
    console.log('GET /');

    let stockName = 'FB'; //facebook
    let url;

    request(url, function(err, response, body) {
        if (err) {
            console.log('error: ', error);
            return res.error(err);
        } else {
            let stockPrice = JSON.parse(body)
            let message = `The recent price of ${stockName}'s stock is ${stockPrice}$`;
            console.log(message);
            return res.json(stockPrice);
        }
    });

});

/*
setInterval(() => {
    alpha.data.batch(`${company}`).then(priceValue => { 
        let sharePriceValue = `${priceValue['Stock Quotes'][0]['2. price']}`;
        console.log(sharePriceValue); // test2

      // sends the company share value to everyone listening to this port including the client.
        io.emit('userSharedACompany', {
             Company : `${company}`,
             Value : sharePriceValue
        }); 
    })
    .catch(err => {
        console.error("Error: " + err);
    });
}, 15000);
});
**/


//before
app.listen(port, () => console.log(`App listening on port ${port}!`))