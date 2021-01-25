const express = require('express');
const user = require('./routes/user');
const comment = require('./routes/comment');
const mongoose = require('mongoose')
const MONGOURI = "mongodb://localhost:27017/commentdb"

const app = express();

mongoose.connect(process.env.MONGODB_URI || MONGOURI, {useNewUrlParser: true, useUnifiedTopology: true})
.then(() => {
    console.log('MongoDB Connected…');
}) 
.catch(err => console.log(err));

const con = mongoose.connection;

app.use(express.json());

app.use('/comment',comment);
app.use('/user',user);


const PORT = process.env.PORT || 4000;

app.listen(PORT, (req, res) => {
    console.log('Server Listening on port '+ PORT);
})
