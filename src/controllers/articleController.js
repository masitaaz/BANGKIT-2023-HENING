const articleService = require("../services/articleService");

const getAllArticles = (req, res) => {
    const allArticles = articleService.getAllArticles();
    res.send({ status: "OK", data: allArticles });
};

const getOneArticles = (req, res) => {
    const {
        params: { articleId },
    } = req;
    if (!articleId) {
        return;
    }
    const article = articleService.getOneArticles(articleId);
    res.send({ status: "OK", data: article });
};

module.exports = {
    getAllArticles,
    getOneArticles,
};