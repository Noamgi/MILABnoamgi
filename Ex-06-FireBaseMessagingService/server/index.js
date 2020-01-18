const express = require('express')
const bodyParser = require('body-parser');
const request = require('request');
const admin = require('firebase-admin');

const PORT = 8080;
const alphaKey = '2M724GKFUTU88TZX';

const stockDefault = 'MSFT';
var app = express()
const serviceAccount = require('C:/Users/Noam Ginsberg/milabNoam/ex-06-74fc6-firebase-adminsdk-f1gtv-36234ab74d.json');
///const serviceAccount = require('/Users/Noam Ginsberg\milabNoam\ex-06-74fc6-firebase-adminsdk-f1gtv-36234ab74d.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://ex-06-74fc6.firebaseio.com"
});

app.use(express.json());

var registrationToken = '';

app.post('/stockPrice', (req, res, next) => {

    console.log("Post request to /stockPrice");

    registrationToken = req.body.token;
    let reqStock = req.body.stock || stockDefault;

    console.log(`the token is ${registrationToken} and the choosen stock is ${reqStock}`);

    var url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${reqStock}&apikey=${alphaKey}`;

    var globalQuote;
    var firstTime = 1;

    var oldPrice = 0;
    var newPrice = 0;

    function timeOut() {
        setTimeout(function() {
            firstTime = 0;
            console.log('check1'); //
            request(url, function(err, response, body) {
                console.log('check2');

                if (err) {
                    console.log('error:', error);
                    response.status(400).json({ err: "Failed sending request to AlphaVantage" });
                } else {
                    let stockDetails = JSON.parse(body);
                    let globalQuote = stockDetails["Global Quote"];
                    if (globalQuote != undefined) newPrice = globalQuote["05. price"];
                    let message = `Stock ${reqStock} current price is: ${newPrice}`;
                    console.log(message);

                    // if (oldPrice != newPrice) {
                    //     sendToFCM(registrationToken, reqStock, newPrice);
                    //     oldPrice = newPrice;
                    // }
                    sendToFCM(registrationToken, reqStock, newPrice);
                    oldPrice = newPrice;
                    console.log("check 3");
                }
            })
            timeOut();
        }, firstTime ? 1 : 15000)
    }
    timeOut;
});

app.post('/:user/token', (req, res, next) => {
    console.log(`Received save token request from ${req.params.user} for token=${token}`);
    if (!token) return res.status(400).json({ err: "missing token" });
    registrationToken = token;
    res.status(200).json({ msg: "saved ok" });
});

function sendToFCM(token, stock, price) {
    var payload = {
        data: {
            symbol: stock,
            price: price
        },
        notification: {
            body: `The price of ${stock} is ${price}`
        }
    };
    admin.messaging().sendToDevice(token, payload)
        .then(function(response) {
            console.log("Successfully sent message:", response);
        })
        .catch(function(error) {
            console.log("Error sending message:", error);
        });
}

//before
app.listen(PORT, () => console.log(`App listening on port ${PORT}!`))