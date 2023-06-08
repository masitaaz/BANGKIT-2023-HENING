const Article = require("../database/Article");

const getAllArticles = () => {
    const allArticles = Article.getAllArticles();
    return allArticles;
};

const getOneArticles = (articleId) => {
    const article = Article.getOneArticles(articleId);
    return article;
};

module.exports = {
    getAllArticles,
    getOneArticles,
};