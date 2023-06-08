const DB = require("./db.json");

const getAllArticles = () => {
    return DB.articles;
};

const getOneArticles = (articleId) => {
    const article = DB.articles.find((article) => article.id === articleId);
    if (!article) {
        return;
    }
    return article;
};

module.exports = { getAllArticles, getOneArticles };