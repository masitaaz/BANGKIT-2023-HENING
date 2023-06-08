const express = require("express"); 

const bodyParser = require("body-parser");
const v1ArticleRouter = require("./src/v1/routes/articleRoutes");

const app = express(); 
const PORT = process.env.PORT || 3000; 

app.use(bodyParser.json());
// For testing purposes 
app.use("/api/v1/articles", v1ArticleRouter);

app.listen(PORT, () => { 
    console.log(`API is listening on port ${PORT}`); 
});