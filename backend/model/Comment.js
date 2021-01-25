const mongoose = require('mongoose');

const commentSchema = new mongoose.Schema({
    pageNum: {
        type: Number,
        required: true,
    },
    username: {
        type: String,
        required: true
    },
    comment: {
        type: String,
        required: true
    },
    replies: [{
        username: String,
        reply: String,
    }],
    created: {
        type: Date,
        default: Date.now
    }

});

module.exports = mongoose.model('Comment',commentSchema);