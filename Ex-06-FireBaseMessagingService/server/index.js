const express = require('express');
const bodyParser = require('body-parser');
const request = require('request');
const admin = require('firebase-admin');

const PORT = 8080;
const alphaKey = '2M724GKFUTU88TZX';
const stockDefault = 'TEVA';

var app = express()
app.use(express.json());
const serviceAccount = require('C:/Users/Noam Ginsberg/milabNoam/server/ex-06-74fc6-firebase-adminsdk-f1gtv-36234ab74d.json');


admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://ex-06-74fc6.firebaseio.com"
});



app.get('/', (req, res) => {

    console.log("Got Get request");
    res.send("Hello, go to /stockprice for stock info");

});

var registrationToken = '';

app.post('/stockprice', (req, res, next) => {
    console.log("Got POST request to /stockprice");

    registrationToken = req.body.token;
    let reqStock = req.body.stock || stockDefault;

    console.log(`the token is ${registrationToken} and the choosen stock is ${reqStock}`);

    var url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${reqStock}&apikey=${alphaKey}`;

    var firstTime = 1;
    var updatePrice = 0;
    var price = 0;

    function timeout() {
        setTimeout(function() {
            firstTime = 0;
            request(url, function(err, response, body) {
                if (err) {
                    console.log('error:', error);
                    response.status(400).json({ err: "Failed sending request to AlphaVantage" });
                } else {
                    let stockInformation = JSON.parse(body);
                    let globalQuote = stockInformation["Global Quote"];
                    if (globalQuote != undefined)
                        updatePrice = globalQuote["05. price"];
                    let message = `The current price of ${reqStock} stock is: ${updatePrice}`;
                    console.log(message);

                    // if (price != updatePrice) {
                    price = updatePrice;
                    sendToFCM(registrationToken, reqStock, price);
                    //}

                }
            })
            timeout();
        }, firstTime ? 1 : 15000)
    }
    timeout();
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



app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});