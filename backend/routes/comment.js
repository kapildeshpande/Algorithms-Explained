const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const Comment = require("../model/Comment");

router.get('/:id', auth, async (req,res) => {
    try {
        var allComments = await Comment.find({pageNum: parseInt(req.params.id)});
        var commentObj = {};
        commentObj["comments"] = allComments;
        res.json(commentObj);
    } catch (err) {
        res.send(err);
    }
})

router.post("/",auth, async (req, res) => {
    var pageNum = req.body.pageNum;
    var username = req.body.username;
    var comment = req.body.comment;
    try {
        var post = new Comment({pageNum,username, comment});
        const a1 = await post.save();
        res.json(a1);
    } catch (e) {
      res.send({ message: "Error in Fetching user" });
    }
  });

  router.post("/:id", auth, async (req, res) => {
    var id = req.params.id;
    var pageNum = req.body.pageNum;
    var username = req.body.username;
    var reply = req.body.comment;
    try {
        var comment = await Comment.findById(id);
        comment.replies.push({pageNum, username, reply});
        const a1 = await comment.save();
        res.json(a1)
    } catch (e) {
      res.send({ message: "Error in Fetching user" });
    }
  });


module.exports = router;