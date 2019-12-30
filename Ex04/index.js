const express = require('express')
    //const bodyParser = require('body-parser');
const fs = require('fs');
let app = express()
const port = 8080

app.get('/tasks', (req, res) => { //show all tasks in tasks.json   
    console.log('GET /tasks');

    fs.readFile('./tasks.json', (err, content) => {
        if (err) {
            console.error(err);
            return
        }
        res.send(JSON.parse(content));
    });
});


app.post('/tasks/new', (req, res) => { //add task to tasks.json   
    console.log('POST /tasks/new');

    //get from the URL
    let taskId = req.query.id;
    let theTask = req.query.task;

    let updatedTasks;

    fs.readFile('./tasks.json', (err, content) => {
        if (err) {
            console.error(err);
            return
        }

        let myTasks = JSON.parse(content); //get the json into an object
        myTasks[taskId] = theTask; //add the task

        updatedTasks = JSON.stringify(myTasks);
        fs.writeFile('./tasks.json', updatedTasks, (err) => {
            if (err) {
                return err;
            } else {
                res.send('New Task Added');
            }
        });
    });
});

app.delete('/tasks/remove', (req, res) => { //remove task from tasks.json   
    console.log('DELETE /tasks/remove');

    //get the task id from the URL
    let taskId = req.query.id;

    fs.readFile('./tasks.json', (err, content) => {
        if (err) {
            console.error(err);
            return
        }

        let myTasks = JSON.parse(content); //get the json into an object
        delete myTasks[taskId]; //remove from the tasks list

        fs.writeFile('./tasks.json', JSON.stringify(myTasks), (err) => {
            if (err) {
                return err;
            } else {
                res.send('Task Deleted');
            }
        });
    });
});


//before
app.listen(port, () => console.log(`App listening on port ${port}!`))